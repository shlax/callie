package org.callie

import org.callie.math.Angle

object AngleTst extends App{

  val from = Angle()
  val to = Angle()

  from() = 3f
  to() = 3.1f
  from.rotateTo(to, 0.25f)
  println(from)

  from() = 3.1f
  to() = 3f
  from.rotateTo(to, 0.25f)
  println(from)

  from() = 3f
  to() = -3f
  from.rotateTo(to, 0.25f)
  println(from)

  from() = -3f
  to() = 3f
  from.rotateTo(to, 0.25f)
  println(from)

}
