package org.callie.model

import scala.util.parsing.combinator.RegexParsers

class Mod(val points : List[Mod.F3], val faces : List[Mod.Face])

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

  def face : Parser[Face] = (index <~ ":") ~ (vector3 <~ ":") ~ vector2 ^^ { i =>
    (i._1._1, i._2, i._1._2 )
  }

  def face3 : Parser[List[Face]] = "[" ~> repsep(face, ",") <~ "]" ^^ { x =>
    assert(x.size == 3)
    x
  }

  // f
  def faces : Parser[List[Face]] = "{" ~> rep(face3) <~ "}" ^^ (_.flatten)

  def value: Parser[(List[F3], List[Face])] = (points ~ faces) ^^ { i =>
    (i._1, i._2)
  }

  def apply(r:CharSequence) = {
    val t = parseAll(value, r).get
    new Mod(t._1, t._2)
  }

}
