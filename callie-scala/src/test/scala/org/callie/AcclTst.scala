package org.callie

import org.callie.math.intr.Accl

object AcclTst extends App{

  val a = new Accl()

  a.update(5, true)
  //println(a.active)
  for(t <- (0 to 20).map(v => v / 20f)) println(a(t))

  a.update(-5, true)
  //println(a.active)
  for(t <- (1 to 20).map(v => v / 20f)) println(a(t))

  a.update(5, false)
  //println(a.active)
  for(t <- (1 to 20).map(v => v / 20f)) println(a(t))

  a.update(-5, true)
  //println(a.active)
  for(t <- (1 to 20).map(v => v / 20f)) println(a(t))

  a.update(-5, false)
  //println(a.active)
  for(t <- (1 to 20).map(v => v / 20f)) println(a(t))

  a.update(-5, false)
  //println(a.active)
  for(t <- (1 to 20).map(v => v / 20f)) println(a(t))

  a.update(5, true)
  //println(a.active)
  for(t <- (1 to 20).map(v => v / 20f)) println(a(t))

}
