package org.callie.math

case class Ray(point: Vector3, dir:Vector3)

case class Triangle(a:Vector3, b:Vector3, c:Vector3)

class TriangleIntersect{

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
    if(det == 0) Float.NaN
    else{
      val iDet = 1f/det

      vt.sub(r.point, t.a)

      val u = vt.dot(vp) * iDet
      if(u < 0 || u > 1) Float.NaN
      else{
        vq.cross(vt, e1)

        val v = r.dir.dot(vq) * iDet
        if(v < 0 || u + v > 1) Float.NaN
        else e2.dot(vq) * iDet
      }
    }
  }

}

trait Intersection{
  def intersect(r:Ray): Float
}

object Sphere{
  def apply(center: Translation, radius: Float) = new Sphere(center, radius)
}

class Sphere(center:Translation, radius:Float) extends Intersection{
  val radius2 = radius * radius

  override def intersect(r:Ray) = {
    val sx = r.point.x - center.x
    val sy = r.point.y - center.y
    val sz = r.point.z - center.z

    val a = r.dir.dot(r.dir)
    val b = 2f * (sx * r.dir.x + sy * r.dir.y + sz * r.dir.z)
    val c = (sx*sx + sy*sy + sz*sz) - radius2

    val d = b*b - 4f*a*c

    if(d < 0f) Float.NaN
    else{
      val sq = Math.sqrt(d).asInstanceOf[Float]
      val t1 = -b + sq
      val t2 = -b - sq

      if (t1 >= 0f && t2 <= 0f) t1 / (2f * a)
      else if (t2 >= 0f && t1 <= 0f) t2 / (2f * a)
      else if(t1 >= 0f && t2 >= 0f) Math.min(t1, t2) / (2f * a)
      else Float.NaN // je za
    }
  }

}

object Box{
  def apply(trans: Matrix4, xSize: Float, ySize: Float, zSize: Float ) = new Box(trans, xSize, ySize, zSize)
}

// https://github.com/opengl-tutorials/ogl/blob/master/misc05_picking/misc05_picking_custom.cpp
class Box(trans:Matrix4, xSize:Float, ySize:Float, zSize:Float) extends Intersection{

  override def intersect(r:Ray): Float = {
    val dx = trans.x - r.point.x
    val dy = trans.y - r.point.y
    val dz = trans.z - r.point.z

    var tMin = 0f
    var tMax = Float.MaxValue

    { // x >>
      val ax = trans.m00
      val ay = trans.m10
      val az = trans.m20

      val e = dx * ax + dy * ay + dz * az
      val f = r.dir.x * ax + r.dir.y * ay + r.dir.z * az

      if (Math.abs(f) > 0f) {
        var t1 = (e - xSize) / f
        var t2 = (e + xSize) / f

        if (t1 > t2) {
          val w = t1
          t1 = t2
          t2 = w
        }

        if (t2 < tMax) tMax = t2
        if (t1 > tMin) tMin = t1

        if (tMax < tMin) return Float.NaN
      } else {
        if (-e - xSize > 0f || -e + xSize < 0f) return Float.NaN
      }
    } // << x
    { // y >>
      val ax = trans.m01
      val ay = trans.m11
      val az = trans.m21

      val e = dx * ax + dy * ay + dz * az
      val f = r.dir.x * ax + r.dir.y * ay + r.dir.z * az

      if (Math.abs(f) > 0f) {
        var t1 = (e - ySize) / f
        var t2 = (e + ySize) / f

        if (t1 > t2) {
          val w = t1
          t1 = t2
          t2 = w
        }

        if (t2 < tMax) tMax = t2
        if (t1 > tMin) tMin = t1

        if (tMax < tMin) return Float.NaN
      } else {
        if (-e - ySize > 0f || -e + ySize < 0f) return Float.NaN
      }
    } // << y
    { // z >>
      val ax = trans.m02
      val ay = trans.m12
      val az = trans.m22

      val e = dx * ax + dy * ay + dz * az
      val f = r.dir.x * ax + r.dir.y * ay + r.dir.z * az

      if (Math.abs(f) > 0f) {
        var t1 = (e - zSize) / f
        var t2 = (e + zSize) / f

        if (t1 > t2) {
          val w = t1
          t1 = t2
          t2 = w
        }

        if (t2 < tMax) tMax = t2
        if (t1 > tMin) tMin = t1

        if (tMax < tMin) return Float.NaN
      } else {
        if (-e - zSize > 0f || -e + zSize < 0f) return Float.NaN
      }
    } // << z

    tMin
  }
  
}

