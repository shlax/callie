package ws.joint;


import com.ardor3d.scenegraph.MeshData;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.nio.FloatBuffer;

public final class Skin /*implements GeometryUpdater*/{

	private final MeshData mesh;
    private final int coordIndexes[];
    private final int normalIndexes[];
	//private final float coords[];
	//private final float normals[];

	/* public IndexedTriangleArray getIndexedTriangleArray(){
		return this.array;
	} */

	private final Point3f vertices[];
	private final Vector3f verticesNormals[];

	/* public final Point3f getPoint(int ind){
		return this.vertices[ind];
	}

	public final Vector3f getVector(int ind){
		return this.verticesNormals[ind];
	} */

	public Skin(MeshData mesh, int coordIndexes[], int normalIndexes[], Point3f vertices[], Vector3f verticesNormals[]) {
        this.mesh = mesh;
		this.coordIndexes = coordIndexes;
		this.normalIndexes = normalIndexes;

        this.vertices = vertices;
		this.verticesNormals = verticesNormals;

		/* vertices = new Point3f[coords.length / 3];
		verticesNormals = new Vector3f[normals.length / 3];

		for(int i = 0; i < vertices.length; i++) vertices[i] = new Point3f(coords[i*3],coords[(i*3)+1],coords[(i*3)+2]);
		for(int i = 0; i < verticesNormals.length; i++) verticesNormals[i] = new Vector3f(normals[i*3],normals[(i*3)+1],normals[(i*3)+2]); */
	}

	//public final void update(){
		//array.updateData(this);
        //mesh.updateModelBound();
	//}

	//@Override
	public final void updateData(/*Geometry geometry*/) {
		FloatBuffer storeVerts = mesh.getVertexBuffer();
        storeVerts.rewind();

        FloatBuffer storeNorms = mesh.getNormalBuffer();
        storeNorms.rewind();

        for(int i = 0; i < coordIndexes.length; i++){
            //coords[i*3] = vertices[i].x;
			//coords[(i*3)+1] = vertices[i].y;
			//coords[(i*3)+2] = vertices[i].z;
            Point3f p = vertices[coordIndexes[i]];
            // (-0.62220794, 1.3952314, 0.0975226)
            // (-1.680661, 0.5123323, -0.06272988)
        //    if(coordIndexes[i] == 1169) System.out.println(p);
            storeVerts.put(p.x).put(p.y).put(p.z);
		}

		for(int i = 0; i < normalIndexes.length; i++){
			/* normals[i*3] = verticesNormals[i].x;
			normals[(i*3)+1] = verticesNormals[i].y;
			normals[(i*3)+2] = verticesNormals[i].z; */
            Vector3f v = verticesNormals[normalIndexes[i]];
            storeNorms.put(v.x).put(v.y).put(v.z);
		}

        mesh.getVertexCoords().setNeedsRefresh(true);
        mesh.getNormalCoords().setNeedsRefresh(true);


        //System.out.println("a");
	}

}

