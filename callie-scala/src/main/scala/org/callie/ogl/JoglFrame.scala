//package org.callie.jogl
//
//import java.awt.{BorderLayout, Cursor, Frame, Robot}
//import java.awt.event._
//
//import com.jogamp.opengl.{GLCapabilities, GLProfile}
//import com.jogamp.opengl.awt.GLCanvas
//import com.jogamp.opengl.util.Animator
//import org.callie.input.Inputs
//
//object JoglFrame {
//
//  val frame = new Frame()
//  frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR))
//
//  frame.addWindowListener(new WindowAdapter(){
//    override def windowClosing(e:WindowEvent):Unit={
//      animator.stop()
//      frame.setVisible(false)
//      glCanvas.destroy()
//      frame.dispose()
//    }
//  })
//
//  //println(GLProfile.glAvailabilityToString)
//
//  //val profile = GLProfile.get(GLProfile.GLES3)
//  val profile = GLProfile.get(glProfile)
//  val capabilities = new GLCapabilities(profile)
//  capabilities.setDoubleBuffered(true)
////  capabilities.setSampleBuffers(true) // FSAA
////  capabilities.setNumSamples(4)
//  val glCanvas = new GLCanvas(capabilities)
//  val animator = new Animator(glCanvas)
//
//  glCanvas.setSize(1280, 720)
//  frame.setLocation(100, 100)
//  frame.setLayout(new BorderLayout())
//  frame.add(glCanvas, BorderLayout.CENTER)
//  frame.pack()
//
//  val robot = new Robot()
//  glCanvas.addMouseWheelListener((e: MouseWheelEvent) => {
//    Inputs.mouseZ += e.getWheelRotation
//  })
//
//  glCanvas.addMouseMotionListener(new MouseMotionAdapter {
//    override def mouseDragged(e: MouseEvent):Unit={
//      mouseMoved(e)
//    }
//
//    override def mouseMoved(e: MouseEvent):Unit={
//      val w2 = glCanvas.getWidth / 2
//      val h2 = glCanvas.getHeight / 2
//
//      val p = e.getPoint
//      Inputs.mouseX += p.x - w2
//      Inputs.mouseY += p.y - h2
//
//      val sp = glCanvas.getLocationOnScreen
//      robot.mouseMove(sp.x + w2, sp.y + h2)
//    }
//  })
//
//  glCanvas.addKeyListener(new KeyAdapter {
//    override def keyPressed(e: KeyEvent):Unit={
//      if(e.getKeyCode == KeyEvent.VK_W) Inputs.w = true
////      else if(e.getKeyCode == KeyEvent.VK_A) Inputs.a = true
////      else if(e.getKeyCode == KeyEvent.VK_S) Inputs.s = true
////      else if(e.getKeyCode == KeyEvent.VK_D) Inputs.d = true
//    }
//    override def keyReleased(e: KeyEvent):Unit={
//      if(e.getKeyCode == KeyEvent.VK_W) Inputs.w = false
////      else if(e.getKeyCode == KeyEvent.VK_A) Inputs.a = false
////      else if(e.getKeyCode == KeyEvent.VK_S) Inputs.s = false
////      else if(e.getKeyCode == KeyEvent.VK_D) Inputs.d = false
//    }
//  })
//
//  def apply(el:GlEventListener):Unit={
//    glCanvas.addGLEventListener(el)
//    frame.setVisible(true)
//    animator.start()
//  }
//
//}