package org.callie.ringing

import org.callie.input.Camera
import org.callie.model.{GlObject, ObjectGroup}

object JointAttachment{

  def apply(joint: Joint, nm:String, obj: GlObject*): JointAttachment = {
    val a = joint.lookup(nm).get match {
      case b: AttachmentJoint => b
    }

    new JointAttachment(a, obj:_*)
  }

}

class JointAttachment(joint:AttachmentJoint, obj:GlObject*) extends ObjectGroup(obj:_*){
  val transform = joint.transform
  val normal = joint.normal

  override def update():Unit={
    Camera.update(transform, normal)
    super.update()
  }

}
