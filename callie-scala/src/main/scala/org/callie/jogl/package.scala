package org.callie

import com.jogamp.opengl._

package object jogl {

  type GlType = GL4ES3

  @inline
  //def glProfile = GLProfile.GLES3
  def glProfile = GLProfile.GL4

  @inline
  //def glProfile (drawable: GLAutoDrawable) = drawable.getGL.getGLES3
  def glProfile (drawable: GLAutoDrawable) : GlType = drawable.getGL.getGL4

}
