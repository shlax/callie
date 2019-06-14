package org.callie.ogl;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL13C;
import org.lwjgl.opengl.GL15C;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.GL30C;
        
public final class Gl {
  public static final int SIZEOF_INT = 4;
  public static final int SIZEOF_FLOAT = 4;

  public static void glDeleteBuffers(int buffer){
    GL15C.glDeleteBuffers(buffer);
  }

  public static void glDeleteVertexArrays(int buffer){
    GL30C.glDeleteVertexArrays(buffer);
  }

  public static void glDeleteTextures(int buffer){
    GL11C.glDeleteTextures(buffer);
  }

  public static void glDeleteFramebuffers(int buffer){
    GL30C.glDeleteFramebuffers(buffer);
  }

  public static void glDeleteRenderbuffers(int buffer){
    GL30C.glDeleteRenderbuffers(buffer);
  }

  public static void glDeleteProgram(int buffer){
    GL20C.glDeleteProgram(buffer);
  }

  public static final int glGenTextures(){
    return GL11C.glGenTextures();
  }

  public static final int GL_TEXTURE_2D = GL11C.GL_TEXTURE_2D;

  public static final int GL_TEXTURE_WRAP_S = GL11C.GL_TEXTURE_WRAP_S;

  public static final int GL_REPEAT = GL11C.GL_REPEAT;

  public static final int GL_TEXTURE_WRAP_T = GL11C.GL_TEXTURE_WRAP_T;

  public static final int GL_TEXTURE_MIN_FILTER = GL11C.GL_TEXTURE_MIN_FILTER;

  public static final int GL_LINEAR = GL11C.GL_LINEAR;

  public static final int GL_LINEAR_MIPMAP_LINEAR = GL11C.GL_LINEAR_MIPMAP_LINEAR;

  public static void glBindTexture(int target, int texture){
    GL11C.glBindTexture(target, texture);
  }

  public static final int glGenVertexArrays(){
    return GL30C.glGenVertexArrays();
  }

  public static void glBindVertexArray(int array){
    GL30C.glBindVertexArray(array);
  }

  public static final int glGenBuffers(){
    return GL15C.glGenBuffers();
  }

  public static void glBindBuffer(int target, int buffer){
    GL15C.glBindBuffer(target, buffer);
  }

  public static final int glGenFramebuffers(){
    return GL30C.glGenFramebuffers();
  }

  public static void glBindFramebuffer(int target, int framebuffer){
    GL30C.glBindFramebuffer(target, framebuffer);
  }

  public static final int GL_FRAMEBUFFER = GL30C.GL_FRAMEBUFFER;

  public static final int GL_FRAMEBUFFER_COMPLETE = GL30C.GL_FRAMEBUFFER_COMPLETE;

  public static final int glCheckFramebufferStatus(int target){
    return GL30C.glCheckFramebufferStatus(target);
  }

  public static final int glGenRenderbuffers(){
    return GL30C.glGenRenderbuffers();
  }

  public static final int GL_RENDERBUFFER = GL30C.GL_RENDERBUFFER;

  public static void glBindRenderbuffer(int target, int renderbuffer){
    GL30C.glBindRenderbuffer(target, renderbuffer);
  }

  public static final int glCreateShader(int tpe){
    return GL20C.glCreateShader(tpe);
  }

  public static void glShaderSource(int shader, String source){
    GL20C.glShaderSource(shader, source);
  }

  public static void glCompileShader(int shader){
    GL20C.glCompileShader(shader);
  }

  public static void glGetShaderiv(int shader, int pname, int[] params){
    GL20C.glGetShaderiv(shader, pname, params);
  }

  public static String glGetShaderInfoLog(int shader){
    return GL20C.glGetShaderInfoLog(shader);
  }

  public static final int GL_COMPILE_STATUS = GL20C.GL_COMPILE_STATUS;

  public static final int GL_FALSE = GL11C.GL_FALSE;

  public static final int glCreateProgram(){
    return GL20C.glCreateProgram();
  }

  public static void glAttachShader(int program, int shader){
    GL20C.glAttachShader(program, shader);
  }

  public static void glLinkProgram(int program){
    GL20C.glLinkProgram(program);
  }

  public static void glGetProgramiv(int program, int pname, int[] params){
    GL20C.glGetProgramiv(program, pname, params);
  }

  public static final int GL_LINK_STATUS = GL20C.GL_LINK_STATUS;

  public static final int GL_ARRAY_BUFFER = GL15C.GL_ARRAY_BUFFER;

  public static final int GL_STATIC_DRAW = GL15C.GL_STATIC_DRAW;

  public static final int GL_DYNAMIC_DRAW = GL15C.GL_DYNAMIC_DRAW;

  public static String glGetProgramInfoLog(int program){
    return GL20C.glGetProgramInfoLog(program);
  }

  public static void glDetachShader(int program, int shader){
    GL20C.glDetachShader(program, shader);
  }

  public static final int GL_VERTEX_SHADER = GL20C.GL_VERTEX_SHADER;

  public static final int GL_FRAGMENT_SHADER = GL20C.GL_FRAGMENT_SHADER;

  public static final int GL_TEXTURE0 = GL13C.GL_TEXTURE0;

  public static void glEnableVertexAttribArray(int index){
    GL20C.glEnableVertexAttribArray(index);
  }

  public static void glVertexAttribPointer(int index, int size, int tpe, boolean normalized, int stride, long buffer){
    GL20C.glVertexAttribPointer(index, size, tpe, normalized, stride, buffer);
  }

  public static void glBufferData(int target, int[] data, int usage){
    GL15C.glBufferData(target, data, usage);
  }

  public static void glBufferData(int target, float[] data, int usage){
    GL15C.glBufferData(target, data, usage);
  }

  public static void glDrawElements(int mode, int count, int tpe, long indices){
    GL11C.glDrawElements(mode, count, tpe, indices);
  }

  public static final int GL_FLOAT = GL11C.GL_FLOAT;

  public static final int GL_TRIANGLES = GL11C.GL_TRIANGLES;

  public static final int GL_UNSIGNED_INT = GL11C.GL_UNSIGNED_INT;

  public static final int GL_ELEMENT_ARRAY_BUFFER = GL15C.GL_ELEMENT_ARRAY_BUFFER;

  public static void glActiveTexture(int texture){
    GL13C.glActiveTexture(texture);
  }

  public static void glGenerateMipmap(int target){
    GL30C.glGenerateMipmap(target);
  }

  public static void glPixelStorei(int pname, int param){
    GL11C.glPixelStorei(pname, param);
  }

  public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int tpe, ByteBuffer pixels){
    GL11C.glTexImage2D(target, level, internalformat, width, height, border, format, tpe, pixels);
  }

  public static final int GL_UNPACK_ALIGNMENT = GL11C.GL_UNPACK_ALIGNMENT;

  public static final int GL_RGBA = GL11C.GL_RGBA;

  public static final int GL_RGBA8 = GL11C.GL_RGBA8;

  public static final int GL_RGB8 = GL11C.GL_RGB8;

  public static final int GL_RGB = GL11C.GL_RGB;

  public static final int GL_UNSIGNED_BYTE = GL11C.GL_UNSIGNED_BYTE;

  public static void glTexParameteri(int target, int pname, int param){
    GL11C.glTexParameteri(target, pname, param);
  }

  public static void glUniform3fv(int location, float[] value){
    GL20C.glUniform3fv(location, value);
  }

  public static void glUniformMatrix4fv(int location, boolean transpose, float[] value){
    GL20C.glUniformMatrix4fv(location, transpose, value);
  }

  public static final int glGetUniformLocation(int program, String name){
    return GL20C.glGetUniformLocation(program, name);
  }

  public static final int GL_DEPTH_TEST = GL11C.GL_DEPTH_TEST;

  public static final int GL_DEPTH_BUFFER_BIT = GL11C.GL_DEPTH_BUFFER_BIT;

  public static final int GL_COLOR_BUFFER_BIT = GL11C.GL_COLOR_BUFFER_BIT;

  public static final int GL_FRONT_AND_BACK = GL11C.GL_FRONT_AND_BACK;

  public static final int GL_LINE = GL11C.GL_LINE;

  public static void glClear(int mask){
    GL11C.glClear(mask);
  }

  public static void glUseProgram(int program){
    GL20C.glUseProgram(program);
  }

  public static void glEnable(int target){
    GL11C.glEnable(target);
  }

  public static void glPolygonMode(int face, int mode){
    GL11C.glPolygonMode(face, mode);
  }

  public static void glClearColor(float red, float green, float blue, float alpha){
    GL11C.glClearColor(red, green, blue, alpha);
  }

}
