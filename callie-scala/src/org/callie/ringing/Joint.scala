package org.callie.ringing

import org.callie.math.Matrix4
import org.callie.math.intr.Intr
import org.callie.math.Vector3

trait Joint {

  /** time s intervalu <0,1>  */
  def apply(trans : Matrix4, normalTrans : Matrix4, time:Float) : (Matrix4, Matrix4)
//def set(ax:Float, ay:Float, az:Float) 
  
}

class IntrJoint(val offset:Matrix4, val ax: Intr, val ay:Intr, val az : Intr, 
				val points:Array[(Vector3, Vector3)], val normals:Array[(Vector3, Vector3)] ) extends Joint{
  
  var rotX : Float = _
  var rotY : Float = _
  var rotZ : Float = _
  
  override def apply(trans : Matrix4, normalTrans : Matrix4, time:Float) = {
    val n = Matrix4()
    rotX = ax(time); rotY = ay(time); rotZ = az(time)
    val m = new Matrix4().rotZ(rotX) * n.rotY(rotY) * n.rotX(rotZ)
    
    n.mul(trans, offset) * m // next trans
    m.mul(normalTrans, m) // next normalTrans
    
    //cluster(n, m)
    for(v <- points) n(v._1, v._2)
    for(v <- normals) m(v._1, v._2)
    
    (n, m)
  }
   
}

/** join hierarchy */
class IntrTravJoint(offset:Matrix4, ax: Intr, ay:Intr, az : Intr, val childs:Array[Joint], 
                    points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends IntrJoint(offset, ax, ay, az, points, normals){

  override def apply(trans : Matrix4, normalTrans : Matrix4, time:Float) = {
    val tmp = super.apply(trans, normalTrans, time)
//    var i = 0
//    while(i < childs.length){
//      childs(i).apply(tmp._1, tmp._2, time)
//      i += 1
//    }
    for(j <- childs) j.apply(tmp._1, tmp._2, time) 
    tmp
  }
  
}

object LinearJoint{
  object Mapping extends Enumeration { val X, Y, Z = Value }
}

class LinearJoint(parent:IntrTravJoint, ix:(LinearJoint.Mapping.Value, Float), iy:(LinearJoint.Mapping.Value, Float), iz:(LinearJoint.Mapping.Value, Float)) extends Joint{
  
  def value(m:LinearJoint.Mapping.Value) = m match{
    case LinearJoint.Mapping.X => parent.rotX
    case LinearJoint.Mapping.Y => parent.rotY
    case LinearJoint.Mapping.Z => parent.rotZ
  }
  
  override def apply(trans : Matrix4, normalTrans : Matrix4, time:Float) = {
    
    (trans, normalTrans)
  }
  
}
