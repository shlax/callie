package org.callie.polar

import org.callie.control.{MovingControl, MovingState}
import org.callie.input.TrackingObject
import org.callie.math.{Angle, Matrix4, Vector3}

class PolarCoordinates extends TrackingObject with MovingControl{

  override val position = Vector3()

  var a = Angle()
  var r:Float = 0f

  var z:Float = 0f

  override val transformation = Matrix4()
  override val normalTransformation = Matrix4()

  override def toSpeed(v: Float): Unit = {
  }

  override def apply(delta: Float): MovingState = {
    MovingState.STAND
  }
}
