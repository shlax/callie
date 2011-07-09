package ws.loaders.tools.joint;

import com.ardor3d.renderer.IndexMode;
import com.ardor3d.scenegraph.MeshData;
import com.ardor3d.util.geom.BufferUtils;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.TexCoord2f;
import org.openmali.vecmath2.Vector3f;
import ws.joint.Skin;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public final class LoadedSkin {

   private final int coordIndexes[];
   private final int normalIndexes[];
   private final int textureIndexes[];

    private final Point3f vertices[];
    private final Vector3f verticesNormals[];
    private final TexCoord2f texCoords[];

    private MeshData array = null;
    public final MeshData getGeometry(){
        if(array == null){
            /*int tmp = coords.length > texCoordsRef.length ? coords.length : texCoordsRef.length;
            tmp = tmp > normals.length ? tmp : normals.length;

            int t = coordIndexes.length > textureIndexes.length ? coordIndexes.length : textureIndexes.length;
            t = t > normalIndexes.length ? t : normalIndexes.length;

           // t = 7500;

            array = new IndexedTriangleArray( tmp , IndexedTriangleArray.USE_COORD_INDEX_ONLY | IndexedTriangleArray.NORMALS | IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.COORDINATES | IndexedTriangleArray.BY_REFERENCE , t); */
            /* array = new IndexedTriangleArray(vertCount, IndexedTriangleArray.NORMALS | IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.COORDINATES | IndexedTriangleArray.BY_REFERENCE , indexCount);
            array.setCapability(IndexedTriangleArray.ALLOW_REF_DATA_WRITE);

            array.setCoordRefFloat(coords);
            array.setTexCoordRefFloat(0, texCoordsRef);
            array.setNormalRefFloat(normals);

            array.setCoordinateIndices(0, coordIndexes);
		    array.setTextureCoordinateIndices(0, 0, textureIndexes);
		    array.setNormalIndices(0, normalIndexes); */
            IntBuffer indexBuffer = BufferUtils.createIntBuffer(coordIndexes.length);

            FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(coordIndexes.length/3);
            FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(coordIndexes.length/3);
            FloatBuffer textureBuffer = BufferUtils.createVector2Buffer(coordIndexes.length/3);

            for (int i = 0; i < coordIndexes.length; i++){
                {
                    Point3f t = vertices[coordIndexes[i]];
                    vertexBuffer.put(t.x()).put(t.y()).put(t.z());
                }{
                    Vector3f t = verticesNormals[normalIndexes[i]];
                    normalBuffer.put(t.x()).put(t.y()).put(t.z());
                }{
                    TexCoord2f t = texCoords[ textureIndexes[i] ];
                    textureBuffer.put(t.getS()).put(t.getT());
                }
                indexBuffer.put(i);
            }

            MeshData m = new MeshData();

            m.setVertexBuffer(vertexBuffer);
            m.setNormalBuffer(normalBuffer);
            m.setTextureBuffer(textureBuffer, 0);

            m.setIndexBuffer(indexBuffer);
            m.setIndexMode(IndexMode.Triangles);

            return m;
        }
        return this.array;
    }

    public LoadedSkin(Point3f points[], Vector3f normals[], TexCoord2f texCoords[], int coordIndexes[], int normalIndexes[], int textureIndexes[],  HashMap<Integer, ArrayList<Integer>> vertexNormal) {
        this.vertexNormal = vertexNormal;

        this.vertices = points;
        this.verticesNormals = normals;
        this.texCoords = texCoords;

        this.coordIndexes = coordIndexes;
        this.normalIndexes = normalIndexes;
        this.textureIndexes = textureIndexes;

        //for(int i = 0; i < vertices.length; i++) vertices[i] = new Point3f(points[i]);
        //for(int i = 0; i < verticesNormals.length; i++) verticesNormals[i] = new Vector3f(normals[i]);
    }

    public LoadedSkin(LoadedSkin ls){
        this.vertexNormal = ls.vertexNormal;

        this.coordIndexes = ls.coordIndexes;
        this.normalIndexes = ls.normalIndexes;
        this.textureIndexes = ls.textureIndexes;

        this.texCoords = ls.texCoords;
        this.vertices = ls.vertices;
        this.verticesNormals = ls.verticesNormals;
    }

    public final Point3f getPoint(int ind){
        return this.vertices[ind];
    }

    public final Vector3f getVector(int ind){
        return this.verticesNormals[ind];
    }

    private Skin s = null; //IndexedTriangleArray array, float coords[], float normals[], Point3f vertices[], Vector3f verticesNormals[]

    public final Skin getSkin(){
        if(s == null) s = new Skin(getGeometry(), coordIndexes, normalIndexes, vertices, verticesNormals);
        return s;
    }

	private final HashMap<Integer, ArrayList<Integer>> vertexNormal; // = new HashMap<Integer, ArrayList<Integer>>();
    
	public final ArrayList<Integer> getNormals(Integer vertex){
        return vertexNormal.get(vertex);
	}
    
}
