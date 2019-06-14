package org.callie.ogl

import org.callie.input.Inputs
import org.lwjgl.glfw._
import org.lwjgl.opengl._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.system.MemoryUtil._

object LwglFrame{
  def apply(l: GlEventListener):Unit={
    new LwglFrame(l).run()
  }
}

class LwglFrame(l:GlEventListener){

  GLFWErrorCallback.createPrint(System.err).set

  if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW")

  glfwDefaultWindowHints()
  glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
  glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)

  val window = glfwCreateWindow(1280, 720, "", NULL, NULL)

  if(window == NULL) throw new RuntimeException("Failed to create the GLFW window")

  glfwSetWindowPos(window, 200, 100)
  glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN)

  glfwMakeContextCurrent(window)
  glfwSwapInterval(1)

  glfwSetScrollCallback(window, (_: Long, _: Double, yoffset: Double) => {
    Inputs.mouseZ += yoffset.toInt
  })

  glfwSetCursorPosCallback(window, (window: Long, xpos: Double, ypos: Double) => {
    glfwSetCursorPos(window, 640, 360)
    Inputs.mouseX += xpos.toInt - 640
    Inputs.mouseY += ypos.toInt - 360
  })

  glfwSetKeyCallback(window, (_: Long, key: Int, _: Int, action: Int, _: Int) => {
    if (action == GLFW_PRESS) {
      if (key == GLFW_KEY_W) {
        Inputs.w = true
      }
    } else if (action == GLFW_RELEASE) {
      if (key == GLFW_KEY_W) {
        Inputs.w = false
      }
    }

  })

  def run():Unit={
    glfwShowWindow(window)
    GL.createCapabilities()

    try {
      l.init()

      while (!glfwWindowShouldClose(window)) {
        l.display()

        glfwSwapBuffers(window)
        glfwPollEvents()
      }

    } finally {
      l.dispose()
    }
  }

}
