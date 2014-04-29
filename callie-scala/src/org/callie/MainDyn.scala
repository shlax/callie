package org.callie

import org.callie.jogl.{buffers, GL_4, GL4EventListener, JoglFrame}
import javax.media.opengl.GL4
import buffers._
import java.awt.event.KeyEvent

object MainDyn extends App{

  val vertex =
    """
      |#version 400
      |
      |layout(location = 0) in vec4 position;
      |void main(){
      |    gl_Position = position;
      |}
    """.stripMargin

  val fragment =
    """
      |#version 400
      |
      |out vec4 outputColor;
      |void main(){
      |   outputColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);
      |}
    """.stripMargin

  val positions1 = Array(
    -0.75f,  0.75f, 0f,
    -0.75f, -0.75f, 0f,
    0.75f, -0.75f, 0f,
    0.75f,  0.75f, 0f
  )

  val positions2 = Array(
    -0.5f,  0.5f, 0f,
    -0.5f, -0.5f, 0f,
    0.5f, -0.5f, 0f,
    0.5f,  0.5f, 0f
  )

  val indices = Array(
    // Left bottom triangle
    0, 1, 2,
    // Right top triangle
    2, 3, 0
  )

  JoglFrame.gl(new GL4EventListener(){

    override def initGL4(implicit gl: GL4) {
      val p = createProgram(createShader(GL_4.VERTEX_SHADER, vertex),
                            createShader(GL_4.FRAGMENT_SHADER, fragment))

      // > code
      vao = createVertexArray{
        gl.glEnableVertexAttribArray(0)

        //val vbo = &(gl.glGenBuffers(1, _, 0))
        vbo = createBuffer(GL_4.ARRAY_BUFFER){
          positions1.asBuffer(gl.glBufferData(GL_4.ARRAY_BUFFER, _, _, GL_4.DYNAMIC_DRAW))
          gl.glVertexAttribPointer(0, 3, GL_4.FLOAT, false, 0, 0)
        }
      }

      // + glDrawElements
      vbi = createBuffer(GL_4.ELEMENT_ARRAY_BUFFER){
        indices.asBuffer(gl.glBufferData(GL_4.ELEMENT_ARRAY_BUFFER, _, _, GL_4.STATIC_DRAW))
      }
      // < code

      gl.glUseProgram(p)
      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    }

    // Vertex Array Object
    var vao : Int = _

    // Vertex Buffer Index
    var vbi : Int = _

    // Vertex Buffer Object
    var vbo : Int = _

    override def displayGL4(implicit gl: GL4) {
      gl.glClear(GL_4.COLOR_BUFFER_BIT)

      // > code

      bindVertexArray(vao){

        bindBuffer(GL_4.ARRAY_BUFFER, vbo){
          positions2.asBuffer(gl.glBufferData(GL_4.ARRAY_BUFFER, _, _, GL_4.DYNAMIC_DRAW))
        }

        bindBuffer(GL_4.ELEMENT_ARRAY_BUFFER, vbi){
          gl.glDrawElements(GL_4.TRIANGLES, indices.length, GL_4.UNSIGNED_INT, 0)
        }
      }
      // < code
    }

  })

}
