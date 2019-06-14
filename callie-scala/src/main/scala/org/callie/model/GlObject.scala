package org.callie.model

import scala.collection.mutable.ListBuffer
import de.matthiasmann.twl.utils.PNGDecoder
import org.callie.ogl.{Gl, GlEventListener}
import org.callie.math.Vector3

trait GlObject{

  def init():Unit
  
  def update():Unit
}

class ObjectGroup(objs:GlObject*) extends GlObject{

  override def init():Unit={
    for(o <- objs) o.init()
  }

  override def update():Unit={
    for(o <- objs) o.update()
  }

}

class TextureGroup(ev: GlEventListener, image:String, ind:Int, objs:GlObject*) extends ObjectGroup(objs:_*){

  var texId : Int = _

  override def init():Unit={
    import org.callie._
    val (buff, w, h, f, fi) = getClass.getResourceAsStream(image)|{ in =>
      import java.nio.ByteBuffer

      val dec = new PNGDecoder(in)

      val n = if(dec.hasAlpha) PNGDecoder.Format.RGBA else PNGDecoder.Format.RGB
      val nc = n.getNumComponents
      val w = dec.getWidth
      val h = dec.getHeight

      val buf = ByteBuffer.allocateDirect(nc * w * h)
      dec.decodeFlipped(buf, w * nc, n)
      buf.flip()

      (buf, w, h, n match {
        case PNGDecoder.Format.RGBA =>  Gl.GL_RGBA
        case PNGDecoder.Format.RGB => Gl.GL_RGB
        case f => throw new RuntimeException("match "+f)
      }, n match {
        case PNGDecoder.Format.RGBA =>  Gl.GL_RGBA8
        case PNGDecoder.Format.RGB => Gl.GL_RGB8
        case f => throw new RuntimeException("match "+f)
      })
    }

    texId = ev.createTexture{
      //Gl.glPixelStorei(Gl.GL_UNPACK_ALIGNMENT, 1)
      Gl.glTexImage2D(Gl.GL_TEXTURE_2D, 0, fi, w, h, 0, f, Gl.GL_UNSIGNED_BYTE, buff)

      Gl.glGenerateMipmap(Gl.GL_TEXTURE_2D)

      Gl.glTexParameteri(Gl.GL_TEXTURE_2D, Gl.GL_TEXTURE_WRAP_S, Gl.GL_REPEAT)
      Gl.glTexParameteri(Gl.GL_TEXTURE_2D, Gl.GL_TEXTURE_WRAP_T, Gl.GL_REPEAT)
      Gl.glTexParameteri(Gl.GL_TEXTURE_2D, Gl.GL_TEXTURE_MIN_FILTER, Gl.GL_LINEAR)
      Gl.glTexParameteri(Gl.GL_TEXTURE_2D, Gl.GL_TEXTURE_MIN_FILTER, Gl.GL_LINEAR_MIPMAP_LINEAR)

      super.init()
    }
  }

  override def update():Unit={
    Gl.glActiveTexture(ind)
    ev.bindTexture(texId){
      super.update()
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

  override def init():Unit={
    vao = ev.createVertexArray{
      Gl.glEnableVertexAttribArray(0)
      Gl.glEnableVertexAttribArray(1)
      for(i <- 0 until uvCount) Gl.glEnableVertexAttribArray(2 + i)

      ev.createBuffer(Gl.GL_ARRAY_BUFFER){
        Gl.glBufferData(Gl.GL_ARRAY_BUFFER, coords, Gl.GL_STATIC_DRAW)

        val stride = (6 + uvCount * 2)*Gl.SIZEOF_FLOAT

        Gl.glVertexAttribPointer(0, 3, Gl.GL_FLOAT, false, stride, 0*Gl.SIZEOF_FLOAT)
        Gl.glVertexAttribPointer(1, 3, Gl.GL_FLOAT, false, stride, 3*Gl.SIZEOF_FLOAT)
        for(i <- 0 until uvCount) {
          Gl.glVertexAttribPointer(2 + i, 2, Gl.GL_FLOAT, false, stride, (6 + i * 2) * Gl.SIZEOF_FLOAT)
        }
      }
    }

    vbi = ev.createBuffer(Gl.GL_ELEMENT_ARRAY_BUFFER){
      Gl.glBufferData(Gl.GL_ELEMENT_ARRAY_BUFFER, indices, Gl.GL_STATIC_DRAW)
    }
  }

  override def update():Unit={
    ev.bindVertexArray(vao){
      ev.bindBuffer(Gl.GL_ELEMENT_ARRAY_BUFFER, vbi){
        Gl.glDrawElements(Gl.GL_TRIANGLES, indices.length, Gl.GL_UNSIGNED_INT, 0)
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

  override def init():Unit={
    vao = ev.createVertexArray{
      Gl.glEnableVertexAttribArray(0)
      Gl.glEnableVertexAttribArray(1)
      Gl.glEnableVertexAttribArray(2)

      vbo = ev.createBuffer(Gl.GL_ARRAY_BUFFER){
        Gl.glBufferData(Gl.GL_ARRAY_BUFFER, coords, Gl.GL_DYNAMIC_DRAW)

        val stride = (6 + uvCount * 2)*Gl.SIZEOF_FLOAT

        Gl.glVertexAttribPointer(0, 3, Gl.GL_FLOAT, false, stride, 0*Gl.SIZEOF_FLOAT)
        Gl.glVertexAttribPointer(1, 3, Gl.GL_FLOAT, false, stride, 3*Gl.SIZEOF_FLOAT)
        for(i <- 0 until uvCount) {
          Gl.glVertexAttribPointer(2 + i, 2, Gl.GL_FLOAT, false, stride, (6 + i * 2) * Gl.SIZEOF_FLOAT)
        }
      }
    }

    vbi = ev.createBuffer(Gl.GL_ELEMENT_ARRAY_BUFFER){
      Gl.glBufferData(Gl.GL_ELEMENT_ARRAY_BUFFER, indices, Gl.GL_STATIC_DRAW)
    }
  }

  override def update():Unit={
    ev.bindVertexArray(vao){
      ev.bindBuffer(Gl.GL_ARRAY_BUFFER, vbo){
        Gl.glBufferData(Gl.GL_ARRAY_BUFFER, coords, Gl.GL_DYNAMIC_DRAW)
      }
      
      ev.bindBuffer(Gl.GL_ELEMENT_ARRAY_BUFFER, vbi){
        Gl.glDrawElements(Gl.GL_TRIANGLES, indices.length, Gl.GL_UNSIGNED_INT, 0)
      }     
    }
  }
}


