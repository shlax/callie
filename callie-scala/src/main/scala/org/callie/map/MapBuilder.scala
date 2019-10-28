package org.callie.map

import java.util.function.Predicate

import org.callie.math.Vector3

trait MapBuilder{

  def apply(points:List[Vector3]) : Predicate[(Int, Int)]
}

