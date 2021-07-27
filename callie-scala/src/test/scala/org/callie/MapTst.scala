package org.callie

import org.callie.map.{Map25, Triangle25}
import org.callie.math.{Vector2, Vector3}

object MapTst extends App{

  val t25 = new Triangle25(Vector3(-1, 0, 0), Vector3(1, 0, 0), Vector3(0, 1, 2), Nil.toArray, Nil.toArray)
  println(t25.apply(new Vector2(0, 1)))

  val m = Map25.apply(getClass.getResourceAsStream("data/floorMap.mod"))

  val t = m(Vector2(0, 0))

  println(t) // 0
  println(m(Vector2(1000, -1000))) // None
}
