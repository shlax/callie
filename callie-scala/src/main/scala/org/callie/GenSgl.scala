package org.callie

import com.jogamp.opengl.GL3ES3

import scala.collection.mutable

object GenSgl extends App{

  val out = new StringBuilder("@inline\nobject Gl {")
  val done = mutable.Set[Class[_]]()

  def prc(cls:Class[_]){
    if(cls != null &&  done.add(cls)){
      if(cls != null && classOf[Object] != cls){

        for(f <- cls.getDeclaredFields){
          out ++= "\n  @inline def " + f.getName.substring(3) + " = " + cls.getName + "." + f.getName
        }

        prc(cls.getSuperclass)
        for(s <- cls.getInterfaces)prc(s)
      }
    }
  }
  prc(classOf[GL3ES3])

  out ++= "\n}"

  println(out)

}
