package org.callie.map

import org.callie.math.{Vector2, Vector3}

class Triangle25(val a : Vector3, val b : Vector3, val c : Vector3, override val near: Array[Triangle25], override val far: Array[Triangle25]) extends AbstractTriangle[Triangle25] {

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

class Map25(triangles : Array[Triangle25], last:Triangle25) extends AbstractMap[Triangle25, Vector2](triangles, last){
  override def test(t: Triangle25, r: Vector2): Float = t.apply(r)
}

case class MapData(points: Array[Vector3], indexes: Array[IndexTriangle])

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

//  def load(nm:String) = {
//    import org.callie._
//    getClass.getResourceAsStream(nm)|{ s =>
//      apply(s)
//    }
//  }

  def load(nm:String/* , predicate:MapBuilder* */): Map25 = {
    import org.callie._
    getClass.getResourceAsStream(nm)|{ s =>
      apply(s /*, predicate*/)
    }
  }

  def apply(r:java.io.InputStream /* , predicate:MapBuilder = new MapBuilder*/): Map25 = {
    val (pts, inds) = MapLoader.apply(r)

    val trgs = inds.map{ i =>
      val ind = i._1
      new Triangle25(pts(ind.a), pts(ind.b), pts(ind.c), new Array[Triangle25](i._1.near.size), new Array[Triangle25](i._1.far.size))
    }

    MapLoader.connect(inds, trgs)

    new Map25(trgs, trgs.head)
  }

}
