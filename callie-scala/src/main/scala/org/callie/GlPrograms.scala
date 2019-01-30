package org.callie

import org.callie.jogl.glslVersion

class GlPrograms(val vertex:String, val  fragment:String)

// https://stackoverflow.com/questions/18510701/glsl-how-to-show-normals-with-geometry-shader
object GlPrograms {

  val vertex = s"""#version $glslVersion
        |
        |layout(location = 0) in vec3 inPosition;
        |layout(location = 1) in vec3 inNormal;
        |layout(location = 2) in vec2 inTextureCoord;
        |/*lightMap*/ layout(location = 3) in vec2 inTextureCoord2;
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
        |out float lightIntensity;
        |out vec2 texCoord;
        |/*lightMap*/out vec2 texCoord2;
        |
        |void main(){
        |  gl_Position = viewMatrix * vec4(inPosition, 1); // (projection * viewMatrix) * vec4(inPosition, 1); //vec4(inPosition, 1);
        |
        |  //vec4 tmp = viewMatrix * vec4(inPosition, 1);
        |  //gl_Position = vec4(tmp.x / tmp.w, tmp.y / tmp.w, tmp.z / tmp.w, 1);
        |
        |  lightIntensity = 0.2 + (max(dot((normalMatrix * vec4(inNormal,1)).xyz, lightDirection), 0.0) * 0.8);
        |  texCoord = inTextureCoord;
        |  /*lightMap*/ texCoord2 = inTextureCoord2;
        |
        |} """.stripMargin.trim

  val fragment = s"""#version $glslVersion
        |
        |uniform sampler2D textureDiffuse;
        |/*lightMap*/ uniform sampler2D textureLight;
        |
        |in highp float lightIntensity;
        |in highp vec2 texCoord;
        |/*lightMap*/ in highp vec2 texCoord2;
        |
        |out highp vec4 fragColor;
        |
        |void main(){
        |  highp vec4 c = texture(textureDiffuse, texCoord);
        |  if (c.w < 0.5) discard;
        |
        |  /*lightMap*/ highp float li = lightIntensity;
        |  /*lightMap*/ highp vec4 l = texture(textureLight, texCoord2);
        |  /*lightMap*/ li = l.x + li - l.x*li;
        |
        |  /*lightMap*/ fragColor = c * li; // vec4(lightIntensity,lightIntensity, lightIntensity, 1);
        |  /*base*/ fragColor = c * lightIntensity;
        |} """.stripMargin.trim

  def apply(lightMap:Boolean) = {
    val l = if(lightMap) List("/*lightMap*/", "*base*") else List("*lightMap*", "/*base*/")
    new GlPrograms(l.fold(vertex)((a, b) => a.replace(b, "")), l.fold(fragment)((a, b) => a.replace(b, "")))
  }

}
