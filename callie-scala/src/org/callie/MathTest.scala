package org.callie

import org.callie.math.Matrix4
import javax.vecmath.Matrix4f
import scala.util.Random

object MathTest extends App{
  val rand = new Random()
  def rnd() = rand.nextFloat() * 100f - 50f

  for(i <- 0 until 10000000){
    val m = Matrix4(rnd(), rnd(), rnd(), rnd(),
                    rnd(), rnd(), rnd(), rnd(),
                    rnd(), rnd(), rnd(), rnd(),
                    rnd(), rnd(), rnd(), rnd())

    val q = Matrix4(m.m00, m.m01, m.m02, m.m03,
                    m.m10, m.m11, m.m12, m.m13,
                    m.m20, m.m21, m.m22, m.m23,
                    m.m30, m.m31, m.m32, m.m33)

    val n = new Matrix4f(m.m00, m.m01, m.m02, m.m03,
                         m.m10, m.m11, m.m12, m.m13,
                         m.m20, m.m21, m.m22, m.m23,
                         m.m30, m.m31, m.m32, m.m33)

    m.inverse().transpose()
    n.invert(); n.transpose()

    def tst(a:Float, b:Float, msg:String){
      val s = Math.abs(a-b)
      val h = (Math.abs(a)+Math.abs(b))/2f
      assert( s/h < 0.1f || s < 0.1f, ""+i+"->"+msg+" "+a+" "+b+"\n\n"+q+"\n\n"+m+"\n\n"+n)
    }

    tst(m.m00, n.m00, "m00:"); tst(m.m01, n.m01, "m01:"); tst(m.m02, n.m02, "m02:"); tst(m.m03, n.m03, "m03:")
    tst(m.m10, n.m10, "m10:"); tst(m.m11, n.m11, "m11:"); tst(m.m12, n.m12, "m12:"); tst(m.m13, n.m13, "m13:")
    tst(m.m20, n.m20, "m20:"); tst(m.m21, n.m21, "m21:"); tst(m.m22, n.m22, "m22:"); tst(m.m23, n.m23, "m23:")
    tst(m.m30, n.m30, "m30:"); tst(m.m31, n.m31, "m31:"); tst(m.m32, n.m32, "m32:"); tst(m.m33, n.m33, "m33:")


  }
}
