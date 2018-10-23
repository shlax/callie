package org.callie.math.intr

trait Intr {

  def update(value: Float, outV:Boolean, rescale:Float)
  
  def apply(t: Float): Float
  
}