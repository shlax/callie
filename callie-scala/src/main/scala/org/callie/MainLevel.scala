package org.callie

import org.callie.control.{JoinControl, MovingObject}
import org.callie.input.{Camera, CameraProgram}
import org.callie.ogl.{Gl, GlEventListener, LwglFrame}
import org.callie.map.Map25
import org.callie.math.Vector3
import org.callie.model.{GlObject, Model, MorfingObject, StaticObject, TextureGroup}
import org.callie.ringing.{Joint, JointAttachment, KeyFrameLoader, Node}

object MainLevel extends App {

  LwglFrame{gl: GlEventListener =>
    val camCtrl = MovingObject(Map25.load("/data/map/data.map"), 1.65f)
    Camera.lookAt(camCtrl)

    val (charObj: Array[MorfingObject], joint: Joint, zero: Vector3) = Node(gl, Map(
      "pSphere5" -> Model("/data/char/model.mod").scale(0.1f),
      "polySurface115" -> Model("/data/char/hair.mod").scale(0.1f)
    ), "/data/char/joints.skl", 0.1f)

    val stand = KeyFrameLoader(joint, "/data/char/anim/stand.ang", 0.1f)
    val run = for (i <- 1 to 8) yield KeyFrameLoader(joint, "/data/char/anim/run/run" + i + ".ang", 0.1f)

    val rotate = for (i <- 1 to 2) yield KeyFrameLoader(joint, "/data/char/anim/rotate" + i + ".ang", 0.1f)

    val pistolTakeDown = KeyFrameLoader(joint, "/data/char/anim/pistol/takeDown.ang", 0.1f)
    val pistolTakeUp = KeyFrameLoader(joint, "/data/char/anim/pistol/takeUp.ang", 0.1f)

    val pistolStand = KeyFrameLoader(joint, "/data/char/anim/pistol/stand.ang", 0.1f)
    val pistolAim = KeyFrameLoader(joint, "/data/char/anim/pistol/aim.ang", 0.1f)

    //    val leg = joint.lookup("joint752").get.asInstanceOf[JointAttachment]
    //    val hand = joint.lookup("joint714").get.asInstanceOf[JointAttachment]



    /* val sphere = new TextureGroup(this, "/demo/box/white.png", Gl.TEXTURE0,
    new StaticObject(this, Mod.load("/demo/box/sphere.mod"))
  ) */

    val objects = Array[GlObject](
      TextureGroup(gl, "/data/char/char.png", Gl.GL_TEXTURE0, charObj.toIndexedSeq: _*),

      //new TextureGroup(this, "/demo/t.png", Gl.TEXTURE0,
      TextureGroup(gl, "/data/map/sand.png", Gl.GL_TEXTURE0,
        StaticObject(gl, Model("/data/map/dunes.mod"))
      ),

      TextureGroup(gl, "/data/map/sky.png", Gl.GL_TEXTURE0,
        StaticObject(gl, Model("/data/map/sky.mod"))
      ),

      TextureGroup(gl, "/data/map/sun.png", Gl.GL_TEXTURE0,
        StaticObject(gl, Model("/data/map/sun.mod"))
      )
    )

    val prog = gl.createProgram(
      gl.createShader(Gl.GL_VERTEX_SHADER, GlPrograms.vertex()),
      gl.createShader(Gl.GL_FRAGMENT_SHADER, GlPrograms.fragment(discard = true))
    )

    //char.init(gl)

    //sphere.init(gl)
    val camProg = CameraProgram(prog)

    //joint752 x: -0.031, y: 0.8684, z: -0.0056, rx: 102.311, ry: 1.413, rz: 6.938
    //joint714 x: 0, y: 1.2088, z: 0.3247, rx: 0, ry: 0, rz: 0
    val revolver = JointAttachment(camProg,
      JointAttachment(joint, "joint752", -0.0989f, -0.0934f, 0.0008f, 98.021f, 1.931f, 6.813f),
      JointAttachment(joint, "joint714", -0.1088f, -0.0211f, 0.0357f, 90.786f, -3.232f, -90.099f),
      StaticObject(gl, Model("/data/char/pistol/revolver.mod").scale(0.1f))
    )

    val anim = JoinControl(camCtrl, joint, zero, stand, run.toArray, rotate.toArray,
                            revolver,
                            pistolTakeDown, pistolTakeUp, pistolStand, pistolAim)

    val attachments = TextureGroup(gl, "/data/char/pistol/revolver.png", Gl.GL_TEXTURE0,
      JointAttachment(camProg, joint, "joint752",
        StaticObject(gl, Model("/data/char/pistol/holder.mod").scale(0.1f))
      ), revolver
    )

    Gl.glUseProgram(prog)
    Gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    Gl.glEnable(Gl.GL_DEPTH_TEST)

    //Gl.glPolygonMode(Gl.GL_FRONT_AND_BACK, Gl.GL_LINE)



    (dt:Float) => {
      Gl.glClear(Gl.GL_COLOR_BUFFER_BIT | Gl.GL_DEPTH_BUFFER_BIT)

      anim.apply(dt)
      //char.update(gl)
      //sphere.update(gl)

      camProg.light()

      attachments.update()

      camProg.identity()
      for (o <- objects) o.update()
    }

  }
}
