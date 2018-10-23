package org.callie.ringing

import org.callie.math.intr.Intr

class KeyValue(i:Intr, v:Float){
  def apply(rescale:Float){ i.update(v, rescale) }
}

class KeyFrame(intrs:Array[KeyValue]) {
  def apply(rescale:Float = 1f){ for (j <- intrs) j(rescale) }
}
