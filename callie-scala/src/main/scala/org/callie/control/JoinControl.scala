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
class JoinControl(cntrl:MovingControl, joint:Joint, standFrame: KeyFrame, runFrames: Array[KeyFrame], rotateFrames: Array[KeyFrame],
                  pistolAttch: JointAttachmentIf,
                  pistolTakeDownFrame: KeyFrame, pistolTakeUpFrame: KeyFrame, pistolStandFrame:KeyFrame, pistolAimFrame:KeyFrame) {

  trait State{
    def activate():Unit
    def apply(delta:Float):Unit

    trait StateMoving{
      def activate():Unit
      def apply(delta:Float, ns:MovingState):Unit
    }
  }

  val normal: State = new State{

    val stand:StateMoving = new StateMoving{
      val standInterval = 0.35f
      val standInvInter = 1f/standInterval

      var acc = 0f

      override def activate(): Unit = {
        localState = this

        standFrame.apply()
        joint.apply(cntrl, 0f)

        acc = 0f
      }

      override def apply(delta: Float, ns: MovingState): Unit = {
        ns match {
          case MovingState.RUN => // STAND -> RUN
            run.activate()

          case MovingState.ROTATE_NEGATIVE | MovingState.ROTATE_POSITIVE => // //  STAND -> ROTATE
            rotate.activate()

          case _ => // STAND -> STAND
            acc += delta
            if (acc > standInterval) {
              acc -= standInterval
              standFrame.apply()
            }
            joint.apply(cntrl, acc * standInvInter)
        }
      }
    }

    val rotate:StateMoving = new StateMoving{
      val rotateInterval = 0.25f
      val rotateInvInter = 1f/rotateInterval

      var acc = 0f
      var frameInd = 0

      override def activate(): Unit = {
        localState = this

        rotateFrames(0).apply()
        joint.apply(cntrl, 0f)

        acc = 0f
        frameInd = 0
      }

      override def apply(delta: Float, ns: MovingState): Unit = {
        ns match {
          case MovingState.ROTATE_NEGATIVE | MovingState.ROTATE_POSITIVE => // ROTATE -> ROTATE
            acc += delta
            if(acc > rotateInterval){
              acc -= rotateInterval

              frameInd += 1
              if(frameInd >= rotateFrames.length) frameInd = 0
              rotateFrames(frameInd).apply()
            }
            joint.apply(cntrl, acc * rotateInvInter)

          case MovingState.RUN => // ROTATE -> RUN
            run.activate()

          case _ =>  // ROTATE -> STAND
            stand.activate()
        }
      }
    }

    val run:StateMoving = new StateMoving{
      val runInterval = 0.35f / JoinControl.runSpeed
      val runInvInter = 1f/runInterval

      val runTransitionInterval = runInterval * 2f
      val runTransitionInvInter = 1f/runTransitionInterval

      var transition = true
      var frameInd = 0
      var acc = 0f

      override def activate(): Unit = {
        localState = this

        runFrames(0).apply()
        joint.apply(cntrl, 0f)

        transition = true
        frameInd = 0
        acc = 0f
      }

      override def apply(delta: Float, ns: MovingState): Unit = {
        ns match{
          case MovingState.RUN => // RUN -> RUN
            acc += delta

            if(transition){
              if(acc > runTransitionInterval){
                acc -= runTransitionInterval
                transition = false

                frameInd += 1
                if(frameInd >= runFrames.length) frameInd = 0
                runFrames(frameInd).apply()
              }
              joint.apply(cntrl, acc * runTransitionInvInter)
            }else{
              if(acc > runInterval){
                acc -= runInterval

                frameInd += 1
                if(frameInd >= runFrames.length) frameInd = 0
                runFrames(frameInd).apply()
              }
              joint.apply(cntrl, acc * runInvInter)
            }

          case MovingState.ROTATE_NEGATIVE | MovingState.ROTATE_POSITIVE => // //  RUN -> ROTATE
            rotate.activate()

          case _ => //  RUN -> STAND
            stand.activate()
        }
      }
    }

    override def activate(): Unit = {
      globalState = this
      stand.activate()
    }

    var localState : StateMoving = stand
    override def apply(delta: Float): Unit = {
      localState.apply(delta, cntrl.apply(delta))
    }

  }

  var globalState : State = normal
  def apply(delta:Float):Unit={
    globalState.apply(delta)
  }

  globalState.activate()

}


