package ws.loaders.tools.map;

import ws.SceneAction;
import ws.map.*;

import javax.vecmath.Point3f;
import java.util.ArrayList;
import java.util.Arrays;

public final class Y25MapBuilder{

    private final ArrayList<LoadedTriangle[]> parts = new ArrayList<LoadedTriangle[]>();
    private final ArrayList<SceneAction> actions = new ArrayList<SceneAction>();
    public final void addLoadedMap(LoadedTriangle[] m, SceneAction act){
        this.parts.add(m);
        this.actions.add(act);
    }

    private Y25Map map = null;
    private final ArrayList<LoadedTriangle> trg = new ArrayList<LoadedTriangle>();

    public final Y25Map getMap() {
        if(map == null){
            int pocActions = 0;

            for(int i = 0; i < parts.size(); i++){
                trg.addAll(Arrays.asList(parts.get(i)));
                if(actions.get(i) != null) pocActions ++;
            }

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

            if(pocActions != 0){

                MapGroup groups[] = new MapGroup[pocActions];
                int j = 0;
                for(int i = 0; i < parts.size(); i++){
                    if(actions.get(i) != null){
                        LoadedTriangle[] q = parts.get(i);
                        Y25Triangle[] w = new Y25Triangle[q.length];
                        for(int k = 0; k < q.length; k++)w[k] = q[k].getY25Triangle();

                        groups[j] = new MapGroup(w, actions.get(i) );
                        j++;
                    }
                }
                map = new Y25GroupMap(t, t[0], groups);
            }else{
                map = new Y25Map(t, t[0]);
            }
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
