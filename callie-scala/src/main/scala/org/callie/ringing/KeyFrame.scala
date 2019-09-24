package org.callie.ringing

import org.callie.math.{Axis, Vector3}
import org.callie.math.intr.Intr

class KeyValue(val joint:String, val axis: Axis, i:Intr, val value:Float){
  var next = this

  def apply():Unit={
    i.update(value, next.value)
  }
}

class KeyFrame(val intrs:Array[KeyValue]) {

  def add(v:KeyValue*) = new KeyFrame(v ++: intrs)

  def next(n:KeyFrame):Unit={
    for(i <- intrs; j <- n.intrs if i.joint == j.joint && i.axis == j.axis) i.next = j
  }

  def apply():Unit={
    for (j <- intrs) j()
  }

}

case class OffsetFrame(offset: Vector3, frame:KeyFrame)
