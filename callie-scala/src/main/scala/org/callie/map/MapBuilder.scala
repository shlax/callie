package org.callie.map

import org.callie.math.Vector3

class MapBuilder{
  var points:Array[Vector3] = new Array[Vector3](0)

  def set(p:Array[Vector3]):Unit = {
    points = p
  }

  def test(i:Int, j:Int):Boolean = {
    i == j
  }

}

