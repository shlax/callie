package org.callie.model

trait Physics {

  /** seconds */
  def update(deltaTime:Float)

}

object Physics{
  var time :Float = _
  var parts:Array[Physics] = Array()

  def register(e:Physics*){
    parts = Array.concat(parts, e.toArray)
  }

  var startTime:Long = _

  def init(){
    startTime = System.currentTimeMillis()
    time = startTime
  }

  def update(){
    val t = (System.currentTimeMillis() - startTime).asInstanceOf[Float] / 1000000f
    val dt = t - time
    time = t
    for(e <- parts) e.update(dt)
  }

}
