package org.callie

import org.callie.control.MovingObject
import org.callie.input.Camera
import org.callie.ogl.{Gl, GlEventListener, LwglFrame}
import org.callie.map.Map25
import org.callie.math.Vector3
import org.callie.model.{GlObject, Mod, MorfingObject, StaticObject, TextureGroup}
import org.callie.ringing.{JoinControl, Joint, JointAttachment, KeyFrameLoader, Node}

object MainLevel extends App {

  LwglFrame{gl: GlEventListener =>
    val camCtrl = new MovingObject(Map25.load("/data/map/data.map"), 1.65f)
    Camera.lookAt(camCtrl)

    val (charObj: Array[MorfingObject], joint: Joint, zero: Vector3) = Node.load(gl, Map(
      "pSphere5" -> Mod.load("/data/char/model.mod").scale(0.1f),
      "polySurface115" -> Mod.load("/data/char/hair.mod").scale(0.1f)
    ), "/data/char/joints.skl", 0.1f)

    val stand = KeyFrameLoader.load(joint, "/data/char/anim/stand.ang", 0.1f)
    val run = for (i <- 1 to 8) yield KeyFrameLoader.load(joint, "/data/char/anim/run/run" + i + ".ang", 0.1f)

    val anim: JoinControl = JoinControl(camCtrl, joint, zero, stand, run: _*)

    //    val leg = joint.lookup("joint752").get.asInstanceOf[JointAttachment]
    //    val hand = joint.lookup("joint714").get.asInstanceOf[JointAttachment]



    /* val sphere = new TextureGroup(this, "/demo/box/white.png", Gl.TEXTURE0,
    new StaticObject(this, Mod.load("/demo/box/sphere.mod"))
  ) */

    val objects = Array[GlObject](
      new TextureGroup(gl, "/data/char/char.png", Gl.GL_TEXTURE0, charObj.toIndexedSeq: _*),

      //new TextureGroup(this, "/demo/t.png", Gl.TEXTURE0,
      new TextureGroup(gl, "/data/map/sand.png", Gl.GL_TEXTURE0,
        new StaticObject(gl, Mod.load("/data/map/dunes.mod"))
      ),

      new TextureGroup(gl, "/data/map/sky.png", Gl.GL_TEXTURE0,
        new StaticObject(gl, Mod.load("/data/map/sky.mod"))
      ),

      new TextureGroup(gl, "/data/map/sun.png", Gl.GL_TEXTURE0,
        new StaticObject(gl, Mod.load("/data/map/sun.mod"))
      )
    )

    val prog = gl.createProgram(
      gl.createShader(Gl.GL_VERTEX_SHADER, GlPrograms.vertex()),
      gl.createShader(Gl.GL_FRAGMENT_SHADER, GlPrograms.fragment(discard = true))
    )

    //char.init(gl)

    //sphere.init(gl)
    val camProg = Camera.program(prog)

    val revolver = new TextureGroup(gl, "/data/char/pistol/revolver.png", Gl.GL_TEXTURE0,
      JointAttachment(camProg, joint, "joint752",
        new StaticObject(gl, Mod.load("/data/char/pistol/holder.mod").scale(0.1f))
      )
    )

    revolver.init()

    for (o <- objects) o.init()

    Gl.glUseProgram(prog)
    Gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    Gl.glEnable(Gl.GL_DEPTH_TEST)

    //Gl.glPolygonMode(Gl.GL_FRONT_AND_BACK, Gl.GL_LINE)

    var t = System.nanoTime()

    () => {
      Gl.glClear(Gl.GL_COLOR_BUFFER_BIT | Gl.GL_DEPTH_BUFFER_BIT)

      Camera.apply()

      val tmp = System.nanoTime()
      val dt: Float = ((tmp - t) / 1e9d).asInstanceOf[Float]
      t = tmp

      anim.apply(dt)
      //char.update(gl)
      //sphere.update(gl)

      camProg.light()

      revolver.update()

      camProg.identity()
      for (o <- objects) o.update()
    }

  }
}
