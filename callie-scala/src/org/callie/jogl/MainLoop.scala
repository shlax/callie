package org.callie.jogl

import javax.media.opengl.GL4
import org.callie.model.{Graphics, Physics}
import org.callie.input.Inputs

object MainLoop extends App {
  // load stuff

  JoglFrame.key(Inputs).mouse(Inputs).gl(new GL4EventListener(){
    override def initGL4(implicit gl: GL4){
      Physics.init()
      Graphics.init(gl)
    }

    override def displayGL4(implicit gl: GL4){
      Physics.update()
      Graphics.display(gl)
    }
  })
}
