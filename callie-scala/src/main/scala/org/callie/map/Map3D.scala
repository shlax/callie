package org.callie.map

import org.callie.math.{Ray, Triangle, TriangleIntersect}

class Triangle3D(val t : Triangle, override val near: Array[Triangle3D], override val far: Array[Triangle3D]) extends AbstractTriangle[Triangle3D]

class Map3D(triangles : Array[Triangle3D], last:Triangle3D) extends AbstractMap[Triangle3D, Ray](triangles, last){
  val intersect = new TriangleIntersect()

  override def test(t: Triangle3D, r: Ray): Float = intersect.intersect(r, t.t)
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
      val t = Triangle(pts(ind.a), pts(ind.b), pts(ind.c))
      new Triangle3D(t, new Array[Triangle3D](i._1.near.size), new Array[Triangle3D](i._1.far.size))
    }

    for(i <- inds){ // connect map
      val t = trgs(i._2)
      for(j <- i._1.near.zipWithIndex) t.near(j._2) = trgs(j._1)
      for(j <- i._1.far.zipWithIndex) t.far(j._2) = trgs(j._1)
    }

    new Map3D(trgs, trgs.head)
  }

}
