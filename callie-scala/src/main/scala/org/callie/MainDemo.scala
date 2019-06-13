package org.callie

import org.callie.control.MovingObject
import org.callie.input.Camera
import org.callie.jogl.{Gl, GlEventListener, GlType, JoglFrame}
import org.callie.map.Map25
import org.callie.model.{Mod, StaticObject, TextureGroup}
import org.callie.ringing.{JoinControl, KeyFrameLoader, Node}

object MainDemo extends App{

  val camCtrl = new MovingObject(Map25.load("/demo/map/floor.map"), 1.5f)

  JoglFrame(new GlEventListener {

    val (charObj, joint, zero) = Node.load(this, Map("pSphere5" -> Mod.load("/demo/char/base.mod").scale(0.1f),
                                               "polySurface115" -> Mod.load("/demo/char/hair.mod").scale(0.1f)),
                                  "/demo/char/joints.mod", 0.1f)

    val stand = KeyFrameLoader.load(joint, "/demo/char/stand.mod", 0.1f)

    val run1 = KeyFrameLoader.load(joint, "/demo/char/run1.mod", 0.1f)
    val run2 = KeyFrameLoader.load(joint, "/demo/char/run2.mod", 0.1f)
    val run3 = KeyFrameLoader.load(joint, "/demo/char/run3.mod", 0.1f)
    val run4 = KeyFrameLoader.load(joint, "/demo/char/run4.mod", 0.1f)

    val char = new TextureGroup(this, "/demo/char/texture.png", Gl.TEXTURE0, charObj.toIndexedSeq:_* )

    val mapa = new TextureGroup(this, "/demo/t.png", Gl.TEXTURE0,
      new StaticObject(this, Mod.load("/demo/map/floor.mod"))
    )

    val anim = JoinControl(camCtrl, joint, zero, stand, run1, run2, run3, run4)

    var t: Long = 0

    override def initGL4(gl: GlType):Unit={
      val vertexSchader = createShader(gl, Gl.VERTEX_SHADER, GlPrograms.vertex())
      val fragmentSchader = createShader(gl, Gl.FRAGMENT_SHADER, GlPrograms.fragment(discard = true))
      val prog = createProgram(gl, vertexSchader, fragmentSchader)

      mapa.init(gl)
      char.init(gl)

      Camera.program(gl, Seq(prog))
      Camera.lookAt(camCtrl)

      gl.glUseProgram(prog)
      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
      gl.glEnable(Gl.DEPTH_TEST)

      //gl.glPolygonMode(Gl.FRONT_AND_BACK, Gl.LINE)

      t = System.nanoTime()
    }

    override def displayGL4(gl: GlType):Unit={
      gl.glClear(Gl.COLOR_BUFFER_BIT | Gl.DEPTH_BUFFER_BIT)

      val tmp = System.nanoTime()
      val dt: Float = ((tmp - t) / 1e9d).asInstanceOf[Float]
      t = tmp

      anim.apply(dt)
      Camera.display(gl)

      mapa.display(gl)
      char.display(gl)
    }

  })

}
