package org.callie.ringing

import scala.util.parsing.combinator.RegexParsers
import org.callie.math.Axis

object KeyFrameLoader extends RegexParsers {
  type F3 = (Float, Float, Float)

  class NodeKeys(nm:String, angles:F3, childs:List[NodeKeys])
  class MainNodeKeys(nm:String, offset:F3, angles:F3, childs:List[NodeKeys]) extends NodeKeys(nm, angles, childs)

  def apply(j:Joint, )

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)

  def name: Parser[String] = "[a-zA-Z0-9_]+".r

  def vector: Parser[F3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 3)
    ( l(0), l(1), l(2) )
  }

  def node:Parser[NodeKeys] = ("[" ~> name <~ ":") ~ vector ~ (rep(node) <~ "]") ^^ { n =>
    new NodeKeys(n._1._1, n._1._2, n._2)
  }

  def mainNode:Parser[MainNodeKeys] = ("[" ~> name <~ ":") ~ vector ~ ( ":" ~> vector) ~ (rep(node) <~ "]") ^^ { n =>
    new MainNodeKeys(n._1._1._1, n._1._1._2, n._1._2, n._2)
  }

}
