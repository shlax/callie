package org.callie.control

import org.callie.input.{Camera, Inputs}
import org.callie.math.intr.Accl
import org.callie.math.{Axis, Vector3}
import org.callie.ringing._

object JoinControl{
  val runSpeed = 2.5f

  def apply(cntrl:MovingControl, j: Joint, zero:Vector3, stand: OffsetFrame, run: Array[OffsetFrame], rotate: Array[OffsetFrame],
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

    def cycle(of:Array[OffsetFrame]):Array[KeyFrame] = {
      val kf = of.map(remap)
      for(i <- 0 until (kf.length - 1)) kf(i).next(kf(i+1))
      kf.last.next(kf.head)
      kf
    }

    cntrl.toSpeed(runSpeed)
    new JoinControl(cntrl, oj, s, cycle(run), cycle(rotate),
      pistol,
      remap(pistolTakeDown), remap(pistolTakeUp), remap(pistolStand), remap(pistolAim))
  }

}

/** animacia nad JoinState */
class JoinControl(cntrl:MovingControl, j:Joint, stand: KeyFrame, run: Array[KeyFrame], rotate: Array[KeyFrame],
                    pistolAttch: JointAttachmentIf,
                    pistolTakeDown: KeyFrame, pistolTakeUp: KeyFrame, pistolStand:KeyFrame, pistolAim:KeyFrame) {

  val standInterval = 0.35f
  val standInvInter = 1f/standInterval

  val runInterval = 0.35f / JoinControl.runSpeed
  val runInvInter = 1f/runInterval

  val rotateInterval = 0.25f
  val rotateInvInter = 1f/rotateInterval

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

  val pistolStandTransitionInterval = pistolStandInterval/2.5f

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

              case MovingState.ROTATE_NEGATIVE | MovingState.ROTATE_POSITIVE => // //  RUN -> ROTATE
                acc = 0f

                rotate(0).apply()
                frameInd = 0

                moving = ns

                rotateInvInter

              case _ => //  RUN -> STAND
                acc = 0f
                stand.apply()
                moving = ns

                standInvInter
            }

          case MovingState.ROTATE_NEGATIVE | MovingState.ROTATE_POSITIVE =>
            ns match {
              case MovingState.ROTATE_NEGATIVE | MovingState.ROTATE_POSITIVE => // ROTATE -> ROTATE

                acc += delta
                if(acc > rotateInterval){
                  acc -= rotateInterval

                  frameInd += 1
                  if(frameInd >= rotate.length) frameInd = 0
                  rotate(frameInd).apply()
                }

                rotateInvInter

              case MovingState.RUN => // ROTATE -> RUN
                transition = true
                frameInd = 0

                acc = 0f
                run(0).apply()

                moving = ns

                runTransitionInvInter

              case _ =>  // ROTATE -> STAND
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

                runTransitionInvInter

              case MovingState.ROTATE_NEGATIVE | MovingState.ROTATE_POSITIVE => // //  STAND -> ROTATE
                acc = 0f

                rotate(0).apply()
                frameInd = 0

                moving = ns

                rotateInvInter

              case _ => // STAND -> STAND
                if(Inputs.key2){ // take weapon
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
            frameInd = 1

            pistolTakeInvInter
          }else{
            global = GlobalState.PISTOL
            transition = true
            frameInd = 0

            Camera.offset.x = -0.25f
            cntrl.toSpeed(0f)

            pistolStand.apply()
            acc = 0f

            pistolStandInvInter
          }

        }else {
          var iv = (-0.125f * acc) / pistolTakeInterval
          if (frameInd == 1) iv -= 0.125f
          Camera.offset.x = iv

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
            if(frameInd == 1){
              acc -= pistolTakeInterval
              pistolAttch.update(false)

              frameInd = 2
              stand.apply()
            }else{
              acc = 0f
              global = GlobalState.NORMAL

              frameInd = 3
              stand.apply()
            }
          }

          if(frameInd == 3) {
            cntrl.toSpeed(JoinControl.runSpeed)
            Camera.offset.x = 0f

          }else {
            var iv = (0.125f * acc) / pistolTakeInterval

            if(frameInd == 1){
              iv = -0.25f + iv
            }else{
              iv = -0.125f + iv
            }

            Camera.offset.x = iv
          }

          pistolTakeInvInter
        }

      case GlobalState.PISTOL =>
        if(transition){
          acc += delta

          if (acc > pistolStandTransitionInterval) {
            transition = false
          }
        }else {
          if (Inputs.mouse2) {
            if(frameInd !=2 ) {
              cntrl.apply(delta)
            }

            if(frameInd == 0){
              pistolAim.apply()
              frameInd = 1
              acc = 0f
            }else{
              acc += delta
              if (acc > pistolStandInterval) {
                frameInd = 2
                pistolAim.apply()
                acc -= pistolStandInterval
              }
            }
          } else {
            if(frameInd != 0){
              pistolStand.apply()
              frameInd = 0
              acc = 0f
            }else {
              if (Inputs.key2) {
                global = GlobalState.PISTOL_AWAY

                acc = 0f
                pistolTakeUp.apply()

              } else {
                acc += delta
                if (acc > pistolStandInterval) {
                  acc -= pistolStandInterval
                  pistolStand.apply()
                }
              }
            }
          }
        }

        pistolStandInvInter
    }

    j.apply(cntrl, acc * invInter)
    //act = ns

  }

}
