package org.callie

import javax.media.opengl.GL4

object GenSgl extends App{

  val out = new StringBuilder("object GL_4 {")

  def prc(cls:Class[_]){
    if(cls != null && classOf[Object] != cls){

      for(f <- cls.getDeclaredFields){
        out ++= "\n  @inline def " + f.getName.substring(3) + " = " + cls.getName + "." + f.getName
      }

      prc(cls.getSuperclass)
      for(s <- cls.getInterfaces)prc(s)
    }
  }
  prc(classOf[GL4])

  out ++= "\n}"

  println(out)

}
