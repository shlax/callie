package org.callie.math

object Matrix4{
  def apply() = new Matrix4()
  
  def apply(v: Vector3) = new Matrix4(v)

  def apply(m: Matrix4) = new Matrix4(m)

  def apply(x:Float, y:Float, z:Float) = new Matrix4(x, y, z)

  def rotX(angle: Float) = {
    val s = Math.sin(angle).asInstanceOf[Float]
    val c = Math.cos(angle).asInstanceOf[Float]
    new Matrix4(1f, 0f, 0f, 0f,
                0f, c , -s, 0f,
                0f, s , c , 0f,
                0f, 0f, 0f, 1f)
  }

  def rotY(angle: Float) = {
    val s = Math.sin(angle).asInstanceOf[Float]
    val c = Math.cos(angle).asInstanceOf[Float]

    new Matrix4( c, 0f, s , 0f,
                0f, 1f, 0f, 0f,
                -s, 0f, c , 0f,
                0f, 0f, 0f, 1f)

  }

  def rotZ(angle: Float) = {
    val s = Math.sin(angle).asInstanceOf[Float]
    val c = Math.cos(angle).asInstanceOf[Float]

    new Matrix4( c, -s, 0f, 0f,
                 s, c , 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f)
  }

  def apply(m00: Float, m01: Float, m02: Float, m03: Float,
            m10: Float, m11: Float, m12: Float, m13: Float,
            m20: Float, m21: Float, m22: Float, m23: Float,
            m30: Float, m31: Float, m32: Float, m33: Float) = new Matrix4(m00, m01, m02, m03, 
                                                                          m10, m11, m12, m13, 
                                                                          m20, m21, m22, m23, 
                                                                          m30, m31, m32, m33)
}

class Matrix4(var m00: Float, var m01: Float, var m02: Float, var m03: Float,  // 0  1  2  3
              var m10: Float, var m11: Float, var m12: Float, var m13: Float,  // 4  5  6  7
              var m20: Float, var m21: Float, var m22: Float, var m23: Float,  // 8  9  10 11
              var m30: Float, var m31: Float, var m32: Float, var m33: Float) {
  // 12 13 14 15

  def this() = this(1f, 0f, 0f, 0f,
                    0f, 1f, 0f, 0f,
                    0f, 0f, 1f, 0f,
                    0f, 0f, 0f, 1f)

  def this(v:Vector3) = this(1f, 0f, 0f, v.x,
                 		         0f, 1f, 0f, v.y,
                 		         0f, 0f, 1f, v.z,
                 		         0f, 0f, 0f, 1f)

  def this(x:Float, y:Float, z:Float) = this(1f, 0f, 0f, x,
                                             0f, 1f, 0f, y,
                                             0f, 0f, 1f, z,
                                             0f, 0f, 0f, 1f)

  def this(m:Matrix4) = this(m.m00, m.m01, m.m02, m.m03,
                             m.m10, m.m11, m.m12, m.m13,
                             m.m20, m.m21, m.m22, m.m23,
                             m.m30, m.m31, m.m32, m.m33)
                    
  def apply(v: Vector3) : Vector3 = apply(v, v)
  
  def apply(in: Vector3, out : Vector3) = {
	    val x = m00*in.x + m01*in.y + m02*in.z + m03
      val y = m10*in.x + m11*in.y + m12*in.z + m13
      val z = m20*in.x + m21*in.y + m22*in.z + m23
      out.x = x; out.y =y; out.z = z 
      out
  }

  def mul (m : Matrix4) : Matrix4 = mul(this, m)
  
  def mul(m : Matrix4, n : Matrix4) = {
    val t00 = m.m00 * n.m00 + m.m01 * n.m10 + m.m02 * n.m20 + m.m03 * n.m30
    val t01 = m.m00 * n.m01 + m.m01 * n.m11 + m.m02 * n.m21 + m.m03 * n.m31
    val t02 = m.m00 * n.m02 + m.m01 * n.m12 + m.m02 * n.m22 + m.m03 * n.m32
    val t03 = m.m00 * n.m03 + m.m01 * n.m13 + m.m02 * n.m23 + m.m03 * n.m33
    val t10 = m.m10 * n.m00 + m.m11 * n.m10 + m.m12 * n.m20 + m.m13 * n.m30
    val t11 = m.m10 * n.m01 + m.m11 * n.m11 + m.m12 * n.m21 + m.m13 * n.m31
    val t12 = m.m10 * n.m02 + m.m11 * n.m12 + m.m12 * n.m22 + m.m13 * n.m32
    val t13 = m.m10 * n.m03 + m.m11 * n.m13 + m.m12 * n.m23 + m.m13 * n.m33
    val t20 = m.m20 * n.m00 + m.m21 * n.m10 + m.m22 * n.m20 + m.m23 * n.m30
    val t21 = m.m20 * n.m01 + m.m21 * n.m11 + m.m22 * n.m21 + m.m23 * n.m31
    val t22 = m.m20 * n.m02 + m.m21 * n.m12 + m.m22 * n.m22 + m.m23 * n.m32
    val t23 = m.m20 * n.m03 + m.m21 * n.m13 + m.m22 * n.m23 + m.m23 * n.m33
    val t330 = m.m30 * n.m00 + m.m31 * n.m10 + m.m32 * n.m20 + m.m33 * n.m30
    val t31 = m.m30 * n.m01 + m.m31 * n.m11 + m.m32 * n.m21 + m.m33 * n.m31
    val t32 = m.m30 * n.m02 + m.m31 * n.m12 + m.m32 * n.m22 + m.m33 * n.m32
    val t33 = m.m30 * n.m03 + m.m31 * n.m13 + m.m32 * n.m23 + m.m33 * n.m33

    m00 = t00;  m01 = t01;  m02 = t02; m03 = t03
    m10 = t10;  m11 = t11;  m12 = t12; m13 = t13
    m20 = t20;  m21 = t21;  m22 = t22; m23 = t23
    m30 = t330; m31 = t31;  m32 = t32; m33 = t33
    
    this
  }
  
  def update(v: Vector3) = {
    m00 = 1f; m01 = 0f; m02 = 0f; m03 = v.x
    m10 = 0f; m11 = 1f; m12 = 0f; m13 = v.y
    m20 = 0f; m21 = 0f; m22 = 1f; m23 = v.z
    m30 = 0f; m31 = 0f; m32 = 0f; m33 = 1f
    
    this
  }

  def rotX(angle: Float) = {
    val s = Math.sin(angle).asInstanceOf[Float]
    val c = Math.cos(angle).asInstanceOf[Float]

    m00 = 1f; m01 = 0f; m02 = 0f; m03 = 0f
    m10 = 0f; m11 = c ; m12 = -s; m13 = 0f
    m20 = 0f; m21 = s ; m22 = c ; m23 = 0f
    m30 = 0f; m31 = 0f; m32 = 0f; m33 = 1f
    
    this
  }

  def rotY(angle: Float) = {
    val s = Math.sin(angle).asInstanceOf[Float]
    val c = Math.cos(angle).asInstanceOf[Float]

    m00 = c ; m01 = 0f; m02 = s ; m03 = 0f
    m10 = 0f; m11 = 1f; m12 = 0f; m13 = 0f
    m20 = -s; m21 = 0f; m22 = c ; m23 = 0f
    m30 = 0f; m31 = 0f; m32 = 0f; m33 = 1f
    
    this
  }
  
  def rotZ(angle: Float) = {
    val s = Math.sin(angle).asInstanceOf[Float]
    val c = Math.cos(angle).asInstanceOf[Float]

    m00 = c ; m01 = -s; m02 = 0f; m03 = 0f
    m10 = s ; m11 = c ; m12 = 0f; m13 = 0f
    m20 = 0f; m21 = 0f; m22 = 1f; m23 = 0f
    m30 = 0f; m31 = 0f; m32 = 0f; m33 = 1f
    
    this
  }

  // http://stackoverflow.com/questions/1148309/inverting-a-4x4-matrix
  def inverse() = {
    val t00 =  m11*m22*m33 - m11*m23*m32 - m21*m12*m33 + m21*m13*m32 + m31*m12*m23 - m31*m13*m22
    val t10 = -m10*m22*m33 + m10*m23*m32 + m20*m12*m33 - m20*m13*m32 - m30*m12*m23 + m30*m13*m22
    val t20 =  m10*m21*m33 - m10*m23*m31 - m20*m11*m33 + m20*m13*m31 + m30*m11*m23 - m30*m13*m21
    val t30 = -m10*m21*m32 + m10*m22*m31 + m20*m11*m32 - m20*m12*m31 - m30*m11*m22 + m30*m12*m21
    val t01 = -m01*m22*m33 + m01*m23*m32 + m21*m02*m33 - m21*m03*m32 - m31*m02*m23 + m31*m03*m22
    val t11 =  m00*m22*m33 - m00*m23*m32 - m20*m02*m33 + m20*m03*m32 + m30*m02*m23 - m30*m03*m22
    val t21 = -m00*m21*m33 + m00*m23*m31 + m20*m01*m33 - m20*m03*m31 - m30*m01*m23 + m30*m03*m21
    val t31 =  m00*m21*m32 - m00*m22*m31 - m20*m01*m32 + m20*m02*m31 + m30*m01*m22 - m30*m02*m21
    val t02 =  m01*m12*m33 - m01*m13*m32 - m11*m02*m33 + m11*m03*m32 + m31*m02*m13 - m31*m03*m12
    val t12 = -m00*m12*m33 + m00*m13*m32 + m10*m02*m33 - m10*m03*m32 - m30*m02*m13 + m30*m03*m12
    val t22 =  m00*m11*m33 - m00*m13*m31 - m10*m01*m33 + m10*m03*m31 + m30*m01*m13 - m30*m03*m11
    val t32 = -m00*m11*m32 + m00*m12*m31 + m10*m01*m32 - m10*m02*m31 - m30*m01*m12 + m30*m02*m11
    val t03 = -m01*m12*m23 + m01*m13*m22 + m11*m02*m23 - m11*m03*m22 - m21*m02*m13 + m21*m03*m12
    val t13 =  m00*m12*m23 - m00*m13*m22 - m10*m02*m23 + m10*m03*m22 + m20*m02*m13 - m20*m03*m12
    val t23 = -m00*m11*m23 + m00*m13*m21 + m10*m01*m23 - m10*m03*m21 - m20*m01*m13 + m20*m03*m11
    val t33 =  m00*m11*m22 - m00*m12*m21 - m10*m01*m22 + m10*m02*m21 + m20*m01*m12 - m20*m02*m11

    val det = 1f / (m00*t00 + m01*t10 + m02*t20 + m03*t30)

    m00 = t00*det; m01 = t01*det; m02 = t02*det; m03 = t03*det
    m10 = t10*det; m11 = t11*det; m12 = t12*det; m13 = t13*det
    m20 = t20*det; m21 = t21*det; m22 = t22*det; m23 = t23*det
    m30 = t30*det; m31 = t31*det; m32 = t32*det; m33 = t33*det

    this
  }

  def transpose() = {
    var tmp = m10
    this.m10 = this.m01
    this.m01 = tmp

    tmp = this.m20
    this.m20 = this.m02
    this.m02 = tmp

    tmp = this.m30
    this.m30 = this.m03
    this.m03 = tmp

    tmp = this.m21
    this.m21 = this.m12
    this.m12 = tmp

    tmp = this.m31
    this.m31 = this.m13
    this.m13 = tmp

    tmp = this.m32
    this.m32 = this.m23
    this.m23 = tmp

    this
  }

  def toArray = Array(m00, m01, m02, m03,
                      m10, m11, m12, m13,
                      m20, m21, m22, m23,
                      m30, m31, m32, m33)

  override def toString = {
    def padMk(n: Float) =  (if(n < 0f) "-" else "+" )+Math.abs(n).toString.padTo(12, " ").mkString
    "(" + padMk(m00) + ", " + padMk(m01) + ", " + padMk(m02) + ", " + padMk(m03) + "\n" +
    " " + padMk(m10) + ", " + padMk(m11) + ", " + padMk(m12) + ", " + padMk(m13) + "\n" +
    " " + padMk(m20) + ", " + padMk(m21) + ", " + padMk(m22) + ", " + padMk(m23) + "\n" +
    " " + padMk(m30) + ", " + padMk(m31) + ", " + padMk(m32) + ", " + padMk(m33) + ")"
  }

}