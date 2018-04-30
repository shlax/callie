package org.callie.ringing

import org.callie.math.intr.Intr

class KeyValue(i:Intr, v:Float){
  def apply(){ i() = v }
}

class KeyJoint(v:Array[KeyValue]){
  def apply(){ for(t <- v) t() }
}

class KeyFrame(x:KeyValue, y: KeyValue, z:KeyValue, joints:Array[KeyJoint]) {
  def apply(){
    x(); y(); z()
    for (j <- joints) j()
  }
}
