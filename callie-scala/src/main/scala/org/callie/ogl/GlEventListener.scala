package org.callie.ogl

import org.callie.model.GlObject

trait GlEventListener extends GlObject{

  var disposeTextures : List[Int] = Nil
  var disposeVbo : List[Int] = Nil
  var disposeVao : List[Int] = Nil
  var disposeProgram : List[Int] = Nil
  var disposeFbo : List[Int] = Nil
  var disposeRbo : List[Int] = Nil

  def dispose():Unit={
    for(i <- disposeVbo) Gl.glDeleteBuffers(i)
    for(i <- disposeVao) Gl.glDeleteVertexArrays(i)
    for(i <- disposeTextures) Gl.glDeleteTextures(i)
    for(i <- disposeFbo) Gl.glDeleteFramebuffers(i)
    for(i <- disposeRbo) Gl.glDeleteRenderbuffers(i)

    for(p <- disposeProgram) Gl.glDeleteProgram(p)
  }
  
  // ********************************************************************************

//  /** get pointer */
//  def &(f:(Array[Int]) => Unit) = {
//    val tmp = new Array[Int](1)
//    f(tmp)
//    tmp(0)
//  }

  // implicit Gl

  def createTexture(f: => Unit):Int = {
    val texId = Gl.glGenTextures()
    disposeTextures = texId :: disposeTextures
    bindTexture(texId)(f)
    texId
  }
  
  def bindTexture(tex:Int)(f: =>Unit):Unit={
    Gl.glBindTexture(Gl.GL_TEXTURE_2D, tex)
    f
    Gl.glBindTexture(Gl.GL_TEXTURE_2D, 0)
  }
  
  def createVertexArray(f: =>Unit):Int = {
    val vao = Gl.glGenVertexArrays()
    disposeVao = vao :: disposeVao
    bindVertexArray(vao)(f)
    vao
  }

  def bindVertexArray(array:Int)(f: => Unit):Unit={
    Gl.glBindVertexArray(array)
    f
    Gl.glBindVertexArray(0)
  }

  def createBuffer(target:Int)(f: => Unit):Int = {
    val vbo = Gl.glGenBuffers()
    disposeVbo = vbo :: disposeVbo
    bindBuffer(target, vbo)(f)
    vbo
  }

  def bindBuffer(target:Int, buffer:Int)(f: => Unit):Unit={
    Gl.glBindBuffer(target, buffer)
    f
    Gl.glBindBuffer(target, 0)
  }

  // --------------------------------------------------------------------------------

  def createFrameBuffer(f: => Unit):Int= {
    val fbo = Gl.glGenFramebuffers()
    disposeFbo = fbo :: disposeFbo
    bindFrameBuffer(fbo)(f)
    val ok = Gl.glCheckFramebufferStatus(Gl.GL_FRAMEBUFFER)
    if(ok != Gl.GL_FRAMEBUFFER_COMPLETE){
      throw new RuntimeException("createFrameBuffer: "+ok)
    }
    fbo
  }

  def bindFrameBuffer(buffer:Int)(f: => Unit):Unit={
    Gl.glBindFramebuffer(Gl.GL_FRAMEBUFFER, buffer)
    f
    Gl.glBindFramebuffer(Gl.GL_FRAMEBUFFER, 0)
  }

  def createRenderbuffer(f: => Unit):Int= {
    val rbo = Gl.glGenRenderbuffers()
    disposeRbo = rbo :: disposeRbo
    bindRenderbuffer(rbo)(f)
    rbo
  }

  def bindRenderbuffer(buffer:Int)(f: => Unit):Unit={
    Gl.glBindRenderbuffer(Gl.GL_RENDERBUFFER, buffer)
    f
    Gl.glBindRenderbuffer(Gl.GL_RENDERBUFFER, 0)
  }

  // --------------------------------------------------------------------------------

  def createShader(tpe:Int, source:String):Int = {
    val tmp = Array(0)
    val s = Gl.glCreateShader(tpe) // VERTEX/FRAGMENT/GEOMETRY_SHADER
    Gl.glShaderSource(s, source)
    Gl.glCompileShader(s)
    
    Gl.glGetShaderiv(s, Gl.GL_COMPILE_STATUS, tmp)
    if(tmp(0) == Gl.GL_FALSE){
      val msg = Gl.glGetShaderInfoLog(s)
      throw new RuntimeException(msg)
    }        
    s
  }

  def createProgram(shaders:Int*):Int = {
    val p = Gl.glCreateProgram()
    disposeProgram = p :: disposeProgram

    for(s <- shaders) Gl.glAttachShader(p, s)
    Gl.glLinkProgram(p)
     
    val tmp = Array(0)
    Gl.glGetProgramiv(p, Gl.GL_LINK_STATUS, tmp)
    
    if(tmp(0) == Gl.GL_FALSE){
      val msg = Gl.glGetProgramInfoLog(p)
      throw new RuntimeException(msg)
    }    
    for(s <- shaders) Gl.glDetachShader(p, s)
    p
  }
}
