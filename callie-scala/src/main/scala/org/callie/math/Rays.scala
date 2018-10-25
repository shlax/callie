package org.callie.math

class Intersect{

  val e1 = Vector3()
  val e2 = Vector3()

  val vp = Vector3()
  val vq = Vector3()
  val vt = Vector3()

  /** https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm */
  def intersect(r:Ray, t: Triangle) = {
    e1.sub(t.b, t.a)
    e2.sub(t.c, t.a)

    vp.cross(r.dir, e2)

    val det = e1.dot(vp)
    if(det == 0) None
    else{
      val iDet = 1f/det

      vt.sub(r.point, t.a)

      val u = vt.dot(vp) * iDet
      if(u < 0 || u > 1) None
      else{
        vq.cross(vt, e1)

        val v = r.dir.dot(vq) * iDet
        if(v < 0 || u + v > 1) None
        else Some(e2.dot(vq) * iDet)
      }
    }
  }

  def intersect(r:Ray, s:Sphere) = {
    val a = r.dir.len
    if(a == 0) false
    else{
      val b = 2f * ( r.point.dot(r.dir) - r.dir.dot(s.center) )
      val c = vq.sub(s.center, r.point).len - s.radius * s.radius
      b*b - 4f*a*c >= 0
    }
  }

  // http://woo4.me/wootracer/cylinder-intersection/
  def intersect(r:Ray, s:Cylinder) = {

  }

  def intersect(r:Ray, b:Box) = {
    // rotate

    val dfx = 1f/r.dir.x
    val dfy = 1f/r.dir.y
    val dfz = 1f/r.dir.z

    val t1 = (b.center.x - b.xSize - r.point.x) * dfx
    val t2 = (b.center.x + b.xSize - r.point.x) * dfx
    val t3 = (b.center.y - b.ySize - r.point.y) * dfy
    val t4 = (b.center.y + b.ySize - r.point.y) * dfy
    val t5 = (b.center.z - b.zSize - r.point.z) * dfz
    val t6 = (b.center.z + b.zSize - r.point.z) * dfz

    val tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6))
    val tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6))

    if (tmax < 0) false // if tmax < 0, ray (line) is intersecting AABB, but the whole AABB is behind us
    else if (tmin > tmax) false // if tmin > tmax, ray doesn't intersect AABB
    else true
  }

}

case class Ray(point: Vector3, dir:Vector3)

case class Sphere(center:Vector3, radius:Float)
case class Triangle(a:Vector3, b:Vector3, c:Vector3)

case class Box(center:Vector3, xSize:Float, ySize:Float, zSize:Float, rotX:Float = 0f, rotY:Float = 0f, rotZ:Float = 0f)
case class Cylinder(center:Vector3, radius:Float, height:Float, rotX:Float = 0f, rotY:Float = 0f, rotZ:Float = 0f)
