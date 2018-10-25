package org.callie

import org.callie.math._

object RayTst extends App{

  val i = new Intersect()

  val r = Ray(Vector3(0, 0, 0), Vector3(0, 0, -1))

  val s1 = Sphere(Vector3(0, 0, -1), 0.5f)
  println(i.intersect(r, s1))

  val s2 = Sphere(Vector3(0, 1, -1), 0.5f)
  println(i.intersect(r, s2))

  val t1 = Triangle( Vector3(-1, -1, -1), Vector3(1, -1, -1), Vector3(0, 1, -1) )
  println(i.intersect(r, t1))

  val t2 = Triangle( Vector3(0, -2, -1), Vector3(-3, 1, -1), Vector3(-3, -1, -1) )
  println(i.intersect(r, t2))

}
