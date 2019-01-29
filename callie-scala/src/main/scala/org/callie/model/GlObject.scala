package org.callie.model

import scala.collection.mutable.ListBuffer
import com.jogamp.common.nio.Buffers
import com.jogamp.opengl.util.texture.TextureIO
import org.callie.jogl.{Gl, GlEventListener, GlType, buffers}
import org.callie.math.Vector3
import buffers._

object Graphics{
  var parts:Array[GlObject] = Array()

  def init(gl:GlType){
    for(o <- parts)o.init(gl)
  }

  def display(gl:GlType){
    for(o <- parts)o.display(gl)
  }

}

trait GlObject{

  def init(gl:GlType)
  
  def display(gl:GlType)
    
}

class ObjectGroup(objs:GlObject*) extends GlObject{
  
  override def init(gl:GlType){
    for(o <- objs) o.init(gl)
  }
  
  override def display(gl:GlType){
    for(o <- objs) o.display(gl)
  }
  
}

class TextureGroup(ev: GlEventListener, image:String, ind:Int, objs:GlObject*) extends ObjectGroup(objs:_*){
  
  var texId : Int = _
    
  override def init(gl:GlType){
    val texture = TextureIO.newTextureData(gl.getGLProfile, getClass.getResourceAsStream(image), false, TextureIO.PNG)
    
    texId = ev.createTexture(gl){
      gl.glPixelStorei(Gl.TEXTURE_2D, texture.getAlignment)
      gl.glTexImage2D(Gl.TEXTURE_2D, 0, texture.getInternalFormat, texture.getWidth, texture.getHeight, texture.getBorder, texture.getPixelFormat, texture.getPixelType, texture.getBuffer)

      gl.glGenerateMipmap(Gl.TEXTURE_2D)

      gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_WRAP_S, Gl.REPEAT)
      gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_WRAP_T, Gl.REPEAT)
      gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_MAG_FILTER, Gl.LINEAR)
      gl.glTexParameteri(Gl.TEXTURE_2D, Gl.TEXTURE_MIN_FILTER, Gl.LINEAR_MIPMAP_LINEAR)
    
      super.init(gl)
    }
    
    texture.destroy()    
  }
  
  override def display(gl:GlType){
    gl.glActiveTexture(ind)
    ev.bindTexture(gl, texId){
      super.display(gl)
    }
  }
  
}

class StaticObject(ev:GlEventListener) extends GlObject{

  case class PointFace(point:Point3, normal:Point3, uv:List[Point2])

  var coords:Array[Float] = _
  var indices:Array[Int] = _

  var uvCount:Int = _

  def this(ev:GlEventListener, m:Mod){
    this(ev)

    uvCount = m.faces.head.uv.size

    // stack
    val s = ListBuffer[PointFace]()

    val a = ListBuffer[Float]()
    val i = ListBuffer[Int]()

    for(f <- m.faces){
      val x = PointFace(m.points(f.point), f.normal, f.uv)
      val ind = s.indexOf(x)
      if(ind == -1){
        a += x.point.x += x.point.y += x.point.z
        a += x.normal.x += x.normal.y += x.normal.z
        for(j <- x.uv) a += j.x += j.y

        i += s.size
        s += x
      }else i += ind
    }

    coords = a.toArray
    indices = i.toArray
  }
    
  var vao : Int = _
  var vbi : Int = _
  
  override def init(gl:GlType){
    vao = ev.createVertexArray(gl){
      gl.glEnableVertexAttribArray(0)
      gl.glEnableVertexAttribArray(1)
      gl.glEnableVertexAttribArray(2)
      
      ev.createBuffer(gl, Gl.ARRAY_BUFFER){
        coords.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))

        val stride = (6 + uvCount * 2)*Buffers.SIZEOF_FLOAT

        gl.glVertexAttribPointer(0, 3, Gl.FLOAT, false, stride, 0*Buffers.SIZEOF_FLOAT)
        gl.glVertexAttribPointer(1, 2, Gl.FLOAT, false, stride, 3*Buffers.SIZEOF_FLOAT)
        for(i <- 0 until uvCount) {
          gl.glVertexAttribPointer(2 + i, 2, Gl.FLOAT, false, stride, (6 + i * 2) * Buffers.SIZEOF_FLOAT)
        }
      }
    }
    
    vbi = ev.createBuffer(gl, Gl.ELEMENT_ARRAY_BUFFER){
      indices.asBuffer(gl.glBufferData(Gl.ELEMENT_ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))
    }
  }
  
  override def display(gl:GlType){
    ev.bindVertexArray(gl, vao){
      ev.bindBuffer(gl, Gl.ELEMENT_ARRAY_BUFFER, vbi){
        gl.glDrawElements(Gl.TRIANGLES, indices.length, Gl.UNSIGNED_INT, 0)
      }
    }
  }
  
}

class MorfingObject(ev:GlEventListener) extends GlObject{

  case class PointFace(point:Int, normal:Point3, uv:List[Point2])

  var uvCount:Int = _

  var coords:Array[Float] = _
  var indices:Array[Int] = _
  var projPoint:Array[(Vector3, Vector3)] = _
  var projNormals:Array[Array[(Vector3, Vector3)]] = _

  def this(ev:GlEventListener, m:Mod){
    this(ev)

    uvCount = m.faces.head.uv.size

    // stack
    val s = ListBuffer[PointFace]()

    val a = ListBuffer[Float]()
    val i = ListBuffer[Int]()

    for(f <- m.faces){
      val x = PointFace(f.point, f.normal, f.uv)
      val ind = s.indexOf(x)

      if(ind == -1){
        val p = m.points(f.point)

        a += p.x += p.y += p.z
        a += x.normal.x += x.normal.y += x.normal.z
        for(j <- x.uv) a += j.x += j.y

        i += s.size
        s += x
      }else i += ind
    }

    val prPoint = ListBuffer[(Vector3, Vector3)]()
    val prNormals = ListBuffer[Array[(Vector3, Vector3)]]()

    coords = a.toArray

    val fi = s.zipWithIndex

    val size = 6 + uvCount * 2

    for(i <- m.points.zipWithIndex){
      val t = fi.filter(_._1.point == i._2)

      prPoint += ( (Vector3(i._1.x, i._1.y, i._1.z), Vector3(coords, t.map(_._2 * size).toArray )) )
      prNormals += t.map{j =>
          ( j._1.normal, j._2 * size + 3)
        }.groupBy(_._1).map{ e =>
          (Vector3(e._1.x, e._1.y, e._1.z), Vector3(coords,  e._2.map(_._2).toArray))
        }.toArray
    }

    indices = i.toArray
    projPoint = prPoint.toArray
    projNormals = prNormals.toArray
  }
  
  var vao : Int = _
  var vbi : Int = _
  var vbo : Int = _
  
  override def init(gl:GlType){
    vao = ev.createVertexArray(gl){
      gl.glEnableVertexAttribArray(0)
      gl.glEnableVertexAttribArray(1)
      gl.glEnableVertexAttribArray(2)
      
      vbo = ev.createBuffer(gl, Gl.ARRAY_BUFFER){
        coords.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.DYNAMIC_DRAW))

        val stride = (6 + uvCount * 2)*Buffers.SIZEOF_FLOAT

        gl.glVertexAttribPointer(0, 3, Gl.FLOAT, false, stride, 0*Buffers.SIZEOF_FLOAT)
        gl.glVertexAttribPointer(1, 3, Gl.FLOAT, false, stride, 3*Buffers.SIZEOF_FLOAT)
        for(i <- 0 until uvCount) {
          gl.glVertexAttribPointer(2 + i, 2, Gl.FLOAT, false, stride, (6 + i * 2) * Buffers.SIZEOF_FLOAT)
        }
      }
    }
    
    vbi = ev.createBuffer(gl, Gl.ELEMENT_ARRAY_BUFFER){
      indices.asBuffer(gl.glBufferData(Gl.ELEMENT_ARRAY_BUFFER, _, _, Gl.STATIC_DRAW))
    }
  }
  
  override def display(gl:GlType){
    ev.bindVertexArray(gl, vao){
      ev.bindBuffer(gl, Gl.ARRAY_BUFFER, vbo){
        coords.asBuffer(gl.glBufferData(Gl.ARRAY_BUFFER, _, _, Gl.DYNAMIC_DRAW))
      }
      
      ev.bindBuffer(gl, Gl.ELEMENT_ARRAY_BUFFER, vbi){
        gl.glDrawElements(Gl.TRIANGLES, indices.length, Gl.UNSIGNED_INT, 0)
      }     
    }
  }
}


