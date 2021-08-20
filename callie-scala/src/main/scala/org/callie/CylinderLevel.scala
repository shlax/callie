package org.callie

import org.callie.control.CylinderMoving
import org.callie.input.{Camera, CameraProgram}
import org.callie.map.Map3D
import org.callie.model.{GlObject, Model, StaticObject, TextureGroup}
import org.callie.ogl.{Gl, GlEventListener, LwglFrame}

object CylinderLevel extends App {
  LwglFrame { gl: GlEventListener =>
    val moving = new CylinderMoving(Map3D.load("/demo/cylinder/cylinder.map"), 1.65f)

    Camera.lookAt(moving)

    val prog = gl.createProgram(
      gl.createShader(Gl.GL_VERTEX_SHADER, GlPrograms.vertex()),
      gl.createShader(Gl.GL_FRAGMENT_SHADER, GlPrograms.fragment(discard = true))
    )

    val camProg = CameraProgram(prog)

    val objects = Array[GlObject](
      TextureGroup(gl, "/demo/t.png", Gl.GL_TEXTURE0,
        StaticObject(gl, Model("/demo/cylinder/cylinder.mod"))
      )
    )

    Gl.glUseProgram(prog)
    Gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    Gl.glEnable(Gl.GL_DEPTH_TEST)

    (dt:Float) => {
      Gl.glClear(Gl.GL_COLOR_BUFFER_BIT | Gl.GL_DEPTH_BUFFER_BIT)
      Camera.update()
      camProg.light()

      moving.apply(dt)

      camProg.update()
      for (o <- objects) o.update()
    }
  }
}
