package org.callie.math.intr

class Const(var v : Float) extends Intr{
  def this() = this(0)
  
  override def := (value: Float){ v = value; }

  override def apply(t: Float): Float =  v
  
}
