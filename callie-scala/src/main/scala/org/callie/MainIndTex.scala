//package org.callie
//
//import com.jogamp.opengl.GL4
//import com.jogamp.opengl.util.texture.TextureIO
//import com.jogamp.common.nio.Buffers
//import org.callie.jogl.{buffers, Gl, GlEventListener, JoglFrame}
//import buffers._
//
//object MainIndTex extends App{
//
//  val vertex =
//    """
//      |#version 400
//      |
//      |layout(location = 0) in vec4 position;
//      |layout(location = 1) in vec2 inTextureCoord;
//      |
//      |out vec2 passTextureCoord;
//      |
//      |void main(){
//      |    gl_Position = position;
//      |    passTextureCoord = inTextureCoord;
//      |}
//    """.stripMargin
//
//  val fragment =
//    """
//      |#version 400
//      |
//      |uniform sampler2D textureDiffuse;
//      |
//      |in vec2 passTextureCoord;
//      |
//      |out vec4 outputColor;
//      |
//      |void main(){
//      |   //outputColor = vec4(passTextureCoord[0], passTextureCoord[1], 1.0f, 1.0f);
//      |   outputColor = texture(textureDiffuse, passTextureCoord);
//      |}
//    """.stripMargin
//
//  val positions = Array(
//  //position (3)        texture coord (2)
//    -0.75f,  0.75f, 0f, 0f, 1f,
//    -0.75f, -0.75f, 0f, 0f, 0f,
//     0.75f, -0.75f, 0f, 1f, 0f,
//     0.75f,  0.75f, 0f, 1f, 1f
//  )
//
//  val indices = Array(
//    // Left bottom triangle
//    0, 1, 2,
//    // Right top triangle
//    2, 3, 0
//  )
//
//  JoglFrame(new GlEventListener(){
//    //var texture : TextureData = _
//    var texId : Int = _
//
//    var vao : Int = _
//    var vbi : Int = _
//
//    override def initGL4(implicit gl: GL4) {
//
//      val texture = TextureIO.newTextureData(gl.getGLProfile, getClass.getResourceAsStream("/t.png"), false, TextureIO.PNG)
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
//      val p = createProgram(createShader(Gl.VERTEX_SHADER, vertex),
//                            createShader(Gl.FRAGMENT_SHADER, fragment))
//
//      // > code
//      vao = createVertexArray{
//        gl.glEnableVertexAttribArray(0)
//        gl.glEnableVertexAttribArray(1)
//
//        //val vbo = &(gl.glGenBuffers(1, _, 0))
//        createBuffer(Gl.ARRAY_BUFFER){
//          positions.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))
//
//          gl.glVertexAttribPointer(0, 3, Gl.FLOAT, false, (3+2)*Buffers.SIZEOF_FLOAT, 0*Buffers.SIZEOF_FLOAT)
//          gl.glVertexAttribPointer(1, 2, Gl.FLOAT, false, (3+2)*Buffers.SIZEOF_FLOAT, 3*Buffers.SIZEOF_FLOAT)
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
//
//    }
//
//    override def displayGL4(implicit gl: GL4) {
//      gl.glClear(Gl.COLOR_BUFFER_BIT)
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
//
//    }
//  })
//
//}
