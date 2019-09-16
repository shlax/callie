package org.callie.ringing

import org.callie.input.{Camera, Inputs}
import org.callie.math.{Axis, Vector3}
import org.callie.math.intr.Accl

trait JoinState extends Transformation{

  def toSpeed(v:Float):Unit

  def apply(delta:Float):AnimState
}

case class OffsetFrame(offset: Vector3, frame:KeyFrame)

object JoinControl{
  val runSpeed = 2.5f

  def apply(cntrl:JoinState, j: Joint, zero:Vector3, stand: OffsetFrame, run: Array[OffsetFrame],
              pistol: JointAttachmentIf,
              pistolTakeDown: OffsetFrame, pistolTakeUp: OffsetFrame, pistolStand:OffsetFrame, pistolAim:OffsetFrame):JoinControl = {
    val ax = new Accl()
    val ay = new Accl()
    val az = new Accl()

    val name = "main"
    def remap(i: OffsetFrame):KeyFrame = {
      val off = i.offset
      val vx = new KeyValue(name, Axis.X ,ax, off.x - zero.x)
      val vy = new KeyValue(name, Axis.Y ,ay, off.y - zero.y)
      val vz = new KeyValue(name, Axis.Z ,az, off.z - zero.z)
      i.frame.add(vx, vy, vz)
    }

    val s = remap(stand)
    s.apply()

    val oj = new OffsetJoint(name, ax, ay, az, j)
    oj.apply(cntrl, 1f)

    val runCycle = run.map(remap)
    for(i <- 0 until (runCycle.length - 1)) runCycle(i).next(runCycle(i+1))
    runCycle.last.next(runCycle.head)

    cntrl.toSpeed(runSpeed)
    new JoinControl(cntrl, oj, s, runCycle,
      pistol,
      remap(pistolTakeDown), remap(pistolTakeUp), remap(pistolStand), remap(pistolAim))
  }

}

/** animacia nad JoinState */
class JoinControl(cntrl:JoinState, j:Joint, stand: KeyFrame, run: Array[KeyFrame],
                    pistolAttch: JointAttachmentIf,
                    pistolTakeDown: KeyFrame, pistolTakeUp: KeyFrame, pistolStand:KeyFrame, pistolAim:KeyFrame) {

  val standInterval = 0.35f
  val standInvInter = 1f/standInterval

  val runInterval = 0.35f / JoinControl.runSpeed
  val runInvInter = 1f/runInterval

  val runTransitionInterval = runInterval * 2f
  val runTransitionInvInter = 1f/runTransitionInterval

  var transition = false
  var act = AnimState.STAND
  var acc = 0f

  var frameInd = 0

  val pistolTakeInterval = 0.2f
  val pistolTakeIntervalInv = 1f/pistolTakeInterval

  val pistolStandInterval = 0.45f
  val pistolStandInvInter = 1f/pistolStandInterval

  var pistol = PistolState.STAND

  def apply(delta:Float):Unit={
    val invInter : Float = pistol match {

      case PistolState.STAND =>
        val ns = cntrl.apply(delta)

        act match {
          case AnimState.STAND =>
            ns match {
              case AnimState.STAND => // STAND -> STAND
                if(Inputs.mouse2){ // take weapon
                  pistol = PistolState.TAKE
                  frameInd = 0

                  pistolTakeDown.apply()
                  acc = delta

                  pistolTakeIntervalInv
                }else {
                  acc += delta
                  if (acc > standInterval) {
                    stand.apply()
                    acc = delta
                  }

                  standInvInter
                }

              case AnimState.RUN => // STAND -> RUN
                transition = true

                frameInd = 0
                run(0).apply()
                acc = delta
                act = ns

                standInvInter
              case _ =>
                standInvInter
            }

          case AnimState.RUN =>
            ns match {
              case AnimState.STAND => //  RUN -> STAND
                stand.apply()
                acc = delta
                act = ns

                standInvInter

              case AnimState.RUN => // RUN -> RUN
                acc += delta

                val current = if(transition) runTransitionInterval else runInterval

                if(acc > current){
                  transition = false

                  frameInd += 1
                  if(frameInd >= run.length) frameInd = 0

                  run(frameInd).apply()
                  acc = delta
                }

                if(transition) runTransitionInvInter else runInvInter
              case _ =>
                standInvInter
            }

          case _ =>
            standInvInter
        }

      case PistolState.TAKE =>
        acc += delta
        if (acc > pistolTakeInterval) {
          acc = delta

          if(frameInd == 0) {
            Camera.side = -0.125f
            pistolAttch.update(true)
            frameInd = 1

            pistolTakeUp.apply()
            pistolTakeIntervalInv
          }else{
            Camera.side = -0.25f
            pistol = PistolState.PISTOL

            pistolStand.apply()
            pistolStandInvInter
          }
        }else {
          var iv = (-0.125f * acc) / pistolTakeInterval
          if(frameInd == 1) iv -= 0.125f
          Camera.side = iv

          pistolTakeIntervalInv
        }

      case PistolState.DROP =>


        pistolTakeIntervalInv
      case PistolState.PISTOL =>
        acc += delta
        if (acc > pistolStandInterval) {
          pistolStand.apply()
          acc = delta
        }

        pistolStandInvInter
    }



    j.apply(cntrl, acc * invInter)
    //act = ns

  }

}
