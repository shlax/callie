package org.callie

import org.lwjgl.glfw.GLFWErrorCallback

import org.lwjgl._
import org.lwjgl.glfw._
import org.lwjgl.opengl._
import org.lwjgl.system._

import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryStack._
import org.lwjgl.system.MemoryUtil._

import java.nio._

object LwglDemo extends App{

  GLFWErrorCallback.createPrint(System.err).set

  if ( !glfwInit() ) throw new IllegalStateException("Unable to initialize GLFW")

  glfwDefaultWindowHints(); // optional, the current window hints are already the default
  glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
  glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

  val window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL)

  if ( window == NULL ) throw new RuntimeException("Failed to create the GLFW window")



  glfwMakeContextCurrent(window)
  glfwSwapInterval(1)
  glfwShowWindow(window)

  // This line is critical for LWJGL's interoperation with GLFW's
  // OpenGL context, or any context that is managed externally.
  // LWJGL detects the context that is current in the current thread,
  // creates the GLCapabilities instance and makes the OpenGL
  // bindings available for use.

  GL.createCapabilities()
  glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

  while ( !glfwWindowShouldClose(window) ) {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

    glfwSwapBuffers(window); // swap the color buffers

    // Poll for window events. The key callback above will only be
    // invoked during this call.
    glfwPollEvents()
  }

}
