package org.callie.ringing

import org.callie.math.Vector
import org.callie.math.Matrix4f

class JointCluster(val points:Traversable[(Vector, Vector)], val normals:Traversable[(Vector, Vector)]){
  
  def apply(p:Matrix4f, n:Matrix4f){
    for(v <- points) p(v._1, v._2)
    for(v <- normals) n(v._1, v._2)
  }
}