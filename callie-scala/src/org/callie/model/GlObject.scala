package org.callie.model

import javax.media.opengl.GL2;
import javax.media.opengl.glu.gl2.GLUgl2;

trait GlObject{

  def init(gl:GL2, glu: GLUgl2)

  def dispose(gl:GL2, glu: GLUgl2)
  
}


