package org.callie.ringing

import org.callie.math.{Axis, Vector3}

import scala.util.parsing.combinator.RegexParsers
import scala.collection.mutable
import scala.io.Source

object KeyFrameLoader extends RegexParsers {
  type F3 = (Float, Float, Float)

  class NodeKeys(val nm:String, angles:F3, childs:List[NodeKeys]){
    def apply(j:Joint, l:mutable.ArrayBuffer[KeyValue]):Unit={
      val ji = j.asInstanceOf[JointIntr]

      val conv = (Math.PI/180d).asInstanceOf[Float]

      l += new KeyValue(nm, Axis.X, ji.ax, conv * angles._1)
      l += new KeyValue(nm, Axis.Y, ji.ay, conv * angles._2)
      l += new KeyValue(nm, Axis.Z, ji.az, conv * angles._3)

      for(c <- childs){
        val pj = j.asInstanceOf[JointTrav]
        for(n <- pj.childs if n.name == c.nm) c(n, l)
      }
    }

  }

  class MainNodeKeys(nm:String, offset:Vector3, angles:F3, childs:List[NodeKeys]) extends NodeKeys(nm, angles, childs){
    def apply(j:Joint) : (Vector3, KeyFrame) = {
      val l = mutable.ArrayBuffer[KeyValue]()
      apply(j, l)
      (offset, new KeyFrame(l.toArray))
    }
  }

  def apply(j:Joint, nm:String, scale:Float = 1f) = {
    import org.callie._
    Source.fromInputStream(getClass.getResourceAsStream(nm), "UTF-8")|{ s =>
      load(j, s.mkString, scale)
    }
  }

  def load(j:Joint, r:CharSequence, scale:Float = 1f) = {
    val n = parseAll(mainNode(scale), r).get
    n(j)
  }

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)

  def name: Parser[String] = "[a-zA-Z0-9_]+".r

  def vector: Parser[F3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 3)
    ( l(0), l(1), l(2) )
  }

  def node:Parser[NodeKeys] = ("[" ~> name <~ ":") ~ vector ~ (rep(node) <~ "]") ^^ { n =>
    new NodeKeys(n._1._1, n._1._2, n._2)
  }

  def mainNode(scale:Float):Parser[MainNodeKeys] = ("[" ~> name <~ ":") ~ (vector <~ ":" )~ vector ~ (rep(node) <~ "]") ^^ { n =>
    new MainNodeKeys(n._1._1._1, Vector3(n._1._1._2).mul(scale), n._1._2, n._2)
  }

}
