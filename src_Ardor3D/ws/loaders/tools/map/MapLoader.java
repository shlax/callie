package ws.loaders.tools.map;

import ws.ResourceHandle;
import ws.map.Type;

import javax.vecmath.Point3f;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public final class MapLoader {

    private final HashMap<String, LoadedTriangle[]> map = new HashMap<String, LoadedTriangle[]>();

    public final LoadedTriangle[] getLoadedTriangle(String f, Type t, boolean dynamic) throws IOException {
        f = "data/"+f;
        String key = dynamic+"|"+f+"|"+t;
        LoadedTriangle[] tr = map.get(key);
        if(tr == null){
            tr = ResourceHandle.bin ? this.getLoadedFileTriangleBin(f+".bin", t, dynamic) : this.getLoadedFileTriangle(f, t, dynamic);
            map.put(key, tr);
        }
        return tr;
    }

    private final LoadedTriangle[] getLoadedFileTriangleBin(String f, Type t, boolean dynamic) throws IOException {
        DataInputStream bi = new DataInputStream( ResourceHandle.getInputStream(f) );

		Point3f vertices[] = new Point3f[bi.readInt()];

        //tmp = bi.readLine();
		//tmp = tmp.substring(1, tmp.length() - 1);
        //int j = 0;
        for(int i = 0; i < vertices.length; i++){
			//String xyz[] = i.split("\\,");
			vertices[i] = new Point3f(bi.readFloat(), bi.readFloat(), bi.readFloat());
            //j++;
		}

		//tmp = bi.readLine();
		//tmp  = tmp.split("\\=")[1];
        LoadedTriangle triangles[] = new LoadedTriangle[bi.readInt()];

	//	tmp = bi.readLine();
	//	tmp = tmp.substring(1, tmp.length() - 1);
     //   j = 0;
		for(int i = 0; i < triangles.length; i++){
		//	String abc[] = i.split("\\,");
			triangles[i] = new LoadedTriangle( vertices[ bi.readInt() ], vertices[ bi.readInt() ], vertices[ bi.readInt() ], t, dynamic );
        //    j++;
        }

		bi.close();

        return triangles;
    }

    private final LoadedTriangle[] getLoadedFileTriangle(String f, Type t, boolean dynamic) throws IOException {
        BufferedReader bi = new BufferedReader( new InputStreamReader(ResourceHandle.getInputStream(f)) );

		String tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
        Point3f vertices[] = new Point3f[Integer.parseInt(tmp)];

        tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
        int j = 0;
        for(String i : tmp.split("\\]\\[")){
			String xyz[] = i.split("\\,");
			vertices[j] = new Point3f(Float.parseFloat(xyz[0]),Float.parseFloat(xyz[1]),Float.parseFloat(xyz[2]));
            j++;
		}

		tmp = bi.readLine();
		tmp  = tmp.split("\\=")[1];
        LoadedTriangle triangles[] = new LoadedTriangle[Integer.parseInt(tmp)];

		tmp = bi.readLine();
		tmp = tmp.substring(1, tmp.length() - 1);
        j = 0;
		for(String i : tmp.split("\\}\\{")){
			String abc[] = i.split("\\,");
			triangles[j] = new LoadedTriangle( vertices[ Integer.parseInt(abc[0]) ], vertices[ Integer.parseInt(abc[1]) ], vertices[ Integer.parseInt(abc[2])], t, dynamic );
            j++;
        }

		bi.close();

        return triangles;
    }

}
