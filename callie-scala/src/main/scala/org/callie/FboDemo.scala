package org.callie

import com.jogamp.common.nio.Buffers
import org.callie.control.MovingObject
import org.callie.input.Camera
import org.callie.jogl.{Gl, GlEventListener, GlType, JoglFrame}
import org.callie.map.Map25
import org.callie.model.{Mod, StaticObject, TextureGroup}
import org.callie.ringing.{JoinControl, KeyFrameLoader, Node}

object FboDemo extends App{

  val indices = Array(0, 1, 2,
    2, 3, 0)

  //                       Positions           Texture
  val quadVertices = Array(-1f,  1f, 0.0f, 0.0f, 1.0f,
    -1f, -1f, 0.0f, 0.0f, 0.0f,
    1f, -1f, 0.0f, 1.0f, 0.0f,
    1f,  1f, 0.0f, 1.0f, 1.0f)

  val passVertex = """
                     |#version 300 es
                     |
                     |layout(location = 0) in vec3 inPosition;
                     |layout(location = 1) in vec2 inTextureCoord;
                     |
                     |out vec2 texCoord;
                     |
                     |void main() {
                     |    texCoord = inTextureCoord;
                     |    gl_Position = vec4(inPosition, 1.0);
                     |}
                   """.stripMargin.trim

  val passFragment = """
                       |#version 300 es
                       |
                       |uniform sampler2D inTexture;
                       |
                       |in highp vec2 texCoord;
                       |
                       |out highp vec4 fragColor;
                       |
                       |void main(){
                       |    fragColor = texture(inTexture, texCoord);
                       |}
                     """.stripMargin.trim

  // https://stackoverflow.com/questions/18510701/glsl-how-to-show-normals-with-geometry-shader
  val vertex = """
                 |#version 300 es
                 |//#version 400
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
                 |
                 |  //vec4 tmp = viewMatrix * vec4(inPosition, 1);
                 |  //gl_Position = vec4(tmp.x / tmp.w, tmp.y / tmp.w, tmp.z / tmp.w, 1);
                 |
                 |  lightIntensity = 0.2 + (max(dot((normalMatrix * vec4(inNormal,1)).xyz, lightDirection), 0.0) * 0.8);
                 |  texCoord = inTextureCoord;
                 |}
               """.stripMargin.trim

  val fragment = """
                   |#version 300 es
                   |//#version 400
                   |
                   |uniform sampler2D textureDiffuse;
                   |
                   |in highp vec2 texCoord;
                   |in highp float lightIntensity;
                   |
                   |out highp vec4 fragColor;
                   |
                   |void main(){
                   |  highp vec4 c = texture(textureDiffuse, texCoord);
                   |  if (c.w < 0.5) discard;
                   |  fragColor = c * lightIntensity; // vec4(lightIntensity,lightIntensity, lightIntensity, 1);
                   |}
                 """.stripMargin.trim

  val camCtrl = new MovingObject(Map25.load("/demo/map/floor.map"), 1.5f)

  JoglFrame(new GlEventListener {

    val (charObj, joint) = Node.load(this, Map("pSphere5" -> Mod.load("/demo/char/base.mod").scale(0.1f),
      "polySurface115" -> Mod.load("/demo/char/hair.mod").scale(0.1f)),
      "/demo/char/joints.mod", 0.1f)

    val stand = KeyFrameLoader.load(joint, "/demo/char/stand.mod", 0.1f)

    val run1 = KeyFrameLoader.load(joint, "/demo/char/run1.mod", 0.1f)
    val run2 = KeyFrameLoader.load(joint, "/demo/char/run2.mod", 0.1f)
    val run3 = KeyFrameLoader.load(joint, "/demo/char/run3.mod", 0.1f)
    val run4 = KeyFrameLoader.load(joint, "/demo/char/run4.mod", 0.1f)

    val char = new TextureGroup(this, "/demo/char/texture.png", Gl.TEXTURE0, charObj:_* )

    val mapa = new TextureGroup(this, "/demo/t.png", Gl.TEXTURE0,
      new StaticObject(this, Mod.load("/demo/map/floor.mod"))
    )

    val anim = JoinControl(camCtrl, joint, stand, run1, run2, run3, run4)

    var t: Long = 0

    var prog:Int = _

    var fbo: Int = _
    var fboTex: Int = _

    var progQuad:Int = _
    var dataQuad:Int = _
    var dataQuadInd:Int = _

    override def initGL4(gl: GlType){
      fbo = createFrameBuffer(gl){

        fboTex = createTexture(gl){
          gl.glTexImage2D(Gl.TEXTURE_2D, 0, Gl.RGB, 1280, 720, 0, Gl.RGB, Gl.UNSIGNED_BYTE, null)

          gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_MAG_FILTER, Gl.NEAREST)
          gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_MIN_FILTER, Gl.NEAREST)
        }

        gl.glFramebufferTexture2D(Gl.FRAMEBUFFER, Gl.COLOR_ATTACHMENT0, Gl.TEXTURE_2D, fboTex, 0)

        val rbo = createRenderbuffer(gl){
          gl.glRenderbufferStorage(Gl.RENDERBUFFER, Gl.DEPTH_COMPONENT32F, 1280, 720)
        }

        gl.glFramebufferRenderbuffer(Gl.FRAMEBUFFER, Gl.DEPTH_ATTACHMENT, Gl.RENDERBUFFER, rbo)

      }

      val vertexSchader = createShader(gl, Gl.VERTEX_SHADER, vertex)
      val fragmentSchader = createShader(gl, Gl.FRAGMENT_SHADER, fragment)
      prog = createProgram(gl, vertexSchader, fragmentSchader)

      // quad >>

      val vertexSchaderQuad = createShader(gl, Gl.VERTEX_SHADER, passVertex)
      val fragmentSchaderQuad = createShader(gl, Gl.FRAGMENT_SHADER, passFragment)
      progQuad = createProgram(gl, vertexSchaderQuad, fragmentSchaderQuad)

      dataQuad = createVertexArray(gl){
        gl.glEnableVertexAttribArray(0)
        gl.glEnableVertexAttribArray(1)

        createBuffer(gl, Gl.ARRAY_BUFFER){
          import org.callie.jogl.buffers._
          quadVertices.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))

          gl.glVertexAttribPointer(0, 3, Gl.FLOAT, false, (3+2)*Buffers.SIZEOF_FLOAT, 0*Buffers.SIZEOF_FLOAT)
          gl.glVertexAttribPointer(1, 2, Gl.FLOAT, false, (3+2)*Buffers.SIZEOF_FLOAT, 3*Buffers.SIZEOF_FLOAT)
        }
      }

      dataQuadInd = createBuffer(gl, Gl.ELEMENT_ARRAY_BUFFER){
        import org.callie.jogl.buffers._
        indices.asBuffer(gl.glBufferData(Gl.ELEMENT_ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))
      }

      // << quad

      mapa.init(gl)
      char.init(gl)

      Camera.program(gl, prog)
      Camera.lookAt(camCtrl)

      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
      gl.glEnable(Gl.DEPTH_TEST)

      //gl.glPolygonMode(Gl.FRONT_AND_BACK, Gl.LINE)

      t = System.nanoTime()
    }

    override def displayGL4(gl: GlType){
      val tmp = System.nanoTime()
      val dt: Float = ((tmp - t) / 1e9d).asInstanceOf[Float]
      t = tmp

      bindFrameBuffer(gl, fbo) {
        gl.glClear(Gl.COLOR_BUFFER_BIT | Gl.DEPTH_BUFFER_BIT)

        gl.glUseProgram(prog)

        anim.apply(dt)
        Camera.display(gl)

        mapa.display(gl)
        char.display(gl)
      }

      gl.glClear(Gl.COLOR_BUFFER_BIT | Gl.DEPTH_BUFFER_BIT)

      gl.glUseProgram(progQuad)
      gl.glActiveTexture(Gl.TEXTURE0)
      bindTexture(gl, fboTex){
        bindVertexArray(gl, dataQuad){
          bindBuffer(gl, Gl.ELEMENT_ARRAY_BUFFER, dataQuadInd) {
            gl.glDrawElements(Gl.TRIANGLES, indices.length, Gl.UNSIGNED_INT, 0)
          }
        }
      }

    }

  })

}
