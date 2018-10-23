package org.callie.math.intr

class Accl extends Intr{
  val epsilon = 0.000001f

  var active = false
//
  var start = 0f
  var end = 0f

  var lastValue = 0f

  override def update(value: Float, rescale:Float) {
    //  koncim kde som zacinal                &  nemam ziadnu pociatocnu rychlost
    if (Math.abs(value - lastValue) < epsilon) { // && v0 < epsilon
      active = false

      lastValue = value; // reset value
    } else {
      active = true
      start = lastValue
      end = value - start
    }
  }

  override def apply(t: Float): Float = {
    if (!active) return this.lastValue

    lastValue = start + (t * end)
    lastValue
  }
  
}
