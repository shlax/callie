package org.callie

import org.callie.jogl.{buffers, GL_4, GL4EventListener, JoglFrame}
import com.jogamp.opengl.GL4
import java.io.InputStreamReader
import scala.collection.mutable.ListBuffer
import com.jogamp.opengl.util.texture.TextureIO
import com.jogamp.common.nio.Buffers
import buffers._
import org.callie.model.Mod
import org.callie.input.Camera

object MainModRotate extends App{

  // coords : position(x, y, z) ~ texture(u, v) ~ normal(x, y, z)
  val (coords, indices) = {
    val m = Mod(new InputStreamReader(getClass.getResourceAsStream("/sphere.mod"), "US-ASCII"))

    // stack
    val s = ListBuffer[Mod.vtn]()

    val a = ListBuffer[Float]()
    val i = ListBuffer[Int]()

    def vtx(sel: Mod.vtn){
      val ind = s.indexOf(sel)
      if(ind == -1){
        val v = m.points(sel._1)
        a += v._1 += v._2 += v._3

        val t = m.uvCoord(sel._2)
        a += t._1 += t._2

        val n = m.normals(sel._3)
        a += n._1 += n._2 += n._3

        i += s.size
        s += sel
      }else i += ind
    }

    for(f <- m.faces){
      vtx(f._1)
      vtx(f._2)
      vtx(f._3)
    }

    (a.toArray, i.toArray)
  }

  val vertex =
    """
      |#version 330
      |
      |layout(location = 0) in vec3 inPosition;
      |layout(location = 1) in vec2 inTextureCoord;
      |layout(location = 2) in vec3 inNormal;
      |
      |uniform mat4 viewMatrix;
      |uniform mat4 normalMatrix;
      |
      |/* const mat4 projection = mat4( // 1 : 10 X 0.5
      | vec4(2, 0, 0, 0),
      | vec4(0, 2, 0, 0),
      | vec4(0, 0, -1.2222222, -2.2222222),
      | vec4(0, 0, -1, 0)
      |); */
      |
      |const vec3 lightDirection = normalize(vec3(1, 1, 2));
      |
      |out vec2 texCoord;
      |out float lightIntensity;
      |
      |void main(){
      |  gl_Position = viewMatrix * vec4(inPosition, 1); // (projection * viewMatrix) * vec4(inPosition, 1); //vec4(inPosition, 1);
      |  lightIntensity = 0.2 + (max(dot((normalMatrix * vec4(inNormal,1)).xyz, lightDirection), 0.0) * 0.8);
      |  texCoord = inTextureCoord;
      |}
    """.stripMargin

  val fragment =
    """
      |#version 330
      |
      |uniform sampler2D textureDiffuse;
      |
      |in vec2 texCoord;
      |in float lightIntensity;
      |
      |void main(){
      |  gl_FragColor = texture(textureDiffuse, texCoord)*lightIntensity; // vec4(lightIntensity,lightIntensity, lightIntensity, 1);
      |}
    """.stripMargin

  JoglFrame(new GL4EventListener(){
    var texId : Int = _

    var vao : Int = _
    var vbi : Int = _

    override def initGL4(implicit gl: GL4) {
      val texture = TextureIO.newTextureData(gl.getGLProfile, getClass.getResourceAsStream("/t.png"), false, TextureIO.PNG)

      gl.glEnable(GL_4.CULL_FACE)
      gl.glCullFace(GL_4.BACK)

      gl.glEnable(GL_4.DEPTH_TEST)
      gl.glDepthMask(true)

      gl.glActiveTexture(GL_4.TEXTURE0)

      texId = &(gl.glGenTextures(1, _, 0))
      gl.glBindTexture(GL_4.TEXTURE_2D, texId)

      gl.glPixelStorei(GL_4.TEXTURE_2D, texture.getAlignment)
      gl.glTexImage2D(GL_4.TEXTURE_2D, 0, texture.getInternalFormat, texture.getWidth, texture.getHeight, texture.getBorder, texture.getPixelFormat, texture.getPixelType, texture.getBuffer)

      gl.glGenerateMipmap(GL_4.TEXTURE_2D)

      gl.glTexParameteri(GL_4.TEXTURE_2D, GL_4.TEXTURE_WRAP_S, GL_4.REPEAT)
      gl.glTexParameteri(GL_4.TEXTURE_2D, GL_4.TEXTURE_WRAP_T, GL_4.REPEAT)
      gl.glTexParameteri(GL_4.TEXTURE_2D, GL_4.TEXTURE_MAG_FILTER, GL_4.LINEAR)
      gl.glTexParameteri(GL_4.TEXTURE_2D, GL_4.TEXTURE_MIN_FILTER, GL_4.LINEAR_MIPMAP_LINEAR)

      val vertexSchader = createShader(GL_4.VERTEX_SHADER, vertex)
      val p = createProgram(vertexSchader,
        createShader(GL_4.FRAGMENT_SHADER, fragment))

      // > code
      vao = createVertexArray{
        gl.glEnableVertexAttribArray(0)
        gl.glEnableVertexAttribArray(1)
        gl.glEnableVertexAttribArray(2)

        //val vbo = &(gl.glGenBuffers(1, _, 0))
        createBuffer(GL_4.ARRAY_BUFFER){
          coords.asBuffer(gl.glBufferData(GL_4.ARRAY_BUFFER, _, _, GL_4.STATIC_DRAW))

          gl.glVertexAttribPointer(0, 3, GL_4.FLOAT, false, (3+2+3)*Buffers.SIZEOF_FLOAT, 0*Buffers.SIZEOF_FLOAT)
          gl.glVertexAttribPointer(1, 2, GL_4.FLOAT, false, (3+2+3)*Buffers.SIZEOF_FLOAT, 3*Buffers.SIZEOF_FLOAT)
          gl.glVertexAttribPointer(2, 3, GL_4.FLOAT, false, (3+2+3)*Buffers.SIZEOF_FLOAT, 5*Buffers.SIZEOF_FLOAT)
        }

      }

      // + glDrawElements
      vbi = createBuffer(GL_4.ELEMENT_ARRAY_BUFFER){
        indices.asBuffer(gl.glBufferData(GL_4.ELEMENT_ARRAY_BUFFER, _, _, GL_4.STATIC_DRAW))
      }
      // < code

      //gl.glEnable(GL_4.DEPTH_TEST)
      //gl.glEnable(GL_4.DEPTH_CLAMP)
      //gl.glDepthRange(-0.1d, -100d)
      gl.glUseProgram(p)
      Camera.program(p)
      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    }

    override def displayGL4(implicit gl: GL4) {
      gl.glClear(GL_4.COLOR_BUFFER_BIT | GL_4.DEPTH_BUFFER_BIT)
      // > code

      Camera.display
      //gl.glDrawArrays(GL_4.TRIANGLES, 0, 3)

      gl.glBindTexture(GL_4.TEXTURE_2D, texId)

      bindVertexArray(vao){
        bindBuffer(GL_4.ELEMENT_ARRAY_BUFFER, vbi){ // glDrawElementsIndirect
          gl.glDrawElements(GL_4.TRIANGLES, indices.length, GL_4.UNSIGNED_INT, 0)
        }
      }
      // < code
    }
  })

}

