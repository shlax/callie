package org.callie.ringing

import org.callie.input.{Camera, Inputs}
import org.callie.math.{Axis, Vector3}
import org.callie.math.intr.Accl

trait JoinState extends Transformation{

  def toSpeed(v:Float):Unit

  def apply(delta:Float):MovingState
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
  var frameInd = 0

  var global = GlobalState.NORMAL
  var moving = MovingState.STAND

  var acc = 0f

  val pistolTakeInterval = 0.2f
  val pistolTakeInvInter = 1f/pistolTakeInterval

  val pistolStandInterval = 0.45f
  val pistolStandInvInter = 1f/pistolStandInterval

  def apply(delta:Float):Unit={

    val invInter : Float = global match {

      case GlobalState.NORMAL =>
        val ns = cntrl.apply(delta)

        moving match {
          case MovingState.RUN =>
            ns match {

              case MovingState.RUN => // RUN -> RUN
                val current = if(transition) runTransitionInterval else runInterval

                acc += delta
                if(acc > current){
                  acc -= current
                  transition = false

                  frameInd += 1
                  if(frameInd >= run.length) frameInd = 0

                  run(frameInd).apply()
                }

                if(transition) runTransitionInvInter else runInvInter

              case _ => //  RUN -> STAND
                acc = 0f
                stand.apply()
                moving = ns

                standInvInter
            }

          case _ => // AnimState.STAND
            ns match {
              case MovingState.RUN => // STAND -> RUN
                transition = true
                frameInd = 0

                acc = 0f
                run(0).apply()

                moving = ns

                standInvInter

              case _ => // STAND -> STAND
                if(Inputs.mouse2){ // take weapon
                  global = GlobalState.PISTOL_TAKE
                  frameInd = 0

                  acc = 0f
                  pistolTakeDown.apply()

                  pistolTakeInvInter
                }else {
                  acc += delta
                  if (acc > standInterval) {
                    acc -= standInterval
                    stand.apply()
                  }

                  standInvInter
                }
            }
        }

      case GlobalState.PISTOL_TAKE =>
        acc += delta

        if (acc > pistolTakeInterval) {
          acc -= pistolTakeInterval

          if(frameInd == 0) {
            pistolAttch.update(true)
            pistolTakeUp.apply()
          }else{
            global = GlobalState.PISTOL

            acc = 0f
            pistolStand.apply()
          }

          frameInd += 1
        }

        if(frameInd == 2) {
          Camera.side = -0.25f

          pistolStandInvInter
        }else{
          var iv = (-0.125f * acc) / pistolTakeInterval
          if (frameInd == 1) iv -= 0.125f
          Camera.side = iv

          pistolTakeInvInter
        }

      case GlobalState.PISTOL_AWAY =>
        acc += delta

        if(frameInd == 0){
          if (acc > pistolStandInterval) {
            acc -= pistolStandInterval

            pistolTakeDown.apply()
            frameInd = 1

            pistolTakeInvInter
          }else{
            pistolStandInvInter
          }
        }else{
          if (acc > pistolTakeInterval) {
            acc -= pistolTakeInterval

            if(frameInd == 1){
              frameInd = 2
              pistolAttch.update(false)
              stand.apply()
            }else{
              frameInd = 3
              global = GlobalState.NORMAL
              stand.apply()
              acc = 0f
            }
          }

          if(frameInd == 3) {
            Camera.side = 0f
          }else {
            var iv = (0.125f * acc) / pistolTakeInterval

            if(frameInd == 1){
              iv = -0.25f + iv
            }else{
              iv = -0.125f + iv
            }

            Camera.side = iv
          }

          pistolTakeInvInter
        }

      case GlobalState.PISTOL =>
        if(Inputs.keyTab){
          global = GlobalState.PISTOL_AWAY
          frameInd = 0

          acc = 0f
          pistolTakeUp.apply()

        }else {
          acc += delta
          if (acc > pistolStandInterval) {
            acc -= pistolStandInterval
            pistolStand.apply()
          }
        }

        pistolStandInvInter
    }

    j.apply(cntrl, acc * invInter)
    //act = ns

  }

}
