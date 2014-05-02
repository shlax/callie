package org.callie.input

object Inputs{

  @volatile var w = false
  @volatile var a = false
  @volatile var s = false
  @volatile var d = false

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

  @volatile var mouseX = 0
  @volatile var mouseY = 0
}
