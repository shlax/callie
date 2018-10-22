package org.callie.control

import org.callie.input.{Camera, Inputs, TrackingObject}
import org.callie.map.Map25
import org.callie.math.{Angle, Matrix4, Vector2, Vector3}
import org.callie.ringing.Transformation

class MovingObject(val map:Map25, height:Float, val pos2D: Vector2 = Vector2(), lookFrom:Float = 0f) extends TrackingObject with Transformation {
  val epsilon = 10f
  val angle = Angle(lookFrom)

  val acceleration = 0.3f
  val deAcceleration = 0.7f

  val vMax = 3f
  var speed = 0f

  override val position = Vector3()

  override val transformation = Matrix4()
  override val normalTransformation = Matrix4()

  normalTransformation.rotY(Angle.PI1 - angle())
  calculate(map.apply(pos2D).get)

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

  def apply(delta:Float){
    if(Inputs.w){ // accelerate
      if(angle.rotateTo(Camera.angY, delta * epsilon)){
        normalTransformation.rotY(Angle.PI1 - angle())
      }

      speed += delta * acceleration
      if(speed > vMax) speed = vMax

    }else if(speed > 0f){  // stop
      speed -= delta * deAcceleration
      if(speed < 0f) speed = 0f
    }

    if(speed > 0f) {
      val rot = angle()

      pos2D.x += speed * Math.sin(rot).asInstanceOf[Float]
      pos2D.y -= speed * Math.cos(rot).asInstanceOf[Float]

      val z = map.apply(pos2D)

      if (z.isDefined) calculate(z.get)
      else { // collision
        pos2D.x = -1f * position.x
        pos2D.y = -1f * position.z

        speed = 0f
      }
    }

  }
}
