package org.callie.map

import org.callie.math.Vector3

case class Vector2(val x : Float, val y : Float)

class Triangle(val a : Vector3, val b : Vector3, val c : Vector3) {

  val ab = Vector2(b.x-a.x, b.z-a.z)
  val bc = Vector2(c.x-b.x, c.z-b.z)
  val ca = Vector2(a.x-c.x, a.z-c.z) 
  
  val (ta, tb, tc, td) = {
    val tmp = Vector3.cross( Vector3(c.x-a.x, c.y-a.y, c.z-a.z ), Vector3(b.x-a.x, b.y-a.y, b.z-a.z) )
	(tmp.x, tmp.y, tmp.z, -((tmp.x*c.x)+(tmp.y*c.y)+(tmp.z*c.z)))
  }
  
  def apply(p : Vector3) = {
    val na = ((p.x-a.x)*ab.y)-(ab.x*(p.z-a.y))
	val nb = ((p.x-b.x)*bc.y)-(bc.x*(p.z-b.y))
	val nc = ((p.x-c.x)*ca.y)-(ca.x*(p.z-c.y))
	if( ( na > 0 && nb > 0 && nc > 0 ) || ( na < 0 && nb < 0 && nc < 0 ) ){
	  p.y = -(((ta*p.x)+(tc*p.z)+td)/tb)
	  true
	}
    false
  }
  
}