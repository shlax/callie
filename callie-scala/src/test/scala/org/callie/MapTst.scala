package org.callie

import org.callie.map.{Map25, MapBuilder, Triangle25}
import org.callie.math.{Vector2, Vector3}

import scala.io.Source

object MapTst extends App{

  val t25 = new Triangle25(Vector3(-1, 0, 0), Vector3(1, 0, 0), Vector3(0, 1, 2), Nil.toArray, Nil.toArray)
  println(t25.apply(new Vector2(0, 1)))

  val s = Source.fromFile("data/floorMap.mod")

  val m = Map25.apply(s.mkString)

  val t = m(Vector2(0, 0))

  println(t) // 0
  println(m(Vector2(1000, -1000))) // None
}
