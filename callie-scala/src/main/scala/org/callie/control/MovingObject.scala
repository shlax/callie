package org.callie.control

import org.callie.input.{Camera, Inputs, TrackingObject}
import org.callie.map.Map25
import org.callie.math.{Angle, Matrix4, Vector2, Vector3}

class MovingObject(map:Map25, height:Float) extends TrackingObject{
  val epsilon = 10f
  var angle = Angle()

  val acceleration = 0.3f
  val deAcceleration = 0.6f

  val vMax = 3f
  var speed = 0f

  val position2D = Vector2()
  override val position = Vector3(-position2D.x, -height, -position2D.y)

  val location = Matrix4()
  val rotation = Matrix4()

  def update(delta:Float){
    if(Inputs.w){ // accelerate
      if(angle.rotateTo(Camera.angY, delta * epsilon)) rotation.rotY(Angle.PI1 - angle())

      speed += delta * acceleration
      if(speed > vMax) speed = vMax

    }else if(speed > 0){  // stop
      speed -= delta * deAcceleration
      if(speed < 0f) speed = 0f
    }

    if(speed > 0) {
      val rot = angle()

      position2D.x += speed * Math.sin(rot).asInstanceOf[Float]
      position2D.y -= speed * Math.cos(rot).asInstanceOf[Float]

      val z = map.apply(position2D)

      if (z.isDefined) { // no collision
        position.x = position2D.x
        position.y = z.get
        position.z = position2D.y

        location.set(position)
        location.mul(rotation)

        position.x *= -1
        position.y -= height
        position.z *= -1
      } else { // collision
        position2D.x = -1 * position.x
        position2D.y = -1 * position.z

        speed = 0
      }
    }

  }
}
