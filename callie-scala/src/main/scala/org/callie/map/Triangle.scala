package org.callie.map

import org.callie.math.{Vector3, Vector2}

class Triangle25[T](val a : Vector3, val b : Vector3, val c : Vector3, val near: Array[Triangle25[T]], val far: Array[Triangle25[T]], val data:T) {

  val ab = Vector2(b.x-a.x, b.z-a.z)
  val bc = Vector2(c.x-b.x, c.z-b.z)
  val ca = Vector2(a.x-c.x, a.z-c.z)
  
  val (ta, tb, tc, td) = {
    val tmp = Vector3.cross( Vector3(c.x-a.x, c.y-a.y, c.z-a.z ), Vector3(b.x-a.x, b.y-a.y, b.z-a.z) )
	  (tmp.x, tmp.y, tmp.z, -((tmp.x*c.x)+(tmp.y*c.y)+(tmp.z*c.z)))
  }
  
  def apply(p : Vector2) = {
    val na = ((p.x-a.x)*ab.y)-(ab.x*(p.y-a.y))
	  val nb = ((p.x-b.x)*bc.y)-(bc.x*(p.y-b.y))
	  val nc = ((p.x-c.x)*ca.y)-(ca.x*(p.y-c.y))
	  if( ( na > 0 && nb > 0 && nc > 0 ) || ( na < 0 && nb < 0 && nc < 0 ) ) Some(-(((ta*p.x)+(tc*p.y)+td)/tb))
	  else None
  }
}

object Map25{
  def find[T](v: Vector2, triangles : Array[Triangle25[T]])(check: T => Boolean = { _:T => true}) : Option[(Float,Triangle25[T])] = {
    for(t <- triangles if check(t.data) ){
      val tmp = t(v)
      if(tmp.isDefined) return Some(tmp.get, t)
    }
    None
  }
}

class Map25[T](val triangles : Array[Triangle25[T]], var last:Triangle25[T]){
  
  /** if(true) set y to v */
  def fast(v: Vector2)(check: T => Boolean = { _ => true}) : Option[Float] = {
    if(check(last.data)){
      val tmp = last(v)
      if(tmp.isDefined)return tmp
    }
    for(t <- last.near if check(t.data)){
      val tmp = t(v)
      if(tmp.isDefined) {
        last = t
        return tmp
      }
    }
    for(t <- last.far if check(t.data)){
      val tmp = t(v)
      if(tmp.isDefined) {
        last = t
        return tmp
      }
    }
    None
  }

  def apply(v: Vector2)(check: T => Boolean = { _ => true}) : Option[Float] = {
    val f = fast(v)(check)
    if(f.isDefined) return f
    val s = Map25.find(v, triangles)(check)
    if(s.isDefined){
      val v = s.get
      last = v._2
      Some(v._1)
    }else None
  }

}
