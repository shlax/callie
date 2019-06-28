package org.callie

import org.callie.ogl.glslVersion

// https://stackoverflow.com/questions/18510701/glsl-how-to-show-normals-with-geometry-shader
object GlPrograms {

  val vertexCode = s"""#version $glslVersion
        |
        |layout(location = 0) in vec3 inPosition;
        |layout(location = 1) in vec3 inNormal;
        |layout(location = 2) in vec2 inTextureCoord1;
        |/*lightMap*/layout(location = 3) in vec2 inTextureCoord2;
        |
        |uniform mat4 viewMatrix;
        |uniform mat4 normalMatrix;
        |uniform vec3 lightDirection;
        |
        |/* const mat4 projection = mat4( // 1 : 10 X 0.5
        | vec4(2, 0, 0, 0),
        | vec4(0, 2, 0, 0),
        | vec4(0, 0, -1.2222222, -2.2222222),
        | vec4(0, 0, -1, 0)
        |); */
        |
        |//const vec3 lightDirection = vec3(0, 0, 1);
        |
        |out float lightIntensity;
        |
        |out vec2 texCoord1;
        |/*lightMap*/out vec2 texCoord2;
        |
        |void main(){
        |  gl_Position = viewMatrix * vec4(inPosition, 1); // (projection * viewMatrix) * vec4(inPosition, 1); //vec4(inPosition, 1);
        |
        |  //vec4 tmp = viewMatrix * vec4(inPosition, 1);
        |  //gl_Position = vec4(tmp.x / tmp.w, tmp.y / tmp.w, tmp.z / tmp.w, 1);
        |
        |  //lightIntensity = 0.2 + (max(dot((normalMatrix * vec4(inNormal,0)), (normalMatrix * vec4(lightDirection,0))), 0.0) * 0.8);
        |
        |  vec3 nv = (normalMatrix * vec4(inNormal,1)).xyz;
        |  lightIntensity = 0.35 + (max(dot(nv, lightDirection), 0.0) * 0.65);
        |
        |  texCoord1 = inTextureCoord1;
        |  /*lightMap*/texCoord2 = inTextureCoord2;
        |
        |} """.stripMargin.trim

  val fragmentCode = s"""#version $glslVersion
        |
        |uniform sampler2D textureDiffuse;
        |
        |in highp float lightIntensity;
        |in highp vec2 texCoord1;
        |
        |out highp vec4 fragColor;
        |
        |void main(){
        |  highp vec4 c = texture(textureDiffuse, texCoord1);
        |  /*discard*/if (c.w < 0.5) discard;
        |
        |  //fragColor = vec4(lightIntensity, lightIntensity, lightIntensity, 1);
        |  fragColor = c * lightIntensity;
        |} """.stripMargin.trim

  val fragmentCodeTexture = s"""#version $glslVersion
        |
        |uniform sampler2D textureLight;
        |uniform sampler2D textureDiffuse;
        |
        |in highp float lightIntensity;
        |
        |in highp vec2 texCoord1;
        |in highp vec2 texCoord2;
        |
        |out highp vec4 fragColor;
        |
        |void main(){
        |  highp vec4 c = texture(textureLight, texCoord1);
        |  /*discard*/if (c.w < 0.5) discard;
        |
        |  highp float li = c.x;
        |  li = lightIntensity + li - lightIntensity * li;
        |
        |  highp vec4 d = texture(textureLight, texCoord2);
        |
        |  //fragColor = vec4(lightIntensity, lightIntensity, lightIntensity, 1);
        |  fragColor = d * lightIntensity;
        |} """.stripMargin.trim

  /* val fragmentCodeMul = s"""#version $glslVersion
        |
        |uniform sampler2D textureLight;
        |uniform sampler2D textureDiffuse;
        |
        |in highp float lightIntensity;
        |
        |in highp vec2 texCoord1;
        |
        |uniform highp float scale;
        |
        |out highp vec4 fragColor;
        |
        |void main(){
        |  highp vec4 c = texture(textureLight, texCoord1);
        |  /*discard*/if (c.w < 0.5) discard;
        |
        |  highp float li = c.x;
        |  li = lightIntensity + li - lightIntensity * li;
        |
        |  highp vec4 d = texture(textureLight, texCoord1 * scale);
        |
        |  fragColor = d * lightIntensity;
        |} """.stripMargin.trim */

  def vertex(lightMap:Boolean = false) = {
    val l = if (lightMap) List("/*lightMap*/") else List("*lightMap*")
    l.fold(vertexCode) { (a, b) =>
      a.replace(b, "")
    }
  }

  def fragment(lightMap:Boolean = false, discard:Boolean = false) = {
    val code = if(lightMap) fragmentCodeTexture else fragmentCode

    val l = if (discard) List("/*discard*/") else List("*discard*")

    l.fold(code) { (a, b) =>
      a.replace(b, "")
    }
  }


}
