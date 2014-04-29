package org.callie.input

import javax.media.opengl.GL4
import org.callie.math.{Matrix4, Vector3}

// http://www.gamedev.net/topic/617711-glulookat-replacement/
object Camera {
  var target:TrackingObject = new ZeroTrackingObject
  def lookAt(t:TrackingObject){ target = t }

  val position = Vector3(0, 0, 1f)

  def init(implicit gl:GL4){
    display(gl)
  }

  val up = Vector3(0f, 1f, 0f)

  def lookAt = {
    val forward = Vector3.add(target.position(), position).normalize()
    val right = Vector3.cross(forward, up).normalize()
    val tmp = Vector3.cross(right, forward).normalize()

    // viewing matrix
    val m = Matrix4(right.x, tmp.x, -forward.x, 0f,
      right.y, tmp.y, -forward.y, 0f,
      right.z, tmp.z, -forward.z, 0f,
      0f    , 0f   , 0f        , 1f)

    Matrix4(Vector3(-position.x, -position.y, -position.z)).mul(m)
  }

  def display(implicit gl:GL4){

  }

}

trait TrackingObject{
  def position():Vector3
}

class ZeroTrackingObject extends TrackingObject{
  override def position() = Vector3()
}
