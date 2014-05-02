package org.callie.math

object Axis extends Enumeration{
  case class AxisValue(axis:Value, value:Float)

  val X, Y, Z = Value

  def apply(s:String) = s match {
    case "x" => X
    case "y" => Y
    case "z" => Z
  }
}
