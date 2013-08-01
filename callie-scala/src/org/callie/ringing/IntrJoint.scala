package org.callie.ringing

import org.callie.math.Matrix4
import org.callie.math.intr.Intr
import org.callie.math.Vector3

class IntrJoint(val offset:Matrix4, val ax: Intr, val ay:Intr, val az : Intr, 
				val points:Traversable[(Vector3, Vector3)], val normals:Traversable[(Vector3, Vector3)] ) extends Joint{
  
  var rotX : Float = _
  var rotY : Float = _
  var rotZ : Float = _
  
  override def apply(trans : Matrix4, normalTrans : Matrix4, time:Float) = {
    val n = Matrix4()
    rotX = ax(time); rotY = ay(time); rotZ = az(time)
    val m = new Matrix4().rotZ(rotX) * (n.rotY(rotY)) * n.rotX(rotZ)
    
    n ** (trans, offset) * m // next trans
    m ** (normalTrans, m) // next normalTrans
    
    //cluster(n, m)
    for(v <- points) n(v._1, v._2)
    for(v <- normals) m(v._1, v._2)
    
    (n, m)
  }
   
}