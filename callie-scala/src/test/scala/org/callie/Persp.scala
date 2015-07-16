package org.callie

import org.callie.math.{Vector3, Matrix4}

// http://www.songho.ca/opengl/gl_projectionmatrix.html
object Persp extends App{

  val r = 1f
  val t = 1f

  val f = -3f
  val n = -1f

  val pm = Matrix4(n/r, 0f, 0f, 0f,
                   0f, n/t, 0f, 0f,
                   0f, 0f, -(f+n)/(f-n), -2f*f*n/(f-n),
                   0f, n/t, -1f, 0f)


  def tst(i: Vector3) {
    val q = Vector3()
    pm.apply(i, q)
    println(q + " -> " + q.div(i.z))
  }

  tst(Vector3(1, 1, 1))
  tst(Vector3(1, -1, 1))
  tst(Vector3(-1, 1, 1))
  tst(Vector3(-1, -1, 1))

  println()

  tst(Vector3(3, 3, 3))
  tst(Vector3(-3, 3, 3))
  tst(Vector3(3, -3, 3))
  tst(Vector3(-3, -3, 3))
}
