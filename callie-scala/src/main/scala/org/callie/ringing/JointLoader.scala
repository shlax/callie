package org.callie.ringing

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.callie.gen.joint.{JointLexer, JointParser}

import org.callie.math.{Matrix4, Vector3}
import org.callie.math.intr.Accl
import org.callie.ogl.GlEventListener
import org.callie.model.{Model, MorfingObject}
import org.callie.math.Axis

import scala.jdk.CollectionConverters._

case class AxisValue(axis:Axis, value:Float)

abstract class Node(off:Option[Vector3], ind:Map[String,List[Int]]){

  def apply(ev:GlEventListener, m:Map[String,Model]): (Array[MorfingObject], Joint, Vector3) = {
    val o = m.map(kv => (kv._1, MorfingObject(ev, kv._2)))
    (o.values.toArray, join(o, Vector3()), off.getOrElse(Vector3()))
  }

  def cordsNormals(m:Map[String, MorfingObject], offset:Vector3): (Mapping, Mapping) = {
    val t = ind.map{ i =>
      val obj = m(i._1)
      i._2.map(j => (obj.projPoint(j), obj.projNormals(j)) )
    }.flatten
    (t.map(_._1).map(x => (x._1.sub(offset), x._2)).toArray, t.map(_._2).toArray.flatten)
  }

  type Mapping = Array[(Vector3, Vector3)]
  
  def join(m:Map[String, MorfingObject], offset:Vector3, parent:Option[IntrTravJoint] = None) : Joint
}

object IntNode{
  def create(name:String, v:Vector3, ind:java.util.Map[String,java.util.Set[Integer]], childs:java.util.List[Node]) : IntNode = {
    val i = ind.asScala.map( i => (i._1, i._2.asScala.map(_.intValue()).toList) ).toMap
    new IntNode(name, v, i, childs.asScala.toList)
  }
}

class IntNode(name:String, v:Vector3, ind:Map[String,List[Int]], childs:List[Node]) extends Node(Some(v), ind){
  override def join(ojbs:Map[String, MorfingObject], offset:Vector3, parent:Option[IntrTravJoint]):IntrJoint = {
    val ax = new Accl; val ay = new Accl; val az = new Accl
    val m = Matrix4(v)

    val off = Vector3.add(offset, v)

    val (coord, normals) = cordsNormals(ojbs, off)
    if(childs.isEmpty) new IntrJoint(name, m, ax, ay, az, coord, normals)
    else{
      val ch = new Array[Joint](childs.size)
      val sj = Some(new IntrTravJoint(name, m, ax, ay, az, ch, coord, normals))
      for(i <- ch.indices) ch(i) = childs(i).join(ojbs, off, sj)
      sj.get
    }
  }
}

class LinMap(m:String){
  val from = Axis.apply(m.substring(0, 1))
  val to = Axis.apply(m.substring(1, 2))
}

object LinNode{

  def create(name:String, v:Vector3, m:java.util.List[LinMap], ind:java.util.Map[String,java.util.Set[Integer]]) : LinNode = {
    val i = ind.asScala.map( i => (i._1, i._2.asScala.map(_.intValue()).toList) ).toMap

    var ix = AxisValue(Axis.X, -v.x)
    var iy = AxisValue(Axis.Y, -v.y)
    var iz = AxisValue(Axis.Z, -v.z)

    for(w <- m.asScala){
      w.from match {
        case Axis.X =>
          ix = AxisValue(w.to, ix.value)
        case Axis.Y =>
          iy = AxisValue(w.to, iy.value)
        case Axis.Z =>
          iz = AxisValue(w.to, iz.value)
      }
    }

    new LinNode(name, ix, iy, iz, i)
  }

}

class LinNode(name:String, ix:AxisValue, iy:AxisValue, iz:AxisValue, ind:Map[String,List[Int]])  extends Node(None ,ind){
  override def join(ojbs:Map[String, MorfingObject], offset:Vector3, parent:Option[IntrTravJoint]):LinearJoint = {
    val (coord, normals) = cordsNormals(ojbs, offset)
    new LinearJoint(name, parent.get, ix, iy, iz, coord, normals)
  }
}

case class Group(nm:String, ind:java.util.Set[Integer])

object Node { //extends RegexParsers {
//  type F3 = (Float,Float,Float)
//  type LinMap = (String, String)
//
//  def index: Parser[Int] = """\d+""".r ^^ (_.toInt)
//
//  def float: Parser[Float] = """[+-]?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?""".r ^^ (_.toFloat)
//
//  def vector: Parser[F3] = "(" ~> repsep(float, ",") <~ ")" ^^ { l =>
//    assert(l.size == 3)
//    ( l(0), l(1), l(2) )
//  }
//
//  def name: Parser[String] = "[a-zA-Z0-9_]+".r
//
//  def group: Parser[(String, List[Int])] = (name <~ ":(") ~ repsep(index, ",") <~ ")" ^^ { i => (i._1, i._2) }
//
//  def groupMap: Parser[Map[String, List[Int]]] = "{" ~> repsep(group, ",") <~ "}" ^^ { v =>
//    val m = mutable.Map[String, mutable.Set[Int]]()
//    for(i <- v) m.getOrElseUpdate(i._1, mutable.Set[Int]()) ++= i._2
//    m.toMap.map{kv => (kv._1, kv._2.toList)}
//  }
//
//  def node(scale:Float) : Parser[Node] = "[" ~> ( normal(scale) | linear ) <~ "]"
//
//  def normal(scale:Float) : Parser[IntNode] = (name <~ ":" ) ~ vector ~ ( ":" ~> groupMap ) ~ rep(node(scale)) ^^ { q =>
//    new IntNode(q._1._1._1, Vector3(q._1._1._2).mul(scale), q._1._2, q._2)
//  }
//
//  def linMap : Parser[LinMap] = "|" ~> ( "x" | "y" | "z")  ~ ("x" | "y" | "z")  ^^ { a => (a._1, a._2) }
//
//  def linear : Parser[LinNode] = (name <~ "|" ) ~ vector ~ rep(linMap) ~ ( ":" ~> groupMap ) ^^ { q =>
//    var ix = AxisValue(Axis.X, - q._1._1._2._1)
//    var iy = AxisValue(Axis.Y, - q._1._1._2._2)
//    var iz = AxisValue(Axis.Z, - q._1._1._2._3)
//
//    for(w <- q._1._2){
//      val to = Axis.apply(w._2)
//      w._1 match {
//        case "x" =>
//          ix = AxisValue(to, ix.value)
//        case "y" =>
//          iy = AxisValue(to, iy.value)
//        case "z" =>
//          iz = AxisValue(to, iz.value)
//      }
//    }
//
//    new LinNode(q._1._1._1, ix, iy, iz, q._2)
//  }

  def apply(ev:GlEventListener, m:Map[String,Model], nm:String, scale:Float = 1f): (Array[MorfingObject], Joint, Vector3) = {
    import org.callie._
    getClass.getResourceAsStream(nm)|{ s =>
      load(ev, m, s, scale)
    }
  }

  def load(ev:GlEventListener, m:Map[String,Model], r:java.io.InputStream, scale:Float = 1f): (Array[MorfingObject], Joint, Vector3) = {
    val par = new JointParser(new CommonTokenStream(new JointLexer(CharStreams.fromStream(r))))
    par.setScale(scale)
    val n = par.node().result
    n.apply(ev, m)
  }

}
