package org.callie.math.intr

/*
    v1 = v0 + a1/2
    v2 = v1 + a2/2

    s1 = v0/2 + a1/8
    s2 = s1 + v1/2 + a2/8
*/
class Accl extends Intr{
  var lastS = 0f
  var lastV = 0f

  var v0 = 0f
  var v1 = 0f

  var a1 = 0f
  var a2 = 0f

  var s0 = 0f
  var s1 = 0f

  //var active = false

  override def update(value: Float, nextValue:Float):Unit={
    s0 += lastS

    val s2 = value - s0

    /* if(Math.abs(s2) < 1e-4f && Math.abs(lastV) < 1e-4f ){
      active = false

      s0 = value

      lastS = 0f
      lastV = 0f
    }else { */
    // active = true
    val s3 = nextValue - value
    val v2 = if(s3 != 0f && Math.signum(s2) == Math.signum(s3)) nextValue - s0 else 0f  // (nextValue - s0)*2f/3f ?

    v0 = lastV

    a1 = 4f * s2 - 3f * v0 - v2
    v1 = v0 + a1 / 2f

    a2 = 2f * v2 - 2f * v1

    s1 = v0 / 2f + a1 / 8f
    // }
  }

  override def apply(t: Float): Float = {
    // if(!active) return s0

    if(t <= 0.5f){
      val at = a1 * t
      lastV = v0 + at
      lastS = v0 * t + (at * t)/2
    }else{
      val dt = t - 0.5f
      val adt = a2 * dt
      lastV = v1 + adt
      lastS = v1 * dt + (adt * dt)/2 + s1
    }

    s0 + lastS
  }

}
