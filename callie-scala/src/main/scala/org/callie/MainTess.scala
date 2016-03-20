package org.callie

import org.callie.jogl.{GL4EventListener, GL_4, JoglFrame, buffers}
import com.jogamp.opengl.GL4
import java.io.InputStreamReader

import scala.collection.mutable.ListBuffer
import com.jogamp.common.nio.Buffers
import buffers._
import org.callie.input.Camera
import org.callie.model.Mod

object MainTess extends App{

  // coords : position(x, y, z) ~ texture(u, v) ~ normal(x, y, z)
  val (coords, indices) = {
    val m = Mod(new InputStreamReader(getClass.getResourceAsStream("/tst.mod"), "US-ASCII"))

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
      |layout(location = 0) in vec3 inPosition;
      |layout(location = 1) in vec2 inTexCoord;
      |layout(location = 2) in vec3 inNormal;
      |
      |uniform mat4 viewMatrix;
      |uniform mat4 normalMatrix;
      |
      |out vec3 position;
      |out vec2 texCoord;
      |out vec3 normal;
      |
      |void main(){
      |  vec4 tmp = viewMatrix * vec4(inPosition, 1);
      |  position = vec3(tmp.x / tmp.w, tmp.y / tmp.w, tmp.z / tmp.w);
      |  texCoord = inTexCoord;
      |  normal = (normalMatrix * vec4(inNormal, 1)).xyz;
      |}
    """.stripMargin.trim

  // http://ogldev.atspace.co.uk/www/tutorial31/tutorial31.html

  val  bezierSurface =
    """
      |struct BezierSurface{
      |  vec3 b030;
      |  vec3 b021;
      |  vec3 b012;
      |  vec3 b003;
      |  vec3 b102;
      |  vec3 b201;
      |  vec3 b300;
      |  vec3 b210;
      |  vec3 b120;
      |  vec3 b111;
      |
      |  vec3 normal[3];
      |  vec2 texCoord[3];
      |};
    """.stripMargin.trim

  val tessControl =
    s"""
      |#version 430 core
      |
      |layout (vertices = 3) out;
      |
      |in vec3 position[];
      |in vec2 texCoord[];
      |in vec3 normal[];
      |
      |$bezierSurface
      |out patch BezierSurface plane;
      |
      |vec3 projectToPlane(vec3 point, vec3 planePoint, vec3 planeNormal) {
      |  vec3 v = point - planePoint;
      |  float len = dot(v, planeNormal);
      |  vec3 d = len * planeNormal;
      |  return (point - d);
      |}
      |
      |void main(void){
      |  if (gl_InvocationID == 0){
      |    gl_TessLevelInner[0] = 4.0;
      |    gl_TessLevelOuter[0] = 4.0;
      |    gl_TessLevelOuter[1] = 4.0;
      |    gl_TessLevelOuter[2] = 4.0;
      |  }
      |
      |  for (int i = 0 ; i < 3 ; i++) {
      |    plane.normal[i] = normal[i];
      |    plane.texCoord[i] = texCoord[i];
      |  }
      |
      |  // calc >
      |
      |  // The original vertices stay the same
      |  plane.b030 = position[0];
      |  plane.b003 = position[1];
      |  plane.b300 = position[2];
      |
      |  // Edges are names according to the opposing vertex
      |  vec3 edgeB300 = plane.b003 - plane.b030;
      |  vec3 edgeB030 = plane.b300 - plane.b003;
      |  vec3 edgeB003 = plane.b030 - plane.b300;
      |
      |  // Generate two midpoints on each edge
      |  plane.b021 = plane.b030 + edgeB300 / 3.0;
      |  plane.b012 = plane.b030 + edgeB300 * 2.0 / 3.0;
      |  plane.b102 = plane.b003 + edgeB030 / 3.0;
      |  plane.b201 = plane.b003 + edgeB030 * 2.0 / 3.0;
      |  plane.b210 = plane.b300 + edgeB003 / 3.0;
      |  plane.b120 = plane.b300 + edgeB003 * 2.0 / 3.0;
      |
      |  // Project each midpoint on the plane defined by the nearest vertex and its normal
      |  plane.b021 = projectToPlane(plane.b021, plane.b030, normal[0]);
      |  plane.b012 = projectToPlane(plane.b012, plane.b003, normal[1]);
      |  plane.b102 = projectToPlane(plane.b102, plane.b003, normal[1]);
      |  plane.b201 = projectToPlane(plane.b201, plane.b300, normal[2]);
      |  plane.b210 = projectToPlane(plane.b210, plane.b300, normal[2]);
      |  plane.b120 = projectToPlane(plane.b120, plane.b030, normal[0]);
      |
      |  // Handle the center
      |  vec3 center = (plane.b003 + plane.b030 + plane.b300) / 3.0;
      |  plane.b111 = (plane.b021 + plane.b012 + plane.b102 + plane.b201 + plane.b210 + plane.b120) / 6.0;
      |  plane.b111 += (plane.b111 - center) / 2.0;
      |
      |  // < calc
      |
      |  gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
      |}
    """.stripMargin.trim

  val tessEval =
    s"""
      |#version 430 core
      |
      |layout(triangles, equal_spacing, ccw) in;
      |
      |$bezierSurface
      |in patch BezierSurface plane;
      |
      |out vec2 texCoord;
      |out vec3 normal;
      |
      |vec2 interpolate2D(vec2 v0, vec2 v1, vec2 v2){
      |  return vec2(gl_TessCoord.x) * v0 + vec2(gl_TessCoord.y) * v1 + vec2(gl_TessCoord.z) * v2;
      |}
      |
      |vec3 interpolate3D(vec3 v0, vec3 v1, vec3 v2){
      |  return vec3(gl_TessCoord.x) * v0 + vec3(gl_TessCoord.y) * v1 + vec3(gl_TessCoord.z) * v2;
      |}
      |
      |void main(void){
      |  texCoord = interpolate2D(plane.texCoord[0], plane.texCoord[1], plane.texCoord[2]);
      |  normal = interpolate3D(plane.normal[0], plane.normal[1], plane.normal[2]);
      |
      |  float u = gl_TessCoord.x;
      |  float v = gl_TessCoord.y;
      |  float w = gl_TessCoord.z;
      |
      |  float uPow3 = pow(u, 3);
      |  float vPow3 = pow(v, 3);
      |  float wPow3 = pow(w, 3);
      |
      |  float uPow2 = pow(u, 2);
      |  float vPow2 = pow(v, 2);
      |  float wPow2 = pow(w, 2);
      |
      |  vec3 pos = plane.b300 * wPow3 + plane.b030 * uPow3 + plane.b003 * vPow3 +
      |             plane.b210 * 3.0 * wPow2 * u + plane.b120 * 3.0 * w * uPow2 + plane.b201 * 3.0 * wPow2 * v +
      |             plane.b021 * 3.0 * uPow2 * v + plane.b102 * 3.0 * w * vPow2 + plane.b012 * 3.0 * vPow2 * u +
      |             plane.b111 * 6.0 * w * u * v;
      |
      |  gl_Position = vec4(pos, 1.0);
      |}
    """.stripMargin.trim

  val fragment =
    """
      |#version 430
      |
      |void main(){
      |  gl_FragColor  = vec4(1,1,1,1);
      |}
    """.stripMargin.trim

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

          gl.glVertexAttribPointer(0, 3, GL_4.FLOAT, false, (3+2+3)*Buffers.SIZEOF_FLOAT,     0*Buffers.SIZEOF_FLOAT)
          gl.glVertexAttribPointer(1, 2, GL_4.FLOAT, false, (3+2+3)*Buffers.SIZEOF_FLOAT,     3*Buffers.SIZEOF_FLOAT)
          gl.glVertexAttribPointer(2, 3, GL_4.FLOAT, false, (3+2+3)*Buffers.SIZEOF_FLOAT, (3+2)*Buffers.SIZEOF_FLOAT)
        }

      }

      // + glDrawElements
      vbi = createBuffer(GL_4.ELEMENT_ARRAY_BUFFER){
        indices.asBuffer(gl.glBufferData(GL_4.ELEMENT_ARRAY_BUFFER, _, _, GL_4.STATIC_DRAW))
      }
      // < code

      gl.glUseProgram(p)
      Camera.program(p)

      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    }

    override def displayGL4(implicit gl: GL4) {
      gl.glClear(GL_4.COLOR_BUFFER_BIT | GL_4.DEPTH_BUFFER_BIT)
      // > code
      //gl.glDrawArrays(GL_4.TRIANGLES, 0, 3)
      Camera.display

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
