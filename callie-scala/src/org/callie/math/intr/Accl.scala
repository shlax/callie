package org.callie.math.intr

class Accl extends Intr{
  val epsilon = 0.000001f
  val t1 = 1f/3f

  var start = 0f

  var a = 0f
  var at = 0f

  var v0 = 0f
  var v = 0f
  
  var lastTime = 0f
  var lastValue = 0f

  var active = false
  
  override def update(value: Float) {
    if (lastTime < t1) v0 += a * lastTime;
    else v0 = v;

    //  koncim kde som zacinal                &  nemam ziadnu pociatocnu rychlost
    if (Math.abs(value - lastValue) < epsilon && v0 < epsilon) {
      active = false;

      lastValue = value; // reset value
      v0 = 0; // reset speed

      // 0 - ziaden pohyb
      a = 0;
      v = 0;
    } else {
      active = true;
      start = lastValue;

      val t1Pow = (t1 * t1) / 2f;
      val v0t1 = v0 * t1;

      val t2 = 1 - t1;

      a = ((value - start) - v0 * t2 - v0t1) / (t1Pow + t1 * t2);
      v = v0 + a * t1;

      at = v0t1 + a * t1Pow + start;
    }
  }

  override def apply(t: Float): Float = {
    if (!active) return this.lastValue;

    this.lastTime = t;
    if (t < t1) lastValue = start + v0 * t + (a * t * t) / 2f;
    else lastValue = at + (t - t1) * v;

    return lastValue;
  }
  
}
