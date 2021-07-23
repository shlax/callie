package org.callie.model

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.callie.gen.mod.{ModLexer, ModParser}

case class Point2(x:Float, y:Float)

case class Point3(x:Float, y:Float, z:Float){
  def scale(s:Float) = Point3(x*s, y*s, z*s)
}

case class Face(point:Int, normal:Point3, uv:Array[Point2])

case class Model(points : Array[Point3], faces : Array[Face]){
  def scale(s:Float) = Model(points.map(_.scale(s)), faces)
}

object Model { //extends RegexParsers {
//  def index: Parser[Int] = """\d+""".r ^^ (_.toInt)
//
//  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)
//
//  def vector2 : Parser[Point2] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
//    assert(l.size == 2)
//    Point2( l(0), l(1) )
//  }
//
//  def vector3 : Parser[Point3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
//    assert(l.size == 3)
//    Point3( l(0), l(1), l(2) )
//  }
//
//  // v
//  def points : Parser[List[Point3]] = "[" ~> repsep(vector3, ",") <~ "]"
//
//  def uvs : Parser[List[Point2]] = "[" ~> repsep(vector2, ",") <~ "]"
//
//  def face : Parser[Face] = (index <~ ":") ~ (vector3 <~ ":") ~ uvs ^^ { i =>
//    Face(i._1._1, i._1._2, i._2)
//  }
//
//  def face3 : Parser[List[Face]] = "[" ~> repsep(face, ",") <~ "]" ^^ { x =>
//    assert(x.size == 3)
//    x
//  }
//
//  // f
//  def faces : Parser[List[Face]] = "{" ~> rep(face3) <~ "}" ^^ (_.flatten)
//
//  def value: Parser[Model] = (points ~ faces) ^^ { i =>
//    Model(i._1, i._2)
//  }

  def apply(nm:String):Model = {
    import org.callie._
    getClass.getResourceAsStream(nm)|{s =>
      load(s)
    }
  }

  def load(r:java.io.InputStream):Model = {
    val par = new ModParser(new CommonTokenStream(new ModLexer(CharStreams.fromStream(r))))
    par.mod().result
  }

}
