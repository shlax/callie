//package org.callie.ogl
//
//import java.nio.FloatBuffer
//import org.lwjgl.BufferUtils
//
//object Buffers{
//
//  implicit class IntArray(a:Array[Int]){
//    def asBuffer(f:(Int, IntBuffer) => Unit):IntBuffer = {
//      val buff = BufferUtils.createIntBuffer(a.length)
//      buff.put(a); buff.rewind()
//      f(a.length * SIZEOF_INT, buff)
//      buff
//    }
//  }
//
//  implicit class FloatArray(a:Array[Float]){
//    def asBuffer(f:(Int, FloatBuffer) => Unit):FloatBuffer = {
//      val buff = BufferUtils.createFloatBuffer(a.length)
//      buff.put(a); buff.rewind()
//      f(a.length * Gl.SIZEOF_FLOAT, buff)
//      buff
//    }
//  }
//}
