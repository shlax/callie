package org.callie.math

case class Ray(point: Vector3, dir:Vector3){

  val e1 = Vector3()
  val e2 = Vector3()

  val vp = Vector3()
  val vq = Vector3()
  val vt = Vector3()

  /** https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm */
  def intersect(t: Triangle) = {
    e1.sub(t.b, t.a)
    e2.sub(t.c, t.a)

    vp.cross(dir, e2)

    val det = e1.dot(vp)
    if(det == 0) Float.NaN
    else{
      val iDet = 1f/det

      vt.sub(point, t.a)

      val u = vt.dot(vp) * iDet
      if(u < 0 || u > 1) Float.NaN
      else{
        vq.cross(vt, e1)

        val v = dir.dot(vq) * iDet
        if(v < 0 || u + v > 1) Float.NaN
        else e2.dot(vq) * iDet
      }
    }
  }

  def intersect(s:Sphere) = {
    val a = dir.len
    if(a == 0) false
    else{
      val b = 2f * ( point.dot(dir) - dir.dot(s.center) )
      val c = vq.sub(s.center, point).len - s.radius * s.radius
      b*b - 4f*a*c >= 0
    }
  }

}

case class Sphere(center:Vector3, radius:Float)
case class Triangle(a:Vector3, b:Vector3, c:Vector3)
