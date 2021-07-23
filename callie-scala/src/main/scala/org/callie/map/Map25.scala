package org.callie.map

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.callie.gen.map.{MapLexer, MapParser}
import org.callie.math.{Vector2, Vector3}

import scala.collection.mutable
import java.lang.{Float => jFloat}

class Triangle25(val a : Vector3, val b : Vector3, val c : Vector3, val near: Array[Triangle25], val far: Array[Triangle25]) {

  val ab = Vector2(b.x-a.x, b.z-a.z)
  val bc = Vector2(c.x-b.x, c.z-b.z)
  val ca = Vector2(a.x-c.x, a.z-c.z)
  
  val (ta, tb, tc, td) = {
    val tmp = Vector3.cross( Vector3(c.x-a.x, c.y-a.y, c.z-a.z ), Vector3(b.x-a.x, b.y-a.y, b.z-a.z) )
	  (tmp.x, tmp.y, tmp.z, -((tmp.x*c.x)+(tmp.y*c.y)+(tmp.z*c.z)))
  }
  
  def apply(p : Vector2):Float = {
    val na = ((p.x-a.x)*ab.y)-(ab.x*(p.y-a.z))
	  val nb = ((p.x-b.x)*bc.y)-(bc.x*(p.y-b.z))
	  val nc = ((p.x-c.x)*ca.y)-(ca.x*(p.y-c.z))
	  if( ( na > 0 && nb > 0 && nc > 0 ) || ( na < 0 && nb < 0 && nc < 0 ) ) -(((ta*p.x)+(tc*p.y)+td)/tb)
	  else Float.NaN
  }
}

class Map25(val triangles : Array[Triangle25], var last:Triangle25){

  def find[T](v: Vector2) : Option[(Float,Triangle25)] = {
    for(t <- triangles){
      val tmp = t(v)
      if(!jFloat.isNaN(tmp)) return Some(tmp, t)
    }
    None
  }

  def fast(v: Vector2): Float = {
    val tmp = last(v)
    if(!jFloat.isNaN(tmp)) return tmp
    for(t <- last.near){
      val tmp = t(v)
      if(!jFloat.isNaN(tmp)) {
        last = t
        return tmp
      }
    }
    for(t <- last.far){
      val tmp = t(v)
      if(!jFloat.isNaN(tmp)) {
        last = t
        return tmp
      }
    }
    Float.NaN
  }

  def apply(v: Vector2): Float = {
    val f = fast(v)
    if(!jFloat.isNaN(f)) return f
    val s = find(v)
    if(s.isDefined){
      val v = s.get
      last = v._2
      v._1
    }else Float.NaN
  }

}

case class Triangle(i:Int, j:Int, k:Int)

case class MapData(points: Array[Vector3], indexes: Array[Triangle])

object Map25{ // extends RegexParsers {
//  type F3 = (Float,Float,Float)
//  type I3 = (Int, Int, Int)
//
//  def index: Parser[Int] = """\d+""".r ^^ (_.toInt)
//
//  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)
//
//  def float3 : Parser[F3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
//    assert(l.size == 3)
//    ( l(0), l(1), l(2) )
//  }
//
//  def index3 : Parser[I3] = "(" ~> repsep(index, ",") <~ ")" ^^ { l =>
//    assert(l.size == 3)
//    ( l(0), l(1), l(2) )
//  }
//
//  def pointInd: Parser[(List[F3], List[I3])] = ("[" ~> repsep(float3, ",") <~ "]") ~ ("{" ~> repsep(index3, ",") <~ "}") ^^ { i =>
//    (i._1, i._2)
//  }

  def load(nm:String) = {
    import org.callie._
    getClass.getResourceAsStream(nm)|{ s =>
      apply(s)
    }
  }

  def load(nm:String, predicate:MapBuilder): Map25 = {
    import org.callie._
    getClass.getResourceAsStream(nm)|{ s =>
      apply(s, predicate)
    }
  }

  class TriangleBuilder(val a:Int, val b:Int, val c:Int){
    val near = new mutable.ListBuffer[Int]
    val far = new mutable.ListBuffer[Int]

    def vertexes = Seq(a, b, c)
  }

  def apply(r:java.io.InputStream, predicate:MapBuilder = new MapBuilder): Map25 = {
    val par = new MapParser(new CommonTokenStream(new MapLexer(CharStreams.fromStream(r))))
    val pi = par.map().result

    val pts = pi.points
    predicate.set(pts.toList)

    val inds = pi.indexes.map(i => new TriangleBuilder(i.i, i.j, i.k)).zipWithIndex
    for(i <- inds; j <- inds if i._2 != j._2){
      var k = 0
      for(a <- i._1.vertexes; b <- j._1.vertexes if predicate.test(a, b)) k += 1
      if(k == 2) i._1.near += j._2
      else if(k == 1) i._1.far += j._2
    }

    val trgs = inds.map{ i =>
      val ind = i._1
      new Triangle25(pts(ind.a), pts(ind.b), pts(ind.c), new Array[Triangle25](i._1.near.size), new Array[Triangle25](i._1.far.size))
    }

    for(i <- inds){ // connect map
      val t = trgs(i._2)
      for(j <- i._1.near.zipWithIndex) t.near(j._2) = trgs(j._1)
      for(j <- i._1.far.zipWithIndex) t.far(j._2) = trgs(j._1)
    }

    new Map25(trgs, trgs.head)
  }

}
