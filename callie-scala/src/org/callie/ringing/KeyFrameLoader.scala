package org.callie.ringing

import scala.util.parsing.combinator.RegexParsers
import org.callie.math.Axis

object KeyFrameLoader extends RegexParsers {
  case class NodeKeys(nm:String, values:List[(Axis.Value,Float)])

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)

  def name: Parser[String] = "[a-zA-Z0-9_]+".r

  def axis: Parser[(Axis.Value,Float)] = ( ("x" | "y" | "z") <~ "=" ) ~ float ^^ { case a ~ v => (Axis(a),v) }

  def key : Parser[NodeKeys] = (name <~ "(") ~ repsep(axis, ",") <~ ")" ^^ { case nm ~ v => NodeKeys(nm, v) }

  def keys : Parser[List[NodeKeys]] = rep(key)

}
