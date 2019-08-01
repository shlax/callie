package org.callie.ringing

import org.callie.input.{Camera, CameraProgram}
import org.callie.model.{GlObject, ObjectGroup}

object JointAttachment{

  def apply(prog:CameraProgram, joint: Joint, nm:String, obj: GlObject*): JointAttachment = {
    val j = joint.lookup(nm).get match {
      case b: AttachmentJoint => b
    }

    val ja = new JointAttachment(prog, j, obj:_*)
    ja.init()
    ja
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
