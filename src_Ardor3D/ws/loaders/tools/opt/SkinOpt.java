package ws.loaders.tools.opt;

import com.ardor3d.renderer.IndexMode;
import com.ardor3d.scenegraph.MeshData;
import com.ardor3d.util.geom.BufferUtils;

import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public final class SkinOpt {

    private final ArrayList<Vertex> vertData = new ArrayList<Vertex>();
    private final int projected[]; // = new int[pointIndexes.length];

    public SkinOpt(Point3f points[], Vector3f normals[], TexCoord2f textCoorg[], int pointIndexes[], int normalIndexes[], int textureIndexes[]){
        projected = new int[pointIndexes.length];

        for (int i = 0; i < projected.length; i++){
            Point3f p = points[pointIndexes[i]];
            Vector3f n = normals[normalIndexes[i]];
            TexCoord2f tc =  textCoorg[ textureIndexes[i] ];

            Vertex v = new Vertex(p, n, tc, pointIndexes[i], normalIndexes[i]);
            int ind = vertData.indexOf(v);
            if(ind == -1){
                ind = vertData.size();
                vertData.add(v);
            }
            projected[i] = ind;
        }

        vertIndex = new int[vertData.size()];
        normalIndex = new int[vertData.size()];

        int i = 0;
        for(Vertex v : vertData){
            vertIndex[i] = v.pointIndex;
            normalIndex[i] = v.normalIndex;
            i++;
        }

        //System.out.println(pointIndexes.length+" "+vertData.size());
    }

    //private final MeshData m;
    private final int[] vertIndex;
    private final int[] normalIndex;

    public final MeshData getMeshData() {
      //  return m;
        MeshData m = new MeshData();

        IntBuffer indexBuffer = BufferUtils.createIntBuffer(projected.length);
        indexBuffer.put(projected);

        FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(vertData.size());
        FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(vertData.size());
        FloatBuffer textureBuffer = BufferUtils.createVector2Buffer(vertData.size()); //new FloatBuffer[textCoorg.length]; //;(coordIndexes.length/3);
        //for(int k = 0;  k < textCoorg.length; k++) textureBuffer[k] =
        for(Vertex v : vertData){
            vertexBuffer.put(v.p.x).put(v.p.y).put(v.p.z);
            normalBuffer.put(v.v.x).put(v.v.y).put(v.v.z);
            textureBuffer.put(v.tc.x).put(v.tc.y);
        }

        m = new MeshData();

        m.setVertexBuffer(vertexBuffer);
        m.setNormalBuffer(normalBuffer);
        m.setTextureBuffer(textureBuffer, 0);

        m.setIndexBuffer(indexBuffer);
        m.setIndexMode(IndexMode.Triangles);

        return m;
    }

    public final int[] getVertIndex() {
        return vertIndex;
    }

    public final int[] getNormalIndex() {
        return normalIndex;
    }

    private final class Vertex{
        private final int pointIndex;
        private final int normalIndex;

        private final Point3f p;
        private final Vector3f v;
        private final TexCoord2f tc;

        private Vertex(Point3f p, Vector3f v, TexCoord2f tc, int pointIndex, int normalIndex) {
            this.p = p;
            this.v = v;
            this.tc = tc;

            this.pointIndex = pointIndex;
            this.normalIndex = normalIndex;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            if (!p.equals(vertex.p)) return false;
            if (!tc.equals(vertex.tc)) return false;
            if (!v.equals(vertex.v)) return false;

            return true;
        }

        @Override
        public final int hashCode() {
            int result = p.hashCode();
            result = 31 * result + v.hashCode();
            result = 31 * result + tc.hashCode();
            return result;
        }
    }

}
