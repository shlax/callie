package org.callie.ringing

import org.callie.math.Matrix4
import org.callie.math.Vector3
import org.callie.math.intr.Intr

/** join hierarchy */
class IntrTravJoint(offset:Matrix4, ax: Intr, ay:Intr, az : Intr, val childs:Traversable[Joint], 
                    points:Traversable[(Vector3, Vector3)], normals:Traversable[(Vector3, Vector3)] ) extends IntrJoint(offset, ax, ay, az, points, normals){

  override def apply(trans : Matrix4, normalTrans : Matrix4, time:Float) = {
    val tmp = super.apply(trans, normalTrans, time)
    for(j <- childs) j.apply(tmp._1, tmp._2, time) 
    tmp
  }
  
}
