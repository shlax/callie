//package org.callie.ringing
//
//import org.callie.math.Vector3
//import org.callie.math.Matrix4
//
//class JointCluster(val points:Traversable[(Vector3, Vector3)], val normals:Traversable[(Vector3, Vector3)]){
//  
//  def apply(p:Matrix4, n:Matrix4){
//    for(v <- points) p(v._1, v._2)
//    for(v <- normals) n(v._1, v._2)
//  }
//}