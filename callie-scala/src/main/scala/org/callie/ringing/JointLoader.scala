package org.callie.ringing

import scala.util.parsing.combinator.RegexParsers
import org.callie.math.{Matrix4, Vector3}
import org.callie.math.intr.Accl
import org.callie.jogl.GlEventListener
import org.callie.model.{Mod, MorfingObject}
import org.callie.math.Axis
import Axis.AxisValue

import scala.collection.mutable
import scala.io.Source

abstract class Node(ind:Map[String,List[Int]]){

  def apply(ev:GlEventListener, m:Map[String,Mod]) = {
    val o = m.map(kv => (kv._1, new MorfingObject(ev, kv._2)))
    val pn = ind.map{ i =>
      val obj = o(i._1)
      i._2.map(j => (obj.projPoint(j), obj.projNormals(j)) )
    }.flatten
    (o.values.toArray, join(pn.map(_._1).toArray, pn.map(_._2).toArray) )
  }
  
  type Mapping = Array[(Vector3, Vector3)]
  
  def join(coord : Mapping, normals: Mapping, parent:Option[IntrTravJoint] = None) : Joint
}

class IntNode(name:String, v:Vector3, ind:Map[String,List[Int]], childs:List[Node]) extends Node(ind){
  override def join(coord : Mapping, normals: Mapping, parent:Option[IntrTravJoint]) = {
    val ax = new Accl; val ay = new Accl; val az = new Accl
    val m = Matrix4(v)
    
    if(childs.isEmpty) new IntrJoint(name, m, ax, ay, az, coord, normals)
    else{
      val ch = new Array[Joint](childs.size)
      val j = new IntrTravJoint(name, m, ax, ay, az, ch, coord, normals)
      val sj = Some(j)
      for(i <- ch.indices) ch(i) = childs(i).join(coord, normals, sj)
      j
    }
  }
}

class LinNode(name:String, ix:AxisValue, iy:AxisValue, iz:AxisValue, ind:Map[String,List[Int]])  extends Node(ind){
  override def join(coord : Mapping, normals: Mapping, parent:Option[IntrTravJoint]) = new LinearJoint(name, parent.get, ix, iy, iz, coord, normals)
}

object Node extends RegexParsers {
  type F3 = (Float,Float,Float)
  type LinMap = (String, String)

  def index: Parser[Int] = """\d+""".r ^^ (_.toInt)

  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)

  def vector: Parser[F3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
    assert(l.size == 3)
    ( l(0), l(1), l(2) )
  }

  def name: Parser[String] = "[a-zA-Z0-9_]+".r

  def group: Parser[(String, List[Int])] = (name <~ ":(") ~ repsep(index, ",") <~ ")" ^^ { i => (i._1, i._2) }

  def groupMap: Parser[Map[String, List[Int]]] = "{" ~> repsep(group, ",") <~ "}" ^^ { v =>
    val m = mutable.Map[String, mutable.Set[Int]]()
    for(i <- v) m.getOrElseUpdate(i._1, mutable.Set[Int]()) ++= i._2
    m.mapValues(_.toList).toMap
  }

  def node(scale:Float) : Parser[Node] = "[" ~> ( normal(scale) | linear ) <~ "]"

  def normal(scale:Float) : Parser[IntNode] = (name <~ ":" ) ~ vector ~ ( ":" ~> groupMap ) ~ rep(node(scale)) ^^ { q =>
    new IntNode(q._1._1._1, Vector3(q._1._1._2).mul(scale), q._1._2, q._2)
  }

  def linMap : Parser[LinMap] = "|" ~> ( "x" | "y" | "z")  ~ ("x" | "y" | "z")  ^^ { a => (a._1, a._2) }

  def linear : Parser[LinNode] = (name <~ "|" ) ~ vector ~ rep(linMap) ~ ( ":" ~> groupMap ) ^^ { q =>
    var ix = AxisValue(Axis.X, q._1._1._2._1)
    var iy = AxisValue(Axis.Y, q._1._1._2._2)
    var iz = AxisValue(Axis.Z, q._1._1._2._3)

    for(w <- q._1._2){
      val to = Axis(w._2)
      w._1 match {
        case "x" =>
          ix = AxisValue(to, ix.value)
        case "y" =>
          iy = AxisValue(to, iy.value)
        case "z" =>
          iz = AxisValue(to, iz.value)
      }
    }

    new LinNode(q._1._1._1, ix, iy, iz, q._2)
  }

  def load(ev:GlEventListener, m:Map[String,Mod], nm:String, scale:Float = 1f) = {
    import org.callie._
    Source.fromInputStream(getClass.getResourceAsStream(nm), "UTF-8")|{ s =>
      apply(ev, m, s.mkString, scale)
    }
  }

  def apply(ev:GlEventListener, m:Map[String,Mod], r:CharSequence, scale:Float = 1f) = {
    val n = parseAll(node(scale), r).get
    n(ev, m)
  }

}
