package org.callie.math

object Matrix4{
  def apply() = new Matrix4()
  
  def apply(m00: Float, m01: Float, m02: Float, m03: Float,
			m10: Float, m11: Float, m12: Float, m13: Float,
			m20: Float, m21: Float, m22: Float, m23: Float,
			m30: Float, m31: Float, m32: Float, m33: Float) = new Matrix4(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33)
}

class Matrix4(var m00: Float, var m01: Float, var m02: Float, var m03: Float,  // 0  1  2  3
			  var m10: Float, var m11: Float, var m12: Float, var m13: Float,  // 4  5  6  7
			  var m20: Float, var m21: Float, var m22: Float, var m23: Float,  // 8  9  10 11
			  var m30: Float, var m31: Float, var m32: Float, var m33: Float) {// 12 13 14 15

  def this() = this(1, 0, 0, 0,
				    0, 1, 0, 0,
				    0, 0, 1, 0,
				    0, 0, 0, 1)

  def apply(v: Vector3) : Vector3 = apply(v, v)
  
  def apply(in: Vector3, out : Vector3) = {
	  val x = m00*in.x + m01*in.y + m02*in.z + m03
      val y = m10*in.x + m11*in.y + m12*in.z + m13
      val z = m20*in.x + m21*in.y + m22*in.z + m23
      out.x = x; out.y =y; out.z = z 
      out
  }

  def * (m : Matrix4) : Matrix4 = ** (this, m)
  
  def ** (n : Matrix4, m : Matrix4) = {
    val t00 = n.m00*m.m00 + n.m01*m.m10 + n.m02*m.m20 + n.m03*m.m30
    val t01 = n.m00*m.m01 + n.m01*m.m11 + n.m02*m.m21 + n.m03*m.m31
    val t02 = n.m00*m.m02 + n.m01*m.m12 + n.m02*m.m22 + n.m03*m.m32
    val t03 = n.m00*m.m03 + n.m01*m.m13 + n.m02*m.m23 + n.m03*m.m33
    
    val t10 = n.m10*m.m00 + n.m10*m.m10 + n.m12*m.m20 + n.m13*m.m30
    val t11 = n.m10*m.m01 + n.m10*m.m11 + n.m12*m.m21 + n.m13*m.m31
    val t12 = n.m10*m.m02 + n.m10*m.m12 + n.m12*m.m22 + n.m13*m.m32
    val t13 = n.m10*m.m03 + n.m10*m.m13 + n.m12*m.m23 + n.m13*m.m33
    
    val t20 = n.m20*m.m00 + n.m20*m.m10 + n.m22*m.m20 + n.m23*m.m30
    val t21 = n.m20*m.m01 + n.m20*m.m11 + n.m22*m.m21 + n.m23*m.m31
    val t22 = n.m20*m.m02 + n.m20*m.m12 + n.m22*m.m22 + n.m23*m.m32
    val t23 = n.m20*m.m03 + n.m20*m.m13 + n.m22*m.m23 + n.m23*m.m33
    
    val t30 = n.m30*m.m00 + n.m30*m.m10 + n.m32*m.m20 + n.m33*m.m30
    val t31 = n.m30*m.m01 + n.m30*m.m11 + n.m32*m.m21 + n.m33*m.m31
    val t32 = n.m30*m.m02 + n.m30*m.m12 + n.m32*m.m22 + n.m33*m.m32
    val t33 = n.m30*m.m03 + n.m30*m.m13 + n.m32*m.m23 + n.m33*m.m33
    
    m00 = t00; m01 = t01; m02 = t02; m03 = t03
    m10 = t10; m11 = t11; m12 = t12; m13 = t13
    m20 = t20; m21 = t21; m22 = t22; m23 = t23
    m30 = t30; m31 = t31; m32 = t32; m33 = t33
    
    this
  }
  
  def update(v: Vector3) = {
    m00 = 1; m01 = 0; m02 = 0; m03 = v.x
    m10 = 0; m11 = 1; m12 = 0; m13 = v.y
    m20 = 0; m21 = 0; m22 = 1; m23 = v.z
    m30 = 0; m31 = 0; m32 = 0; m33 = 1
    
    this
  }

  def rotX(angle: Float) = {
    val s = Math.sin(angle).asInstanceOf[Float]
    val c = Math.cos(angle).asInstanceOf[Float]

    m00 = 1; m01 = 0; m02 = 0 ; m03 = 0
    m10 = 0; m11 = c; m12 = -s; m13 = 0
    m20 = 0; m21 = s; m22 = c ; m23 = 0
    m30 = 0; m31 = 0; m32 = 0 ; m33 = 1
    
    this
  }

  def rotY(angle: Float) = {
    val s = Math.sin(angle).asInstanceOf[Float]
    val c = Math.cos(angle).asInstanceOf[Float]

    m00 = c ; m01 = 0; m02 = s; m03 = 0
    m10 = 0 ; m11 = 1; m12 = 0; m13 = 0
    m20 = -s; m21 = 0; m22 = c; m23 = 0
    m30 = 0 ; m31 = 0; m32 = 0; m33 = 1
    
    this
  }
  
  def rotZ(angle: Float) = {
    val s = Math.sin(angle).asInstanceOf[Float]
    val c = Math.cos(angle).asInstanceOf[Float]

    m00 = c; m01 = -s; m02 = 0; m03 = 0
    m10 = s; m11 = c ; m12 = 0; m13 = 0
    m20 = 0; m21 = 0 ; m22 = 1; m23 = 0
    m30 = 0; m31 = 0 ; m32 = 0; m33 = 1
    
    this
  }
  
}