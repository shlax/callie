package org.callie

import com.jogamp.opengl._

package object jogl {

  //val glslVersion = "300 es"
  val glslVersion = "400"

  type GlType = GL4ES3

  @inline
  //final def glProfile = GLProfile.GLES3
  final def glProfile = GLProfile.GL4

  @inline
  //final def glProfile (drawable: GLAutoDrawable) : GlType = drawable.getGL.getGLES3
  final def glProfile (drawable: GLAutoDrawable) : GlType = drawable.getGL.getGL4

}
