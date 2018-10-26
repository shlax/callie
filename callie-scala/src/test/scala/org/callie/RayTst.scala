package org.callie

import org.callie.math._

object RayTst extends App{

  val r1 = Ray(Vector3(0, 0, 0), Vector3(0, 0, -1))
  val r2 = Ray(Vector3(0, 0, 0), Vector3(0, 0, 1))

  // Box
  val b1 = Box(Matrix4(0, 0, -1), 0.5f, 0.5f, 0.5f)
  println(b1.intersect(r1))
  println(b1.intersect(r2))

  val b2 = Box(Matrix4(0, 0, 1), 0.5f, 0.5f, 0.5f)
  println(b2.intersect(r1))
  println(b2.intersect(r2))

  println()

  // Sphere
  val s1 = new Sphere(Vector3(0, 0, -1), 0.5f)
  println(s1.intersect(r1))
  println(s1.intersect(r2))

  val s11 = new Sphere(Vector3(0, 0, 1), 0.5f)
  println(s11.intersect(r1))
  println(s11.intersect(r2))

  println()

  val i = new TriangleIntersect()

  val t1 = Triangle( Vector3(-1, -1, -1), Vector3(1, -1, -1), Vector3(0, 1, -1) )
  println(i.intersect(r1, t1))

  val t2 = Triangle( Vector3(0, -2, -1), Vector3(-3, 1, -1), Vector3(-3, -1, -1) )
  println(i.intersect(r1, t2))

}
