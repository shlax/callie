package org.callie.jogl

import java.awt.{Robot, Frame, BorderLayout}
import java.awt.event._
import javax.media.opengl.{GLProfile, GLCapabilities}
import javax.media.opengl.awt.GLCanvas
import com.jogamp.opengl.util.FPSAnimator
import org.callie.input.Inputs

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

  val robot = new Robot()
  glCanvas.addMouseMotionListener(new MouseMotionAdapter {
    override def mouseMoved(e: MouseEvent){
      val w2 = glCanvas.getWidth / 2
      val h2 = glCanvas.getHeight / 2

      val p = e.getPoint
      Inputs.mouseX += p.x - w2
      Inputs.mouseY += p.y - h2

      val sp = glCanvas.getLocationOnScreen
      robot.mouseMove(sp.x + w2, sp.y + h2)
    }
  })

  glCanvas.addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent){
      if(e.getKeyCode == KeyEvent.VK_W) Inputs.w = true
      else if(e.getKeyCode == KeyEvent.VK_A) Inputs.a = true
      else if(e.getKeyCode == KeyEvent.VK_S) Inputs.s = true
      else if(e.getKeyCode == KeyEvent.VK_D) Inputs.d = true
    }
    override def keyReleased(e: KeyEvent){
      if(e.getKeyCode == KeyEvent.VK_W) Inputs.w = false
      else if(e.getKeyCode == KeyEvent.VK_A) Inputs.a = false
      else if(e.getKeyCode == KeyEvent.VK_S) Inputs.s = false
      else if(e.getKeyCode == KeyEvent.VK_D) Inputs.d = false
    }
  })

  def apply(el:GL4EventListener){
    glCanvas.addGLEventListener(el)
    frame.setVisible(true)
    animator.start()
  }
  
}