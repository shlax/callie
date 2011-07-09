package ws.loaders.tools;

import com.ardor3d.renderer.IndexMode;
import com.ardor3d.scenegraph.MeshData;
import com.ardor3d.util.geom.BufferUtils;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.TexCoord2f;
import org.openmali.vecmath2.Vector3f;
import ws.ResourceHandle;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

public final class GeometryLoader {

    private final HashMap<String,MeshData> map = new HashMap<String,MeshData>();

    public final MeshData getIndexedTriangleArray(String f) throws IOException {
        MeshData ret = map.get(f);
        if(ret == null){
            ret = ResourceHandle.bin ? loadBin(f+".bin") : load(f);
            map.put(f, ret);
        }
        return ret;
    }

    private final MeshData loadBin(String f) throws IOException {
   //     System.out.println('a');
        DataInputStream bi = new DataInputStream(ResourceHandle.getInputStream(f));

        boolean mto = f.endsWith(".mto.bin");
        //System.out.println(mto);

		//String tmp;
		//int j;

		//tmp = bi.readLine();
		//tmp  = tmp.split("\\=")[1];

		Point3f vertices[] = new Point3f[bi.readInt()];

	//	tmp = bi.readLine();
	//	tmp = tmp.substring(1, tmp.length() - 1);
	//	j = 0;
		for(int i = 0; i < vertices.length; i++){
			//String xyz[] = i.split("\\,");
			vertices[i] = new Point3f(bi.readFloat(), bi.readFloat(), bi.readFloat());
			//j ++;
		}

		//for(Point3f r : vertices)System.out.println(r);

        int coordCount = 1;

        TexCoord2f texCoords[][];
        //ArrayList<TexCoord2f> coordList[] = null;

        if(mto){
           // tmp = bi.readLine();
		   // tmp  = tmp.split("\\=")[1];
		    coordCount = bi.readInt(); //Integer.parseInt(tmp);
            texCoords = new TexCoord2f[coordCount][];
            //coordList = new ArrayList[coordCount];
            //for(int i = 0; i < coordCount; i++) coordList[i] = new ArrayList<TexCoord2f>();
        }else {
            texCoords = new TexCoord2f[1][];
            //texCoords[0] = texCoordsAll;
        }

        for(int l = 0; l < coordCount; l++){
            //tmp = bi.readLine();
		    //tmp  = tmp.split("\\=")[1];

            texCoords[l] = new TexCoord2f[ bi.readInt() ];

           // tmp = bi.readLine();
           // tmp = tmp.substring(1, tmp.length() - 1);

           // j = 0;
            for(int i = 0; i < texCoords[l].length; i++){
                //String xyz[] = i.split("\\,");
                texCoords[l][i] = new TexCoord2f(bi.readFloat(), bi.readFloat());
                //j++;
            }
        }

		//for(TexCoord2f r : texCoords)System.out.println(r);

	//	tmp = bi.readLine();
	//	tmp  = tmp.split("\\=")[1];

		Vector3f normals[] = new Vector3f[bi.readInt()];

	//	tmp = bi.readLine();
	//	tmp = tmp.substring(1, tmp.length() - 1);

	//	j = 0;
		for(int i = 0; i < normals.length; i++){
		//	String xyz[] = i.split("\\,");
			normals[i] = new Vector3f(bi.readFloat(), bi.readFloat(), bi.readFloat());
		//	j++;
		}

		//for(Vector3f r : normals)System.out.println(r);





	//	tmp = bi.readLine();
	//	tmp  = tmp.split("\\=")[1];
		int j = bi.readInt();

		int coordIndexes[] = new int[j*3];
		int textureIndexes[][] = new int[coordCount][j*3];
		int normalIndexes[] = new int[j*3];

	//	tmp = bi.readLine();
	//	tmp = tmp.substring(1, tmp.length() - 1);
	//	String arr[] = tmp.split("\\}\\{");

		for(int i = 0; i < j; i++){
		//	arr[i] = arr[i].substring(1, arr[i].length() - 1);
		//	String face[] = arr[i].split("\\]\\[");
			for(int k = 0; k < 3; k ++){
			//	String cnt[] = face[k].split("\\,");
				coordIndexes[(i*3)+k] = bi.readInt();
				// texture
                if(mto){
                   // String txIndex[] = cnt[1].substring(1, cnt[1].length() -1).split("\\;");
                    for(int l = 0; l < coordCount; l++){
                        /*TexCoord2f coord = texCoordsAll[Integer.parseInt(txIndex[l])];
                        ArrayList<TexCoord2f> al = coordList[l];
                        int ind = al.indexOf(coord);
                        if(ind == -1){
                            ind = al.size();
                            al.addKeyFrameObj(coord);
                        }*/
                        textureIndexes[l][(i*3)+k] = bi.readInt();
                    }
                }else{
                    textureIndexes[0][(i*3)+k] = bi.readInt(); // Integer.parseInt(cnt[1]);
                }
				normalIndexes[(i*3)+k] = bi.readInt(); // Integer.parseInt(cnt[2]);
			}
		}

		bi.close();

        /*int vertCount = vertices.length;

        for(int l = 0; l < coordCount; l++){
            //texCoords[l] = coordList[l].toArray(new TexCoord2f[coordList[l].size()]);
            if(texCoords[l].length > vertCount) vertCount = texCoords[l].length;
        }

		if(normals.length > vertCount) vertCount = normals.length; */



        IntBuffer indexBuffer = BufferUtils.createIntBuffer(coordIndexes.length);

        FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(coordIndexes.length/3);
        FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(coordIndexes.length/3);
        FloatBuffer textureBuffer[] = new FloatBuffer[coordCount]; //;(coordIndexes.length/3);
        for(int k = 0;  k < coordCount; k++) textureBuffer[k] = BufferUtils.createVector2Buffer(coordIndexes.length/3);

        for (int i = 0; i < coordIndexes.length; i++){
            {
                Point3f tmp = vertices[coordIndexes[i]];
                vertexBuffer.put(tmp.x()).put(tmp.y()).put(tmp.z());
            }{
                Vector3f tmp = normals[normalIndexes[i]];
                normalBuffer.put(tmp.x()).put(tmp.y()).put(tmp.z());
            }
            for(int k = 0;  k < coordCount; k++){
                TexCoord2f tmp = texCoords[k][ textureIndexes[k][i] ];
                textureBuffer[k].put(tmp.getS()).put(tmp.getT());
            }
        }

        MeshData m = new MeshData();

        m.setVertexBuffer(vertexBuffer);
        m.setNormalBuffer(normalBuffer);
        for(int k = 0;  k < coordCount; k++)  m.setTextureBuffer(textureBuffer[k], k);

        m.setIndexBuffer(indexBuffer);
        m.setIndexMode(IndexMode.Triangles);

        return m;

        //m.setVertexBuffer();
        //
        /* IndexedTriangleArray ar;
        if(mto){
            int cordMap[] = new int[coordCount];
            for(int i = 0; i < coordCount; i++) cordMap[i] = i;
            ar = new IndexedTriangleArray(vertCount, IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.NORMALS | IndexedTriangleArray.COORDINATES, coordCount, cordMap , j*3);
        }else{
            ar = new IndexedTriangleArray(vertCount, IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.NORMALS | IndexedTriangleArray.COORDINATES, j*3);
        }

        ar.setCoordinates(0, vertices);

        ar.setNormals(0, normals);

		ar.setCoordinateIndices(0, coordIndexes);

		ar.setNormalIndices(0, normalIndexes);

        for(int i = 0; i < coordCount; i++){
            ar.setTextureCoordinates(i, 0, texCoords[i]);
            ar.setTextureCoordinateIndices(i, 0, textureIndexes[i]);
        }

        return ar; */
    }

    private final MeshData load(String f) throws IOException {
        boolean mto = f.endsWith(".mto");

        BufferedReader bi = new BufferedReader( new InputStreamReader(ResourceHandle.getInputStream(f)) );
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
			vertices[j] = new Point3f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]),Float.parseFloat(xyz[2]));
			j ++;
		}

		//for(Point3f r : vertices)System.out.println(r);

        int coordCount = 1;

        TexCoord2f texCoords[][];
        //ArrayList<TexCoord2f> coordList[] = null;

        if(mto){
            tmp = bi.readLine();
		    tmp  = tmp.split("\\=")[1];
		    coordCount = Integer.parseInt(tmp);
            texCoords = new TexCoord2f[coordCount][];
            //coordList = new ArrayList[coordCount];
            //for(int i = 0; i < coordCount; i++) coordList[i] = new ArrayList<TexCoord2f>();
        }else {
            texCoords = new TexCoord2f[1][];
            //texCoords[0] = texCoordsAll;
        }

        for(int l = 0; l < coordCount; l++){
            tmp = bi.readLine();
		    tmp  = tmp.split("\\=")[1];

            texCoords[l] = new TexCoord2f[Integer.parseInt(tmp)];

            tmp = bi.readLine();
            tmp = tmp.substring(1, tmp.length() - 1);

            j = 0;
            for(String i : tmp.split("\\]\\[")){
                String xyz[] = i.split("\\,");
                texCoords[l][j] = new TexCoord2f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]));
                j++;
            }
        }

		//for(TexCoord2f r : texCoords)System.out.println(r);

		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];

		Vector3f normals[] = new Vector3f[Integer.parseInt(tmp)];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);

		j = 0;
		for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
			normals[j] = new Vector3f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]),Float.parseFloat(xyz[2]));
			j++;
		}

		//for(Vector3f r : normals)System.out.println(r);





		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
		j = Integer.parseInt(tmp);

		int coordIndexes[] = new int[j*3];
		int textureIndexes[][] = new int[coordCount][j*3];
		int normalIndexes[] = new int[j*3];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
		String arr[] = tmp.split("\\}\\{");

		for(int i = 0; i < j; i++){
			arr[i] = arr[i].substring(1, arr[i].length() - 1);
			String face[] = arr[i].split("\\]\\[");
			for(int k = 0; k < 3; k ++){
				String cnt[] = face[k].split("\\,");
				coordIndexes[(i*3)+k] = Integer.parseInt(cnt[0]);
				// texture
                if(mto){
                    String txIndex[] = cnt[1].substring(1, cnt[1].length() -1).split("\\;");
                    for(int l = 0; l < coordCount; l++){
                        /*TexCoord2f coord = texCoordsAll[Integer.parseInt(txIndex[l])];
                        ArrayList<TexCoord2f> al = coordList[l];
                        int ind = al.indexOf(coord);
                        if(ind == -1){
                            ind = al.size();
                            al.addKeyFrameObj(coord);
                        }*/
                        textureIndexes[l][(i*3)+k] = Integer.parseInt(txIndex[l]);
                    }
                }else{
                    textureIndexes[0][(i*3)+k] = Integer.parseInt(cnt[1]);
                }
				normalIndexes[(i*3)+k] = Integer.parseInt(cnt[2]);
			}
		}

		bi.close();

        IntBuffer indexBuffer = BufferUtils.createIntBuffer(coordIndexes.length);

        FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(coordIndexes.length/3);
        FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(coordIndexes.length/3);
        FloatBuffer textureBuffer[] = new FloatBuffer[coordCount]; //;(coordIndexes.length/3);
        for(int k = 0;  k < coordCount; k++) textureBuffer[k] = BufferUtils.createVector2Buffer(coordIndexes.length/3);

        for (int i = 0; i < coordIndexes.length; i++){
            {
                Point3f t = vertices[coordIndexes[i]];
                vertexBuffer.put(t.x()).put(t.y()).put(t.z());
            }{
                Vector3f t = normals[normalIndexes[i]];
                normalBuffer.put(t.x()).put(t.y()).put(t.z());
            }
            for(int k = 0;  k < coordCount; k++){
                TexCoord2f t = texCoords[k][ textureIndexes[k][i] ];
                textureBuffer[k].put(t.getS()).put(t.getT());
            }
            indexBuffer.put(i);
        }

        MeshData m = new MeshData();

        m.setVertexBuffer(vertexBuffer);
        m.setNormalBuffer(normalBuffer);
        for(int k = 0;  k < coordCount; k++)  m.setTextureBuffer(textureBuffer[k], k);

        m.setIndexBuffer(indexBuffer);
        m.setIndexMode(IndexMode.Triangles);

        return m;

        /* int vertCount = vertices.length;

        for(int l = 0; l < coordCount; l++){
            //texCoords[l] = coordList[l].toArray(new TexCoord2f[coordList[l].size()]);
            if(texCoords[l].length > vertCount) vertCount = texCoords[l].length;
        }

		if(normals.length > vertCount) vertCount = normals.length;

        //
        IndexedTriangleArray ar;
        if(mto){
            int cordMap[] = new int[coordCount];
            for(int i = 0; i < coordCount; i++) cordMap[i] = i;
            ar = new IndexedTriangleArray(vertCount, IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.NORMALS | IndexedTriangleArray.COORDINATES, coordCount, cordMap , j*3);
        }else{
            ar = new IndexedTriangleArray(vertCount, IndexedTriangleArray.TEXTURE_COORDINATE_2 | IndexedTriangleArray.NORMALS | IndexedTriangleArray.COORDINATES, j*3);
        }

        ar.setCoordinates(0, vertices);

        ar.setNormals(0, normals);

		ar.setCoordinateIndices(0, coordIndexes);

		ar.setNormalIndices(0, normalIndexes);

        for(int i = 0; i < coordCount; i++){
            ar.setTextureCoordinates(i, 0, texCoords[i]);
            ar.setTextureCoordinateIndices(i, 0, textureIndexes[i]);
        }

        return ar; */
    }

}
