package org.callie.input

object Inputs{

  var w = false

  var mouse2 = false

  var mouseX = 0
  var mouseY = 0
  var mouseZ = 0

  def xDiff() = {
    val x = mouseX
    mouseX -= x
    x
  }

  def yDiff() = {
    val y = mouseY
    mouseY -= y
    y
  }

  /** mouse wheel */
  def zDiff() = {
    val z = mouseZ
    mouseZ -= z
    z
  }

}
