package org.callie.jogl

import java.nio.{IntBuffer, FloatBuffer}
import com.jogamp.common.nio.Buffers

object buffers{

  implicit class IntArray(a:Array[Int]){
    def asBuffer(f:(Int, IntBuffer) => Unit) = {
      val buff = Buffers.newDirectIntBuffer(a.length)
      buff.put(a); buff.rewind()
      f(a.length * Buffers.SIZEOF_INT, buff)
      buff
    }
  }

  implicit class FloatArray(a:Array[Float]){
    def asBuffer(f:(Int, FloatBuffer) => Unit) = {
      val buff = Buffers.newDirectFloatBuffer(a.length)
      buff.put(a); buff.rewind()
      f(a.length * Buffers.SIZEOF_FLOAT, buff)
      buff
    }
  }
}
