package org.callie.math.intr

trait Intr {

  def update(value: Float, nextValue:Float, rescale:Float)
  
  def apply(t: Float): Float
  
}