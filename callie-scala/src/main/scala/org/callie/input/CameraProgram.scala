package org.callie.input

import org.callie.math.Matrix4
import org.callie.ogl.Gl

object CameraProgram{
  val matrixArray = new Array[Float](16)
  val vectorArray = new Array[Float](3)

  def apply(id: Int, view:String="viewMatrix", normal:String = "normalMatrix", lightDirection:String="lightDirection"):CameraProgram={
    val viewMatrix = Gl.glGetUniformLocation(id, view)
    val normalMatrix = Gl.glGetUniformLocation(id, normal)
    val lightDirectionVec = Gl.glGetUniformLocation(id, lightDirection)
    new CameraProgram(viewMatrix, normalMatrix, lightDirectionVec)
  }
}

class CameraProgram(view: Int, normal:Int, lightDirectionVec:Int){

  def light() : Unit= {
    Camera.lightDirection.toArray(CameraProgram.vectorArray)
    Gl.glUniform3fv(lightDirectionVec, CameraProgram.vectorArray)
  }

  def update(model:Matrix4, norm:Matrix4):Unit={
    Camera.norMat.mul(norm, CameraProgram.matrixArray)
    Gl.glUniformMatrix4fv(normal, true, CameraProgram.matrixArray)

    Camera.modMat.mul(model, CameraProgram.matrixArray)
    Gl.glUniformMatrix4fv(view, true, CameraProgram.matrixArray)
  }


  def update():Unit={
    Camera.norMat.toArray(CameraProgram.matrixArray)
    Gl.glUniformMatrix4fv(normal, true, CameraProgram.matrixArray)

    Camera.modMat.toArray(CameraProgram.matrixArray)
    Gl.glUniformMatrix4fv(view, true, CameraProgram.matrixArray)
  }

}
