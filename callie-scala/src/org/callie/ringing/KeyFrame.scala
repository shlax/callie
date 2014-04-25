package org.callie.ringing

import org.callie.math.intr.Intr

class KeyValue(a:Intr, v:Float){
  def apply(){ a() = v }
}

class KeyJoint(x:KeyValue, y:KeyValue, z:KeyValue){
  def apply(){ x(); y(); z() }
}

class KeyFrame(joints:List[KeyJoint]) {
  def apply(){ for (j <- joints) j() }
}
