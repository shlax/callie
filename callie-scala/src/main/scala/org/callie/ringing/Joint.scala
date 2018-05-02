package org.callie.ringing

import org.callie.math.Matrix4
import org.callie.math.intr.Intr
import org.callie.math.Vector3
import org.callie.math.Axis
import Axis.AxisValue

trait Joint {
  def name:String

  /** time s intervalu <0,1>  */
  def apply(trans : Matrix4, normalTrans : Matrix4, time:Float)// : (Matrix4, Matrix4)
}

trait JointIntr extends Joint{
  val ax: Intr
  val ay: Intr
  val az: Intr
}

/** join hierarchy offset */
class OffsetJoint(override val ax: Intr, override val  ay:Intr, override val az : Intr, child:Joint) extends JointIntr{
  override def name = ""

  val m = Matrix4()
  override def apply(trans: Matrix4, normalTrans: Matrix4, time: Float){
    m.set(ax(time), ay(time), az(time)).mul(trans)
    child.apply(m, normalTrans, time)
  }
}

trait JointTrav extends Joint{
  val childs:Array[Joint]
}

class IntrJoint(override val name:String,
                offset:Matrix4, override val ax: Intr, override val  ay:Intr, override val  az : Intr,
				        points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends JointIntr{
  
  var rotX : Float = _
  var rotY : Float = _
  var rotZ : Float = _

  val n = Matrix4(); val m = Matrix4()
  def apply(trans : Matrix4, normalTrans : Matrix4, time:Float){
    //val n = Matrix4()
    rotX = ax(time); rotY = ay(time); rotZ = az(time)
    m.rotZ(rotZ).mul(n.rotY(rotY)).mul(n.rotX(rotX))
    
    n.mul(trans, offset).mul(m) // next trans
    m.mul(normalTrans, m) // next normalTrans
    
    //cluster(n, m)
    for(v <- points) n(v._1, v._2)
    for(v <- normals) m(v._1, v._2)
  }

}

/** join hierarchy */
class IntrTravJoint(name:String,
                    offset:Matrix4, ax: Intr, ay:Intr, az : Intr, override val childs:Array[Joint],
                    points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends IntrJoint(name, offset, ax, ay, az, points, normals) with JointTrav{

  override def apply(trans : Matrix4, normalTrans : Matrix4, time:Float){
    super.apply(trans, normalTrans, time)
    for(j <- childs) j.apply(n, m, time)
  }
}

class LinearJoint(override val name:String,
                  parent:IntrTravJoint, ix:AxisValue, iy:AxisValue, iz:AxisValue,
    							points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends Joint{

  import Axis._

  def value(m:Axis.Value) = m match {
    case X => parent.rotX
    case Y => parent.rotY
    case Z => parent.rotZ
  }

  val n = Matrix4(); val m = Matrix4()
  override def apply(trans:Matrix4, normalTrans:Matrix4, time:Float){

    m.rotZ(iz.value * value(iz.axis)).mul(n.rotY(iy.value * value(iy.axis))).mul(n.rotX(ix.value * value(ix.axis)))
    
    n.mul(trans, m) // next trans
    m.mul(normalTrans, m) // next normalTrans
    
    //cluster(n, m)
    for(v <- points) n(v._1, v._2)
    for(v <- normals) m(v._1, v._2)    
  }
}
