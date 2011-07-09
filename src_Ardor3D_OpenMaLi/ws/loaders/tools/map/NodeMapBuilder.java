package ws.loaders.tools.map;

import org.openmali.vecmath2.Point3f;
import ws.map.ai.NodeMap;

import java.util.ArrayList;
import java.util.Arrays;

public final class NodeMapBuilder {

    private final ArrayList<LoadedTriangle[]> parts = new ArrayList<LoadedTriangle[]>();
    public final void addLoadedMap(LoadedTriangle[] m){
        this.parts.add(m);
    }

    private final ArrayList<LoadedTriangle> trg = new ArrayList<LoadedTriangle>();
    
    private NodeMap nodes[] = null;

    public final NodeMap[] getMap(){
        if(this.nodes == null){
            for(LoadedTriangle[] tmp : parts)trg.addAll(Arrays.asList(tmp));
            for(LoadedTriangle a : trg){
                for(LoadedTriangle b : trg){
                    if(a == b) continue;
                    if(a.pocSame(b) == 2) a.addLoacation(b);
                }
            }
            nodes = new NodeMap[trg.size()];
            for(int i = 0; i < trg.size(); i++) nodes[i] = trg.get(i).getNodeMap(nodes);
        }
        return this.nodes;
    }

    public final NodeMap getNodeMapAt(Point3f lastPos){
        LoadedTriangle ret = null;
        float dst = Float.MAX_VALUE;
        for(LoadedTriangle tmp : trg){
            float d = tmp.getDistance(lastPos);
            if(d < dst){
                dst = d;
                ret = tmp;
            }
        }
        
        return ret.getNodeMap();
    }

}
