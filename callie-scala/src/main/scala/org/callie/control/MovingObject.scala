package org.callie.control

import org.callie.input.{Camera, Inputs, TrackingObject}
import org.callie.map.Map25
import org.callie.math.{Angle, AngleRotation, Matrix4, Vector2, Vector3}
import java.lang.{Float => jFloat}

import org.callie.ringing.Transformation

trait MovingControl extends Transformation{

  def toSpeed(v:Float):Unit

  def apply(delta:Float):MovingState
}

object MovingObject{
  def apply(map:Map25, height:Float, pos2D: Vector2 = Vector2(), lookFrom:Float = 0f): MovingObject = new MovingObject(map, height, pos2D, lookFrom)
}

class MovingObject(map:Map25, height:Float, pos2D: Vector2 = Vector2(), lookFrom:Float = 0f) extends TrackingObject with MovingControl{
  val epsilon = 5f
  val angle = Angle(lookFrom)

  val acceleration = 15f
  val deAcceleration = acceleration * 2f

  var maxSpeed = 0f
  override def toSpeed(v: Float):Unit = {
    maxSpeed = v
  }

  var speed = 0f

  override val position = Vector3()

  override val transformation = Matrix4()
  override val normalTransformation = Matrix4()

  normalTransformation.rotY(Angle.PI1 - angle())
  var lastZ:Float = map.apply(pos2D)
  calculate(lastZ)

  def calculate(z:Float):Unit={
    lastZ = z

    position.x = pos2D.x
    position.y = z
    position.z = pos2D.y

    transformation.set(position)
    transformation.mul(normalTransformation)

    position.y += height
  }

  def calculate():Unit={
    transformation.set(pos2D.x, lastZ, pos2D.y)
    transformation.mul(normalTransformation)
  }

  override def apply(delta:Float):MovingState={
    var state = MovingState.STAND
    var rotate = false

    if(Inputs.keyW || Inputs.mouse2) { // accelerate
      val ar = angle.rotateTo(Camera.angY, delta * epsilon)
      if (ar != AngleRotation.ZERO) {
        rotate = true
        normalTransformation.rotY(Angle.PI1 - angle())
        state = if (ar == AngleRotation.POSITIVE) MovingState.ROTATE_POSITIVE else MovingState.ROTATE_NEGATIVE
      }
    }

    if(Inputs.keyW && maxSpeed != 0f) {
      speed += delta * acceleration
      if(speed > maxSpeed) speed = maxSpeed
    }else if(speed > 0f){  // stop
      speed -= delta * deAcceleration
      if(speed < 0f) speed = 0f
    }

    if(speed != 0f) {
      val rot = angle()

      val sd = speed * delta
      pos2D.x += sd * Math.sin(rot).asInstanceOf[Float]
      pos2D.y -= sd * Math.cos(rot).asInstanceOf[Float]

      val z = map.fast(pos2D)

      if (!jFloat.isNaN(z)) calculate(z)
      else { // collision
        pos2D.x = position.x
        pos2D.y = position.z

        speed = 0f
      }
    }else if(rotate){
      calculate()
    }

    if(speed > 0f) MovingState.RUN
    else state
  }

}
