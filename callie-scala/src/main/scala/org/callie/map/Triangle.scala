package org.callie.map

import org.callie.math.{Vector2, Vector3}

import scala.collection.mutable
import scala.io.Source
import scala.util.parsing.combinator.RegexParsers

class Triangle25(val a : Vector3, val b : Vector3, val c : Vector3, val near: Array[Triangle25], val far: Array[Triangle25]) {

  val ab = Vector2(b.x-a.x, b.z-a.z)
  val bc = Vector2(c.x-b.x, c.z-b.z)
  val ca = Vector2(a.x-c.x, a.z-c.z)
  
  val (ta, tb, tc, td) = {
    val tmp = Vector3.cross( Vector3(c.x-a.x, c.y-a.y, c.z-a.z ), Vector3(b.x-a.x, b.y-a.y, b.z-a.z) )
	  (tmp.x, tmp.y, tmp.z, -((tmp.x*c.x)+(tmp.y*c.y)+(tmp.z*c.z)))
  }
  
  def apply(p : Vector2) = {
    val na = ((p.x-a.x)*ab.y)-(ab.x*(p.y-a.y))
	  val nb = ((p.x-b.x)*bc.y)-(bc.x*(p.y-b.y))
	  val nc = ((p.x-c.x)*ca.y)-(ca.x*(p.y-c.y))
	  if( ( na > 0 && nb > 0 && nc > 0 ) || ( na < 0 && nb < 0 && nc < 0 ) ) Some(-(((ta*p.x)+(tc*p.y)+td)/tb))
	  else None
  }
}

class Map25(val triangles : Array[Triangle25], var last:Triangle25){

  def find[T](v: Vector2) : Option[(Float,Triangle25)] = {
    for(t <- triangles){
      val tmp = t(v)
      if(tmp.isDefined) return Some(tmp.get, t)
    }
    None
  }

  def fast(v: Vector2): Option[Float] = {
    val tmp = last(v)
    if(tmp.isDefined)return tmp
    for(t <- last.near){
      val tmp = t(v)
      if(tmp.isDefined) {
        last = t
        return tmp
      }
    }
    for(t <- last.far){
      val tmp = t(v)
      if(tmp.isDefined) {
        last = t
        return tmp
      }
    }
    None
  }

  def apply(v: Vector2): Option[Float] = {
    val f = fast(v)
    if(f.isDefined) return f
    val s = find(v)
    if(s.isDefined){
      val v = s.get
      last = v._2
      Some(v._1)
    }else None
  }

}

object Map25 extends RegexParsers {
  type F3 = (Float,Float,Float)
  type I3 = (Int, Int, Int)

  def index: Parser[Int] = """\d+""".r ^^ (_.toInt)

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)

  def float3 : Parser[F3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 3)
    ( l(0), l(1), l(2) )
  }

  def index3 : Parser[I3] = "(" ~> repsep(index, ",") <~ ")" ^^ { l =>
    assert(l.size == 3)
    ( l(0), l(1), l(2) )
  }

  def pointInd: Parser[(List[F3], List[I3])] = ("[" ~> repsep(float3, ",") <~ "]") ~ ("{" ~> repsep(index3, ",") <~ "}") ^^ { i =>
    (i._1, i._2)
  }

  def load(nm:String) = {
    import org.callie._
    Source.fromInputStream(getClass.getResourceAsStream(nm), "UTF-8")|{ s =>
      apply(s.mkString)
    }
  }

  def apply(r:CharSequence) = {
    val pi = parseAll(pointInd, r).get

    val inds = pi._2.map(i => (i, new mutable.ListBuffer[Int], new mutable.ListBuffer[Int])).zipWithIndex
    for(i <- inds; j <- inds if i._2 != j._2){
      var k = 0
      for(a <- i._1._1.productIterator; b <- j._1._1.productIterator if a == b) k += 1
      if(k == 2) i._1._2 += j._2
      else if(k == 1) i._1._3 += j._2
    }

    val pts = pi._1.map(Vector3(_))

    val trgs = inds.map{ i =>
      val ind = i._1._1
      new Triangle25(pts(ind._1), pts(ind._2), pts(ind._3), new Array[Triangle25](i._1._2.size), new Array[Triangle25](i._1._3.size))
    }

    for(i <- inds){ // connect map
      val t = trgs(i._2)
      for(j <- i._1._2.zipWithIndex) t.near(j._2) = trgs(j._1)
      for(j <- i._1._3.zipWithIndex) t.far(j._2) = trgs(j._1)
    }

    new Map25(trgs.toArray, trgs.head)
  }

}
