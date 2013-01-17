package org.callie.math

trait Vector {

  def x:Float; def x_=(v:Float)
  def y:Float; def y_=(v:Float)
  def z:Float; def z_=(v:Float)
  
  
  def len2 = x*x + y*y + z*z
  def len = Math.sqrt(len2).asInstanceOf[Float]
}