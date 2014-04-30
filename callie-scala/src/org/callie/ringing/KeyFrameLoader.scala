package org.callie.ringing

import scala.util.parsing.combinator.RegexParsers

object KeyFrameLoader extends RegexParsers {

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)

  def name: Parser[String] = "[a-zA-Z0-9_]+".r

}
