package org.callie.gen

import org.callie.math.Vector3

case class Int3(i:Int, j:Int, k:Int)

case class Float3Int3(points: Array[Vector3], indexes: Array[Int3]){}