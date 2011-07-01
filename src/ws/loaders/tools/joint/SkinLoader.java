package ws.loaders.tools.joint;

import ws.ResourceHandle;

import javax.media.j3d.Sound;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class SkinLoader {
    public SkinLoader(){}

    private final HashMap<String, LoadedSkin> cache = new HashMap<String, LoadedSkin>();

    public final LoadedSkin loadSkin(String name, float scale) throws IOException {
        String key = name+"|"+scale;

        LoadedSkin tmp = this.cache.get(key);
        if(tmp != null) return new LoadedSkin(tmp);

        tmp = ResourceHandle.bin ? loadBin(name+".bin", scale) : load(name,scale);

        this.cache.put(key, tmp);
        return tmp;
    }

    public final LoadedSkin loadBin(String name, float scale) throws IOException {
        //System.out.println('b');
        DataInputStream bi = new DataInputStream(ResourceHandle.getInputStream(name));

        //String tmp;
		//int j;

		//tmp = bi.readLine();
		//tmp  = tmp.split("\\=")[1];
		Point3f vertices[] = new Point3f[bi.readInt()];

		//tmp = bi.readLine();
		//tmp = tmp.substring(1, tmp.length() - 1);
		//j = 0;
		for(int i = 0; i < vertices.length; i++){
			//String xyz[] = i.split("\\,");
			vertices[i] = new Point3f(bi.readFloat()*scale,bi.readFloat()*scale,bi.readFloat()*scale);
			//j ++;
		}

		//tmp = bi.readLine();
		//tmp  = tmp.split("\\=")[1];
		TexCoord2f texCoords[] = new TexCoord2f[bi.readInt()];

	//	tmp = bi.readLine();
	// tmp = tmp.substring(1, tmp.length() - 1);
	//	j = 0;
		for(int i = 0; i < texCoords.length; i++){
			//String xyz[] = i.split("\\,");
			texCoords[i] = new TexCoord2f(bi.readFloat(), bi.readFloat());
			//j++;
		}

		// T O D O : normal start
		//tmp = bi.readLine();
		//tmp  = tmp.split("\\=")[1];

		Vector3f normals[] = new Vector3f[bi.readInt()];

		//tmp = bi.readLine();
		///tmp = tmp.substring(1, tmp.length() - 1);

		//j = 0;
		for(int i = 0; i < normals.length; i++){
			//String xyz[] = i.split("\\,");
			normals[i] = new Vector3f(bi.readFloat(), bi.readFloat(), bi.readFloat());
			//j ++;
		}

		// faces
		//tmp = bi.readLine();
		//tmp  = tmp.split("\\=")[1];
		int j = bi.readInt();

		int coordIndexes[] = new int[j*3];
		int textureIndexes[] = new int[j*3];
		int normalIndexes[] = new int[j*3];

		/*tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
		String arr[] = tmp.split("\\}\\{"); */

        HashMap<Integer, ArrayList<Integer>> vertexNormal = new HashMap<Integer, ArrayList<Integer>>();

		for(int i = 0; i < j; i++){
			//arr[i] = arr[i].substring(1, arr[i].length() - 1);
			//String face[] = arr[i].split("\\]\\[");
			for(int k = 0; k < 3; k ++){
			//	System.out.println(face[k]);
			//	String cnt[] = face[k].split("\\,");
				coordIndexes[(i*3)+k] = bi.readInt();
				textureIndexes[(i*3)+k] = bi.readInt();
				normalIndexes[(i*3)+k] = bi.readInt();

				add(vertexNormal, coordIndexes[(i*3)+k], normalIndexes[(i*3)+k]);
			}
		}

		bi.close();

		int vertCount = vertices.length;
		/*if(texCoords.length > vertCount) vertCount = texCoords.length;
		if(normals.length > vertCount) vertCount = normals.length; */

		//IndexedTriangleArray ar = new IndexedTriangleArray(vertCount, IndexedTriangleArray.NORMALS | IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.COORDINATES | IndexedTriangleArray.BY_REFERENCE , j*3);
		//ar.setCapability(IndexedTriangleArray.ALLOW_REF_DATA_WRITE);

		float coords[] = new float[vertices.length*3];

		for(int i = 0; i < vertices.length; i++){
			coords[i*3] = vertices[i].x;
			coords[(i*3)+1] = vertices[i].y;
			coords[(i*3)+2] = vertices[i].z;
		}
        // ar.setCoordRefFloat(coords);

		float texCoordsRef[] = new float[texCoords.length*2];
		for(int i = 0; i < texCoords.length; i++){
			texCoordsRef[i*2] = texCoords[i].x;
			texCoordsRef[(i*2)+1] = texCoords[i].y;
		}
		//ar.setTexCoordRefFloat(0, texCoordsRef);

		float normalRef[] = new float[normals.length*3];
		for(int i = 0; i < normals.length; i++){
			normalRef[i*3] = normals[i].x;
			normalRef[i*3+1] = normals[i].y;
			normalRef[i*3+2] = normals[i].z;
		}
        //ar.setNormalRefFloat(normalRef);

		//ar.setCoordinateIndices(0, coordIndexes);
		//ar.setTextureCoordinateIndices(0, 0, textureIndexes);
		//ar.setNormalIndices(0, normalIndexes);

        //  {// save cache
        return new LoadedSkin(vertCount, j*3, coordIndexes, textureIndexes, normalIndexes, texCoordsRef, coords, normalRef, vertexNormal);
    }

    public final LoadedSkin load(String name, float scale) throws IOException {
		BufferedReader bi = new BufferedReader( new InputStreamReader(ResourceHandle.getInputStream(name)) );
		String tmp;
		int j;
                
		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
		Point3f vertices[] = new Point3f[Integer.parseInt(tmp)];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
		j = 0;
		for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
			vertices[j] = new Point3f(Float.parseFloat(xyz[0])*scale,Float.parseFloat(xyz[1])*scale,Float.parseFloat(xyz[2])*scale);
			j ++;
		}
        
		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
		TexCoord2f texCoords[] = new TexCoord2f[Integer.parseInt(tmp)];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
		j = 0;
		for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
			texCoords[j] = new TexCoord2f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]));
			j++;
		}

		// T O D O : normal start
		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];

		Vector3f normals[] = new Vector3f[Integer.parseInt(tmp)];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);

		j = 0;
		for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
			normals[j] = new Vector3f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]),Float.parseFloat(xyz[2]));
			j ++;
		}

		// faces
		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
		j = Integer.parseInt(tmp);

		int coordIndexes[] = new int[j*3];
		int textureIndexes[] = new int[j*3];
		int normalIndexes[] = new int[j*3];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
		String arr[] = tmp.split("\\}\\{");

        HashMap<Integer, ArrayList<Integer>> vertexNormal = new HashMap<Integer, ArrayList<Integer>>(); 

		for(int i = 0; i < j; i++){
			arr[i] = arr[i].substring(1, arr[i].length() - 1);
			String face[] = arr[i].split("\\]\\[");
			for(int k = 0; k < 3; k ++){
			//	System.out.println(face[k]);
				String cnt[] = face[k].split("\\,");
				coordIndexes[(i*3)+k] = Integer.parseInt(cnt[0]);
				textureIndexes[(i*3)+k] = Integer.parseInt(cnt[1]);
				normalIndexes[(i*3)+k] = Integer.parseInt(cnt[2]);

				add(vertexNormal, coordIndexes[(i*3)+k], normalIndexes[(i*3)+k]);
			}
		}

		bi.close();
        
		int vertCount = vertices.length;
		/*if(texCoords.length > vertCount) vertCount = texCoords.length;
		if(normals.length > vertCount) vertCount = normals.length; */

		//IndexedTriangleArray ar = new IndexedTriangleArray(vertCount, IndexedTriangleArray.NORMALS | IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.COORDINATES | IndexedTriangleArray.BY_REFERENCE , j*3);
		//ar.setCapability(IndexedTriangleArray.ALLOW_REF_DATA_WRITE);

		float coords[] = new float[vertices.length*3];

		for(int i = 0; i < vertices.length; i++){
			coords[i*3] = vertices[i].x;
			coords[(i*3)+1] = vertices[i].y;
			coords[(i*3)+2] = vertices[i].z;
		}
        // ar.setCoordRefFloat(coords);

		float texCoordsRef[] = new float[texCoords.length*2];
		for(int i = 0; i < texCoords.length; i++){
			texCoordsRef[i*2] = texCoords[i].x;
			texCoordsRef[(i*2)+1] = texCoords[i].y;
		}
		//ar.setTexCoordRefFloat(0, texCoordsRef);

		float normalRef[] = new float[normals.length*3];
		for(int i = 0; i < normals.length; i++){
			normalRef[i*3] = normals[i].x;
			normalRef[i*3+1] = normals[i].y;
			normalRef[i*3+2] = normals[i].z;
		}
        //ar.setNormalRefFloat(normalRef);
        
		//ar.setCoordinateIndices(0, coordIndexes);
		//ar.setTextureCoordinateIndices(0, 0, textureIndexes);
		//ar.setNormalIndices(0, normalIndexes);

        //  {// save cache
        return new LoadedSkin(vertCount, j*3, coordIndexes, textureIndexes, normalIndexes, texCoordsRef, coords, normalRef, vertexNormal);
        //    this.cache.put(name, ls);
            //return ls;
        //}
	}

    private final void add(HashMap<Integer, ArrayList<Integer>> vertexNormal, Integer vertex, Integer normal){
		ArrayList<Integer> ver = vertexNormal.get(vertex);
		if(ver == null){
			ver = new ArrayList<Integer>();
			vertexNormal.put(vertex, ver);
		}
		ver.add(normal);
	}

}
