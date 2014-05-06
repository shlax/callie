package org.callie.ai

import org.callie.map.{Map25, Triangle25}
import org.callie.math.Vector3

class Map[T](map:Map25[T]){
  /** precomputed values */
  class Node(val triangle:Triangle25[T]){
    val center = Vector3((triangle.a.x + triangle.b.x + triangle.c.x)/3f,
      (triangle.a.y + triangle.b.y + triangle.c.y)/3f,
      (triangle.a.z + triangle.b.z + triangle.c.z)/3f)

    var location: Array[(Float, Node)] = _
  }

  val nodes = map.triangles.map(new Node(_))
  for(n <- nodes) n.location = nodes.collect{ case t if n.triangle.near.contains(t.triangle) => (Vector3.sub(n.center, t.center).len, t) }

  class Path(val path:List[Node]){
    // http://en.wikipedia.org/wiki/B%C3%A9zier_curve
  }

  def path(from:Triangle25[T], to:Triangle25[T]) : Option[Path] = {
    class PathNode(val node:Node){
      var len = Float.MaxValue
      var back: Option[PathNode] = None
    }
    val nds = nodes.map(new PathNode(_))
    def path(it:PathNode, from:PathNode, l:Float){
      if(l < it.len){
        it.len = l
        if(l > 0f) it.back = Some(from)
        for(n <- it.node.location.map(i => (nds.find(i._2==_.node).get, i._1))) path(it, n._1, l+n._2)
      }
    }
    val f = nds.find(_.node.triangle == from).get
    path(f, f, 0f)

    var act = nds.find(_.node.triangle == to).get
    var pth = List(act.node)

    while(act.back.isDefined){
      act = act.back.get
      pth = act.node :: pth
      if(pth.size > nds.size) return None
    }

    Some(new Path(pth.reverse))
  }

}
