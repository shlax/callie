package org.callie.map

import java.util.function.Predicate
import org.callie.math.{Axis, Vector3}

import scala.math.Ordering.Float.TotalOrdering

class Cylinder(a:Axis, up:Axis, points: List[Vector3]) extends Predicate[(Int, Int)]{
  val pi = Math.PI.asInstanceOf[Float]

  def value(t:Vector3) = a match {
    case Axis.X => t.x
    case Axis.Y => t.y
    case Axis.Z => t.z
  }

  def valueUp(t:Vector3) = up match {
    case Axis.X => t.x
    case Axis.Y => t.y
    case Axis.Z => t.z
  }

  val min = points.map(value).min
  val max = points.map(value).max

  val len = max - min
  val koef = 2f * pi / len
  val radius = len / (2f * pi)

  def angle(p:Vector3) = {
    val v = value(p)
    -pi + (v - min) * koef
  }

  val polar = points.map{ p =>
    val ang = angle(p)
    val r = radius - valueUp(p)

    val x = r * Math.cos(ang).asInstanceOf[Float]
    val y = r * Math.sin(ang).asInstanceOf[Float]

    val pol = a match {
      case Axis.X =>
        up match {
          case Axis.Y =>
            Vector3(x, y, p.z)
          case Axis.Z =>
            Vector3(x, p.y, y)
        }
      case Axis.Y =>
        up match {
          case Axis.X =>
            Vector3(y, x, p.z)
          case Axis.Z =>
            Vector3(p.x, x, y)
        }
      case Axis.Z =>
        up match {
          case Axis.X =>
            Vector3(y, p.y, x)
          case Axis.Y =>
            Vector3(p.x, y, x)
        }
    }
    (p, pol)
  }

  override def test(t: (Int, Int)): Boolean = {
    val epsilon = 0.0001f

    if(t._1 == t._2) true
    else{
      val x = points(t._1)
      val y = points(t._2)

      if(Vector3.len2(x, y) < epsilon) true
      else{
        a match {
          case Axis.X =>
            (Vector3.len(Vector3.apply(min, x.y, x.z), x) < epsilon && Vector3.len(Vector3.apply(max, y.y, y.z), y) < epsilon) ||
              (Vector3.len(Vector3.apply(max, x.y, x.z), x) < epsilon && Vector3.len(Vector3.apply(min, y.y, y.z), y) < epsilon)
          case Axis.Y =>
            (Vector3.len(Vector3.apply(x.x, min, x.z), x) < epsilon && Vector3.len(Vector3.apply(y.x, max, y.z), y) < epsilon) ||
              (Vector3.len(Vector3.apply(x.x, max, x.z), x) < epsilon && Vector3.len(Vector3.apply(y.x, min, y.z), y) < epsilon)
          case Axis.Z =>
            (Vector3.len(Vector3.apply(x.x, x.y, min), x) < epsilon && Vector3.len(Vector3.apply(y.x, y.y, max), y) < epsilon) ||
              (Vector3.len(Vector3.apply(x.x, x.y, max), x) < epsilon && Vector3.len(Vector3.apply(y.x, y.y, min), y) < epsilon)
        }
      }
    }
  }



}

class CylinderBuilder(a:Axis, up:Axis) extends MapBuilder{

  override def apply(points: List[Vector3]): Cylinder = {
    new Cylinder(a, up, points)
  }

}
