package org.callie

import java.io.FileOutputStream

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

import scala.language.implicitConversions

object SwTest extends App {

  val c = HttpClientBuilder.create().build()

  for(i <- 3654 until 5000){
    val r = c.execute(new HttpGet("http://try-it-companion.com/data/slecny/slecna-"+i+"-3.jpg"))
    if(r.getStatusLine.getStatusCode == 200) {
      val o = new FileOutputStream("c:\\Users\\root\\Desktop\\b\\cse\\dt\\" + i + ".jpg")
      IOUtils.copy(r.getEntity.getContent, o)
      r.close()
      o.close()
    }else{
      r.close()
      print("x")
    }

    Thread.sleep(250)
    println(i)
  }

}