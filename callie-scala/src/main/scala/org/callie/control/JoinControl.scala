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
  }

  val normal: State = new State{

    trait StateMoving{
      def activate():Unit
      def apply(delta:Float, ns:MovingState):Unit
    }

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
            if(Inputs.key2) { // take weapon
              takePistol.activate()
            }else {
              acc += delta
              if (acc > standInterval) {
                acc -= standInterval
                standFrame.apply()
              }
              joint.apply(cntrl, acc * standInvInter)
            }
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

  val takePistol:State = new State {
    val pistolTakeInterval = 0.2f
    val pistolTakeInvInter = 1f/pistolTakeInterval

    var hold = false
    var acc = 0f

    override def activate(): Unit = {
      globalState = this
      cursorSide = true

      pistolTakeDownFrame.apply()
      joint.apply(cntrl, 0f)

      hold = false
      acc = 0f
    }

    override def apply(delta: Float): Unit = {
      acc += delta

      if (acc > pistolTakeInterval) {
        if(hold){
          pistol.activate()
        }else{
          pistolAttch.update(true)
          cntrl.toSpeed(0f)
          hold = true

          pistolTakeUpFrame.apply()
          acc = 0f
        }
      }else{
        joint.apply(cntrl, acc * pistolTakeInvInter)
      }
    }
  }

  val awayPistol:State = new State {
    val pistolTakeInterval = 0.2f
    val pistolTakeInvInter = 1f/pistolTakeInterval

    val pistolStandInterval = 0.45f
    val pistolStandInvInter = 1f/pistolStandInterval

    var hold = true
    var acc = 0f

    override def activate(): Unit = {
      globalState = this
      cursorSide = false

      pistolTakeUpFrame.apply()
      joint.apply(cntrl, 0f)

      hold = true
      acc = 0f
    }

    override def apply(delta: Float): Unit = {
      acc += delta

      if(hold){
        if (acc > pistolStandInterval) {
          pistolTakeDownFrame.apply()
          joint.apply(cntrl, 0f)

          acc = 0f
          hold = false
        }else {
          joint.apply(cntrl, acc * pistolStandInvInter)
        }
      }else{
        if (acc > pistolTakeInterval) {
          pistolAttch.update(false)
          cntrl.toSpeed(JoinControl.runSpeed)

          normal.activate()
        }else{
          joint.apply(cntrl, acc * pistolTakeInvInter)
        }
      }

    }
  }

  val pistol:State = new State {

    val pistolStandInterval = 0.45f
    val pistolStandInvInter = 1f/pistolStandInterval

    val pistolStandTransitionInterval = pistolStandInterval/2.5f

    var transition = true

    var aiming = false
    var aimed = false

    var acc = 0f

    override def activate(): Unit = {
      globalState = this

      pistolStandFrame.apply()
      joint.apply(cntrl, 0f)

      transition = true

      aiming = false
      aimed = false

      acc = 0f
    }

    override def apply(delta: Float): Unit = {
      if(transition){
        acc += delta
        if (acc > pistolStandTransitionInterval) {
          transition = false
        }

        joint.apply(cntrl, acc * pistolStandInvInter)
      }else {

        if(aiming){

          if (Inputs.mouse2) {
            acc += delta

            if (aimed) {
              if (acc > pistolStandInterval) {
                pistolAimFrame.apply()
                acc -= pistolStandInterval
              }
            } else {
              cntrl.apply(delta) // rotate

              if (acc > pistolStandInterval) {
                aimed = true

                pistolAimFrame.apply()
                acc -= pistolStandInterval
              }
            }

            joint.apply(cntrl, acc * pistolStandInvInter)
          }else{
            pistolStandFrame.apply()
            joint.apply(cntrl, 0f)

            aiming = false
            aimed = false

            acc = 0f
          }

        }else{
          if (Inputs.key2) {
            awayPistol.activate()
          }else{
            if (Inputs.mouse2) {
              pistolAimFrame.apply()
              joint.apply(cntrl, 0f)

              aiming = true
              aimed = false

              acc = 0f
            }else{
              acc += delta
              if (acc > pistolStandInterval) {
                acc -= pistolStandInterval
                pistolStandFrame.apply()
              }
              joint.apply(cntrl, acc * pistolStandInvInter)
            }
          }
        }

      }
    }
  }

  var cursorSide = false
  val offset = Camera.offset

  var cursorSideValue = 0.13f
  var actCursorSide = 0f

  var globalState : State = normal
  def apply(delta:Float):Unit={
    globalState.apply(delta)

    if(cursorSide){
      if(actCursorSide != cursorSideValue){
        actCursorSide += delta * 0.2f
        if(actCursorSide > cursorSideValue) actCursorSide = cursorSideValue

        offset.x = -actCursorSide
      }
    }else{
      if(actCursorSide != 0f){
        actCursorSide -= delta * 0.2f
        if(actCursorSide < 0) actCursorSide = 0f

        offset.x = -actCursorSide
      }
    }

  }

  globalState.activate()

}


