package org.callie.map

import org.callie.math.{Axis, Vector3}

object Cylinder {
  val epsilon = 0.0001f

  def apply(m: Map25, a:Axis): Map25 = {
    val min = m.triangles.map{ t =>
      a match {
        case Axis.X => t.a.x.min(t.b.x).min(t.c.x)
        case Axis.Y => t.a.y.min(t.b.y).min(t.c.y)
        case Axis.Z => t.a.z.min(t.b.z).min(t.c.z)
      }
    }.min

    val max = m.triangles.map{ t =>
      a match {
        case Axis.X => t.a.x.max(t.b.x).max(t.c.x)
        case Axis.Y => t.a.y.max(t.b.y).max(t.c.y)
        case Axis.Z => t.a.z.max(t.b.z).max(t.c.z)
      }
    }.max

    def near(x:Vector3, y:Vector3):Boolean = {
      if(Vector3.len2(x, y) < epsilon) true
      else{
        a match {
          case Axis.X =>
            ( Vector3.len(Vector3.apply(min, x.y, x.z), x) < epsilon && Vector3.len(Vector3.apply(max, y.y, y.z), y) < epsilon) ||
            ( Vector3.len(Vector3.apply(max, x.y, x.z), x) < epsilon && Vector3.len(Vector3.apply(min, y.y, y.z), y) < epsilon )
          case Axis.Y =>
            ( Vector3.len(Vector3.apply(x.x, min, x.z), x) < epsilon && Vector3.len(Vector3.apply(y.x, max, y.z), y) < epsilon) ||
            ( Vector3.len(Vector3.apply(x.x, max, x.z), x) < epsilon && Vector3.len(Vector3.apply(y.x, min, y.z), y) < epsilon )
          case Axis.Z =>
            ( Vector3.len(Vector3.apply(x.x, x.y, min), x) < epsilon && Vector3.len(Vector3.apply(y.x, y.y, max), y) < epsilon) ||
            ( Vector3.len(Vector3.apply(x.x, x.y, max), x) < epsilon && Vector3.len(Vector3.apply(y.x, y.y, min), y) < epsilon )
        }
      }
    }

    val tr = m.triangles


    m
  }

}
