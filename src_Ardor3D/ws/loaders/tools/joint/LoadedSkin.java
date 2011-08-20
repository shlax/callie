package ws.loaders.tools.joint;

import com.ardor3d.scenegraph.MeshData;
import ws.joint.Skin;
import ws.loaders.tools.opt.SkinOpt;

import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;

public final class LoadedSkin {

   /*private final int coordIndexes[];
   private final int normalIndexes[];
   private final int textureIndexes[]; */

    private final Point3f vertices[];
    private final Vector3f verticesNormals[];
    //private final TexCoord2f texCoords[];

    public LoadedSkin(Point3f points[], Vector3f normals[], TexCoord2f texCoords[], int coordIndexes[], int normalIndexes[], int textureIndexes[],  HashMap<Integer, ArrayList<Integer>> vertexNormal) {
        this.vertexNormal = vertexNormal;

        this.vertices = points;
        this.verticesNormals = normals;
        /* this.texCoords = texCoords;

        this.coordIndexes = coordIndexes;
        this.normalIndexes = normalIndexes;
        this.textureIndexes = textureIndexes; */

        skinOpt = new SkinOpt(vertices, verticesNormals, texCoords, coordIndexes, normalIndexes, textureIndexes);

        //for(int i = 0; i < vertices.length; i++) vertices[i] = new Point3f(points[i]);
        //for(int i = 0; i < verticesNormals.length; i++) verticesNormals[i] = new Vector3f(normals[i]);
    }

    public LoadedSkin(LoadedSkin ls){
        this.vertexNormal = ls.vertexNormal;

        /*this.coordIndexes = ls.coordIndexes;
        this.normalIndexes = ls.normalIndexes;
        this.textureIndexes = ls.textureIndexes;

        this.texCoords = ls.texCoords; */

        this.vertices = new Point3f[ls.vertices.length];
        this.verticesNormals = new Vector3f[ls.verticesNormals.length];

        this.skinOpt = ls.skinOpt;

        for(int i = 0; i < vertices.length; i++) vertices[i] = new Point3f(ls.vertices[i]);
        for(int i = 0; i < verticesNormals.length; i++) verticesNormals[i] = new Vector3f(ls.verticesNormals[i]);
    }

    private final SkinOpt skinOpt;

    private Skin s = null;
    public final Skin getSkin(){
        //if(skinOpt == null) skinOpt = new SkinOpt(vertices, verticesNormals, texCoords, coordIndexes, normalIndexes, textureIndexes);
        if(s == null) s = new Skin(getGeometry(), skinOpt.getVertIndex(), skinOpt.getNormalIndex(), vertices, verticesNormals);
        return s;
    }

    private MeshData array = null;
    public final MeshData getGeometry(){
        if(array == null) array = skinOpt.getMeshData();
        //if(skinOpt == null) skinOpt = new SkinOpt(vertices, verticesNormals, texCoords, coordIndexes, normalIndexes, textureIndexes);
        return array;
    }

	private final HashMap<Integer, ArrayList<Integer>> vertexNormal; // = new HashMap<Integer, ArrayList<Integer>>();
	public final ArrayList<Integer> getNormals(Integer vertex){
        return vertexNormal.get(vertex);
	}

    public final Point3f getPoint(int ind){
        return this.vertices[ind];
    }

    public final Vector3f getVector(int ind){
        return this.verticesNormals[ind];
    }

}
