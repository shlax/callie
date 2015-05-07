package org.callie.model

import scala.util.parsing.combinator.RegexParsers
import java.io.Reader

class Mod{
  var points : List[(Float,Float,Float)] = _
  var uvCoord : List[(Float,Float)] = _
  var normals : List[(Float,Float,Float)] = _

  var faces : List[(Mod.vtn,Mod.vtn,Mod.vtn)] = _
}

object Mod extends RegexParsers {
  //          v   t   n
  type vtn = (Int,Int,Int)

  def index: Parser[Int] = """\d+""".r ^^ (_.toInt)

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)

  def vector2 : Parser[(Float,Float)] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 2)
    ( l(0), l(1) )
  }

  // t
  def uvCoord : Parser[List[(Float,Float)]] = ("t" ~ "=" ~ "[") ~> rep(vector2) <~ "]"

  def vector3 : Parser[(Float,Float,Float)] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 3)
    ( l(0), l(1), l(2) )
  }

  // v
  def points : Parser[List[(Float,Float,Float)]] = ("v" ~ "=" ~ "[") ~> rep(vector3) <~ "]"

  // n
  def normals : Parser[List[(Float,Float,Float)]] = ("n" ~ "=" ~ "[") ~> rep(vector3) <~ "]"

  def index3 : Parser[Mod.vtn] = "(" ~> repsep(index, ",") <~ ")" ^^ { l =>
    assert(l.size == 3)
    ( l(0), l(1), l(2) )
  }

  def triangle : Parser[(Mod.vtn,Mod.vtn,Mod.vtn)] = "{" ~> repN(3, index3) <~ "}" ^^ { l => ( l(0), l(1), l(2) )}

  // f
  def faces : Parser[List[(Mod.vtn,Mod.vtn,Mod.vtn)]] = ("f" ~ "=" ~ "[") ~> rep(triangle) <~ "]"

  def value(m:Mod) = repN(4,
        points ^^ (m.points = _)
      | uvCoord ^^ (m.uvCoord = _)
      | normals ^^ (m.normals = _)
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

//object ModLoaderTst extends App{
//  val m = Mod(
//    """
//      |v = [(0.18,-0.98,-0.07)
//      |     (0.13,-0.98,-0.13)]
//      |t = [(0   ,0.06)
//      |     (0.06,0.06)
//      |     (0.12,0.06)]
//      |n = [(0.2 ,-0.07,-0.9e-1)
//      |     (0.15,-0.97,-0.1   )]
//      |f = [{(0 ,0 ,0)(1,1,1)(16,17,2)}
//      |     {(16,17,2)(1,1,1)(17,18,3)}]
//    """.stripMargin)
//
//  println(m.points)
//  println(m.normals)
//  println(m.uvCoord)
//
//  println(m.faces)
//}
