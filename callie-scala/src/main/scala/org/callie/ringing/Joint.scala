package org.callie.ringing

import org.callie.math.Matrix4
import org.callie.math.intr.Intr
import org.callie.math.Vector3
import org.callie.math.Axis

trait Transformation{
  def transformation: Matrix4
  def normalTransformation: Matrix4
}

trait Joint {
  def name:String

  /** time s intervalu <0,1>  */
  def apply(trans: Matrix4, normalTrans: Matrix4, time:Float):Unit// : (Matrix4, Matrix4)

  def apply(trans: Transformation, time:Float):Unit={
    apply(trans.transformation, trans.normalTransformation, time)
  }

  def lookup(nm:String) : Option[Joint] = {
    if(nm == name) Some(this)
    else None
  }
}

trait JointIntr extends Joint{
  val ax: Intr
  val ay: Intr
  val az: Intr
}

/** join hierarchy offset */
class OffsetJoint(override val name: String, override val ax: Intr, override val  ay:Intr, override val az : Intr, child:Joint) extends JointIntr{
  val transform = Matrix4()

  override def apply(trans: Matrix4, normalTrans: Matrix4, time: Float):Unit={
    transform.set(ax(time), ay(time), az(time)).mul(trans)
    child.apply(transform, normalTrans, time)
  }
}

trait JointTrav extends Joint{
  val childs:Array[Joint]

  override def lookup(nm: String) = {
    var v = super.lookup(nm)
    if(v.isEmpty){
      for(c <- childs if v.isEmpty){
        v = c.lookup(nm)
      }
    }
    v
  }
}

trait JointAttachment extends Joint {
  val transform = Matrix4()
  val normal = Matrix4()
}

class IntrJoint(override val name:String,
                offset:Matrix4, override val ax: Intr, override val  ay:Intr, override val  az : Intr,
				        points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends JointAttachment with JointIntr{
  
  var rotX : Float = _
  var rotY : Float = _
  var rotZ : Float = _

  def apply(trans : Matrix4, normalTrans : Matrix4, time:Float):Unit={
    //val n = Matrix4()
    rotX = ax(time); rotY = ay(time); rotZ = az(time)
    normal.rotZ(rotZ).mul(transform.rotY(rotY)).mul(transform.rotX(rotX))

    transform.mul(trans, offset).mul(normal) // next trans
    normal.mul(normalTrans, normal) // next normalTrans

    //cluster(n, m)
    for(v <- points) transform(v._1, v._2)
    for(v <- normals) normal(v._1, v._2)
  }

}

/** join hierarchy */
class IntrTravJoint(name:String,
                    offset:Matrix4, ax: Intr, ay:Intr, az : Intr, override val childs:Array[Joint],
                    points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends IntrJoint(name, offset, ax, ay, az, points, normals) with JointTrav{

  override def apply(trans : Matrix4, normalTrans : Matrix4, time:Float):Unit={
    super.apply(trans, normalTrans, time)
    for(j <- childs) j.apply(transform, normal, time)
  }
}

class LinearJoint(override val name:String,
                  parent:IntrTravJoint, ix:AxisValue, iy:AxisValue, iz:AxisValue,
    							points:Array[(Vector3, Vector3)], normals:Array[(Vector3, Vector3)] ) extends JointAttachment{

  import Axis._

  def value(m:Axis) = m match {
    case X => parent.rotX
    case Y => parent.rotY
    case Z => parent.rotZ
  }

  override def apply(trans:Matrix4, normalTrans:Matrix4, time:Float):Unit={

    normal.rotZ(iz.value * value(iz.axis)).mul(transform.rotY(iy.value * value(iy.axis))).mul(transform.rotX(ix.value * value(ix.axis)))

    transform.mul(trans, normal) // next trans
    normal.mul(normalTrans, normal) // next normalTrans
    
    //cluster(n, m)
    for(v <- points) transform(v._1, v._2)
    for(v <- normals) normal(v._1, v._2)
  }
}
