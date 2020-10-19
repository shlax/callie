package org.callie.math

trait Translation{
  def x:Float
  def y:Float
  def z:Float
}

object Vector2{
  def apply(): Vector2 = new Vector2(0f, 0f)
  def apply(x: Float, y: Float): Vector2 = new Vector2(x, y)
}

class Vector2(var x : Float, var y : Float){

  def len2 = x*x + y*y
  def len = Math.sqrt(len2).asInstanceOf[Float]

  override def toString = "Vector2("+x+", "+y+")"
}

trait Vector3 extends Translation{

  def x_=(v:Float):Unit
  def y_=(v:Float):Unit
  def z_=(v:Float):Unit

  def len2 = x*x + y*y + z*z
  def len = Math.sqrt(len2).toFloat

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

  def add(b:Vector3) = {
    x += b.x
    y += b.y
    z += b.z
    this
  }

  def sub(b:Vector3) = {
    x -= b.x
    y -= b.y
    z -= b.z
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

  def zero() ={
    x = 0
    y = 0
    z = 0
    this
  }

  def toArray(a:Array[Float]) = {
    a(0) = x; a(1) = y; a(2) = z
    a
  }

  def toArray() = Array[Float](x, y, z)

  def dot(a: Vector3) = a.x * this.x + a.y * this.y + a.z * this.z

  override def toString = "{"+x+", "+y+", "+z+"}"

  override def hashCode() = {
    var result = x.hashCode()
    result = 31 * result + y.hashCode()
    result = 31 * result + z.hashCode()
    result
  }

  override def equals(obj: Any) = {
    if(super.equals(obj)) true
    else obj match {
      case v: Vector3 => v.x == x && v.y == y && v.z == z
      case _ => false
    }
  }
}

object Vector3{
  def apply() : Vector3 = new VectorVar(0f, 0f, 0f)
  def apply(v:Vector3) : Vector3 = new VectorVar(v.x, v.y, v.z)

  def apply(v : (Float, Float, Float)) : Vector3 = new VectorVar(v._1, v._2, v._3)
  def apply(x : Float, y : Float, z : Float) : Vector3 = new VectorVar(x, y, z)

  def apply(v : Array[Float], i: Int) : Vector3 = new VectorProjection(v, i)
  def apply(v : Array[Float], i: Array[Int]) : Vector3 = if(i.length == 1) new VectorProjection(v, i(0)) else new VectorProjectionN(v, i)

  def cross(v1:Vector3, v2:Vector3) = new VectorVar(v1.y * v2.z - v1.z * v2.y, 
		  										                          v1.z * v2.x - v1.x * v2.z,
		  										                          v1.x * v2.y - v1.y * v2.x)

  def add(a:Vector3, b:Vector3) = new VectorVar(a.x + b.x, a.y + b.y, a.z + b.z)

  def sub(a:Vector3, b:Vector3) = new VectorVar(a.x - b.x, a.y - b.y, a.z - b.z)

  def len2(a:Vector3, b:Vector3) = {
    val x = a.x - b.x
    val y = a.y - b.y
    val z = a.z - b.z

    x*x + y*y + z*z
  }

  def len(a:Vector3, b:Vector3) = {
    Math.sqrt(len2(a, b)).toFloat
  }

  def dot(a: Vector3, b: Vector3) = a.x * b.x + a.y * b.y + a.z * b.z

  def mul(v:Vector3, m:Float) = Vector3(v.x * m, v.y * m, v.z * m)

  def div(v:Vector3, m:Float) = Vector3(v.x / m, v.y / m, v.z / m)

  def normalize(v:Vector3):Unit={
    val l = v.len
    new VectorVar(v.x / l, v.y / l, v.z / l)
  }

}

class VectorVar(override var x : Float, override var y : Float, override var z : Float) extends Vector3 {
	
  def this() = this(0f, 0f, 0f)
  def this(s : Float) = this(s, s, s)
  
}

class VectorProjection(val v : Array[Float], val i: Int) extends Vector3 {

  override def x = v(i) /*+0*/
  override def y = v(i + 1)
  override def z = v(i + 2)

  override def x_=(nv:Float):Unit={
    v(i) = nv /*+0*/
  }

  override def y_=(nv:Float):Unit={
    v(i + 1) = nv
  }

  override def z_=(nv:Float):Unit={
    v(i + 2) = nv
  }

}

class VectorProjectionN(val v : Array[Float], val i: Array[Int]) extends Vector3 {
    
  override def x = v(i(0)) /*+0*/
  override def y = v(i(0) + 1)
  override def z = v(i(0) + 2)
  
  override def x_=(nv:Float):Unit={
    for(j <- i) v(j) = nv  /*+0*/
  }

  override def y_=(nv:Float):Unit={
    for(j <- i) v(j + 1) = nv
  }

  override def z_=(nv:Float):Unit={
    for(j <- i) v(j + 2) = nv
  }
  
}

