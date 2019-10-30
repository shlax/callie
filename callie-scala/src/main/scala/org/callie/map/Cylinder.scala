package org.callie.map

import java.util.function.Predicate

import org.callie.math.{Matrix4, Vector2, Vector3}

import scala.math.Ordering.Float.TotalOrdering

class Cylinder(points: List[Vector3]) extends Predicate[(Int, Int)]{
  val min = points.map(_.x).min
  val max = points.map(_.x).max

  override def test(t: (Int, Int)): Boolean = {
    val epsilon = 0.0001f

    if(t._1 == t._2) true
    else{
      val x = points(t._1)
      val y = points(t._2)

      if(Vector3.len2(x, y) < epsilon) true
      else{
        (Vector3.len(Vector3.apply(min, x.y, x.z), x) < epsilon && Vector3.len(Vector3.apply(max, y.y, y.z), y) < epsilon) ||
        (Vector3.len(Vector3.apply(max, x.y, x.z), x) < epsilon && Vector3.len(Vector3.apply(min, y.y, y.z), y) < epsilon)
      }
    }
  }

  def apply(m:Map25) = {
    new CylinderMap(m, min, max)
  }

}

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

  override def apply(points: List[Vector3]): Cylinder = {
    new Cylinder(points)
  }

}
