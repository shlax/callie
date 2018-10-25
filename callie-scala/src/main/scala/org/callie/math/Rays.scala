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

}

object Sphere{
  def apply(center: Vector3, radius: Float) = new Sphere(center, radius)
}

class Sphere(center:Vector3, radius:Float){
  val radius2 = radius * radius

  def intersect(r:Ray) = {
    val a = r.dir.len
    if(a == 0) false
    else{
      val b = 2f * ( r.point.dot(r.dir) - r.dir.dot(center) )
      val dx = center.x - r.point.x
      val dy = center.y - r.point.y
      val dz = center.z - r.point.z
      val c =  Math.sqrt(dx*dx + dy*dy + dz*dz) - radius2
      //val c = vq.sub(s.center, r.point).len - s.radius * s.radius
      b*b - 4f*a*c >= 0
    }
  }

}

object Box{
  def apply(xSize: Float, ySize: Float, zSize: Float, trans: Matrix4) = new Box(xSize, ySize, zSize, trans)
}

class Box(xSize:Float, ySize:Float, zSize:Float, trans:Matrix4){

  def intersect(r:Ray) = {

  }
  
}

