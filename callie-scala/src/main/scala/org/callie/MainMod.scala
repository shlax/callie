//package org.callie
//
//import org.callie.jogl.{buffers, Gl, GlEventListener, JoglFrame}
//import com.jogamp.opengl.GL4
//import java.io.InputStreamReader
//import scala.collection.mutable.ListBuffer
//import com.jogamp.opengl.util.texture.TextureIO
//import com.jogamp.common.nio.Buffers
//import buffers._
//import org.callie.model.Mod
//import org.callie.input.Camera
//
//object MainMod extends App{
//
//  // coords : position(x, y, z) ~ texture(u, v) ~ normal(x, y, z)
//  val (coords, indices) = {
//    val m = Mod(new InputStreamReader(getClass.getResourceAsStream("/sphere.mod"), "US-ASCII"))
//
//    // stack
//    val s = ListBuffer[Mod.vtn]()
//
//    val a = ListBuffer[Float]()
//    val i = ListBuffer[Int]()
//
//    def vtx(sel: Mod.vtn){
//      val ind = s.indexOf(sel)
//      if(ind == -1){
//        val v = m.points(sel._1)
//        a += v._1 += v._2 += v._3
//
//        val t = m.uvCoord(sel._2)
//        a += t._1 += t._2
//
//        val n = m.normals(sel._3)
//        a += n._1 += n._2 += n._3
//
//        i += s.size
//        s += sel
//      }else i += ind
//    }
//
//    for(f <- m.faces){
//      vtx(f._1)
//      vtx(f._2)
//      vtx(f._3)
//    }
//
//    (a.toArray, i.toArray)
//  }
//
//  val vertex =
//    """
//      |#version 400
//      |
//      |layout(location = 0) in vec4 inPosition;
//      |layout(location = 1) in vec2 inTextureCoord;
//      |layout(location = 2) in vec3 inNormal;
//      |
//      |//uniform mat4 viewMatrix;
//      |//uniform mat4 normalMatrix;
//      |
//      |out vec2 texCoord;
//      |out float lightIntensity;
//      |
//      |vec3 lightDirection = normalize(vec3(1, 1, 1));
//      |
//      |void main(){
//      |  gl_Position = /* viewMatrix * */ inPosition; //vec4(inPosition, 1);
//      |
//      |  vec3 n = /* normalMatrix * */ inNormal;
//      |  float l = max(dot(n, lightDirection), 0.0);
//      |  lightIntensity = 0.2 + (l * 0.8);
//      |
//      |  texCoord = inTextureCoord;
//      |}
//    """.stripMargin
//
//  val fragment =
//    """
//      |#version 400
//      |
//      |uniform sampler2D textureDiffuse;
//      |
//      |in vec2 texCoord;
//      |in float lightIntensity;
//      |
//      |void main(){
//      |  gl_FragColor  = lightIntensity * texture(textureDiffuse, texCoord);
//      |}
//    """.stripMargin
//
//  JoglFrame(new GlEventListener(){
//    var texId : Int = _
//
//    var vao : Int = _
//    var vbi : Int = _
//
//    override def initGL4(implicit gl: GL4) {
//      val texture = TextureIO.newTextureData(gl.getGLProfile, getClass.getResourceAsStream("/t.png"), false, TextureIO.PNG)
//
//      gl.glEnable(Gl.CULL_FACE)
//      gl.glCullFace(Gl.BACK)
//
//      gl.glEnable(Gl.DEPTH_TEST)
//      gl.glDepthMask(true)
//
//      gl.glActiveTexture(Gl.TEXTURE0)
//
//      texId = &(gl.glGenTextures(1, _, 0))
//      gl.glBindTexture(Gl.TEXTURE_2D, texId)
//
//      gl.glPixelStorei(Gl.TEXTURE_2D, texture.getAlignment)
//      gl.glTexImage2D(Gl.TEXTURE_2D, 0, texture.getInternalFormat, texture.getWidth, texture.getHeight, texture.getBorder, texture.getPixelFormat, texture.getPixelType, texture.getBuffer)
//
//      gl.glGenerateMipmap(Gl.TEXTURE_2D)
//
//      gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_WRAP_S, Gl.REPEAT)
//      gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_WRAP_T, Gl.REPEAT)
//      gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_MAG_FILTER, Gl.LINEAR)
//      gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_MIN_FILTER, Gl.LINEAR_MIPMAP_LINEAR)
//
//      val vertexSchader = createShader(Gl.VERTEX_SHADER, vertex)
//      Camera.program(vertexSchader)
//      val p = createProgram(vertexSchader,
//                            createShader(Gl.FRAGMENT_SHADER, fragment))
//
//      // > code
//      vao = createVertexArray{
//        gl.glEnableVertexAttribArray(0)
//        gl.glEnableVertexAttribArray(1)
//        gl.glEnableVertexAttribArray(2)
//
//        //val vbo = &(gl.glGenBuffers(1, _, 0))
//        createBuffer(Gl.ARRAY_BUFFER){
//          coords.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))
//
//          gl.glVertexAttribPointer(0, 3, Gl.FLOAT, false, (3+2+3)*Buffers.SIZEOF_FLOAT, 0*Buffers.SIZEOF_FLOAT)
//          gl.glVertexAttribPointer(1, 2, Gl.FLOAT, false, (3+2+3)*Buffers.SIZEOF_FLOAT, 3*Buffers.SIZEOF_FLOAT)
//          gl.glVertexAttribPointer(2, 3, Gl.FLOAT, false, (3+2+3)*Buffers.SIZEOF_FLOAT, 5*Buffers.SIZEOF_FLOAT)
//        }
//
//      }
//
//      // + glDrawElements
//      vbi = createBuffer(Gl.ELEMENT_ARRAY_BUFFER){
//        indices.asBuffer(gl.glBufferData(Gl.ELEMENT_ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))
//      }
//      // < code
//
//      gl.glUseProgram(p)
//      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
//    }
//
//    override def displayGL4(implicit gl: GL4) {
//      gl.glClear(Gl.COLOR_BUFFER_BIT | Gl.DEPTH_BUFFER_BIT)
//      // > code
//      //gl.glDrawArrays(Gl.TRIANGLES, 0, 3)
//
//      gl.glBindTexture(Gl.TEXTURE_2D, texId)
//
//      bindVertexArray(vao){
//        bindBuffer(Gl.ELEMENT_ARRAY_BUFFER, vbi){
//          gl.glDrawElements(Gl.TRIANGLES, indices.length, Gl.UNSIGNED_INT, 0)
//        }
//      }
//      // < code
//    }
//  })
//
//}
