package org.callie.map

import org.callie.math.{Ray, Vector3}

class Triangle3D(val a:Vector3, b:Vector3, c:Vector3, override val near: Array[Triangle3D], override val far: Array[Triangle3D]) extends AbstractTriangle[Triangle3D]{
  val e1 = Vector3.sub(b, a)
  val e2 = Vector3.sub(c, a)
}

class Map3D(triangles : Array[Triangle3D], last:Triangle3D) extends AbstractMap[Triangle3D, Ray](triangles, last){
  val vp = Vector3()
  val vq = Vector3()
  val vt = Vector3()

  override def test(t: Triangle3D, r: Ray): Float ={
    vp.cross(r.dir, t.e2)

    val det = t.e1.dot(vp)
    if(det == 0) Float.NaN
    else{
      val iDet = 1f/det

      vt.sub(r.point, t.a)

      val u = vt.dot(vp) * iDet
      if(u < 0 || u > 1) Float.NaN
      else{
        vq.cross(vt, t.e1)

        val v = r.dir.dot(vq) * iDet
        if(v < 0 || u + v > 1) Float.NaN
        else{
          val dis = t.e2.dot(vq) * iDet
          if(dis < 0) Float.NaN else dis
        }
      }
    }
  }
}

object Map3D{

  def load(nm:String/* , predicate:MapBuilder* */): Map3D = {
    import org.callie._
    getClass.getResourceAsStream(nm)|{ s =>
      apply(s /*, predicate*/)
    }
  }

  def apply(r:java.io.InputStream /* , predicate:MapBuilder = new MapBuilder*/): Map3D = {
    val (pts, inds) = MapLoader.apply(r)

    val trgs = inds.map{ i =>
      val ind = i._1
      new Triangle3D(pts(ind.a), pts(ind.b), pts(ind.c), new Array[Triangle3D](i._1.near.size), new Array[Triangle3D](i._1.far.size))
    }

    MapLoader.connect(inds, trgs)

    new Map3D(trgs, trgs.head)
  }

}
