package org.callie

import org.callie.map.Map25
import org.callie.math.Vector2

import scala.io.Source

object MapTst extends App{

  val s = Source.fromFile("data/floorMap.mod")

  val m = Map25(s.mkString)
  val t = m(Vector2(0, 0))

  println(t.get) // 0
  println(m(Vector2(1000, -1000))) // None
}
