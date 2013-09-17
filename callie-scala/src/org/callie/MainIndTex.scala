package org.callie

import org.callie.jogl.{buffers, GL_4, GL4EventListener, JoglFrame}
import javax.media.opengl.GL4
import com.jogamp.opengl.util.texture.{Texture, TextureIO}
import java.io.FileInputStream
import buffers._

object MainIndTex extends App{

  val vertex =
    """
      |#version 400
      |
      |layout(location = 0) in vec4 position;
      |layout(location = 1) in vec2 inTextureCoord;
      |
      |out vec2 passTextureCoord;
      |
      |void main(){
      |    gl_Position = position;
      |    passTextureCoord = inTextureCoord;
      |}
    """.stripMargin

  val fragment =
    """
      |#version 400
      |
      |uniform sampler2D textureDiffuse;
      |
      |in vec2 passTextureCoord;
      |
      |out vec4 outputColor;
      |
      |void main(){
      |   outputColor = texture(textureDiffuse, passTextureCoord);
      |}
    """.stripMargin

  val positions = Array(
  //position (3)        texture coord (2)
    -0.75f,  0.75f, 0f, -1f,  1f,
    -0.75f, -0.75f, 0f, -1f, -1f,
     0.75f, -0.75f, 0f,  1f, -1f,
     0.75f,  0.75f, 0f,  1f,  1f
  )

  val indices = Array(
    // Left bottom triangle
    0, 1, 2,
    // Right top triangle
    2, 3, 0
  )

  JoglFrame(new GL4EventListener(){
    var texture : Texture = _

    var vao : Int = _
    var vbi : Int = _

    override def initGL4(implicit gl: GL4) {
      texture = TextureIO.newTexture(new FileInputStream("t.png"), true, TextureIO.PNG)
      texture.enable(gl)

      val p = createProgram(createShader(GL_4.VERTEX_SHADER, vertex),
        createShader(GL_4.FRAGMENT_SHADER, fragment))

      // > code
      vao = createVertexArray{
        gl.glEnableVertexAttribArray(0)
        gl.glEnableVertexAttribArray(1)

        //val vbo = &(gl.glGenBuffers(1, _, 0))
        createBuffer(GL_4.ARRAY_BUFFER){
          positions.asBuffer(gl.glBufferData(GL_4.ARRAY_BUFFER, _, _, GL_4.STATIC_DRAW))

          gl.glVertexAttribPointer(0, 3, GL_4.FLOAT, false, 3 + 2, 3)
          gl.glVertexAttribPointer(0, 2, GL_4.FLOAT, false, 3 + 2, 3)
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

    override def displayGL4(implicit gl: GL4) {
      gl.glClear(GL_4.COLOR_BUFFER_BIT)

      // > code
      //gl.glDrawArrays(GL_4.TRIANGLES, 0, 3)
      texture.bind(gl)

      bindVertexArray(vao){
        bindBuffer(GL_4.ELEMENT_ARRAY_BUFFER, vbi){
          gl.glDrawElements(GL_4.TRIANGLES, indices.length, GL_4.UNSIGNED_INT, 0)
        }
      }
      // < code

    }
  })

}
