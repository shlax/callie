package org.callie.jogl

import javax.media.opengl.{GL4, GLAutoDrawable, GLEventListener}

trait GL4EventListener extends GLEventListener{

  @inline override def init(drawable: GLAutoDrawable) {
    drawable.setAutoSwapBufferMode(true)
    initGL4(drawable.getGL.getGL4)
  }

  @inline def initGL4(implicit gl:GL4)

  @inline override def display(drawable: GLAutoDrawable) {
    displayGL4(drawable.getGL.getGL4)
  }

  @inline def displayGL4(implicit gl:GL4)

  @inline override def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
    val gl = drawable.getGL.getGL4
    gl.glViewport(x, y, width, height)
  }

  @inline override def dispose(drawable: GLAutoDrawable) {}
  
  // ********************************************************************************
  
  def createShader(tpe:Int, source:String)(implicit gl:GL4) = {
    val tmp = Array(0)
    val s = gl.glCreateShader(tpe) // VERTEX/FRAGMENT/GEOMETRY_SHADER
    gl.glShaderSource(s, 1, Array(source), null)
    gl.glCompileShader(s)
    
    gl.glGetShaderiv(s, GL_4.COMPILE_STATUS, tmp, 0);
    if(tmp(0) == GL_4.FALSE){
      gl.glGetShaderiv(s,GL_4.INFO_LOG_LENGTH, tmp, 0)      
      val msg = new Array[Byte](tmp(0))
      gl.glGetShaderInfoLog(s, tmp(0), null, 0, msg, 0)
      
      throw new RuntimeException(new String(msg))
    }        
    s
  }
  
  def createProgram(shaders:Int*)(implicit gl:GL4) = {
    val p = gl.glCreateProgram()
    for(s <- shaders) gl.glAttachShader(p, s)
    gl.glLinkProgram(p)
     
    val tmp = Array(0)
    gl.glGetProgramiv(p, GL_4.LINK_STATUS, tmp, 0)
    
    if(tmp(0) == GL_4.FALSE){
      gl.glGetProgramiv(p,GL_4.INFO_LOG_LENGTH, tmp, 0)      
      val msg = new Array[Byte](tmp(0))
      gl.glGetProgramInfoLog(p, tmp(0), null, 0, msg, 0)
      
      throw new RuntimeException(new String(msg))
    }    
    for(s <- shaders) gl.glDetachShader(p, s)    
    p
  }
}
