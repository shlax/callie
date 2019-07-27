package org.callie.ringing

import org.callie.math.{Axis, Vector3}
import org.callie.math.intr.Accl

trait JoinState extends Transformation{

  def maxRunSpeed:Float

  def apply(delta:Float):Unit

  def state(): AnimState
}

object JoinControl{
  type VecFrame = (Vector3, KeyFrame)

  def apply(cntrl:JoinState, j: Joint, zero:Vector3, stand: VecFrame, run: VecFrame*):JoinControl = {
    val ax = new Accl()
    val ay = new Accl()
    val az = new Accl()

    val name = "main"
    def remap(i: VecFrame) = {
      val off = i._1
      val vx = new KeyValue(name, Axis.X ,ax, off.x - zero.x)
      val vy = new KeyValue(name, Axis.Y ,ay, off.y - zero.y)
      val vz = new KeyValue(name, Axis.Z ,az, off.z - zero.z)
      i._2.add(vx, vy, vz)
    }

    val s = remap(stand)
    s.apply()

    val oj = new OffsetJoint(name, ax, ay, az, j)
    oj.apply(cntrl, 1f)

    val runCycle = run.map(remap).toArray
    for(i <- 0 until (runCycle.length - 1)) runCycle(i).next(runCycle(i+1))
    runCycle.last.next(runCycle.head)

    new JoinControl(cntrl, oj, s, runCycle)
  }

}

/** animacia nad JoinState */
class JoinControl(cntrl:JoinState, j:Joint, stand: KeyFrame, run: Array[KeyFrame]) {
  val interval = 0.35f / cntrl.maxRunSpeed
  val invInter = 1f/interval

  var act = AnimState.STAND
  var acc = 0f

  var runInd = 0

  def apply(delta:Float):Unit={
    cntrl.apply(delta)

    acc += delta
    val next = acc > interval

    val ns = cntrl.state()
    act match {
      case AnimState.STAND =>
        ns match {
          case AnimState.STAND => // STAND -> STAND
            if(next){
              stand.apply()
              acc = delta
            }

          case AnimState.RUN => // STAND -> RUN
            runInd = 0
            run(runInd).apply()
            acc = delta

        }

      case AnimState.RUN =>
        ns match {
          case AnimState.STAND => //  RUN -> STAND
            stand.apply()
            acc = delta

          case AnimState.RUN => // RUN -> RUN
            if(next){
              runInd += 1
              if(runInd >= run.length) runInd = 0

              run(runInd).apply()
              acc = delta
            }
        }
    }

    j.apply(cntrl, acc * invInter)
    act = ns

  }

}
