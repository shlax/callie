package org.callie

import org.callie.model.Mod

import scala.io.Source

object ModTst extends App{

  val s = Source.fromFile("data/floor.mod")

  val m = Mod.apply(s.mkString)

  println(m.points.size.toString+" / "+m.faces.size)
}
