package org.callie.ringing

import scala.util.parsing.combinator.RegexParsers

class Node{
  
}

object Node extends RegexParsers {
 
  def index: Parser[Int] = """\d+""".r ^^ (_.toInt)

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)
  
  def name: Parser[String] = "[a-zA-Z0-9_]+".r // ^^ (_.toInt)
  
  def normal : Parser[(Float, Float, Float)] = "normal" ~> ":" ~> repsep(float, ",") ^^ { a =>
    assert(a.size == 3)
    (a(0), a(1), a(2)) 
  }
  
  type LinMapTp = (String, String, Float)
  
  def linMap : Parser[LinMapTp] = ( ("x" | "y" | "z") <~ "=" ) ~ ( ("x" | "y" | "z") <~ ":" ) ~ float ^^ { a => (a._1._1, a._1._2, a._2) }
  
  def linear : Parser[List[LinMapTp]] = "linear" ~> ":" ~> repsep(linMap, ",")
    
  def node : Parser[Node] = name ~ ("(" ~> ( normal | linear ) <~ ")" ) ~ ("[" ~> repsep(index, ",") <~ "]" ) ~ ( "{" ~> rep(node) <~ "}" ) ^^ { case nm ~ tp ~ pt ~ ch =>    
    val n = new Node()
    tp match{
      case (x:Float, y:Float, z:Float) =>
        
      case m:List[_] =>
        
    }
    n
  }
  
}
