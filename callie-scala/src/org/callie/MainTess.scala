package org.callie

import org.callie.jogl.{buffers, JoglFrame, GL4EventListener, GL_4}
import javax.media.opengl.GL4
import buffers._

// http://prideout.net/blog/?p=48
object MainTess extends App{

  // 1.3 kg/m3  5ml/min
  // 1.3 * 60 * 5 / 1000

  val vertex =
    """
      |in vec4 Position;
      |out vec3 vPosition;
      |
      |void main(){
      |    vPosition = Position.xyz;
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

  val positions = Array(
    0.000f,  0.000f,  1.000f,
    0.894f,  0.000f,  0.447f,
    0.276f,  0.851f,  0.447f,
    -0.724f,  0.526f,  0.447f,
    -0.724f, -0.526f,  0.447f,
    0.276f, -0.851f,  0.447f,
    0.724f,  0.526f, -0.447f,
    -0.276f,  0.851f, -0.447f,
    -0.894f,  0.000f, -0.447f,
    -0.276f, -0.851f, -0.447f,
    0.724f, -0.526f, -0.447f,
    0.000f,  0.000f, -1.000f
  )

  val indices = Array(
    2, 1, 0,
    3, 2, 0,
    4, 3, 0,
    5, 4, 0,
    1, 5, 0,
    11, 6,  7,
    11, 7,  8,
    11, 8,  9,
    11, 9,  10,
    11, 10, 6,
    1, 2, 6,
    2, 3, 7,
    3, 4, 8,
    4, 5, 9,
    5, 1, 10,
    2,  7, 6,
    3,  8, 7,
    4,  9, 8,
    5, 10, 9,
    1, 6, 10
  )

  JoglFrame(new GL4EventListener(){
    override def initGL4(implicit gl : GL4){
      val p = createProgram(createShader(GL_4.VERTEX_SHADER, vertex),
                            createShader(GL_4.FRAGMENT_SHADER, fragment))

      // > code
      vao = createVertexArray{
        gl.glEnableVertexAttribArray(0)

        //val vbo = &(gl.glGenBuffers(1, _, 0))
        createBuffer(GL_4.ARRAY_BUFFER){
          positions.asBuffer(gl.glBufferData(GL_4.ARRAY_BUFFER, _, _, GL_4.STATIC_DRAW))
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

    override def displayGL4(implicit gl : GL4){
      gl.glClear(GL_4.COLOR_BUFFER_BIT)

      // > code
      //gl.glDrawArrays(GL_4.TRIANGLES, 0, 3)
      bindVertexArray(vao){
        bindBuffer(GL_4.ELEMENT_ARRAY_BUFFER, vbi){
          gl.glDrawElements(GL_4.TRIANGLES, indices.length, GL_4.UNSIGNED_INT, 0)
        }
      }
      // < code
    }
  })
}
