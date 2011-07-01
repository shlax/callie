package ws.loaders.tools.map;

import ws.map.Type;
import ws.map.Y25Map;
import ws.map.Y25Triangle;

import javax.vecmath.Point3f;
import java.util.ArrayList;
import java.util.Arrays;

public final class Y25MapBuilder{

    private final ArrayList<LoadedTriangle[]> parts = new ArrayList<LoadedTriangle[]>();
    public final void addLoadedMap(LoadedTriangle[] m){
        this.parts.add(m);
    }

    private Y25Map map = null;
    private final ArrayList<LoadedTriangle> trg = new ArrayList<LoadedTriangle>();

    public final Y25Map getMap() {
        if(map == null){
            for(LoadedTriangle[] tmp : parts)trg.addAll(Arrays.asList(tmp));
            for(LoadedTriangle a : trg){
                for(LoadedTriangle b : trg){
                    if(a == b) continue;
                    int pz = a.pocSame(b);

                    if(pz == 2) a.addNear(b);
                    else if(pz == 1) a.addFear(b);                  
                }
            }
            Y25Triangle t[] = new Y25Triangle[trg.size()];
            for(int i = 0; i < trg.size(); i++){
                LoadedTriangle tmp = trg.get(i);
                t[i] = tmp.getY25Triangle();
            }
            map = new Y25Map(t, t[0]);
        }
        return map;
    }

    public final LoadedTriangle getNear(Point3f lastPos, Type t){
        LoadedTriangle ret = null;
        float dst = Float.MAX_VALUE;
        for(LoadedTriangle tmp : trg){
            if(tmp.getType() == t){
                float d = tmp.getDistance(lastPos);
                if(d < dst){
                    dst = d;
                    ret = tmp;
                }
            }
        }
        return ret;
    }

}
