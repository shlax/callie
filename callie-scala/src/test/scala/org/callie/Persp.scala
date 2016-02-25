package org.callie

import org.callie.math.{Vector3, Matrix4}

// http://www.songho.ca/opengl/gl_projectionmatrix.html
// http://www.songho.ca/opengl/gl_transform.html
object Persp extends App{

  //val r = 1f
  val l = -1f
  val r = 1f

  val b = -1f
  val t = 1f
//
  val f = 1000f
  val n = 1f

  val pm = Matrix4(2f * n / (r - l), 0f, (r + l) / (r - l), 0f,
                   0f, 2f * n / (t - b), (t + b) / (t - b), 0f,
                   0f, 0f, -(f + n) / (f - n), -(2f * f * n) / (f - n),
                   0f, 0f, -1f, 0f)

  println(pm)

  def tst(i: Vector3) {
    val q = Vector3()
    pm.apply(i, q)
    println(i + " -> " + q)
  }

  tst(Vector3(0, 0, -0.5f))
  tst(Vector3(0, 0, -1f))
  tst(Vector3(0, 0, -10f))
  tst(Vector3(0, 0, -15f))

  println()

  tst(Vector3(1, 1, -1))
  tst(Vector3(1, -1, -1))
  tst(Vector3(-1, 1, -1))
  tst(Vector3(-1, -1, -1))

  println()

  tst(Vector3(2, 2, -2))
  tst(Vector3(-2, 2, -2))
  tst(Vector3(2, -2, -2))
  tst(Vector3(-2, -2, -2))

  println()

  tst(Vector3(3, 3, -5))
  tst(Vector3(-3, 3, -5))
  tst(Vector3(3, -3, -5))
  tst(Vector3(-3, -3, -5))
}
