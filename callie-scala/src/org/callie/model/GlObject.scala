package org.callie.model

import javax.media.opengl.{GL4, GL3, GL2GL3, GL2ES2, GL, GLBase}
import javax.media.opengl.glu.gl2.GLUgl2
import org.callie.math.{Vector2, Vector3}
import java.nio.IntBuffer
import java.nio.FloatBuffer
import org.callie.Mod

trait GlObject{

  def init(gl:GL4){}
  
  def display(gl:GL4){}
  
  def dispose(gl:GL4){}
  
}

class StaticGlObject(m:Mod) extends GlObject{
  
  override def init(gl:GL4){

  }
  
}
