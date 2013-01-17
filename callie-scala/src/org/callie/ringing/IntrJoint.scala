package org.callie.ringing

import org.callie.math.Matrix4f
import org.callie.math.intr.Intr
import org.callie.math.Vector

class IntrJoint(val offset:Matrix4f, val ax: Intr, val ay:Intr, val az : Intr, 
				val cluster:JointCluster ) extends Joint{
  
  var rotX : Float = _
  var rotY : Float = _
  var rotZ : Float = _
  
  override def apply(trans : Matrix4f, normalTrans : Matrix4f, time:Float) = {
    val n = new Matrix4f()
    rotX = ax(time); rotY = ay(time); rotZ = az(time)
    val m = new Matrix4f().rotZ(rotX) * (n.rotY(rotY)) * n.rotX(rotZ)
    
    n ** (trans, offset) * m // next trans
    m ** (normalTrans, m) // next normalTrans
    
    cluster(n, m)
    
    (n, m)
  }
   
}