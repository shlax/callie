package org.callie.math

object Angle{
  val PI1 = Math.PI.asInstanceOf[Float]
  val PI2 = (Math.PI*2d).asInstanceOf[Float]

  def apply(min:Float = -Angle.PI2, max:Float = Angle.PI2) = new Angle(min, max)
}

class Angle(min:Float = -Angle.PI2, max:Float = Angle.PI2) {
  var angle:Float = 0f

  def apply() = angle

  def update(v:Float){
    var tmp = v
    while (v < min) tmp += Angle.PI2
    while (v > max) tmp -= Angle.PI2
    angle = tmp
  }
}
