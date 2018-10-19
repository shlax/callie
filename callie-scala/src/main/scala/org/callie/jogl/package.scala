package org.callie

import com.jogamp.opengl._

package object jogl {

  //type GlType = GL4
  //type GlType = GL3
  type GlType = GLES3

  @inline
  //def glProfile = GLProfile.GL4
  //def glProfile = GLProfile.GL3
  def glProfile = GLProfile.GLES3

  @inline
  //def glProfile (drawable: GLAutoDrawable) = drawable.getGL.getGL4
  //def glProfile (drawable: GLAutoDrawable) = drawable.getGL.getGL3
  def glProfile (drawable: GLAutoDrawable) = drawable.getGL.getGLES3

}
