package org.callie.math

case class Vector2(x : Float, y : Float)

trait Vector3{

  def x:Float; def x_=(v:Float)
  def y:Float; def y_=(v:Float)
  def z:Float; def z_=(v:Float)

  def len2 = x*x + y*y + z*z
  def len = Math.sqrt(len2).asInstanceOf[Float]

  def normalize() = {
    val l = len
    x = x / l
    y = y / l
    z = z / l
    this
  }

  def cross(v1:Vector3, v2:Vector3) = {
    x = v1.y * v2.z - v1.z * v2.y
    y = v1.z * v2.x - v1.x * v2.z
    z = v1.x * v2.y - v1.y * v2.x
    this
  }

  def add(a:Vector3, b:Vector3) = {
    x = a.x + b.x
    y = a.y + b.y
    z = a.z + b.z
    this
  }

  def sub(a:Vector3, b:Vector3) = {
    x = a.x - b.x
    y = a.y - b.y
    z = a.z - b.z
    this
  }

  def mul(m:Float) = {
    x = x * m
    y = y * m
    z = z * m
    this
  }

  def div(m:Float) = {
    x = x / m
    y = y / m
    z = z / m
    this
  }

  def dot(a: Vector3) = a.x * this.x + a.y * this.y + a.z * this.z

  override def toString = "{"+x+", "+y+", "+z+"}"

}

object Vector3{
  def apply() : Vector3 = new VectorVar(0, 0, 0)
  def apply(x : Float, y : Float, z : Float) : Vector3 = new VectorVar(x, y, z)
  def apply(v : Array[Float], i: Array[Int]) : Vector3 = new VectorProjection(v, i)
    
  def cross(v1:Vector3, v2:Vector3) = new VectorVar(v1.y * v2.z - v1.z * v2.y, 
		  										                          v1.z * v2.x - v1.x * v2.z,
		  										                          v1.x * v2.y - v1.y * v2.x)

  def add(a:Vector3, b:Vector3) = new VectorVar(a.x + b.x, a.y + b.y, a.z + b.z)

  def sub(a:Vector3, b:Vector3) = new VectorVar(a.x - b.x, a.y - b.y, a.z - b.z)

  def dot(a: Vector3, b: Vector3) = a.x * b.x + a.y * b.y + a.z * b.z

  def mul(v:Vector3, m:Float) = Vector3(v.x * m, v.y * m, v.z * m)

  def div(v:Vector3, m:Float) = Vector3(v.x / m, v.y / m, v.z / m)

  def normalize(v:Vector3){
    val l = v.len
    new VectorVar(v.x / l, v.y / l, v.z / l)
  }

}

class VectorVar(var x : Float, var y : Float, var z : Float) extends Vector3 {
	
  def this() = this(0, 0, 0)
  def this(s : Float) = this(s, s, s)
  
}

class VectorProjection(val v : Array[Float], val i: Array[Int]) extends Vector3 {
    
  override def x = v(i.head) /*+0*/
  override def y = v(i.head + 1)
  override def z = v(i.head + 2)
  
  override def x_=(nv:Float) = for(j <- i) v(j) = nv /*+0*/ 
  override def y_=(nv:Float) = for(j <- i) v(j + 1) = nv
  override def z_=(nv:Float) = for(j <- i) v(j + 2) = nv
  
}

