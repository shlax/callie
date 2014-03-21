package org.callie.ringing

import scala.util.parsing.combinator.RegexParsers
import org.callie.math.{Matrix4, Vector3}
import org.callie.math.intr.Accl
import org.callie.Mod

trait Node{
  def joint(m:Mod) : Joint
}

case class IntNode(v:Vector3, childs:List[Node]) extends Node{
  override def joint(m:Mod) = {
    val ax = new Accl; val ay = new Accl; val az = new Accl
    val m = Matrix4(v)
    
    //if(childs.isEmpty) new IntrJoint(m, )
    null
  }
}

case class LinNode(ix:LinearJoint.Parms, iy:LinearJoint.Parms, iz:LinearJoint.Parms, childs:List[Node])  extends Node{
  override def joint(m:Mod) = null
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
    tp match{
      case (x:Float, y:Float, z:Float) =>
        IntNode(Vector3(x, y, z), ch)
      case m:List[_] =>
        var ix = LinearJoint.Parms(LinearJoint.X, 0f)
        var iy = LinearJoint.Parms(LinearJoint.Y, 0f) 
        var iz = LinearJoint.Parms(LinearJoint.Z, 0f)
        
        for(i <- m.asInstanceOf[List[LinMapTp]]){
          val to = i._2 match{
            case "x" => LinearJoint.X
            case "y" => LinearJoint.Y
            case "z" => LinearJoint.Z
          }
          
          i._1 match {
            case "x" => 
              ix = LinearJoint.Parms(to, i._3)
            case "y" =>
              iy = LinearJoint.Parms(to, i._3)
            case "z" =>
              iz = LinearJoint.Parms(to, i._3)
          }
        }
        
        LinNode(ix, iy, iz, ch)
    }    
  }
  
}
