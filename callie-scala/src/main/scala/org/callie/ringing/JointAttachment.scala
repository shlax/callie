package org.callie.ringing

import org.callie.input.CameraProgram
import org.callie.math.{Angle, Matrix4, Vector3}
import org.callie.model.{GlObject, ObjectGroup}

object JointAttachment{

  def apply(prog:CameraProgram, joint: Joint, nm:String, obj: GlObject*): JointAttachment = {
    val j = joint.lookup(nm).get match {
      case b: AttachmentJoint => b
    }

    new JointAttachment(prog, j, obj:_*)
  }

  def apply(prog:CameraProgram, primary:AttachmentJointMatrix, secondary:AttachmentJointMatrix, obj:GlObject*):JointAttachmentIf = {
    new JointAttachmentIf(prog, primary, secondary, obj:_*)
  }

  def apply(joint: Joint, nm:String, x:Float, y:Float, z:Float, rx:Float, ry:Float, rz:Float):AttachmentJointMatrix = {
    val j = joint.lookup(nm).get match {
      case b: AttachmentJoint => b
    }

    val normal = Matrix4()
    val model = Matrix4()

    def toRad(f:Float) = {
      (f * Angle.PI1) / 180f
    }

    normal.rotZ(toRad(rz)).mul(model.rotY(toRad(ry))).mul(model.rotX(toRad(rx)))
    model.set(x, y, z).mul(normal)

    new AttachmentJointMatrix(j, model, normal)
  }

}

class AttachmentJointMatrix(val joint:AttachmentJoint, val model:Matrix4, val normal:Matrix4)

class AimYAttachment(prog:CameraProgram, from:AttachmentJointMatrix, toJoint:AttachmentJoint, toPoint:Vector3, obj:GlObject*) extends ObjectGroup(obj:_*){

  val fromModel = from.joint.model
  val fromNormal = from.joint.normal

  val fromModelTransform = from.model
  val fromNormalTransform = from.normal

  val toModel = toJoint.model

  val transform = Matrix4()
  val normal = Matrix4()

  val target = Vector3()

  override def update():Unit = {
    toModel.apply(toPoint, target)

    transform.mul(fromModel, fromModelTransform).inverse()
    transform.apply(target)

    // axis = (0, 1, 0)

    val dx = Math.abs(target.x) > 0.001f
    val dz = Math.abs(target.z) > 0.001f

    if(dz && dx){
      val ax = Math.acos(target.y / target.z)
      val az = Math.acos(target.y / target.x)

      transform.rotZ(az.asInstanceOf[Float])
      normal.rotX(ax.asInstanceOf[Float])

      normal.mul(transform)

      transform.mul(normal, fromModel).mul(fromModelTransform)
      normal.mul(normal, fromNormal).mul(fromNormalTransform)
    } else if(dz){
      val a = Math.acos(target.y / target.z)
      normal.rotX(a.asInstanceOf[Float])

      transform.mul(normal, fromModel).mul(fromModelTransform)
      normal.mul(normal, fromNormal).mul(fromNormalTransform)
    }else  if(dx){
      val a = Math.acos(target.y / target.x)
      normal.rotZ(a.asInstanceOf[Float])

      transform.mul(normal, fromModel).mul(fromModelTransform)
      normal.mul(normal, fromNormal).mul(fromNormalTransform)
    }else{
      transform.mul(fromModel, fromModelTransform)
      normal.mul(fromNormal, fromNormalTransform)
    }

    prog.update(transform, normal)
    super.update()
  }

}

class JointAttachmentIf(prog:CameraProgram, primary:AttachmentJointMatrix, secondary:AttachmentJointMatrix, obj:GlObject*) extends ObjectGroup(obj:_*){
  var sec = false

  def update(v:Boolean):Unit = {
    sec = v
  }

  val priModel = primary.joint.model
  val priNormal = primary.joint.normal

  val priModelTransform = primary.model
  val priNormalTransform = primary.normal

  val secModel = secondary.joint.model
  val secNormal = secondary.joint.normal

  val secModelTransform = secondary.model
  val secNormalTransform = secondary.normal

  val transform = Matrix4()
  val normal = Matrix4()

  override def update():Unit={
    if(sec) {
      transform.mul(secModel, secModelTransform)
      normal.mul(secNormal, secNormalTransform)
    }else{
      transform.mul(priModel, priModelTransform)
      normal.mul(priNormal, priNormalTransform)
    }

    prog.update(transform, normal)
    super.update()
  }

}

class JointAttachment(prog:CameraProgram, joint:AttachmentJoint, obj:GlObject*) extends ObjectGroup(obj:_*){
  val model = joint.model
  val normal = joint.normal

  override def update():Unit={
    prog.update(model, normal)
    super.update()
  }

}
