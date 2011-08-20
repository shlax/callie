package ws.loaders.tools.joint;

import ws.ResourceHandle;
import ws.joint.acelerator.Angle3f;

import javax.vecmath.Vector3f;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public final class BhoneFrameLoader {

    private final HashMap<String, LoadedBhoneFrame> cache = new HashMap<String, LoadedBhoneFrame>();

    public final LoadedBhoneFrame load(String f, float scale) throws IOException {
        f = "data/"+f;

        String key = f+"|"+scale;

        LoadedBhoneFrame tmp = cache.get(key);
        if(tmp != null) return new LoadedBhoneFrame(tmp);

        tmp = ResourceHandle.bin ? processBin(f, scale) : process(f, scale);

        this.cache.put(key, tmp);

        return tmp;
    }

    public final LoadedBhoneFrame processBin(String f, float scale) throws IOException {
        DataInputStream bi = new DataInputStream(ResourceHandle.getInputStream(f+".bin"));

        LoadedBhoneFrame bgl = null;

        int poc = bi.readInt();

        for(int i = 0; i < poc; i++){

            String name = bi.readUTF();
            if(i == 0){
                float x = bi.readFloat() * scale;
                float y = bi.readFloat() * scale;
                float z = bi.readFloat() * scale;

                bgl = new LoadedBhoneFrame(new Vector3f(x,y,z));
            }

            float x = bi.readFloat();
            float y = bi.readFloat();
            float z = bi.readFloat();

            bgl.addAngle(name, new Angle3f(x,y,z));
        }
        bi.close();

        return bgl;
    }

    public final LoadedBhoneFrame process(String f, float scale) throws IOException {
        BufferedReader bi = new BufferedReader( new InputStreamReader( ResourceHandle.getInputStream(f) ) );

        LoadedBhoneFrame bgl = null;

        String l;
        boolean first = true;
        while((l = bi.readLine()) != null){
            String p[] =l.split("\\ ");
            if(p.length > 0){
                int off = 1;

                if(first){
                    float x = Float.parseFloat(p[1])*scale;
                    float y = Float.parseFloat(p[2])*scale;
                    float z = Float.parseFloat(p[3])*scale;

                    bgl = new LoadedBhoneFrame(new Vector3f(x,y,z));
                    off = 4;
                    first = false;
                }

                float x = (float)((Float.parseFloat(p[off])*Math.PI)/180);
                float y = (float)((Float.parseFloat(p[1+off])*Math.PI)/180);
                float z = (float)((Float.parseFloat(p[2+off])*Math.PI)/180);

                bgl.addAngle(p[0], new Angle3f(x,y,z));
            }
        }        
        bi.close();

        return bgl;
    }



}
