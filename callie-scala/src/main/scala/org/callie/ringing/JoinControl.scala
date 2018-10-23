package org.callie.ringing

import org.callie.math.Vector3
import org.callie.math.intr.Accl

trait JoinState extends Transformation{

  def apply(delta:Float)

  def state(): AnimState

}

object JoinControl{
  type VecFrame = (Vector3, KeyFrame)

  def apply(cntrl:JoinState, j: Joint, stand: VecFrame, run: VecFrame*) = {
    val ax = new Accl()
    val ay = new Accl()
    val az = new Accl()

    val zero = stand._1
    def remap(i: VecFrame) = {
      val off = i._1
      val vx = new KeyValue(ax, off.x - zero.x)
      val vy = new KeyValue(ay, off.y - zero.y)
      val vz = new KeyValue(az, off.z - zero.z)
      i._2.add(vx, vy, vz)
    }

    val s = remap(stand)
    s.apply(false)

    val oj = new OffsetJoint(ax, ay, az, j)
    oj.apply(cntrl, 1f)

    new JoinControl(cntrl, oj, s, run.map(remap).toArray)
  }

}

/** animacia nad JoinState */
class JoinControl(cntrl:JoinState, j:Joint, stand: KeyFrame, run: Array[KeyFrame]) {
  val interval = 0.2f
  val invInter = 1f/interval

  var act = AnimState.STAND
  var acc = 0f

  var runInd = 0

  def apply(delta:Float){
    cntrl.apply(delta)

    acc += delta
    val next = acc > interval

    val ns = cntrl.state()
    ns match {
      case AnimState.STAND =>  // to stand
        act match {
          case AnimState.STAND => // STAND -> STAND
            if(next){
              stand.apply()
              acc = delta
            }

          case AnimState.RUN => // RUN -> STAND
            stand.apply()
            acc = delta
        }
      case AnimState.RUN => // to run
        act match {
          case AnimState.STAND => // STAND -> RUN
            runInd = 0
            run(runInd).apply()
            acc = delta

          case AnimState.RUN => // RUN -> RUN
            if(next){
              runInd += 1
              if(runInd > run.length) runInd = 0

              run(runInd).apply()
              acc = delta
            }
        }
    }

    j.apply(cntrl, acc * invInter)
    act = ns

  }

}
