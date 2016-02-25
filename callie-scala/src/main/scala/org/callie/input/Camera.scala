package org.callie.input

import com.jogamp.opengl.GL4
import org.callie.math.{Matrix4, Vector3}

// http://www.gamedev.net/topic/617711-glulookat-replacement/
object Camera {
  var target:TrackingObject = ZeroTrackingObject
  def lookAt(t:TrackingObject){ target = t }

  var position = Vector3(0, 0, -1f)

//  def init(implicit gl:GL4){
//    display(gl)
//  }

  // http://stackoverflow.com/questions/21079623/how-to-calculate-the-normal-matrix

  var viewMatrix: Int = _
  var normalMatrix: Int = _

  def program(id:Int, view:String="viewMatrix", normal:String="normalMatrix")(implicit gl:GL4){
    viewMatrix = gl.glGetUniformLocation(id, view)
    normalMatrix = gl.glGetUniformLocation(id, normal)
    display
  }

  // http://gamedev.stackexchange.com/questions/56609/how-to-create-a-projection-matrix-in-opengl-es-2-0
  // http://stackoverflow.com/questions/14713343/projection-theory-implimented-in-glsl
  // http://stackoverflow.com/questions/30122335/create-view-lookat-matrix-for-opengl-manually-in-java

  // http://spointeau.blogspot.sk/2013/12/hello-i-am-looking-at-opengl-3.html

  var ang = 2f

  def model() = {
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

    // https://unspecified.wordpress.com/2012/06/21/calculating-the-gluperspective-matrix-and-other-opengl-matrix-maths/
    // http://blog.db-in.com/cameras-on-opengl-es-2-x/

    ang += 0.01f
    Matrix4(0f, 0f, -2f).mul(Matrix4.rotX(ang))//.mul(Matrix4.rotY(ang)).mul(Matrix4.rotZ(ang))
//    Matrix4(0.25f, 0.25f, -1.25f).mul(vm)

//    Matrix4(1, 0, 0, 0,
//            0, 1, 0, 0,
//            0, 0, 1, 0,
//            0, 0, 0, 1)
  }

  val view = Matrix4()
  val projection = Matrix4(1f, 0f, 0f, 0f,
                           0f, 1f, 0f, 0f,
                           0f, 0f, -1.002002f, -2.002002f,
                           0f, 0f, -1f, 0f)

  // mat4 normalMatrix = transpose(inverse(modelView));
  def display(implicit gl:GL4){
    val m = model()
    gl.glUniformMatrix4fv(viewMatrix, 1, true, view.mul(projection, m).toArray, 0)
    gl.glUniformMatrix4fv(normalMatrix, 1, false, m.inverse().toArray, 0)
  }

}

trait TrackingObject{
  def position: Vector3
}

object ZeroTrackingObject extends TrackingObject{
  override val position = Vector3()
}
