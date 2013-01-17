package org.callie.ringing

import org.callie.math.Matrix4f
import org.callie.math.Vector
import org.callie.math.intr.Intr

/** join hierarchy */
class IntrTravJoint(offset:Matrix4f, ax: Intr, ay:Intr, az : Intr, val childs:Traversable[Joint], cluster:JointCluster ) extends IntrJoint(offset, ax, ay, az, cluster){

  override def apply(trans : Matrix4f, normalTrans : Matrix4f, time:Float) = {
    val tmp = super.apply(trans, normalTrans, time)
    for(j <- childs) j.apply(tmp._1, tmp._2, time) 
    tmp
  }
  
}
