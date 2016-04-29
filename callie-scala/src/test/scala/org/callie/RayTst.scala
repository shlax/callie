package org.callie

import org.callie.math.{Ray, Sphere, Triangle, Vector3}

object RayTst extends App{

  val r = Ray(Vector3(0, 0, 0), Vector3(0, 0, -1))

  val s1 = Sphere(Vector3(0, 0, -1), 0.5f)
  println(r.intersect(s1))

  val s2 = Sphere(Vector3(0, 1, -1), 0.5f)
  println(r.intersect(s2))

  val t1 = Triangle( Vector3(-1, -1, -1), Vector3(1, -1, -1), Vector3(0, 1, -1) )
  println(r.intersect(t1))

  val t2 = Triangle( Vector3(0, -2, -1), Vector3(-3, 1, -1), Vector3(-3, -1, -1) )
  println(r.intersect(t2))

}
