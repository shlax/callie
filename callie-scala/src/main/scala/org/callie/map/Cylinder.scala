package org.callie.map

import org.callie.math.{Matrix4, Vector2, Vector3}
import scala.math.Ordering.Float.TotalOrdering

class CylinderMap(m:Map25, min:Float, max:Float){
  val pi = Math.PI.asInstanceOf[Float]

  val len = max - min
  val koef = 2f * pi / len
  val radius = len / (2f * pi)

  val model = new Matrix4()
  val normal = new Matrix4()

  def apply(p : Vector2):Unit = {
    if(p.x < min) p.x += len
    else if(p.x > max) p.x -= len

    val y = m.apply(p)
    if(!java.lang.Float.isNaN(y)){

      val ang = -pi + (p.x - min) * koef
      val rad = radius - y

      normal.rotX(ang)

      val rx = rad * normal.m22
      val ry = rad * normal.m21

      model.set(rx, ry, p.y)
      model.mul(normal, model)
    }
  }

}

class CylinderBuilder extends MapBuilder{

  var min:Float = 0f
  var max:Float = 0f

  override def set(p: List[Vector3]): Unit = {
    super.set(p)

    min = points.map(_.x).min
    max = points.map(_.x).max
  }

  override def test(i: Int, j: Int): Boolean = {
    val epsilon = 0.0001f

    if (i == j) true
    else {
      val x = points(i)
      val y = points(j)

      if (Vector3.len2(x, y) < epsilon) true
      else {
        (Vector3.len(Vector3.apply(min, x.y, x.z), x) < epsilon && Vector3.len(Vector3.apply(max, y.y, y.z), y) < epsilon) ||
        (Vector3.len(Vector3.apply(max, x.y, x.z), x) < epsilon && Vector3.len(Vector3.apply(min, y.y, y.z), y) < epsilon)
      }
    }
  }

  def apply(m:Map25) = {
    new CylinderMap(m, min, max)
  }

}
