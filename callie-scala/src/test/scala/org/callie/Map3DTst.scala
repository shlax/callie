package org.callie

import org.callie.map.Map3D
import org.callie.math.{Ray, Vector3}

object Map3DTst extends App{

  val m = Map3D.load("/demo/cylinder/cylinder.map")

  for(i <- Seq(0, 1, -1, -2); j <- Seq(0 ,1, -1, -2) if !(i == 0 && j == 0) ) {
    println(m.find(Ray(Vector3(), Vector3(i, j, 0).normalize() )))
    println(m.find(Ray(Vector3(0, 0, 1), Vector3(i, j, 0).normalize() )))
    println(m.find(Ray(Vector3(0, 0, -1), Vector3(i, j, 0).normalize() )))
  }

}
