package org.callie.model

import javax.media.opengl.{GL4, GL3, GL2GL3, GL2ES2, GL, GLBase}
import javax.media.opengl.glu.gl2.GLUgl2
import org.callie.math.{Vector2, Vector3}
import java.nio.IntBuffer
import java.nio.FloatBuffer

trait GlObject{

  def init(gl:GL4){}//, glu: GLUgl2){}
  
  def display(gl:GL4){}//, glu: GLUgl2){}
  
  def dispose(gl:GL4){}//, glu: GLUgl2){}
  
}

class StaticGlObject(vertices: Array[Float], 
					 normals : Array[Float], 
					 uvCoords: Array[Array[Float]], 
					 verticesIndexes : Array[Int],
					 normalsIndexes : Array[Int],
					 uvCoordsIndexes: Array[Array[Int]]) extends GlObject{
  
  override def init(gl:GL4){//, glu: GLUgl2){
    gl.glBeginTransformFeedback(GL.GL_TRIANGLES)
        
    val vboBuff = IntBuffer.allocate(1)
    gl.glGenBuffers(1, vboBuff)
    //vboBuff.rewind
    val vbo = vboBuff.get
    
    gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo)
    
    val verticesBuff = FloatBuffer.allocate(vertices.length)
    verticesBuff.put(vertices)
    verticesBuff.rewind()
    gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.length, verticesBuff, GL.GL_STATIC_DRAW)
    
//	for (i = 0; i < verticesIndexes.length; i++){
//      if(uvCoordsIndexes.length == 1){
//        //TexCoord2f uv = inUvCoords[0][ uvCoordsIndexes[0][i] ] ;
//        val uv = uvCoords(0)( uvCoordsIndexes(0)(i) )
//        //gl.glTexCoord2f(uv.x, uv.y);
//        gl.gltexco
//            }else if(uvCoordsIndexes.length > 1){
//                for(int j = 0; j < uvCoordsIndexes.length; j++){
//                    TexCoord2f uv = inUvCoords[j][ uvCoordsIndexes[j][i] ] ;
//                    gl.glMultiTexCoord2f(GL.GL_TEXTURE0 + j, uv.x, uv.y);
//                }
//            }
//
//            Vector3f n = inNormals[ normalsIndexes[i] ];
//            gl.glNormal3f(n.x, n.y, n.z);
//
//            Point3f v = inVertices[ verticesIndexes[i] ];
//            gl.glVertex3f(v.x, v.y, v.z);
//        }
    
    gl.glEndTransformFeedback()
  }
  
}
