package org.callie.math.intr

/*
    v1 = v0 + a1*t1
    v2 = v1 + a2*(1-t2)

    s1 = v0*t1 + a1*t1*t1/2
    s2 = v1*(t2-t1)
    s3 = s1 + s2 + v1*(1-t2) + a2*(1-t2)*(1-t2)/2

    =>

    a2 = (v1 - v2)/(t2 - 1)

    s3 = (v0*t1 + a1*t1*t1/2) + ((v0 + a1*t1)*(t2-t1)) + (v0 + a1*t1)*(1-t2) + ((( v0 + a1*t1) - v2)/(t2 - 1))*(1-t2)*(1-t2)/2

    a1 = (-2*s3 + t2*v0 - t2*v2 + v0 + v2)/(t1*(t1 - t2 - 1))

*/
class Accl extends Intr{
  var lastS = 0f
  var lastV = 0f

  val t1 = 0.5f
  val t2 = 0.5f

  var v0 = 0f
  var v1 = 0f

  var a1 = 0f
  var a2 = 0f

  var s0 = 0f
  var s1 = 0f
  var s2 = 0f

  //var active = false

  override def update(value: Float, nextValue:Float, rescale:Float = 1f){
    s0 += lastS

    val s3 = value - s0

    /* if(Math.abs(s3) < 1e-4f && Math.abs(lastV) < 1e-4f ){
      active = false

      s0 = value

      lastS = 0f
      lastV = 0f
    }else { */
      // active = true
    val sn = nextValue - value
    val v2 = if(sn != 0f && Math.signum(s3) == Math.signum(sn)){
      //t1 = 0.5f
      nextValue - s0
    } else{
      //t1 = 0.5f
      0f
    }

    v0 = lastV * rescale

    a1 = (-2*s3 + t2*v0 - t2*v2 + v0 + v2)/(t1*(t1 - t2 - 1))
    v1 = v0 + a1*t1

    a2 = (v1 - v2)/(t2 - 1)

    s1 = v0*t1 + a1*t1*t1/2
    s2 = s1 + v1*(t2-t1)
  }

  override def apply(t: Float): Float = {
    // if(!active) return s0

    if(t <= t1){
      val at = a1 * t
      lastV = v0 + at
      lastS = v0 * t + (at * t)/2
    }else if(t <= t2){
      lastV = v1
      lastS = v1 * (t - t1) + s1
    }else{
      val dt = t - t2
      val adt = a2 * dt
      lastV = v1 + adt
      lastS = v1 * dt + (adt * dt)/2 + s2
    }

    s0 + lastS
  }
  
}
