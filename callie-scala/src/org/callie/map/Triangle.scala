package org.callie.map

import org.callie.math.Vector

class Triangle(val a : Vector, val b : Vector, val c : Vector) {

  val a2 = new Vector2(a.x, a.z);
  val b2 = new Vector2(b.x, b.z);
  val c2 = new Vector2(c.x, c.z);

  val ab = new Vector2(b.x-a.x, b.z-a.z );
  val bc = new Vector2(c.x-b.x, c.z-b.z );
  val ca = new Vector2(a.x-c.x, a.z-c.z ); 
  
//  val (ta, tb, tc, td) = {
//    
//  }
}