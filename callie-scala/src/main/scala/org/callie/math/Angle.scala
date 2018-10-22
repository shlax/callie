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

  def rotateTo(to:Angle, epsilon:Float)= {
    if(angle != to.angle) {
      val t = to.toPI()
      val f = toPI()

      val ft = Math.abs(f - t)
      val tf = Math.abs(ft - Angle.PI2)

      if (ft <= tf) {
        if(ft <= epsilon){
          angle = to.angle
        }else {
          if (t <= f) angle -= epsilon
          else angle += epsilon

          if (angle < min) angle += Angle.PI2
          if (angle > max) angle -= Angle.PI2
        }
      } else {
        if(tf <= epsilon){
          angle = to.angle
        }else {
          if (t > f) angle -= epsilon
          else angle += epsilon

          if (angle < min) angle += Angle.PI2
          if (angle > max) angle -= Angle.PI2
        }
      }

      true
    }else false
  }

  override def toString = "Angle("+angle+")"
}
