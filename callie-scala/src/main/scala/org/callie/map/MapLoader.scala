package org.callie.map

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.callie.gen.map.{MapLexer, MapParser}

import scala.collection.mutable

case class IndexTriangle(i:Int, j:Int, k:Int)

class TriangleBuilder(val a:Int, val b:Int, val c:Int){
  val near = new mutable.ListBuffer[Int]
  val far = new mutable.ListBuffer[Int]

  def vertexes = Seq(a, b, c)
}

object MapLoader{

  def apply(r:java.io.InputStream /* , predicate:MapBuilder = new MapBuilder*/) = {
    val par = new MapParser(new CommonTokenStream(new MapLexer(CharStreams.fromStream(r))))
    val pi = par.map().result

    val pts = pi.points
    //predicate.set(pts)

    val inds = pi.indexes.map(i => new TriangleBuilder(i.i, i.j, i.k)).zipWithIndex
    for (i <- inds; j <- inds if i._2 != j._2) {
      var k = 0
      for (a <- i._1.vertexes; b <- j._1.vertexes if a == b /*predicate.test(a, b)*/ ) k += 1
      if (k == 2) i._1.near += j._2
      else if (k == 1) i._1.far += j._2
    }

    (pts, inds)
  }

}