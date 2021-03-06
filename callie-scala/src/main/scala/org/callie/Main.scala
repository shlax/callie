//package org.callie
//
//import java.awt.{BorderLayout, Frame}
//import com.jogamp.opengl.{GLCapabilities, GLProfile }
//import com.jogamp.opengl.awt.GLCanvas
//import java.awt.event.WindowAdapter
//import java.awt.event.WindowEvent
//import com.jogamp.opengl.GLEventListener
//import com.jogamp.opengl.GLAutoDrawable
////import javax.media.opengl.{GL4, GL3, GL2GL3, GL2ES2, Gl, GLBase}
//import org.callie.jogl.Gl
//import com.jogamp.opengl.util.FPSAnimator
//import com.jogamp.common.nio.Buffers
//
//// http://www.arcsynthesis.org/gltut/index.html
//// http://antongerdelan.net/opengl/
//// http://www.opengl.org/sdk/docs/man/xhtml/
//
//// http://gamedev.stackexchange.com/questions/20584/error-when-trying-to-use-vbo-array-vertex-buffer-object-must-be-disabled-to-cal
//object Main extends App {
//
//  val vertex =
//    """
//      |#version 400
//      |
//      |layout(location = 0) in vec4 position;
//      |void main()
//      |{
//      |    gl_Position = position;
//      |}
//    """.stripMargin
//
//   //   println(vertex)
//
//  val fragment =
//    """
//      |#version 400
//      |
//      |out vec4 outputColor;
//      |void main()
//      |{
//      |   outputColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);
//      |}
//    """.stripMargin
//
////  val VERTEX_SHADER = """
////    in vec4 position;
////    in vec4 color;
////    smooth out vec4 vColor;
////    void main() {
////      gl_Position = position;
////      vColor = color;
////    }
////                      """
////
////  val FRAGMENT_SHADER = """
////    smooth in vec4 vColor;
////    void main() {
////      gl_FragColor = vColor;
////    }
////                        """
////
////  val sValues = Array(
////    0.0f, 0.5f, 0.0f, 1.0f,
////    -0.5f, -0.5f, 0.0f, 1.0f,
////    0.5f, -0.5f, 0.0f, 1.0f,
////    1.0f, 0.0f, 0.0f, 1.0f,
////    0.0f, 1.0f, 0.0f, 1.0f,
////    0.0f, 0.0f, 1.0f, 1.0f
////  )
//
//  val profile = GLProfile.get(GLProfile.GL4)
//
//  val capabilities = new GLCapabilities(profile)
//  val glCanvas = new GLCanvas(capabilities)
//  val animator = new FPSAnimator(glCanvas, 60, true)
//
//  glCanvas.addGLEventListener(new GLEventListener(){
//    //val glu2 = new GLUgl2()
//
//    override def reshape(drawable:GLAutoDrawable, x:Int, y:Int, width:Int, height:Int){
//      val gl = drawable.getGL.getGL4
//      // http://www.songho.ca/opengl/gl_projectionmatrix.html
//      //                   (fovy , aspect      , zNear, zFar) > http://pyopengl.sourceforge.net/documentation/manual-3.0/gluPerspective.xhtml
//      //glu2.gluPerspective(45.0f, width/height, 0.5  , 1000);
//      gl.glViewport(0, 0, width, height)
//      println(x+"-"+y)
//    }
//
////    var program : Int = _
////    var valuesBuffer : Int = _
////    var locPosition  : Int = _
////    var locColor  : Int = _
//
//    override def init(drawable:GLAutoDrawable){
//      drawable.setAutoSwapBufferMode(true)
//      val gl = drawable.getGL.getGL4
//
////      val vertexShader = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER)
////      gl.glShaderSource(vertexShader, 1, Array(VERTEX_SHADER), null)
////      gl.glCompileShader(vertexShader)
////
////      val fragmentShader = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER)
////      gl.glShaderSource(fragmentShader, 1, Array(FRAGMENT_SHADER), null)
////      gl.glCompileShader(fragmentShader)
////
////      program = gl.glCreateProgram()
////      gl.glAttachShader(program, vertexShader)
////      gl.glAttachShader(program, fragmentShader)
////      gl.glLinkProgram(program)
////
////      locPosition = gl.glGetAttribLocation(program, "position")
////      locColor = gl.glGetAttribLocation(program, "color")
////
////      val buff = FloatBuffer.allocate(sValues.length)
////      buff.rewind()
////      buff.put(sValues)
////
////      val sValuesBuffer = Array(0)
////      gl.glGenBuffers(1, sValuesBuffer, 0)
////      valuesBuffer = sValuesBuffer(0)
////      gl.glBindBuffer(Gl.GL_ARRAY_BUFFER, valuesBuffer)
////      gl.glBufferData(Gl.GL_ARRAY_BUFFER, sValues.length, buff, Gl.GL_STATIC_DRAW)
////
////      gl.glUseProgram(program)
////      gl.glVertexAttribPointer(locPosition, 2, Gl.GL_FLOAT, false, 0, 0)
////      gl.glEnableVertexAttribArray(locPosition)
//
//
//
////      println(fragment)
//
//
//
//      val tmp = Array(0)
//
//      val vertexShader = gl.glCreateShader(Gl.VERTEX_SHADER)
//      gl.glShaderSource(vertexShader, 1, Array(vertex), null)
//      gl.glCompileShader(vertexShader)
//
//      gl.glGetShaderiv(vertexShader, Gl.COMPILE_STATUS, tmp, 0);
//
//      println("vertexShader "+vertexShader+" "+tmp(0))
//
//      val fragmentShader = gl.glCreateShader(Gl.FRAGMENT_SHADER)
//      gl.glShaderSource(fragmentShader, 1, Array(fragment), null)
//      gl.glCompileShader(fragmentShader)
//
//      gl.glGetShaderiv(vertexShader, Gl.COMPILE_STATUS, tmp, 0);
//
//      println("fragmentShader "+fragmentShader+" "+tmp(0))
//
//      theProgram = gl.glCreateProgram()
//      gl.glAttachShader(theProgram, vertexShader)
//      gl.glAttachShader(theProgram, fragmentShader)
//      gl.glLinkProgram(theProgram)
//
//      gl.glGetProgramiv(theProgram, Gl.LINK_STATUS, tmp, 0)
//
//      println("theProgram "+theProgram+" "+tmp(0))
//
//      val vertexPositions = Array(
//           0.75f, -0.75f, 0.0f, 1.0f,
//           0.75f,  0.75f, 0.0f, 1.0f,
//          -0.75f, -0.75f, 0.0f, 1.0f
//        )
//
//      gl.glGenBuffers(1, tmp, 0)
//      positionBufferObject = tmp(0)
//
//      println("positionBufferObject "+positionBufferObject)
//
//      val buff = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vertexPositions.length)
//      buff.put(vertexPositions)
//      buff.rewind()
//
//      gl.glBindBuffer(Gl.ARRAY_BUFFER, positionBufferObject)
//      gl.glBufferData(Gl.ARRAY_BUFFER, vertexPositions.length * Buffers.SIZEOF_FLOAT , buff, Gl.STATIC_DRAW)
////
//
//
//
////      gl.glBindBuffer(Gl.GL_ARRAY_BUFFER, positionBufferObject)
//
//      gl.glVertexAttribPointer(0, 4, Gl.FLOAT, false, 0, 0)
//
//      gl.glBindBuffer(Gl.ARRAY_BUFFER, 0)
//
//      gl.glUseProgram(theProgram)
//
//      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
//    }
//
//    var theProgram : Int = _
//    var positionBufferObject : Int = _
//
//    override def update(drawable:GLAutoDrawable){
//      //drawable.setAutoSwapBufferMode(true)
//      val gl = drawable.getGL.getGL4
//      gl.glEnableVertexAttribArray(0)
//
//      gl.glClear(Gl.COLOR_BUFFER_BIT)
//
//      gl.glBindVertexArray(positionBufferObject)
//      gl.glDrawArrays(Gl.TRIANGLES, 0, 3)
//
////      gl.glDisableVertexAttribArray(0)
////      gl.glUseProgram(0)
//
//      //gl.glFlush()
//      println("+")
//
////      gl.glClearColor(0f, 0f, 0f, 0f)
////      gl.glClear(Gl.GL_COLOR_BUFFER_BIT)
////
////      gl.glUseProgram(program)
////
////      gl.glBindVertexArray(valuesBuffer)
////
////      gl.glBindBuffer(Gl.GL_ARRAY_BUFFER, valuesBuffer)
////
////      gl.glEnableVertexAttribArray(locPosition)
////      gl.glVertexAttribPointer(locPosition, 4, Gl.GL_FLOAT, false, 0, 0)
////
////      gl.glEnableVertexAttribArray(locColor)
////      gl.glVertexAttribPointer(locColor, 4, Gl.GL_FLOAT, false, 0, 48)
////
////      gl.glDrawArrays(Gl.GL_TRIANGLES, 0, 3)
////
////      gl.glFlush()
//    }
//
//    override def dispose(drawable:GLAutoDrawable){
//    }
//  })
//
//  val f = new Frame()
//  f.addWindowListener(new WindowAdapter(){
//    override def windowClosing(e:WindowEvent){
//      f.setVisible(false)
//      f.dispose()
//    }
//  })
//
//  glCanvas.setSize(300, 300)
////f.setResizable(false)
//  f.setLocation(100, 100)
//  f.setLayout(new BorderLayout())
//  f.add(glCanvas, BorderLayout.CENTER)
//  f.pack()
//
//  f.setVisible(true)
//
//}