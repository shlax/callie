package org.callie.ringing

import org.callie.math.Matrix4
import org.callie.math.intr.Intr
import org.callie.math.Vector3

trait Joint {

  /** time s intervalu <0,1>  */
  def apply(trans : Matrix4, normalTrans : Matrix4, time:Float)// : (Matrix4, Matrix4)
//def set(ax:Float, ay:Float, az:Float) 
  
}

class IntrJoint(offset:Matrix4, ax: Intr, ay:Intr, az : Intr, 
				points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends Joint{
  
  var rotX : Float = _
  var rotY : Float = _
  var rotZ : Float = _
  
  def transform(trans : Matrix4, normalTrans : Matrix4, time:Float, n:Matrix4, m:Matrix4){
    //val n = Matrix4()
    rotX = ax(time); rotY = ay(time); rotZ = az(time)
    m.rotZ(rotZ).mul(n.rotY(rotY)).mul(n.rotX(rotX))
    
    n.mul(trans, offset).mul(m) // next trans
    m.mul(normalTrans, m) // next normalTrans
    
    //cluster(n, m)
    for(v <- points) n(v._1, v._2)
    for(v <- normals) m(v._1, v._2)
        
  }
  
  def apply(trans : Matrix4, normalTrans : Matrix4, time:Float){
    val n = Matrix4() 
    val m = Matrix4()
    transform(trans, normalTrans, time, n, m)
  }
  
}

/** join hierarchy */
class IntrTravJoint(offset:Matrix4, ax: Intr, ay:Intr, az : Intr, childs:Array[Joint], 
                    points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends IntrJoint(offset, ax, ay, az, points, normals){

  override def apply(trans : Matrix4, normalTrans : Matrix4, time:Float){
    val n = Matrix4() 
    val m = Matrix4()
    transform(trans, normalTrans, time, n, m)
    for(j <- childs) j.apply(n, m, time) 
  }
  
}

object LinearJoint extends Enumeration{
   val X, Y, Z = Value
}

class LinearJoint(parent:IntrTravJoint, ix:(LinearJoint.Value, Float), iy:(LinearJoint.Value, Float), iz:(LinearJoint.Value, Float),
    								points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends Joint{
  
  def value(m:LinearJoint.Value) = m match {
    case LinearJoint.X => parent.rotX
    case LinearJoint.Y => parent.rotY
    case LinearJoint.Z => parent.rotZ
  }
  
  override def apply(trans:Matrix4, normalTrans:Matrix4, time:Float){
    val n = Matrix4() 
    val m = Matrix4()
    m.rotZ(iz._2 * value(iz._1)).mul(n.rotY(iy._2 * value(iy._1))).mul(n.rotX(ix._2 * value(ix._1)))
    
    n.mul(trans, m) // next trans
    m.mul(normalTrans, m) // next normalTrans
    
    //cluster(n, m)
    for(v <- points) n(v._1, v._2)
    for(v <- normals) m(v._1, v._2)    
  }
  
}
