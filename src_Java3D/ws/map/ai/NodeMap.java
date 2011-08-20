package ws.map.ai;

import ws.map.Y25Triangle;

import javax.vecmath.Point3f;
import java.util.ArrayList;

public final class NodeMap {
    private final Point3f pozition;
    private final Y25Triangle t;

    public final Point3f getPoint(){
        return this.pozition;
    }

    private final Point3f nextPos[];
    public final Point3f getPoint(NodeMap act){
        for(int i = 0; i < this.location.length; i++) if(location[i] == act) return nextPos[i];
        return this.pozition;
    }

    public final Y25Triangle getY25Triangle(){
        return t;
    }

    private final NodeMap mapa[];
    
    private final NodeMap location[];
    private final float len[];

    public NodeMap(Point3f pozition, Y25Triangle t, NodeMap[] location, Point3f nextPos[], float len[], NodeMap mapa[]) {
        this.pozition = pozition;
        this.t = t;
        this.len = len;

        this.location = location;
        this.nextPos = nextPos;
        this.mapa = mapa;

/*        float len[] = new float[this.location.length];
    for(int i = 0; i < len.length; i++) len[i] = this.pozition.distance(this.location[i].pozition);
    this.len = len; */
    }

    public NodeMap(NodeMap m){
        this.pozition = m.pozition;
        this.nextPos = m.nextPos;
        this.t = m.t;
        this.len = m.len;

        this.mapa = new NodeMap[m.mapa.length];
        this.location = new NodeMap[m.location.length];

        for(int i = 0; i < this.mapa.length; i++) this.mapa[i] =  m.mapa[i].t == this.t ? this : new NodeMap(m.mapa[i], this.mapa);
        
        // pre vsetky nody v mape
        for(int i = 0; i < this.mapa.length; i++){
            // location
            for( int j = 0; i < m.mapa[i].location.length; j++ ){
                // find index
                for(int k = 0; i < this.mapa.length; k++){
                    if( m.mapa[i].location[j].t == m.mapa[k].t){
                        this.mapa[i].location[j] = this.mapa[k]; 
                        break;
                    }
                }
            }
        }

    }

    private NodeMap(NodeMap m, NodeMap mapa[]){
        this.pozition = m.pozition;
        this.nextPos = m.nextPos;
        this.t = m.t;
        this.len = m.len;
        
        this.mapa = mapa;
        this.location = new NodeMap[m.location.length];
    }
    
    // ----------------------------------------------------------------------------------------------


/*	public syn chronized final NodeMap[] getLocale(){
    return this.location;
} */

    private NodeMap back = null;
    private float actLen = Float.MAX_VALUE;

    private void computePath(NodeMap pred, float val){
        if(val < this.actLen){
            this.back = pred;
            this.actLen = val;
            for(int i = 0; i < location.length; i++) location[i].computePath(this, this.actLen+this.len[i]);
        }
    }

    public final NodeMap[] getPath(NodeMap dst){


        ArrayList<NodeMap> list = new ArrayList<NodeMap>();

    //  if(this == dest) return new NodeMap[]{this};
        for(NodeMap nx : mapa){
            nx.back = null;
            nx.actLen = Float.MAX_VALUE;
        }
        this.actLen = 0;

        for(int i = 0; i < location.length; i++) location[i].computePath(this, this.len[i]);

        NodeMap act = dst;

        int pocIter = 0;
        while(act.back != null){
            act = act.back;
            list.add(act);
            if(pocIter > this.mapa.length) return null;
            pocIter ++;
        }
        if(list.isEmpty()) return null;
        list.add(0, dst);

        NodeMap[] path = new NodeMap[list.size()];
        int l = path.length-1;
        for(int i = 0; i< path.length; i++) path[l-i] = list.get(i);
        return path;

    }
}
