package org.callie.map

import java.util.function.Predicate

import org.callie.math.{Axis, Vector3}

class Cylinder(a:Axis, epsilon:Float = 0.0001f) extends MapBuilder{

  override def apply(points: List[Vector3]): Predicate[(Int, Int)] = {
    import scala.math.Ordering.Float.TotalOrdering

    val min = points.map{ t =>
      a match {
        case Axis.X => t.x
        case Axis.Y => t.y
        case Axis.Z => t.z
      }
    }.min

    val max = points.map{ t =>
      a match {
        case Axis.X => t.x
        case Axis.Y => t.y
        case Axis.Z => t.z
      }
    }.max

    def compare(x:Vector3, y:Vector3):Boolean = {
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

    { t: (Int, Int) =>
      if(t._1 == t._2) true
      else compare( points(t._1), points(t._2))
    }
  }

}
