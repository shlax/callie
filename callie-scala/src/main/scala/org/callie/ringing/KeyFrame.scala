package org.callie.ringing

import org.callie.math.intr.Intr

class KeyValue(i:Intr, v:Float){

  def apply(outV:Boolean, rescale:Float){
    i.update(v, outV, rescale)
  }

}

class KeyFrame(intrs:Array[KeyValue]) {

  def add(v:KeyValue*) = new KeyFrame(v ++: intrs)

  def apply(outV:Boolean = true, rescale:Float = 1f){
    for (j <- intrs) j(outV, rescale)
  }

}
