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
import java.util.Arrays;

public final class MeshOpt {

    public final MeshData getMeshData(Point3f points[], Vector3f normals[], TexCoord2f textCoorg[][], int pointIndexes[], int normalIndexes[], int textureIndexes[][]){
        ArrayList<Vertex> vertData = new ArrayList<Vertex>();
        int projected[] = new int[pointIndexes.length];

        for (int i = 0; i < projected.length; i++){
            Point3f p = points[pointIndexes[i]];
            Vector3f n = normals[normalIndexes[i]];
            TexCoord2f tc[] = new TexCoord2f[textCoorg.length];
            for(int k = 0;  k < textCoorg.length; k++){
                tc[k] = textCoorg[k][ textureIndexes[k][i] ];
            }
            Vertex v = new Vertex(p, n, tc);
            int ind = vertData.indexOf(v);
            if(ind == -1){
                ind = vertData.size();
                vertData.add(v);
            }
            projected[i] = ind;
        }

        IntBuffer indexBuffer = BufferUtils.createIntBuffer(projected.length);
        indexBuffer.put(projected);

        FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(vertData.size());
        FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(vertData.size());
        FloatBuffer textureBuffer[] = new FloatBuffer[textCoorg.length]; //;(coordIndexes.length/3);
        for(int k = 0;  k < textCoorg.length; k++) textureBuffer[k] = BufferUtils.createVector2Buffer(vertData.size());

        for(Vertex v : vertData){
            vertexBuffer.put(v.p.x).put(v.p.y).put(v.p.z);
            normalBuffer.put(v.v.x).put(v.v.y).put(v.v.z);
            for(int k = 0;  k < textCoorg.length; k++) textureBuffer[k].put(v.tc[k].x).put(v.tc[k].y);
        }

        MeshData m = new MeshData();

        m.setVertexBuffer(vertexBuffer);
        m.setNormalBuffer(normalBuffer);
        for(int k = 0;  k < textCoorg.length; k++)  m.setTextureBuffer(textureBuffer[k], k);

        m.setIndexBuffer(indexBuffer);
        m.setIndexMode(IndexMode.Triangles);
        //System.out.println(pointIndexes.length+" "+vertData.size());

        return m;
    }

    private final class Vertex{
        private final Point3f p;
        private final Vector3f v;
        private final TexCoord2f tc[];

        private Vertex(Point3f p, Vector3f v, TexCoord2f[] tc) {
            this.p = p;
            this.v = v;
            this.tc = tc;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            if (!p.equals(vertex.p)) return false;
            if (!Arrays.equals(tc, vertex.tc)) return false;
            if (!v.equals(vertex.v)) return false;

            return true;
        }

        @Override
        public final int hashCode() {
            int result = p.hashCode();
            result = 31 * result + v.hashCode();
            result = 31 * result + Arrays.hashCode(tc);
            return result;
        }
    }

}
