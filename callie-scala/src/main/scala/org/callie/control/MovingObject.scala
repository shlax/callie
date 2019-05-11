package org.callie.control

import org.callie.input.{Camera, Inputs, TrackingObject}
import org.callie.map.Map25
import org.callie.math.{Angle, Matrix4, Vector2, Vector3}
import org.callie.ringing.{AnimState, JoinState}
import java.lang.{Float => jFloat}

class MovingObject(val map:Map25, height:Float, val pos2D: Vector2 = Vector2(), lookFrom:Float = 0f) extends TrackingObject with JoinState{
  val epsilon = 10f
  val angle = Angle(lookFrom)

  val acceleration = 15f
  val deAcceleration = acceleration * 2f

  override val maxRunSpeed = 2.5f
  var speed = 0f

  override val position = Vector3()

  override val transformation = Matrix4()
  override val normalTransformation = Matrix4()

  normalTransformation.rotY(Angle.PI1 - angle())
  calculate(map.apply(pos2D))

  def calculate(z:Float){
    position.x = pos2D.x
    position.y = z
    position.z = pos2D.y

    transformation.set(position)
    transformation.mul(normalTransformation)

    position.x *= -1f
    position.y = (-1f * position.y) - height
    position.z *= -1f
  }

  override def apply(delta:Float){
    if(Inputs.w){ // accelerate
      if(angle.rotateTo(Camera.angY, delta * epsilon)){
        normalTransformation.rotY(Angle.PI1 - angle())
      }

      speed += delta * acceleration
      if(speed > maxRunSpeed) speed = maxRunSpeed

    }else if(speed > 0f){  // stop
      speed -= delta * deAcceleration
      if(speed < 0f) speed = 0f
    }

    if(speed > 0f) {
      val rot = angle()

      val sd = speed * delta
      pos2D.x += sd * Math.sin(rot).asInstanceOf[Float]
      pos2D.y -= sd * Math.cos(rot).asInstanceOf[Float]

      val z = map.apply(pos2D)

      if (!jFloat.isNaN(z)) calculate(z)
      else { // collision
        pos2D.x = -1f * position.x
        pos2D.y = -1f * position.z

        speed = 0f
      }
    }

  }

  // JoinState
  override def state() = if(Inputs.w && speed > 0f) AnimState.RUN else AnimState.STAND

}
