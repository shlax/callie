package org.callie

import org.callie.jogl.{buffers, JoglFrame, GlEventListener, Gl}
import com.jogamp.opengl.GL4
import buffers._

object MainInd extends App{

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
  
  val positions = Array(
      -0.75f,  0.75f, 0f,
      -0.75f, -0.75f, 0f,
       0.75f, -0.75f, 0f,
       0.75f,  0.75f, 0f
    )
  
  val indices = Array(
      // Left bottom triangle
      0, 1, 2,
      // Right top triangle
      2, 3, 0
    )

  JoglFrame(new GlEventListener(){
    override def initGL4(implicit gl : GL4){
      val p = createProgram(createShader(Gl.VERTEX_SHADER, vertex),
                            createShader(Gl.FRAGMENT_SHADER, fragment))

      // > code
      vao = createVertexArray{
        gl.glEnableVertexAttribArray(0)

        //val vbo = &(gl.glGenBuffers(1, _, 0))
        createBuffer(Gl.ARRAY_BUFFER){
          positions.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))
          gl.glVertexAttribPointer(0, 3, Gl.FLOAT, false, 0, 0)
        }
      }

      // + glDrawElements
      vbi = createBuffer(Gl.ELEMENT_ARRAY_BUFFER){
        indices.asBuffer(gl.glBufferData(Gl.ELEMENT_ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))
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
      gl.glClear(Gl.COLOR_BUFFER_BIT)

      // > code
      //gl.glDrawArrays(Gl.TRIANGLES, 0, 3)
      bindVertexArray(vao){
        bindBuffer(Gl.ELEMENT_ARRAY_BUFFER, vbi){
          gl.glDrawElements(Gl.TRIANGLES, indices.length, Gl.UNSIGNED_INT, 0)
        }
      }
      // < code
    }
  })
}
