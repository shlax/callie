package org.callie.ringing

import org.callie.math.intr.Intr

class KeyValue(i:Intr, v:Float){
  def apply(){ i() = v }
}

class KeyJoint(v:Array[KeyValue]){
  def apply(){ for(t <- v) t() }
}

class KeyFrame(joints:Array[KeyJoint]) {
  def apply(){ for (j <- joints) j() }
}
