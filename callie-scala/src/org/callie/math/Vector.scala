package org.callie.math

case class Vector2(x : Float, y : Float)

trait Vector3{

  def x:Float; def x_=(v:Float)
  def y:Float; def y_=(v:Float)
  def z:Float; def z_=(v:Float)
  
  
  def len2 = x*x + y*y + z*z
  def len = Math.sqrt(len2).asInstanceOf[Float]
  
  def cross(v1:Vector3, v2:Vector3) = {
    x = v1.y * v2.z - v1.z * v2.y
    y = v1.z * v2.x - v1.x * v2.z
    z = v1.x * v2.y - v1.y * v2.x
    this
  }
}

object Vector3{
  def apply() = new VectorVar(0, 0, 0)
  def apply(x : Float, y : Float, z : Float) = new VectorVar(x, y, z)
  def apply(v : Array[Float], ix : Int, iy : Int, iz : Int) = new VectorArray(v, ix, iy, iz)
  
  def cross(v1:Vector3, v2:Vector3) = new VectorVar(v1.y * v2.z - v1.z * v2.y, 
		  										    v1.z * v2.x - v1.x * v2.z, 
		  										    v1.x * v2.y - v1.y * v2.x)
}

class VectorVar(var x : Float, var y : Float, var z : Float) extends Vector3 {
	
  def this() = this(0, 0, 0)
  def this(s : Float) = this(s, s, s)
  
}

class VectorArray(val v : Array[Float], val ix : Int, val iy : Int, val iz : Int) extends Vector3 {

  override def x = v(ix)
  override def x_=(nv:Float) = v(ix) = nv 
  override def y = v(iy)
  override def y_=(nv:Float) = v(iy) = nv
  override def z = v(iz)
  override def z_=(nv:Float) = v(iz) = nv
  
}
