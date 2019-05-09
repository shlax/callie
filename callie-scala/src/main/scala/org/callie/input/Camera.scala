package org.callie.input

import org.callie.jogl.GlType
import org.callie.math.{Angle, Matrix4, Vector3}

// http://www.gamedev.net/topic/617711-glulookat-replacement/
object Camera {
  var target:TrackingObject = ZeroTrackingObject
  def lookAt(t:TrackingObject){ target = t }

//  var position = Vector3(0, 0, -1f)

//  def init(implicit gl:GL4){
//    display(gl)
//  }

  // http://stackoverflow.com/questions/21079623/how-to-calculate-the-normal-matrix

  var viewMatrix: Int = _
  var lightDirection: Int = _

  def program(gl:GlType, id:Int, view:String="viewMatrix", normal:String="lightDirection"){
    viewMatrix = gl.glGetUniformLocation(id, view)
    lightDirection = gl.glGetUniformLocation(id, normal)
    display(gl)
  }

  // http://gamedev.stackexchange.com/questions/56609/how-to-create-a-projection-matrix-in-opengl-es-2-0
  // http://stackoverflow.com/questions/14713343/projection-theory-implimented-in-glsl
  // http://stackoverflow.com/questions/30122335/create-view-lookat-matrix-for-opengl-manually-in-java

  // http://spointeau.blogspot.sk/2013/12/hello-i-am-looking-at-opengl-3.html

  val angX = Angle()
  val angY = Angle()

  val off = Vector3(0f, 0f, -5f)

  val light = Vector3(0f, 0f, 1f)
  val vecTmp = Vector3(0f, 0f, 1f)

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

  val viewAr = new Array[Float](16)
  val lightDirectionAr = new Array[Float](3)

  var cnt = 0

  // mat4 normalMatrix = transpose(inverse(modelView));
  def display(gl:GlType){
    off.z += Inputs.zDiff() * 0.25f
    angX() += Inputs.yDiff() * 0.025f
    angY() += Inputs.xDiff() * 0.025f

    //modMat.rotX(angX()).mul(tmp.rotY((Math.PI/2d).asInstanceOf[Float] + angY()))
    modMat.rotX(angX()).mul(tmp.rotY(angY()))
    //println("normalMatrix "+modMat)
    //modMat.apply(light, vecTmp)

    gl.glUniform3fv(lightDirection, 1, vecTmp.toArray(lightDirectionAr), 0)

    /* cnt += 1
    if(cnt == 250) {
      println(angY()+ " " + light + " -> " + vecTmp)
      cnt = 0
    } */



   //modMat.rotY(angY())
    modMat.mul(tmp.set(target.position))
    modMat.mul(tmp.set(off), modMat)
//    modMat.set(off).mul(tmp.rotX(angX())).mul(tmp.rotY(angY())).mul(tmp.set(target.position))
    //println("viewMatrix "+modMat)
    gl.glUniformMatrix4fv(viewMatrix, 1, true, tmp.mul(projection, modMat).toArray(viewAr), 0)

  }

}

trait TrackingObject{
  def position: Vector3
}

object ZeroTrackingObject extends TrackingObject{
  override val position = Vector3(0,0,0)
}
