package org.callie.ogl

import org.callie.input.{Camera, Inputs}
import org.lwjgl.glfw._
import org.lwjgl.opengl._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.system.MemoryUtil._

trait Scene {
  def update(dt:Float):Unit
}

object LwglFrame{

  def apply(fn: GlEventListener => Scene):Unit= {

    GLFWErrorCallback.createPrint(System.err).set()

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

    try {
      GL.createCapabilities()
      val gl = new GlEventListener()

      try {
        val s = fn(gl)
        glfwShowWindow(window)
        var t = System.nanoTime()

        while (!glfwWindowShouldClose(window)) {
          Camera.update()

          val tmp = System.nanoTime()
          val dt: Float = ((tmp - t) / 1e9d).asInstanceOf[Float]
          t = tmp

          s.update(dt)

          glfwSwapBuffers(window)
          glfwPollEvents()
        }

      } finally {
        gl.close()
      }

    }finally {
      Callbacks.glfwFreeCallbacks(window)
      glfwDestroyWindow(window)

      glfwTerminate()
      glfwSetErrorCallback(null).free()
    }
  }

}
