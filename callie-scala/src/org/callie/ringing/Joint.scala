package org.callie.ringing

import org.callie.math.Matrix4f

trait Joint {

  /** time s intervalu <0,1>  */
  def apply(trans : Matrix4f, normalTrans : Matrix4f, time:Float) : (Matrix4f, Matrix4f)  
//def set(ax:Float, ay:Float, az:Float) 
  
}
