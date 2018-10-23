package org.callie.jogl

import com.jogamp.opengl.{GLAutoDrawable, GLEventListener}

trait GlEventListener extends GLEventListener{

  // GLEventListener
  override def init(drawable: GLAutoDrawable) {
    drawable.setAutoSwapBufferMode(true)
    initGL4(glProfile(drawable))
  }

  def initGL4(gl:GlType)

  override def display(drawable: GLAutoDrawable) {
    displayGL4(glProfile(drawable))
  }

  def displayGL4(gl:GlType)

  override def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
    val gl = glProfile(drawable)
    gl.glViewport(x, y, width, height)
  }

  var disposeTestures : List[Int] = Nil
  var disposeVbo : List[Int] = Nil
  var disposeVao : List[Int] = Nil
  var disposeProgram : List[Int] = Nil

  override def dispose(drawable: GLAutoDrawable) {
    val gl = glProfile(drawable)

    if(disposeVbo.nonEmpty) gl.glDeleteBuffers(disposeVbo.size, disposeVbo.toArray, 0)
    if(disposeVao.nonEmpty) gl.glDeleteVertexArrays(disposeVao.size, disposeVao.toArray, 0)
    if(disposeTestures.nonEmpty) gl.glDeleteTextures(disposeTestures.size, disposeTestures.toArray, 0)

    for(p <- disposeProgram) gl.glDeleteProgram(p)
  }
  
  // ********************************************************************************

  /** get pointer */
  def &(f:(Array[Int]) => Unit) = {
    val tmp = new Array[Int](1)
    f(tmp)
    tmp(0)
  }

  // implicit Gl

  def createTexture(gl : GlType)(f: => Unit) = {
    val texId = &(gl.glGenTextures(1, _, 0))
    bindTexture(gl, texId)(f)
    texId
  }
  
  def bindTexture(gl : GlType, tex:Int, ind:Int = Gl.TEXTURE0)(f: =>Unit){
    gl.glActiveTexture(ind)
    gl.glBindTexture(Gl.TEXTURE_2D, tex)
    f
    gl.glBindTexture(Gl.TEXTURE_2D, 0)
  }
  
  def createVertexArray(gl : GlType)(f: =>Unit) = {
    val vao = &(gl.glGenVertexArrays(1, _, 0))
    disposeVao = vao :: disposeVao
    bindVertexArray(gl, vao)(f)
    vao
  }

  def bindVertexArray(gl : GlType, array:Int)(f: => Unit){
    gl.glBindVertexArray(array)
    f
    gl.glBindVertexArray(0)
  }

  def createBuffer(gl : GlType, target:Int)(f: => Unit) = {
    val vbo = &(gl.glGenBuffers(1, _, 0))
    disposeVbo = vbo :: disposeVbo
    bindBuffer(gl, target, vbo)(f)
    vbo
  }

  def bindBuffer(gl : GlType, target:Int, buffer:Int)(f: => Unit){
    gl.glBindBuffer(target, buffer)
    f
    gl.glBindBuffer(target, 0)
  }

  // --------------------------------------------------------------------------------

  def createShader(gl:GlType, tpe:Int, source:String) = {
    val tmp = Array(0)
    val s = gl.glCreateShader(tpe) // VERTEX/FRAGMENT/GEOMETRY_SHADER
    gl.glShaderSource(s, 1, Array(source), null)
    gl.glCompileShader(s)
    
    gl.glGetShaderiv(s, Gl.COMPILE_STATUS, tmp, 0)
    if(tmp(0) == Gl.FALSE){
      gl.glGetShaderiv(s,Gl.INFO_LOG_LENGTH, tmp, 0)
      val msg = new Array[Byte](tmp(0))
      gl.glGetShaderInfoLog(s, tmp(0), null, 0, msg, 0)
      
      throw new RuntimeException(new String(msg))
    }        
    s
  }

  def createProgram(gl:GlType, shaders:Int*) = {
    val p = gl.glCreateProgram()
    disposeProgram = p :: disposeProgram

    for(s <- shaders) gl.glAttachShader(p, s)
    gl.glLinkProgram(p)
     
    val tmp = Array(0)
    gl.glGetProgramiv(p, Gl.LINK_STATUS, tmp, 0)
    
    if(tmp(0) == Gl.FALSE){
      gl.glGetProgramiv(p,Gl.INFO_LOG_LENGTH, tmp, 0)
      val msg = new Array[Byte](tmp(0))
      gl.glGetProgramInfoLog(p, tmp(0), null, 0, msg, 0)
      
      throw new RuntimeException(new String(msg))
    }    
    for(s <- shaders) gl.glDetachShader(p, s)
    p
  }
}
