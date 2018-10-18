//package org.callie
//
//import org.callie.jogl.{Gl, GlEventListener, JoglFrame, buffers}
//import com.jogamp.opengl.GL3ES3
//import buffers._
//
//object MainDyn extends App{
//
//  val vertex =
//    """
//      |#version 300 es
//      |
//      |layout(location = 0) in vec4 position;
//      |void main(){
//      |    gl_Position = position;
//      |}
//    """.stripMargin
//
//  val fragment =
//    """
//      |#version 300 es
//      |
//      |out highp vec4 outputColor;
//      |void main(){
//      |   outputColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);
//      |}
//    """.stripMargin
//
//  val positions1 = Array(
//    -0.75f,  0.75f, 0f,
//    -0.75f, -0.75f, 0f,
//    0.75f, -0.75f, 0f,
//    0.75f,  0.75f, 0f
//  )
//
//  val positions2 = Array(
//    -0.5f,  0.5f, 0f,
//    -0.5f, -0.5f, 0f,
//    0.5f, -0.5f, 0f,
//    0.5f,  0.5f, 0f
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
//
//    override def initGL4(implicit gl: GL3ES3) {
//      val p = createProgram(createShader(Gl.VERTEX_SHADER, vertex),
//                            createShader(Gl.FRAGMENT_SHADER, fragment))
//
//      // > code
//      vao = createVertexArray{
//        gl.glEnableVertexAttribArray(0)
//
//        //val vbo = &(gl.glGenBuffers(1, _, 0))
//        vbo = createBuffer(Gl.ARRAY_BUFFER){
//          positions1.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.DYNAMIC_DRAW))
//          gl.glVertexAttribPointer(0, 3, Gl.FLOAT, false, 0, 0)
//        }
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
//    // Vertex Array Object
//    var vao : Int = _
//
//    // Vertex Buffer Index
//    var vbi : Int = _
//
//    // Vertex Buffer Object
//    var vbo : Int = _
//
//    override def displayGL4(implicit gl: GL3ES3) {
//      gl.glClear(Gl.COLOR_BUFFER_BIT)
//
//      // > code
//
//      bindVertexArray(vao){
//
//
//        bindBuffer(Gl.ARRAY_BUFFER, vbo){
//          if(Math.random() < 0.1){
//            positions1.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.DYNAMIC_DRAW))
//          }else{
//            positions2.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.DYNAMIC_DRAW))
//          }
//
//
//        }
//
//        bindBuffer(Gl.ELEMENT_ARRAY_BUFFER, vbi){
//          gl.glDrawElements(Gl.TRIANGLES, indices.length, Gl.UNSIGNED_INT, 0)
//        }
//      }
//      // < code
//    }
//
//  })
//
//}
