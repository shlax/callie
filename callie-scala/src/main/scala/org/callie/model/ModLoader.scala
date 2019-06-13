package org.callie.model

import scala.io.Source
import scala.util.parsing.combinator.RegexParsers

case class Point2(x:Float, y:Float)

case class Point3(x:Float, y:Float, z:Float){
  def scale(s:Float) = Point3(x*s, y*s, z*s)
}

case class Face(point:Int, normal:Point3, uv:List[Point2])

case class Mod(points : List[Point3], faces : List[Face]){
  def scale(s:Float) = Mod(points.map(_.scale(s)), faces)
}

object Mod extends RegexParsers {
  def index: Parser[Int] = """\d+""".r ^^ (_.toInt)

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)

  def vector2 : Parser[Point2] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 2)
    Point2( l(0), l(1) )
  }

  def vector3 : Parser[Point3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 3)
    Point3( l(0), l(1), l(2) )
  }

  // v
  def points : Parser[List[Point3]] = "[" ~> repsep(vector3, ",") <~ "]"

  def uvs : Parser[List[Point2]] = "[" ~> repsep(vector2, ",") <~ "]"

  def face : Parser[Face] = (index <~ ":") ~ (vector3 <~ ":") ~ uvs ^^ { i =>
    Face(i._1._1, i._1._2, i._2)
  }

  def face3 : Parser[List[Face]] = "[" ~> repsep(face, ",") <~ "]" ^^ { x =>
    assert(x.size == 3)
    x
  }

  // f
  def faces : Parser[List[Face]] = "{" ~> rep(face3) <~ "}" ^^ (_.flatten)

  def value: Parser[Mod] = (points ~ faces) ^^ { i =>
    Mod(i._1, i._2)
  }

  def load(nm:String):Mod = {
    import org.callie._
    Source.fromInputStream(getClass.getResourceAsStream(nm), "UTF-8")|{s =>
      apply(s.mkString)
    }
  }

  def apply(r:CharSequence):Mod = parseAll(value, r).get

}
