package org.callie.jogl

import org.lwjgl.glfw._
import org.lwjgl.opengl._

import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryUtil._

class LwglFrame(l:GlEventListener) {

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

  glfwSetScrollCallback(window, new GLFWScrollCallback() {
    override def invoke(window: Long, xoffset: Double, yoffset: Double): Unit = {
      println(""+xoffset+" "+yoffset)
    }
  })

  glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
    override def invoke(window: Long, xpos: Double, ypos: Double): Unit = {
      glfwSetCursorPos(window, 640,  360)
      println(""+xpos+" "+ypos)
    }
  })

  glfwSetKeyCallback(window, new GLFWKeyCallback() {
    override def invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int): Unit = {
      if(action == GLFW_PRESS){
        if(key == GLFW_KEY_W) {
          println("" + key + " " + action)
        }
      }else if(action == GLFW_RELEASE){
        if(key == GLFW_KEY_W) {
          println("" + key + " " + action)
        }
      }

    }
  })

  glfwShowWindow(window)

  GL.createCapabilities()
  //glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
  l.init()

  while ( !glfwWindowShouldClose(window) ) {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    l.display()

    glfwSwapBuffers(window)
    glfwPollEvents()
  }

}
