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

  def mul(m : Matrix4, n : Matrix4, out:Array[Float]) = {
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
    val t30 = m.m30 * n.m00 + m.m31 * n.m10 + m.m32 * n.m20 + m.m33 * n.m30
    val t31 = m.m30 * n.m01 + m.m31 * n.m11 + m.m32 * n.m21 + m.m33 * n.m31
    val t32 = m.m30 * n.m02 + m.m31 * n.m12 + m.m32 * n.m22 + m.m33 * n.m32
    val t33 = m.m30 * n.m03 + m.m31 * n.m13 + m.m32 * n.m23 + m.m33 * n.m33

    out(0)  = t00; out(1)  = t01; out(2)  = t02; out(3)  = t03
    out(4)  = t10; out(5)  = t11; out(6)  = t12; out(7)  = t13
    out(8)  = t20; out(9)  = t21; out(10) = t22; out(11) = t23
    out(12) = t30; out(13) = t31; out(14) = t32; out(15) = t33

    out
  }

}

class Matrix4(var m00: Float, var m01: Float, var m02: Float, var m03: Float,                      // 0  1  2  3
              var m10: Float, var m11: Float, var m12: Float, var m13: Float,                      // 4  5  6  7
              var m20: Float, var m21: Float, var m22: Float, var m23: Float,                      // 8  9  10 11
              var m30: Float, var m31: Float, var m32: Float, var m33: Float) extends Translation{ // 12 13 14 15

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
                    
  def apply(v: Vector3): Vector3 = apply(v, v)

  def apply(in: Vector3, out : Vector3) = {
    val x = m00*in.x + m01*in.y + m02*in.z + m03
    val y = m10*in.x + m11*in.y + m12*in.z + m13
    val z = m20*in.x + m21*in.y + m22*in.z + m23
    out.x = x; out.y =y; out.z = z
    out
  }

  def apply(in: Vector3, out : Array[Float]) = {
    val x = m00*in.x + m01*in.y + m02*in.z + m03
    val y = m10*in.x + m11*in.y + m12*in.z + m13
    val z = m20*in.x + m21*in.y + m22*in.z + m23
    out(0) = x; out(1) =y; out(2) = z
    out
  }

  def identity() = {
    m00 = 1f; m01 = 0f; m02 = 0f; m03 = 0f
    m10 = 0f; m11 = 1f; m12 = 0f; m13 = 0f
    m20 = 0f; m21 = 0f; m22 = 1f; m23 = 0f
    m30 = 0f; m31 = 0f; m32 = 0f; m33 = 1f
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
    val t30 = m.m30 * n.m00 + m.m31 * n.m10 + m.m32 * n.m20 + m.m33 * n.m30
    val t31 = m.m30 * n.m01 + m.m31 * n.m11 + m.m32 * n.m21 + m.m33 * n.m31
    val t32 = m.m30 * n.m02 + m.m31 * n.m12 + m.m32 * n.m22 + m.m33 * n.m32
    val t33 = m.m30 * n.m03 + m.m31 * n.m13 + m.m32 * n.m23 + m.m33 * n.m33

    m00 = t00; m01 = t01; m02 = t02; m03 = t03
    m10 = t10; m11 = t11; m12 = t12; m13 = t13
    m20 = t20; m21 = t21; m22 = t22; m23 = t23
    m30 = t30; m31 = t31; m32 = t32; m33 = t33
    
    this
  }

  def mul(m : Matrix4, out:Array[Float]): Array[Float] = Matrix4.mul(this, m, out)

  def set(v: Matrix4) = {
    m00 = v.m00; m01 = v.m01; m02 = v.m02; m03 = v.m03
    m10 = v.m10; m11 = v.m11; m12 = v.m12; m13 = v.m13
    m20 = v.m20; m21 = v.m21; m22 = v.m22; m23 = v.m23
    m30 = v.m30; m31 = v.m31; m32 = v.m32; m33 = v.m33

    this
  }

  def set(v: Vector3) = {
    m00 = 1f; m01 = 0f; m02 = 0f; m03 = v.x
    m10 = 0f; m11 = 1f; m12 = 0f; m13 = v.y
    m20 = 0f; m21 = 0f; m22 = 1f; m23 = v.z
    m30 = 0f; m31 = 0f; m32 = 0f; m33 = 1f
    
    this
  }

  def set(vx: Float, vy: Float, vz: Float) = {
    m00 = 1f; m01 = 0f; m02 = 0f; m03 = vx
    m10 = 0f; m11 = 1f; m12 = 0f; m13 = vy
    m20 = 0f; m21 = 0f; m22 = 1f; m23 = vz
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
  def inverse(): Matrix4 = {
    inverse(this)
  }

  def inverse(m:Matrix4): Matrix4 = {
    val t00 =  m.m11*m.m22*m.m33 - m.m11*m.m23*m.m32 - m.m21*m.m12*m.m33 + m.m21*m.m13*m.m32 + m.m31*m.m12*m.m23 - m.m31*m.m13*m.m22
    val t10 = -m.m10*m.m22*m.m33 + m.m10*m.m23*m.m32 + m.m20*m.m12*m.m33 - m.m20*m.m13*m.m32 - m.m30*m.m12*m.m23 + m.m30*m.m13*m.m22
    val t20 =  m.m10*m.m21*m.m33 - m.m10*m.m23*m.m31 - m.m20*m.m11*m.m33 + m.m20*m.m13*m.m31 + m.m30*m.m11*m.m23 - m.m30*m.m13*m.m21
    val t30 = -m.m10*m.m21*m.m32 + m.m10*m.m22*m.m31 + m.m20*m.m11*m.m32 - m.m20*m.m12*m.m31 - m.m30*m.m11*m.m22 + m.m30*m.m12*m.m21
    val t01 = -m.m01*m.m22*m.m33 + m.m01*m.m23*m.m32 + m.m21*m.m02*m.m33 - m.m21*m.m03*m.m32 - m.m31*m.m02*m.m23 + m.m31*m.m03*m.m22
    val t11 =  m.m00*m.m22*m.m33 - m.m00*m.m23*m.m32 - m.m20*m.m02*m.m33 + m.m20*m.m03*m.m32 + m.m30*m.m02*m.m23 - m.m30*m.m03*m.m22
    val t21 = -m.m00*m.m21*m.m33 + m.m00*m.m23*m.m31 + m.m20*m.m01*m.m33 - m.m20*m.m03*m.m31 - m.m30*m.m01*m.m23 + m.m30*m.m03*m.m21
    val t31 =  m.m00*m.m21*m.m32 - m.m00*m.m22*m.m31 - m.m20*m.m01*m.m32 + m.m20*m.m02*m.m31 + m.m30*m.m01*m.m22 - m.m30*m.m02*m.m21
    val t02 =  m.m01*m.m12*m.m33 - m.m01*m.m13*m.m32 - m.m11*m.m02*m.m33 + m.m11*m.m03*m.m32 + m.m31*m.m02*m.m13 - m.m31*m.m03*m.m12
    val t12 = -m.m00*m.m12*m.m33 + m.m00*m.m13*m.m32 + m.m10*m.m02*m.m33 - m.m10*m.m03*m.m32 - m.m30*m.m02*m.m13 + m.m30*m.m03*m.m12
    val t22 =  m.m00*m.m11*m.m33 - m.m00*m.m13*m.m31 - m.m10*m.m01*m.m33 + m.m10*m.m03*m.m31 + m.m30*m.m01*m.m13 - m.m30*m.m03*m.m11
    val t32 = -m.m00*m.m11*m.m32 + m.m00*m.m12*m.m31 + m.m10*m.m01*m.m32 - m.m10*m.m02*m.m31 - m.m30*m.m01*m.m12 + m.m30*m.m02*m.m11
    val t03 = -m.m01*m.m12*m.m23 + m.m01*m.m13*m.m22 + m.m11*m.m02*m.m23 - m.m11*m.m03*m.m22 - m.m21*m.m02*m.m13 + m.m21*m.m03*m.m12
    val t13 =  m.m00*m.m12*m.m23 - m.m00*m.m13*m.m22 - m.m10*m.m02*m.m23 + m.m10*m.m03*m.m22 + m.m20*m.m02*m.m13 - m.m20*m.m03*m.m12
    val t23 = -m.m00*m.m11*m.m23 + m.m00*m.m13*m.m21 + m.m10*m.m01*m.m23 - m.m10*m.m03*m.m21 - m.m20*m.m01*m.m13 + m.m20*m.m03*m.m11
    val t33 =  m.m00*m.m11*m.m22 - m.m00*m.m12*m.m21 - m.m10*m.m01*m.m22 + m.m10*m.m02*m.m21 + m.m20*m.m01*m.m12 - m.m20*m.m02*m.m11

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

  override def x= m03
  override def y= m13
  override def z= m23

  def toArray(a:Array[Float]) = {
    a(0)  = m00; a(1)  = m01; a(2)  = m02; a(3)  = m03
    a(4)  = m10; a(5)  = m11; a(6)  = m12; a(7)  = m13
    a(8)  = m20; a(9)  = m21; a(10) = m22; a(11) = m23
    a(12) = m30; a(13) = m31; a(14) = m32; a(15) = m33
    a
  }

  def toArray = Array(m00, m01, m02, m03,
                      m10, m11, m12, m13,
                      m20, m21, m22, m23,
                      m30, m31, m32, m33)

  override def toString = {
    //def padMk(n: Float) =  (if(n < 0f) "-" else "+" )+Math.abs(n).toString.padTo(12, " ").mkString
//    "(" + padMk(m00) + ", " + padMk(m01) + ", " + padMk(m02) + ", " + padMk(m03) + "\n" +
//    " " + padMk(m10) + ", " + padMk(m11) + ", " + padMk(m12) + ", " + padMk(m13) + "\n" +
//    " " + padMk(m20) + ", " + padMk(m21) + ", " + padMk(m22) + ", " + padMk(m23) + "\n" +
//    " " + padMk(m30) + ", " + padMk(m31) + ", " + padMk(m32) + ", " + padMk(m33) + ")"
    "{{" + m00 + ", " + m01 + ", " + m02 + ", " + m03 + "}, " +
     "{" + m10 + ", " + m11 + ", " + m12 + ", " + m13 + "}, " +
     "{" + m20 + ", " + m21 + ", " + m22 + ", " + m23 + "}, " +
     "{" + m30 + ", " + m31 + ", " + m32 + ", " + m33 + "}}"
  }

}