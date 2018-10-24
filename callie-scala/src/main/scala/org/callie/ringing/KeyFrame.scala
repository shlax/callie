package org.callie.ringing

import org.callie.math.Axis
import org.callie.math.intr.Intr

class KeyValue(val joint:String, val axis: Axis, i:Intr, val value:Float){

  var next:Option[KeyValue] = None

  def apply(rescale:Float){
    i.update(value, if(next.isEmpty) value else next.get.value , rescale)
  }
}

class KeyFrame(val intrs:Array[KeyValue]) {

  def add(v:KeyValue*) = new KeyFrame(v ++: intrs)

  def next(n:KeyFrame){
    for(i <- intrs; j <- n.intrs if i.joint == j.joint && i.axis == j.axis) i.next = Some(j)
  }

  def apply(rescale:Float = 1f){
    for (j <- intrs) j(rescale)
  }

}
