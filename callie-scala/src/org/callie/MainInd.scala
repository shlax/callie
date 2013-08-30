package org.callie

import org.callie.jogl.{JoglFrame, GL4EventListener}
import javax.media.opengl.GL4
import org.callie.jogl.GL_4

object MainInd extends App{

  val vertex =
    """
      |#version 400
      |
      |layout(location = 0) in vec4 position;
      |void main(){
      |    gl_Position = position;
      |}
    """.stripMargin
  
  val fragment =
    """
      |#version 400
      |
      |out vec4 outputColor;
      |void main(){
      |   outputColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);
      |}
    """.stripMargin
  
  val positions = Array(
       0.75f, -0.75f, 0.0f, 1.0f,
       0.75f,  0.75f, 0.0f, 1.0f,
      -0.75f, -0.75f, 0.0f, 1.0f
    )
  
  val indices = Array(
      0, 1, 2
    )
    
  JoglFrame(new GL4EventListener(){
    override def initGL4(implicit gl : GL4){
      val p = createProgram(createShader(GL_4.VERTEX_SHADER, vertex), 
                            createShader(GL_4.FRAGMENT_SHADER, fragment))
      // > code
      
      // < code      
      gl.glUseProgram(p)
      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    }
    override def displayGL4(implicit gl : GL4){
      gl.glClear(GL_4.COLOR_BUFFER_BIT)
      // > code
      
      // < code      
    }
  })
  
}

/*

// A four vertices
static const GLfloat vertex_positions[] =
{
-1.0f, -1.0f, 0.0f, 1.0f,
1.0f, -1.0f, 0.0f, 1.0f,
-1.0f, 1.0f, 0.0f, 1.0f,
-1.0f, -1.0f, 0.0f, 1.0f,
};

// Color for each vertex
static const GLfloat vertex_colors[] =
{
1.0f, 1.0f, 1.0f, 1.0f,
1.0f, 1.0f, 0.0f, 1.0f,
1.0f, 0.0f, 1.0f, 1.0f,
0.0f, 1.0f, 1.0f, 1.0f
};
// Three indices (we’re going to draw one triangle at a time
static const GLushort vertex_indices[] =
{
0, 1, 2
};
// Set up the element array buffer
glGenBuffers(1, ebo);
glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo[0]);
glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(vertex_indices), vertex_indices, GL_STATIC_DRAW);

// Set up the vertex attributes
glGenVertexArrays(1, vao);
glBindVertexArray(vao[0]);
glGenBuffers(1, vbo);
glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
glBufferData(GL_ARRAY_BUFFER,sizeof(vertex_positions) + sizeof(vertex_colors),NULL, GL_STATIC_DRAW);
glBufferSubData(GL_ARRAY_BUFFER, 0,sizeof(vertex_positions), vertex_positions);
glBufferSubData(GL_ARRAY_BUFFER,sizeof(vertex_positions), sizeof(vertex_colors),vertex_colors);

// DrawArrays
model_matrix = vmath::translation(-3.0f, 0.0f, -5.0f);
glUniformMatrix4fv(render_model_matrix_loc, 4, GL_FALSE, model_matrix);
glDrawArrays(GL_TRIANGLES, 0, 3);

// DrawElements
model_matrix = vmath::translation(-1.0f, 0.0f, -5.0f);
glUniformMatrix4fv(render_model_matrix_loc, 4, GL_FALSE, model_matrix);
glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, NULL);

// DrawElementsBaseVertex
model_matrix = vmath::translation(1.0f, 0.0f, -5.0f);
glUniformMatrix4fv(render_model_matrix_loc, 4, GL_FALSE, model_matrix);
glDrawElementsBaseVertex(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, NULL, 1);

// DrawArraysInstanced
model_matrix = vmath::translation(3.0f, 0.0f, -5.0f);
glUniformMatrix4fv(render_model_matrix_loc, 4, GL_FALSE, model_matrix);
glDrawArraysInstanced(GL_TRIANGLES, 0, 3, 1);

*/
