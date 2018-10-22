package org.callie

import com.jogamp.opengl._

package object jogl {

  type GlType = GL4ES3

  @inline
  final def glProfile = GLProfile.GLES3
  //final def glProfile = GLProfile.GL4

  @inline
  final def glProfile (drawable: GLAutoDrawable) : GlType = drawable.getGL.getGLES3
  //final def glProfile (drawable: GLAutoDrawable) : GlType = drawable.getGL.getGL4

}
