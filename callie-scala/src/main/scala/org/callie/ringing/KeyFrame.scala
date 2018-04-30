package org.callie.ringing

import org.callie.math.intr.Intr

class KeyValue(i:Intr, v:Float){
  def apply(){ i() = v }
}

class KeyFrame(intrs:Array[KeyValue]) {
  def apply(){ for (j <- intrs) j() }
}
