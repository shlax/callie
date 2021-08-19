package org.callie.control

import org.callie.input.TrackingObject
import org.callie.map.Map3D
import org.callie.math.{Angle, Ray, Vector3}

class CylinderMoving(map:Map3D, height:Float) extends TrackingObject{
  val point = Vector3()

  val angle = Angle(-Angle.PI1/2f)
  val dir = Vector3()

  def angle2dir(): Vector3 = {
    val a = angle.apply()

    dir.x = Math.sin(a).asInstanceOf[Float]
    dir.y = Math.cos(a).asInstanceOf[Float]

    dir
  }

  angle2dir()

  val ray = Ray(point, dir)

  override val position = Vector3()

  def calculate(f:Float){
    val d = f - height

    val a = angle.apply()
    position.x = d * Math.sin(a).asInstanceOf[Float]
    position.y = d * Math.cos(a).asInstanceOf[Float]
    position.z = point.z
  }

  calculate(map.apply(ray))

  def apply(delta:Float):Unit = {

  }

}
