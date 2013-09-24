package org.callie.jogl

import java.awt.{Frame, BorderLayout}
import java.awt.event.{WindowAdapter, WindowEvent}
import javax.media.opengl.{GLProfile, GLCapabilities, GLEventListener}
import javax.media.opengl.awt.GLCanvas
import com.jogamp.opengl.util.FPSAnimator

object JoglFrame {

  val frame = new Frame()
  frame.addWindowListener(new WindowAdapter(){
    override def windowClosing(e:WindowEvent){
      animator.stop()
      frame.setVisible(false)
      glCanvas.destroy()
      frame.dispose()
    }
  })
  
  //println(GLProfile.glAvailabilityToString)
  
  val profile = GLProfile.get(GLProfile.GL4)
  val capabilities = new GLCapabilities(profile)
  capabilities.setSampleBuffers(true) // FSAA
  capabilities.setNumSamples(4)
  val glCanvas = new GLCanvas(capabilities)
  val animator = new FPSAnimator(glCanvas, 60, true)
  
  glCanvas.setSize(300, 300)
  frame.setLocation(100, 100)
  frame.setLayout(new BorderLayout())
  frame.add(glCanvas, BorderLayout.CENTER)
  frame.pack()

  def apply(el:GLEventListener){
    glCanvas.addGLEventListener(el)
    frame.setVisible(true)
    //animator.start()
  }
  
}