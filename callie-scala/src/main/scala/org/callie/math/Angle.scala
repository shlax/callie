package org.callie.math

object Angle{
  val PI5 = (Math.PI/2d).asInstanceOf[Float]
  val PI1 = Math.PI.asInstanceOf[Float]
  val PI2 = (Math.PI*2d).asInstanceOf[Float]

  def apply(start:Float = 0, min:Float = -Angle.PI2, max:Float = Angle.PI2) = new Angle(start, min, max)
}

class Angle(start:Float = 0, min:Float = -Angle.PI2, max:Float = Angle.PI2) {
  var angle:Float = start

  def apply() = angle

  def update(v:Float):Unit={
    var tmp = v
    if(tmp < min) tmp += Angle.PI2
    if(tmp > max) tmp -= Angle.PI2
    angle = tmp
  }

  def toPI() = {
    val a = angle
    if(a < -Angle.PI1) a + Angle.PI2
    else if(a > Angle.PI1) a - Angle.PI2
    else a
  }

  def rotateTo(to:Angle, epsilon:Float):AngleRotation= {
    if(angle != to.angle) {
      val t = to.toPI()
      val f = toPI()

      val ft = Math.abs(f - t)
      val tf = Math.abs(ft - Angle.PI2)

      if (ft <= tf) {
        if(ft <= epsilon){
          angle = to.angle
          if (t <= f) AngleRotation.NEGATIVE else AngleRotation.POSITIVE
        }else {
          val ret = if (t <= f) {
            angle -= epsilon
            AngleRotation.NEGATIVE
          }else{
            angle += epsilon
            AngleRotation.POSITIVE
          }

          if (angle < min) angle += Angle.PI2
          if (angle > max) angle -= Angle.PI2

          ret
        }
      } else {
        if(tf <= epsilon){
          angle = to.angle
          if (t > f) AngleRotation.NEGATIVE else AngleRotation.POSITIVE
        }else {
          val ret = if (t > f){
            angle -= epsilon
            AngleRotation.NEGATIVE
          }else{
            angle += epsilon
            AngleRotation.POSITIVE
          }

          if (angle < min) angle += Angle.PI2
          if (angle > max) angle -= Angle.PI2

          ret
        }
      }
    }else AngleRotation.ZERO
  }

  override def toString = "Angle("+angle+")"
}
