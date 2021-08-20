package org.callie.input

import org.callie.math.Matrix4
import org.callie.ogl.Gl

object CameraProgram{
  val identityMatrix = Matrix4().toArray
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

  val vectorArray = CameraProgram.vectorArray
  val lightDirection = Camera.lightDirection

  def light() : Unit= {
    lightDirection.toArray(vectorArray)
    Gl.glUniform3fv(lightDirectionVec, vectorArray)
  }

  val matrixArray = CameraProgram.matrixArray
  val modMat = Camera.modMat

  def update(model:Matrix4, norm:Matrix4):Unit={
    norm.toArray(matrixArray)
    Gl.glUniformMatrix4fv(normal, true, matrixArray)

    modMat.mul(model, matrixArray)
    Gl.glUniformMatrix4fv(view, true, matrixArray)
  }

  val identityMatrix = CameraProgram.identityMatrix

  def identity():Unit={
    Gl.glUniformMatrix4fv(normal, true, identityMatrix)

    modMat.toArray(matrixArray)
    Gl.glUniformMatrix4fv(view, true, matrixArray)
  }

  def identity(norm:Matrix4):Unit={
    norm.toArray(matrixArray)
    Gl.glUniformMatrix4fv(normal, true, matrixArray)

    modMat.toArray(matrixArray)
    Gl.glUniformMatrix4fv(view, true, matrixArray)
  }

}
