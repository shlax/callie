//package org.callie.jogl
//
//import com.jogamp.opengl.GL3ES3
//import org.callie.model.{Graphics, Physics}
//import org.callie.input.Camera
/**
 *   input <--------------------------<-|
 *     |                                |
 *   physic(physic)                     |
 *     |                                |
 *     ->--------------------------     |
 *     |                           |    |
 * physic(animation)               |    |
 *     |                           |    |
 *     ->-----------------         |    |
 *     |                  |        |    |
 *     |                sound      |    |
 *     |                           |    |
 * graphics(ray) ----------------  ai ->|
 *     |
 * graphics(render)
 * */
//object MainLoop extends App {
//  // load stuff
//  JoglFrame(new GlEventListener(){
//    override def initGL4(implicit gl: GL3ES3){
//      Physics.init()
//      Graphics.init(gl)
//      //Camera.init(gl)
//    }
//
//    override def displayGL4(implicit gl: GL3ES3){
//      Physics.update()
//      Graphics.display(gl)
//      Camera.display(gl)
//    }
//  })
//}
