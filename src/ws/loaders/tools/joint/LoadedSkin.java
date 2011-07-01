package ws.loaders.tools.joint;

import ws.joint.Skin;

import javax.media.j3d.IndexedTriangleArray;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;

public final class LoadedSkin {

    private final int vertCount;
    private final int indexCount;
    private final float texCoordsRef[];

    private final int coordIndexes[];
	private final int textureIndexes[];
	private final int normalIndexes[];

    private IndexedTriangleArray array = null;
    public final IndexedTriangleArray getGeometry(){
        if(array == null){
            /*int tmp = coords.length > texCoordsRef.length ? coords.length : texCoordsRef.length;
            tmp = tmp > normals.length ? tmp : normals.length;

            int t = coordIndexes.length > textureIndexes.length ? coordIndexes.length : textureIndexes.length;
            t = t > normalIndexes.length ? t : normalIndexes.length;

           // t = 7500;

            array = new IndexedTriangleArray( tmp , IndexedTriangleArray.USE_COORD_INDEX_ONLY | IndexedTriangleArray.NORMALS | IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.COORDINATES | IndexedTriangleArray.BY_REFERENCE , t); */
            array = new IndexedTriangleArray(vertCount, IndexedTriangleArray.NORMALS | IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.COORDINATES | IndexedTriangleArray.BY_REFERENCE , indexCount);
            array.setCapability(IndexedTriangleArray.ALLOW_REF_DATA_WRITE);

            array.setCoordRefFloat(coords);
            array.setTexCoordRefFloat(0, texCoordsRef);
            array.setNormalRefFloat(normals);

            array.setCoordinateIndices(0, coordIndexes);
		    array.setTextureCoordinateIndices(0, 0, textureIndexes);
		    array.setNormalIndices(0, normalIndexes);
        }
        return this.array;
    }

    private final float coords[];
    private final float normals[];

    private final Point3f vertices[];
	private final Vector3f verticesNormals[];

    public LoadedSkin(int vertCount, int indexCount, int coordIndexes[], int textureIndexes[], int normalIndexes[], float texCoordsRef[], float[] coords, float[] normals, HashMap<Integer, ArrayList<Integer>> vertexNormal) {
        this.vertCount = vertCount;
        this.indexCount = indexCount;
        this.vertexNormal = vertexNormal;
        this.texCoordsRef = texCoordsRef;
        this.coordIndexes = coordIndexes;
        this.textureIndexes = textureIndexes;
        this.normalIndexes = normalIndexes;

        this.coords = coords;
        this.normals = normals;

        vertices = new Point3f[coords.length / 3];
        verticesNormals = new Vector3f[normals.length / 3];

        for(int i = 0; i < vertices.length; i++) vertices[i] = new Point3f(coords[i*3],coords[(i*3)+1],coords[(i*3)+2]);
        for(int i = 0; i < verticesNormals.length; i++) verticesNormals[i] = new Vector3f(normals[i*3],normals[(i*3)+1],normals[(i*3)+2]);        
    }

    public LoadedSkin(LoadedSkin ls){
        this.vertCount = ls.vertCount;
        this.indexCount = ls.indexCount;
        this.texCoordsRef = ls.texCoordsRef;

        this.coordIndexes = ls.coordIndexes;
        this.textureIndexes = ls.textureIndexes;
        this.normalIndexes = ls.normalIndexes;

        this.vertexNormal = ls.vertexNormal;

        this.coords = new float[ls.coords.length];
        System.arraycopy(ls.coords,0, this.coords, 0, ls.coords.length);
        this.normals = new float[ls.normals.length];
        System.arraycopy(ls.normals,0, this.normals, 0, ls.normals.length);


        vertices = new Point3f[ls.vertices.length];
        for(int i = 0; i < vertices.length; i++) vertices[i] = new Point3f(ls.vertices[i]);
        verticesNormals = new Vector3f[ls.verticesNormals.length];
        for(int i = 0; i < verticesNormals.length; i++) verticesNormals[i] = new Vector3f(ls.verticesNormals[i]);
    }

    public final Point3f getPoint(int ind){
        return this.vertices[ind];
    }

    public final Vector3f getVector(int ind){
        return this.verticesNormals[ind];
    }

    private Skin s = null; //IndexedTriangleArray array, float coords[], float normals[], Point3f vertices[], Vector3f verticesNormals[]

    public final Skin getSkin(){
        if(s == null) s = new Skin(getGeometry(), coords, normals, vertices, verticesNormals);
        return s;
    }

	private final HashMap<Integer, ArrayList<Integer>> vertexNormal; // = new HashMap<Integer, ArrayList<Integer>>();
    
	public final ArrayList<Integer> getNormals(Integer vertex){
        return vertexNormal.get(vertex);
	}
    
}
