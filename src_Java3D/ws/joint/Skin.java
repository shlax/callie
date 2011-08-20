package ws.joint;

import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryUpdater;
import javax.media.j3d.IndexedTriangleArray;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public final class Skin implements GeometryUpdater{

	private final IndexedTriangleArray array;
	private final float coords[];
	private final float normals[];
	
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

	public Skin(IndexedTriangleArray array, float coords[], float normals[], Point3f vertices[], Vector3f verticesNormals[]) {
        this.array = array;
		this.coords = coords;
		this.normals = normals;

        this.vertices = vertices;
		this.verticesNormals = verticesNormals;
                
		/* vertices = new Point3f[coords.length / 3];
		verticesNormals = new Vector3f[normals.length / 3];

		for(int i = 0; i < vertices.length; i++) vertices[i] = new Point3f(coords[i*3],coords[(i*3)+1],coords[(i*3)+2]);
		for(int i = 0; i < verticesNormals.length; i++) verticesNormals[i] = new Vector3f(normals[i*3],normals[(i*3)+1],normals[(i*3)+2]); */
	}

	public final void update(){
		array.updateData(this);
	}

	@Override
	public final void updateData(Geometry geometry) {
		for(int i = 0; i < vertices.length; i++){
			coords[i*3] = vertices[i].x;
			coords[(i*3)+1] = vertices[i].y;
			coords[(i*3)+2] = vertices[i].z;
		}
		for(int i = 0; i < verticesNormals.length; i++){
			normals[i*3] = verticesNormals[i].x;
			normals[(i*3)+1] = verticesNormals[i].y;
			normals[(i*3)+2] = verticesNormals[i].z;
		}
	}

}

