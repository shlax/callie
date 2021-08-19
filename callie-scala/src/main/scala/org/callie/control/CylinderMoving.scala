package org.callie.control

import org.callie.input.{Inputs, TrackingObject}
import org.callie.map.Map3D
import org.callie.math.{Angle, Ray, Vector3}
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

  var radius:Float = 0
  def calculate(f:Float){
    radius = f
    val d = f - height

    val a = angle.apply()
    position.x = d * Math.cos(a).asInstanceOf[Float]
    position.y = d * Math.sin(a).asInstanceOf[Float]
    position.z = point.z
  }

  calculate(map.apply(ray))

  def apply(delta:Float):Unit = {

    if(Inputs.keyW){
      val s = 10f * delta // v*t

      val a = angle.apply()
      val da = Math.asin(s/radius).asInstanceOf[Float]
      angle() = a + da

      angle2dir()
      val f = map.fast(ray)

      if(!jFloat.isNaN(f)){
        calculate(f)
      }else{
        angle() = a
      }

    }

  }

}
