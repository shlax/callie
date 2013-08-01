package org.callie.ringing

import org.callie.math.Matrix4

trait Joint {

  /** time s intervalu <0,1>  */
  def apply(trans : Matrix4, normalTrans : Matrix4, time:Float) : (Matrix4, Matrix4)
//def set(ax:Float, ay:Float, az:Float) 
  
}
