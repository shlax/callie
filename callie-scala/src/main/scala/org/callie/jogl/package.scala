package org.callie

import com.jogamp.opengl.{GL4, GLAutoDrawable, GLProfile}

package object jogl {

  type GlType = GL4

  @inline
  def glProfile = GLProfile.GL4

  @inline
  def glProfile (drawable: GLAutoDrawable) = drawable.getGL.getGL4

}
