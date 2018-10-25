package org.callie

import org.callie.math._

object RayTst extends App{

  val i = new TriangleIntersect()

  val r = Ray(Vector3(0, 0, 0), Vector3(0, 0, -1))

  val s1 = new Sphere(Vector3(0, 0, -1), 0.5f)
  println(s1.intersect(r))

  val s2 = new Sphere(Vector3(0, 1, -1), 0.5f)
  println(s2.intersect(r))

  val t1 = Triangle( Vector3(-1, -1, -1), Vector3(1, -1, -1), Vector3(0, 1, -1) )
  println(i.intersect(r, t1))

  val t2 = Triangle( Vector3(0, -2, -1), Vector3(-3, 1, -1), Vector3(-3, -1, -1) )
  println(i.intersect(r, t2))

}
