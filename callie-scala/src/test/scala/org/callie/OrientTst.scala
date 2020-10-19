package org.callie

import org.callie.math.{Matrix4, Vector3}

object OrientTst extends App{

  val vec1 = Vector3(0, 0, -1)
  val vec2 = Vector3(0.1f, -0.1f, -1)

  val m = Matrix4(5, 6, 7)
  m.mul(Matrix4.rotX(0.3f))
  m.mul(Matrix4.rotY(0.2f))
  m.mul(Matrix4.rotZ(-0.1f))

  val v1 = m.apply(vec1)
  val v2 = m.apply(vec2)

  println(v1)
  println(v2)

  val i = m.inverse()

  val i1 = i.apply(v1)
  val i2 = i.apply(v2)

  println(i1)
  println(i2)

}
