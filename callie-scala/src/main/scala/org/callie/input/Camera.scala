package org.callie.input

import org.callie.ogl.Gl
import org.callie.math.{Angle, Matrix4, Vector3}

// http://www.gamedev.net/topic/617711-glulookat-replacement/
object Camera {
  var target:TrackingObject = ZeroTrackingObject
  def lookAt(t:TrackingObject):Unit={
    target = t
  }

//  var position = Vector3(0, 0, -1f)

//  def init(implicit gl:GL4){
//    update(gl)
//  }

  // http://stackoverflow.com/questions/21079623/how-to-calculate-the-normal-matrix

  var viewMatrix: Array[Int] = _
  var normalMatrix: Array[Int] = _

  var lightDirectionVec: Array[Int] = _

  def program(id:Seq[Int], view:String="viewMatrix", normal:String = "normalMatrix", lightDirection:String="lightDirection"):Unit={
    viewMatrix = id.map(i => Gl.glGetUniformLocation(i, view)).toArray
    lightDirectionVec = id.map(i => Gl.glGetUniformLocation(i, lightDirection)).toArray
    normalMatrix = id.map(i => Gl.glGetUniformLocation(i, normal)).toArray
  }

  // http://gamedev.stackexchange.com/questions/56609/how-to-create-a-projection-matrix-in-opengl-es-2-0
  // http://stackoverflow.com/questions/14713343/projection-theory-implimented-in-glsl
  // http://stackoverflow.com/questions/30122335/create-view-lookat-matrix-for-opengl-manually-in-java

  // http://spointeau.blogspot.sk/2013/12/hello-i-am-looking-at-opengl-3.html

  val angX = Angle()
  val angY = Angle()

  val off = Vector3(0f, 0f, -5f)

  val light = Vector3(0f, 0f, 1f)
  //val vecTmp = Vector3()

  val tmp = Matrix4()
  val modMat = Matrix4()

//  val pi = Math.PI.toFloat
//  val pi2 = (2 * Math.PI).toFloat

//  def angle(v:Float) = {
//    var r = v
//    if(r < -fMath.PI1) r += fMath.PI2
//    if(r > fMath.PI1) r -= fMath.PI2
//    r
//  }

/*  def model() = {
    off.z += Inputs.zDiff() * 0.25f
    angX = angle(angX + Inputs.yDiff() * 0.025f)
    angY = angle(angY + Inputs.xDiff() * 0.025f)

//    val up = Vector3(0f, 1f, 0f)
//
//    val f = Vector3.sub(target.position, position).normalize()
//    val s = Vector3.cross(f, up).normalize()
//    val u = Vector3.cross(s, f).normalize()
//
////    // viewing matrix
//    val vm = Matrix4(s.x             , u.x             , -f.x            , 0f,
//                     s.y             , u.y             , -f.y            , 0f,
//                     s.z             , u.z             , -f.z            , 0f,
//                     -s.dot(position), -u.dot(position), -f.dot(position), 1f)

    modMat.set(off).mul(tmp.rotX(angX)).mul(tmp.rotY(angY)).mul(tmp.set(target.position))

    // https://unspecified.wordpress.com/2012/06/21/calculating-the-gluperspective-matrix-and-other-opengl-matrix-maths/
    // http://blog.db-in.com/cameras-on-opengl-es-2-x/

    //ang += 0.01f
//    Matrix4(0f, 0f, -2f)//.mul(Matrix4.rotX(0))//.mul(Matrix4.rotY(ang)).mul(Matrix4.rotZ(ang))
//    Matrix4(0.25f, 0.25f, -1.25f).mul(vm)

//    Matrix4(1, 0, 0, 0,
//            0, 1, 0, 0,
//            0, 0, 1, 0,
//            0, 0, 0, 1)
  }   */

  //val view = Matrix4() // {{1.5625, 0.0, 0.0, 0.0}, {0.0, 2.7777777, 0.0, 0.0}, {0.0, 0.0, -1.002002, -2.002002}, {0.0, 0.0, -1.0, 0.0}}
  val projection = Matrix4(1.5625f, 0f, 0f, 0f,
                           0f, 2.7777777f, 0f, 0f,
                           0f, 0f, -1.002002f, -2.002002f,
                           0f, 0f, -1f, 0f)

  val matrixArray = new Array[Float](16)

  val lightDirectionAr = new Array[Float](3)

  var cnt = 0

  // mat4 normalMatrix = transpose(inverse(modelView));
  def apply():Unit={

    off.z += Inputs.zDiff() * 0.25f
    angX() += Inputs.yDiff() * 0.025f
    angY() += Inputs.xDiff() * 0.025f

    //modMat.rotX(angX()).mul(tmp.rotY((Math.PI/2d).asInstanceOf[Float] + angY()))
    modMat.rotY(-1f * angY()).mul(tmp.rotX(-1f * angX())).apply(light, lightDirectionAr)
    for(i <- lightDirectionVec) Gl.glUniform3fv(i, lightDirectionAr)

    //println("normalMatrix "+modMat)

    /* cnt += 1
    if(cnt == 250) {
      println(angY()+ " " + light + " -> " + vecTmp)
      cnt = 0
    } */

   //modMat.rotY(angY())
//    modMat.set(off).mul(tmp.rotX(angX())).mul(tmp.rotY(angY())).mul(tmp.set(target.position))
    //modMat.set(off).mul(tmp.rotX(angX())).mul(tmp.rotY(angY())).mul(tmp.set(target.position)) //.mul(tmp.set(off), modMat)
    //projection.mul(modMat.set(off).mul(tmp.rotX(angX())).mul(tmp.rotY(angY()+1.5f)).mul(tmp.set(target.position)), viewAr)
    modMat.set(off).mul(tmp.rotX(angX())).mul(tmp.rotY(angY())).mul(tmp.set(target.position))
    modMat.mul(projection, modMat)
    //for(i <- viewMatrix) Gl.glUniformMatrix4fv(i, true, viewAr)

    //println("viewMatrix "+modMat)
  }

  def update(norm:Matrix4, model:Matrix4):Unit={
    norm.toArray(matrixArray)
    for(i <- normalMatrix) Gl.glUniformMatrix4fv(i, true, matrixArray)

    modMat.mul(model, matrixArray)
    for(i <- viewMatrix) Gl.glUniformMatrix4fv(i, true, matrixArray)
  }

}

trait TrackingObject{
  def position: Vector3
}

object ZeroTrackingObject extends TrackingObject{
  override val position = Vector3(0,0,0)
}
