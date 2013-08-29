package org.callie.jogl

import javax.media.opengl.{GL4, GLAutoDrawable, GLEventListener}

trait GL4EventListener extends GLEventListener{

  @inline override def init(drawable: GLAutoDrawable) {
    drawable.setAutoSwapBufferMode(true)
    initGL4(drawable.getGL.getGL4)
  }

  @inline def initGL4(gl : GL4)

  @inline override def display(drawable: GLAutoDrawable) {
    displayGL4(drawable.getGL.getGL4)
  }

  @inline def displayGL4(gl : GL4)

  @inline override def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
    val gl = drawable.getGL.getGL4
    gl.glViewport(x, y, width, height)
  }

  @inline override def dispose(drawable: GLAutoDrawable) {}
}
