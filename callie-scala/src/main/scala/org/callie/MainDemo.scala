package org.callie

import org.callie.input.{Camera, TrackingObject}
import org.callie.jogl.{Gl, GlEventListener, GlType, JoglFrame}
import org.callie.map.Map25
import org.callie.math.{Matrix4, Vector3}
import org.callie.model.{Mod, StaticObject, TextureGroup}
import org.callie.ringing.{KeyFrameLoader, Node}

object MainDemo extends App{

  // https://stackoverflow.com/questions/18510701/glsl-how-to-show-normals-with-geometry-shader
  val vertex = """
        |//#version 330 core
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
        |//#version 330 core
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

  val map = Map25.load("/demo/map/floor.map")

  JoglFrame(new GlEventListener {
    val (charObj, joint) = Node.load(this, Map("pSphere5" -> Mod.load("/demo/char/base.mod").scale(0.1f),
                                               "polySurface115" -> Mod.load("/demo/char/hair.mod").scale(0.1f)),
                                  "/demo/char/joints.mod", 0.1f)

    val (standOff, stand) = KeyFrameLoader.load(joint, "/demo/char/stand.mod", 0.1f)

    val (run1Off, run1) = KeyFrameLoader.load(joint, "/demo/char/run1.mod", 0.1f)
    val (run2Off, run2) = KeyFrameLoader.load(joint, "/demo/char/run2.mod", 0.1f)
    val (run3Off, run3) = KeyFrameLoader.load(joint, "/demo/char/run3.mod", 0.1f)
    val (run4Off, run4) = KeyFrameLoader.load(joint, "/demo/char/run4.mod", 0.1f)

    val char = new TextureGroup(this, "/demo/char/texture.png", charObj:_* )

    val mapa = new TextureGroup(this, "/demo/t.png",
      new StaticObject(this, Mod.load("/demo/map/floor.mod"))
    )

    override def initGL4(implicit gl: GlType){
      gl.glEnable(Gl.DEPTH_TEST)
      gl.glDepthMask(true)

      val vertexSchader = createShader(Gl.VERTEX_SHADER, vertex)
      val p = createProgram(vertexSchader, createShader(Gl.FRAGMENT_SHADER, fragment))

      mapa.init
      char.init

      gl.glUseProgram(p)
      Camera.program(p)
      Camera.lookAt(new TrackingObject {
        override val position = Vector3(0, -1f, 0)
      })

      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

      //gl.glPolygonMode(Gl.FRONT_AND_BACK, Gl.LINE)

      stand.apply()
    }

    val zero1 = Matrix4()
    val zero2 = Matrix4()

    var t = System.nanoTime()

    override def displayGL4(implicit gl: GlType){
      gl.glClear(Gl.COLOR_BUFFER_BIT | Gl.DEPTH_BUFFER_BIT)
      Camera.display

      val q = System.nanoTime()

      var x:Float = (q - t)/1000000f
      x = x / 2500f

      if(x > 1) {
        x -= 1; t = q
        run1.apply()
      }

      joint.apply(zero1, zero2, x)

      mapa.display
      char.display
    }

  })

}
