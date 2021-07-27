package org.callie.ringing

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.callie.control
import org.callie.gen.keyFrame.{KeyFrameLexer, KeyFrameParser}
import org.callie.math.{Axis, Vector3}

import scala.util.parsing.combinator.RegexParsers
import scala.collection.mutable
import scala.io.Source
import scala.jdk.CollectionConverters._

object KeyFrameLoader { // extends RegexParsers {
  // type F3 = (Float, Float, Float)

  object NodeKeys{
    def create(nm:String, angles:Vector3, childs:java.util.List[NodeKeys]) : NodeKeys = {
      new NodeKeys(nm, angles, childs.asScala.toList)
    }
  }

  class NodeKeys(val nm:String, angles:Vector3, childs:List[NodeKeys]){
    def apply(j:Joint, l:mutable.ArrayBuffer[KeyValue]):Unit={
      val ji = j.asInstanceOf[JointIntr]

      val conv = (Math.PI/180d).asInstanceOf[Float]

      l += new KeyValue(nm, Axis.X, ji.ax, conv * angles.x)
      l += new KeyValue(nm, Axis.Y, ji.ay, conv * angles.y)
      l += new KeyValue(nm, Axis.Z, ji.az, conv * angles.z)

      for(c <- childs){
        val pj = j.asInstanceOf[JointTrav]
        for(n <- pj.childs if n.name == c.nm) c(n, l)
      }
    }

  }

  object MainNodeKeys{
    def create(nm:String, offset:Vector3, angles:Vector3, childs:java.util.List[NodeKeys]) : MainNodeKeys = {
      new MainNodeKeys(nm, offset, angles, childs.asScala.toList)
    }
  }

  class MainNodeKeys(nm:String, offset:Vector3, angles:Vector3, childs:List[NodeKeys]) extends NodeKeys(nm, angles, childs){
    def apply(j:Joint) : OffsetFrame = {
      val l = mutable.ArrayBuffer[KeyValue]()
      apply(j, l)
      OffsetFrame(offset, new KeyFrame(l.toArray))
    }
  }

  def apply(j:Joint, nm:String, scale:Float = 1f) = {
    import org.callie._
    getClass.getResourceAsStream(nm)|{ s =>
      load(j, s, scale)
    }
  }

  def load(j:Joint, r:java.io.InputStream, scale:Float = 1f) = {
    val par = new KeyFrameParser(new CommonTokenStream(new KeyFrameLexer(CharStreams.fromStream(r))))
    par.setScale(scale)
    val n = par.mainNode().result
    n.apply(j)
  }

//  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)
//
//  def name: Parser[String] = "[a-zA-Z0-9_]+".r
//
//  def vector: Parser[F3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
//    assert(l.size == 3)
//    ( l(0), l(1), l(2) )
//  }
//
//  def node:Parser[NodeKeys] = ("[" ~> name <~ ":") ~ vector ~ (rep(node) <~ "]") ^^ { n =>
//    new NodeKeys(n._1._1, n._1._2, n._2)
//  }
//
//  def mainNode(scale:Float):Parser[MainNodeKeys] = ("[" ~> name <~ ":") ~ (vector <~ ":" )~ vector ~ (rep(node) <~ "]") ^^ { n =>
//    new MainNodeKeys(n._1._1._1, Vector3(n._1._1._2).mul(scale), n._1._2, n._2)
//  }

}
