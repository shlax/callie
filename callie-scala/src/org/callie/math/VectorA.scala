package org.callie.math

class VectorA(val v : Array[Float], val ix : Int, val iy : Int, val iz : Int) extends Vector {

  override def x = v(ix); 
  override def x_=(nv:Float) = v(ix) = nv 
  override def y = v(iy); 
  override def y_=(nv:Float) = v(iy) = nv
  override def z = v(iz); 
  override def z_=(nv:Float) = v(iz) = nv
  
}