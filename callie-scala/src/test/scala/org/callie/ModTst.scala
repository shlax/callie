package org.callie

import org.callie.model.Model

import scala.io.Source

object ModTst extends App{

  val s = Source.fromFile("data/floor.mod")

  val m = Model.load(s.mkString)

  println(m.points.size.toString+" / "+m.faces.size)
}
