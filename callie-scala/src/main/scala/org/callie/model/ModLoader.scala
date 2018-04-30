package org.callie.model

import scala.util.parsing.combinator.RegexParsers
import java.io.Reader

class Mod{
  var points : List[Mod.F3] = _
  var faces : List[Mod.Face] = _
}

object Mod extends RegexParsers {
  type F2 = (Float,Float)
  type F3 = (Float,Float,Float)

  type Face = (Int,F2, F3)

  def index: Parser[Int] = """\d+""".r ^^ (_.toInt)

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)

  def vector2 : Parser[F2] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 2)
    ( l(0), l(1) )
  }

  def vector3 : Parser[F3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 3)
    ( l(0), l(1), l(2) )
  }

  // v
  def points : Parser[List[F3]] = "[" ~> repsep(vector3, ",") <~ "]"

  def triangle : Parser[Face] = "[" ~> ((index <~ ":") ~ (vector3 <~ ":") ~ vector2) <~ "]" ^^ { i =>
    (i._1._1, i._2, i._1._2 )
  }

  // f
  def faces : Parser[List[Face]] = "{" ~> rep(triangle) <~ "}"

  def value(m:Mod) = repN(4,
        points ^^ (m.points = _)
      | faces ^^ (m.faces = _)
    )

  def apply(r:CharSequence) = {
    val m = new Mod()
    parseAll(value(m), r)
    m
  }

  def apply(r:Reader) = try{
    val m = new Mod()
    parseAll(value(m), r)
    m
  }finally { r.close() }

}
