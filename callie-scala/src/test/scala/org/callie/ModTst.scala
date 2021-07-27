package org.callie

import org.callie.model.Model

object ModTst extends App{

  val m = Model.load(getClass.getResourceAsStream("data/floor.mod"))

  println(m.points.size.toString+" / "+m.faces.size)
}
