package org.callie

import org.callie.jogl.{buffers, GL_4, GL4EventListener, JoglFrame}
import javax.media.opengl.GL4
import java.io.InputStreamReader
import scala.collection.mutable.ListBuffer
import com.jogamp.common.nio.Buffers
import buffers._
import org.callie.model.Mod

// http://prideout.net/blog/?p=48
object MainTess extends App{

  // coords : position(x, y, z) ~ texture(u, v) ~ normal(x, y, z)
  val (coords, indices) = {
    val m = Mod(new InputStreamReader(getClass.getResourceAsStream("/tes.mod"), "US-ASCII"))

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
      |#version 430
      |
      |layout(location = 0) in vec4 inPosition;
      |layout(location = 1) in vec2 inTextureCoord;
      |layout(location = 2) in vec3 inNormal;
      |
      |void main(){
      |    gl_Position = inPosition;
      |}
    """.stripMargin

  val tessControl =
    """
      |#version 430 core
      |
      |layout (vertices = 3) out;
      |
      |void main(void){
      |  if (gl_InvocationID == 0){
      |    gl_TessLevelInner[0] = 4.0;
      |    gl_TessLevelOuter[0] = 4.0;
      |    gl_TessLevelOuter[1] = 4.0;
      |    gl_TessLevelOuter[2] = 4.0;
      |  }
      |
      |  gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
      |}
    """.stripMargin

  val tessEval =
    """
      |#version 430 core
      |
      |layout(triangles, equal_spacing, ccw) in;
      |
      |void main(void){
      |  vec3 p0 = gl_TessCoord.x * gl_in[0].gl_Position.xyz;
      |  vec3 p1 = gl_TessCoord.y * gl_in[1].gl_Position.xyz;
      |  vec3 p2 = gl_TessCoord.z * gl_in[2].gl_Position.xyz;
      |
      |  gl_Position = vec4( (p0 + p1 + p2), 1) ; // normalize
      |}
    """.stripMargin

  val fragment =
    """
      |#version 430
      |
      |void main(){
      |  gl_FragColor  = vec4(1,1,1,1);
      |}
    """.stripMargin

  JoglFrame(new GL4EventListener(){
    var vao : Int = _
    var vbi : Int = _

    override def initGL4(implicit gl: GL4) {

      gl.glEnable(GL_4.DEPTH_TEST)
      gl.glDepthMask(true)

      gl.glPolygonMode( GL_4.FRONT_AND_BACK, GL_4.LINE)

      val p = createProgram(createShader(GL_4.VERTEX_SHADER, vertex),
                            createShader(GL_4.TESS_CONTROL_SHADER, tessControl),
                            createShader(GL_4.TESS_EVALUATION_SHADER, tessEval),
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

      gl.glUseProgram(p)
      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    }

    override def displayGL4(implicit gl: GL4) {
      gl.glClear(GL_4.COLOR_BUFFER_BIT | GL_4.DEPTH_BUFFER_BIT)
      // > code
      //gl.glDrawArrays(GL_4.TRIANGLES, 0, 3)

      bindVertexArray(vao){
        bindBuffer(GL_4.ELEMENT_ARRAY_BUFFER, vbi){
          //gl.glPatchParameteri(GL_4.PATCH_VERTICES, 3)
          gl.glDrawElements(GL_4.PATCHES, indices.length, GL_4.UNSIGNED_INT, 0)
        }
      }
      // < code
    }
  })
}
