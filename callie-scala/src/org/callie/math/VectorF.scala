package org.callie.math

class VectorF(var x : Float, var y : Float, var z : Float) extends Vector {
	
  def this() = this(0, 0, 0)
  def this(s : Float) = this(s, s, s)
  
}