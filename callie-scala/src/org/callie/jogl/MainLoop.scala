package org.callie.jogl

import javax.media.opengl.GL4
import org.callie.model.{Graphics, Physics}
import org.callie.input.{Camera, Inputs}

object MainLoop extends App {
  // load stuff

  JoglFrame(new GL4EventListener(){
    override def initGL4(implicit gl: GL4){
      Physics.init()
      Graphics.init(gl)
      Camera.init(gl)
    }

    override def displayGL4(implicit gl: GL4){
      Physics.update()
      Graphics.display(gl)
      Camera.display(gl)
    }
  })
}
