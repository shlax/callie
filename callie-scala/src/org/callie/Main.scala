package org.callie

import org.callie.math.intr.Accl

import java.awt.{BorderLayout, Frame}
import javax.media.opengl.{GLCapabilities, GLProfile }
import javax.media.opengl.awt.GLCanvas

object Main extends App {

  val a = new Accl()
  
  a() = 5

  // -----------------------------
  
  val profile = GLProfile.get(GLProfile.GL2)

  val capabilities = new GLCapabilities(profile)
  val glCanvas = new GLCanvas(capabilities)

  val f = new Frame()
  f.setResizable(false)
  f.setSize(250, 250)
  f.setLocation(100, 100)
  f.setLayout(new BorderLayout())
  f.add(glCanvas, BorderLayout.CENTER)

  f.setVisible(true)
  
}