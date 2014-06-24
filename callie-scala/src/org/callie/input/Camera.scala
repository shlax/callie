package org.callie.input

import javax.media.opengl.GL4
import org.callie.math.{Matrix4, Vector3}

// http://www.gamedev.net/topic/617711-glulookat-replacement/
object Camera {
  var target:TrackingObject = new ZeroTrackingObject
  def lookAt(t:TrackingObject){ target = t }

  var position = Vector3(0, 0, 1f)

  def init(implicit gl:GL4){
    display(gl)
  }

  // http://stackoverflow.com/questions/21079623/how-to-calculate-the-normal-matrix

  var viewMatrix: Int = _
  var normalMatrix: Int = _

  def program(id:Int, view:String="viewMatrix", normal:String="normalMatrix")(implicit gl:GL4){
    viewMatrix = gl.glGetUniformLocation(id, view)
    normalMatrix = gl.glGetUniformLocation(id, normal)
  }

  val up = Vector3(0f, 1f, 0f)

  // http://gamedev.stackexchange.com/questions/56609/how-to-create-a-projection-matrix-in-opengl-es-2-0
  // http://stackoverflow.com/questions/14713343/projection-theory-implimented-in-glsl

  def lookAt() = {
    val forward = Vector3.add(target.position, position).normalize()
    val right = Vector3.cross(forward, up).normalize()
    val tmp = Vector3.cross(right, forward).normalize()

    // viewing matrix
    val m = Matrix4(right.x, tmp.x, -forward.x, 0f,
                    right.y, tmp.y, -forward.y, 0f,
                    right.z, tmp.z, -forward.z, 0f,
                    0f     , 0f   , 0f        , 1f)

    Matrix4(Vector3(-position.x, -position.y, -position.z)).mul(m)
  }

  // mat4 normalMatrix = transpose(inverse(modelView));
  def display(implicit gl:GL4){
    val m = lookAt()
    gl.glUniformMatrix4fv(viewMatrix, 1, false, m.toArray, 0)
    gl.glUniformMatrix4fv(normalMatrix, 1, false, m.inverse().transpose().toArray, 0)
  }

}

trait TrackingObject{
  def position: Vector3
}

class ZeroTrackingObject extends TrackingObject{
  override val position = Vector3()
}
