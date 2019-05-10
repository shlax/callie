package org.callie

import org.callie.control.MovingObject
import org.callie.input.Camera
import org.callie.jogl.{Gl, GlEventListener, GlType, JoglFrame}
import org.callie.map.Map25
import org.callie.model.{Mod, StaticObject, TextureGroup}
import org.callie.ringing.{JoinControl, KeyFrameLoader, Node}

object MainLevel extends App {

  val camCtrl = new MovingObject(Map25.load("/data/map/data.map"), 1.5f)

  JoglFrame(new GlEventListener {

    val (charObj, joint) = Node.load(this, Map(
        "pSphere5" -> Mod.load("/data/char/model.mod").scale(0.1f),
        "polySurface115" -> Mod.load("/data/char/hair.mod").scale(0.1f)
      ), "/data/char/joints.skl", 0.1f)

    val stand = KeyFrameLoader.load(joint, "/data/char/anim/stand.ang", 0.1f)

    val run1 = KeyFrameLoader.load(joint, "/data/char/anim/run/run1.ang", 0.1f)
    val run2 = KeyFrameLoader.load(joint, "/data/char/anim/run/run2.ang", 0.1f)
    val run3 = KeyFrameLoader.load(joint, "/data/char/anim/run/run3.ang", 0.1f)
    val run4 = KeyFrameLoader.load(joint, "/data/char/anim/run/run4.ang", 0.1f)

    val anim = JoinControl(camCtrl, joint, stand, run1, run2, run3, run4)

    /* val sphere = new TextureGroup(this, "/demo/box/white.png", Gl.TEXTURE0,
      new StaticObject(this, Mod.load("/demo/box/sphere.mod"))
    ) */

    val objects = Array(
      new TextureGroup(this, "/data/char/char.png", Gl.TEXTURE0, charObj:_* ),

      new TextureGroup(this, "/data/map/sand.png", Gl.TEXTURE0,
        new StaticObject(this, Mod.load("/data/map/dunes.mod"))
      ),

      new TextureGroup(this, "/data/map/sky.png", Gl.TEXTURE0,
        new StaticObject(this, Mod.load("/data/map/sky.mod"))
      ),

      new TextureGroup(this, "/data/map/sun.png", Gl.TEXTURE0,
        new StaticObject(this, Mod.load("/data/map/sun.mod"))
      )
    )

    var t: Long = 0

    override def initGL4(gl: GlType){
      val vertexSchader = createShader(gl, Gl.VERTEX_SHADER, GlPrograms.vertex())
      val fragmentSchader = createShader(gl, Gl.FRAGMENT_SHADER, GlPrograms.fragment(discard = true))
      val prog = createProgram(gl, vertexSchader, fragmentSchader)

      //char.init(gl)

      //sphere.init(gl)

      for(o <- objects) o.init(gl)

      Camera.program(gl, Seq(prog))
      Camera.lookAt(camCtrl)

      gl.glUseProgram(prog)
      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
      gl.glEnable(Gl.DEPTH_TEST)

      //gl.glPolygonMode(Gl.FRONT_AND_BACK, Gl.LINE)

      t = System.nanoTime()
    }

    override def displayGL4(gl: GlType){
      gl.glClear(Gl.COLOR_BUFFER_BIT | Gl.DEPTH_BUFFER_BIT)

      val tmp = System.nanoTime()
      val dt: Float = ((tmp - t) / 1e9d).asInstanceOf[Float]
      t = tmp

      anim.apply(dt)
      Camera.display(gl)

      //char.display(gl)
      //sphere.display(gl)

      for(o <- objects) o.display(gl)
    }

  })

}
