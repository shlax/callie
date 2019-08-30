package org.callie.ringing

import org.callie.math.{Axis, Vector3}
import org.callie.math.intr.Accl

trait JoinState extends Transformation{

  def toSpeed(v:Float):Unit

  def apply(delta:Float):AnimState
}

case class OffsetFrame(offset: Vector3, frame:KeyFrame)

object JoinControl{
  val runSpeed = 2.5f

  def apply(cntrl:JoinState, j: Joint, zero:Vector3, stand: OffsetFrame, run: OffsetFrame*):JoinControl = {
    val ax = new Accl()
    val ay = new Accl()
    val az = new Accl()

    val name = "main"
    def remap(i: OffsetFrame) = {
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

    val runCycle = run.map(remap).toArray
    for(i <- 0 until (runCycle.length - 1)) runCycle(i).next(runCycle(i+1))
    runCycle.last.next(runCycle.head)

    cntrl.toSpeed(runSpeed)
    new JoinControl(cntrl, oj, s, runCycle)
  }

}

/** animacia nad JoinState */
class JoinControl(cntrl:JoinState, j:Joint, stand: KeyFrame, run: Array[KeyFrame]) {
  val standInterval = 0.35f
  val standInvInter = 1f/standInterval

  val runInterval = 0.35f / JoinControl.runSpeed
  val runInvInter = 1f/runInterval

  val runTransitionInterval = runInterval * 2f
  val runTransitionInvInter = 1f/runTransitionInterval

  var transition = false
  var act = AnimState.STAND
  var acc = 0f

  var runInd = 0

  def apply(delta:Float):Unit={
    val ns = cntrl.apply(delta)

    //acc += delta
    //val next = acc > interval
    val invInter : Float = act match {
      case AnimState.STAND =>
        ns match {
          case AnimState.STAND => // STAND -> STAND
            acc += delta
            if(acc > standInterval){
              stand.apply()
              acc = delta
            }

          case AnimState.RUN => // STAND -> RUN
            transition = true

            runInd = 0
            run(runInd).apply()
            acc = delta
            act = ns

          case _ =>
        }

        standInvInter

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

              runInd += 1
              if(runInd >= run.length) runInd = 0

              run(runInd).apply()
              acc = delta
            }

            if(transition) runTransitionInvInter else runInvInter
          case _ =>
            standInvInter
        }

      case _ =>
        standInvInter
    }

    j.apply(cntrl, acc * invInter)
    //act = ns

  }

}
