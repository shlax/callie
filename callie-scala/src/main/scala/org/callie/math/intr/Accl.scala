package org.callie.math.intr

/*
    v1 = v0 + a1*t1
    v2 = v1 + a2*(1-t1)

    s1 = v0*t1 + a1*t1*t1/2
    s2 = s1 + v1*(1-t1) + a2*(1-t1)*(1-t1)/2

    =>

    a2 = (v1 - v2)/(t1 - 1)

    s2 = v0*t1 + a1*t1*t1/2 + (v0 + a1*t1)*(1-t1) + (((v0 + a1*t1) - v2)/(t1 - 1))*(1-t1)*(1-t1)/2

    a1 = -(-2*s2 + t1*v0 - t1*v2 + v0 + v2)/t1

*/
class Accl extends Intr{
  var lastS = 0f
  var lastV = 0f

  var t1 = 0.5f

  var v0 = 0f
  var v1 = 0f

  var a1 = 0f
  var a2 = 0f

  var s0 = 0f
  var s1 = 0f

  //var active = false

  override def update(value: Float, nextValue:Float, rescale:Float = 1f){
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
    val v2 = if(s3 != 0f && Math.signum(s2) == Math.signum(s3)){
      t1 = 0.5f
      nextValue - s0
    } else{
      t1 = 0.5f
      0f
    }

    v0 = lastV * rescale

    a1 = -(-2*s2 + t1*v0 - t1*v2 + v0 + v2)/t1
    v1 = v0 + a1*t1

    a2 = (v1 - v2)/(t1 - 1)

    s1 = v0*t1 + a1*t1*t1/2
    // }
  }

  override def apply(t: Float): Float = {
    // if(!active) return s0

    if(t <= t1){
      val at = a1 * t
      lastV = v0 + at
      lastS = v0 * t + (at * t)/2
    }else{
      val dt = t - t1
      val adt = a2 * dt
      lastV = v1 + adt
      lastS = v1 * dt + (adt * dt)/2 + s1
    }

    s0 + lastS
  }
  
}
