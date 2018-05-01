package org.callie.ringing

import org.callie.math.Vector3

import scala.util.parsing.combinator.RegexParsers
import scala.collection.mutable

object KeyFrameLoader extends RegexParsers {
  type F3 = (Float, Float, Float)

  class NodeKeys(val nm:String, angles:F3, childs:List[NodeKeys]){
    def apply(j:Joint, l:mutable.MutableList[KeyValue]){
      val ji = j.asInstanceOf[JointIntr]

      l += new KeyValue(ji.ax, angles._1)
      l += new KeyValue(ji.ay, angles._2)
      l += new KeyValue(ji.az, angles._3)

      for(c <- childs){
        val pj = j.asInstanceOf[JointTrav]
        for(n <- pj.childs if n.name == c.nm) c(n, l)
      }
    }

  }

  class MainNodeKeys(nm:String, offset:F3, angles:F3, childs:List[NodeKeys]) extends NodeKeys(nm, angles, childs){
    def apply(j:Joint) : (Vector3, KeyFrame) = {
      val l = mutable.MutableList[KeyValue]()
      apply(j, l)
      (Vector3(offset), new KeyFrame(l.toArray))
    }
  }

  def apply(j:Joint, r:CharSequence) = {
    val n = parseAll(mainNode, r).get
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

  def mainNode:Parser[MainNodeKeys] = ("[" ~> name <~ ":") ~ (vector <~ ":" )~ vector ~ (rep(node) <~ "]") ^^ { n =>
    new MainNodeKeys(n._1._1._1, n._1._1._2, n._1._2, n._2)
  }

}
