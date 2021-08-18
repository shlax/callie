package org.callie.map

import java.lang.{Float => jFloat }

trait AbstractTriangle[T]{
  val near: Array[T]
  val far: Array[T]
}

abstract class AbstractMap[T <: AbstractTriangle[T], R](val triangles : Array[T], var last:T) {

  def test(t:T, r:R) : Float

  def find(v: R) : Option[(Float,T)] = {
    for(t <- triangles){
      val tmp = test(t, v)
      if(!jFloat.isNaN(tmp)) return Some(tmp, t)
    }
    None
  }

  def fast(v: R): Float = {
    val tmp = test(last, v)
    if(!jFloat.isNaN(tmp)) return tmp
    for(t <- last.near){
      val tmp = test(t, v)
      if(!jFloat.isNaN(tmp)) {
        last = t
        return tmp
      }
    }
    for(t <- last.far){
      val tmp = test(t, v)
      if(!jFloat.isNaN(tmp)) {
        last = t
        return tmp
      }
    }
    Float.NaN
  }

  def apply(v: R): Float = {
    val f = fast(v)
    if(!jFloat.isNaN(f)) return f
    val s = find(v)
    if(s.isDefined){
      val v = s.get
      last = v._2
      v._1
    }else Float.NaN
  }

}
