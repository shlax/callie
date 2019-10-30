package org.callie.map

import org.callie.math.Vector3

class MapBuilder{
  var points:List[Vector3] = Nil

  def set(p:List[Vector3]):Unit = {
    points = p
  }

  def test(i:Int, j:Int):Boolean = {
    i == j
  }

}

