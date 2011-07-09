package ws.loaders.tools.joint;

import org.openmali.vecmath2.Vector3f;
import ws.ResourceHandle;
import ws.joint.LinearBhone;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public final class BhoneLoader {
    public BhoneLoader(){}

    private final HashMap<String, LoadedBhone> cache = new HashMap<String, LoadedBhone>();

    public final LoadedBhone loadBhone(String name, float scale) throws IOException {
        String key = name+"|"+scale;

        LoadedBhone tmp = this.cache.get(key);
        if(tmp != null) return new LoadedBhone(tmp, null);

        LoadedBhone ret;
        if(ResourceHandle.bin){
            DataInputStream bi = new DataInputStream(ResourceHandle.getInputStream(name+".bin"));
            ret = loadBoneBin(null, bi, scale);
            bi.close();
        }else{
        //System.out.println(name);
            BufferedReader bi = new BufferedReader( new InputStreamReader(ResourceHandle.getInputStream(name)) );
            bi.readLine(); // skip first
            ret = loadBone(null, bi, scale);
            bi.close();
        }


        this.cache.put(key, ret);

		return ret;
	}

    private final LoadedBhone loadBoneBin(LoadedBhone parent, DataInputStream bi, float scale) throws IOException {
       // System.out.println('.');

        String name = bi.readUTF();

        boolean linear = bi.readBoolean();

        LinearBhone.Maping map[] =  linear ? new LinearBhone.Maping[]{LinearBhone.Maping.X, LinearBhone.Maping.Y, LinearBhone.Maping.Z} : null ;

		float mx = bi.readFloat();
		float my = bi.readFloat();
		float mz = bi.readFloat();

        int poc = bi.readByte();

        for(int i = 0; i < poc; i++){
            int ind = bi.readByte();
            if(ind != -1){
                LinearBhone.Maping val = null;
                int dst = bi.readByte();
                if(dst == 0){
                    val = LinearBhone.Maping.X;
                }else if(dst == 1){
                    val = LinearBhone.Maping.Y;
                }else if(dst == 2){
                    val = LinearBhone.Maping.Z;
                }
                if(val != null){
                    map[ind] = val;
                }else{
                    throw new RuntimeException("wrong value");
                }
            }else{
                throw new RuntimeException("wrong index");
            }
        }


		int index[] = new int[bi.readInt()];
		for(int i = 0; i < index.length; i++) index[i] = bi.readInt();

        LoadedBhone.Type type = linear ? LoadedBhone.Type.Linear : LoadedBhone.Type.Acelerated;
        Vector3f mov = new Vector3f(mx*scale, my*scale, mz*scale);
        LoadedBhone abc = new LoadedBhone(name, mov, parent, index, type, map);

		while( !bi.readBoolean()  ) abc.addLoadedBhone(loadBoneBin(abc, bi, scale));

		return new LoadedBhone(abc, null);
    }

    private final LoadedBhone loadBone(LoadedBhone parent, BufferedReader bi, float scale) throws IOException {
		String tmp = bi.readLine();
		String p[] = tmp.split("\\ ");
		String name = p[0];
		boolean linear = "linear".equals(p[1]);
        LinearBhone.Maping map[] =  linear ? new LinearBhone.Maping[]{LinearBhone.Maping.X, LinearBhone.Maping.Y, LinearBhone.Maping.Z} : null ;

		float mx = Float.parseFloat(p[2]);
		float my = Float.parseFloat(p[3]);
		float mz = Float.parseFloat(p[4]);

        for(int i = 5; i < p.length; i += 2){
            int ind = -1;
            if("x".equals(p[i])){
                ind = 0;
            }else if("y".equals(p[i])){
                ind = 1;
            }else if("z".equals(p[i])){
                ind = 2;
            }
            if(ind != -1){
                LinearBhone.Maping val = null;
                if("x".equals(p[i+1])){
                    val = LinearBhone.Maping.X;
                }else if("y".equals(p[i+1])){
                    val = LinearBhone.Maping.Y;
                }else if("z".equals(p[i+1])){
                    val = LinearBhone.Maping.Z;
                }
                if(val != null){
                    map[ind] = val;
                }else{
                    throw new RuntimeException("wrong value");
                }
            }else{
                throw new RuntimeException("wrong index");
            }
        }

		tmp = bi.readLine();
		p = tmp.split("\\,");
		int index[] = new int[p.length];
		for(int i = 0; i < index.length; i++) index[i] = Integer.parseInt(p[i]);

        LoadedBhone.Type type = linear ? LoadedBhone.Type.Linear : LoadedBhone.Type.Acelerated;
        Vector3f mov = new Vector3f(mx*scale, my*scale, mz*scale);
        LoadedBhone abc = new LoadedBhone(name, mov, parent, index, type, map);

		while( ! "end".equals(bi.readLine() )) abc.addLoadedBhone(loadBone(abc, bi, scale));

		return new LoadedBhone(abc, null);
	}

}
