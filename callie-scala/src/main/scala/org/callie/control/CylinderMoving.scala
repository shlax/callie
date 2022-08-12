package org.callie.control

import org.callie.input.{Camera, Inputs, TrackingObject}
import org.callie.map.Map3D
import org.callie.math.{Angle, Matrix4, Ray, Vector3}

import java.lang.{Float => jFloat}

class CylinderMoving(map:Map3D, height:Float) extends TrackingObject{
  val point = Vector3()

  val angle = Angle(-Angle.PI5)
  val dir = Vector3()

  def angle2dir(): Vector3 = {
    val a = angle.apply()

    dir.x = Math.cos(a).asInstanceOf[Float]
    dir.y = Math.sin(a).asInstanceOf[Float]

    dir
  }

  angle2dir()

  val ray = Ray(point, dir)

  override val position = Vector3()

  override def lookAt(m: Matrix4): Boolean = {
    m.rotZ(angle())
    true
  }

  var radius:Float = 0
  def calculate(f:Float) = {
    radius = f
    val d = f - height

    position.y = -d
    position.z = point.z
  }

  calculate(map.apply(ray))

  def apply(delta:Float):Unit = {

    if(Inputs.keyW){
      val ca = Camera.angY()
      val cx = -Math.sin(ca).asInstanceOf[Float]
      val cz = -Math.cos(ca).asInstanceOf[Float]

      val sx = 10f * cx * delta // v*t
      val sz = 10f * cz * delta

      val a = angle.apply()
      val da = Math.asin(sx/radius).asInstanceOf[Float]

      val z = point.z

      angle() = a + da
      point.z = z + sz

      angle2dir()
      val f = map.fast(ray)

      if(!jFloat.isNaN(f)){
        calculate(f)
      }else{
        angle.angle = a
        point.z = z
      }

    }

  }

}
