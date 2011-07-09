package ws.loaders.groovy.objects;

import groovy.lang.Closure;
import ws.ai.Ai;
import ws.ai.agents.AiWolker;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.tools.map.NodeMapBuilder;
import ws.map.ai.NodeMap;
import ws.tools.controls.AiControl;
import ws.tools.controls.Location;

import javax.media.j3d.BranchGroup;
import javax.vecmath.Point3f;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class AiObject extends FactoryElement {

    public AiObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private ArrayList<MapObject> mapObject = null; // new ArrayList<MapObject>();
    public final void addMapObject(MapObject mapObject) {
        if(this.mapObject == null) this.mapObject = new ArrayList<MapObject>();
        this.mapObject.add(mapObject);
    }

    private ArrayList<AgentObject> agentObjects = new ArrayList<AgentObject>();
    public final void addAgentObject(AgentObject a){
        this.agentObjects.add(a);
    }

/*    private Type lsType = Type.TERAIN;
    public final void setLsType(Type asType) {
        this.lsType = asType;
    } */

    private Closure onDettect = null;
    public final void setOnDettect(Closure onDettect) {
        this.onDettect = onDettect;
    }
    /*    private LsObject lsObject = null;
    public final void setLsObject(LsObject lsObject) {
        this.lsObject = lsObject;
    } */

    private Float activeDistance = null;
    public final void setActiveDistance(Float activeDistance) {
        this.activeDistance = activeDistance;
    }

    private ArrayList<AiCheckObject> aiChecks = null; // new ArrayList<AiCheckObject>();
    public final void addAiCheck(AiCheckObject c){
        if(aiChecks == null) aiChecks = new ArrayList<AiCheckObject>();
        this.aiChecks.add(c);
    }

    public final void buildAi(BranchGroup activeNode, BranchGroup aiNode, BranchGroup efectNode) throws IOException {
        NodeMapBuilder mapBuilder = new NodeMapBuilder();
  //      if(lsObject != null) mapBuilder.addLoadedMap(lsObject.getTriangles(lsType));
        for(MapObject tmp : mapObject)mapBuilder.addLoadedMap(tmp.getTriangles());
        
        for(AgentObject tmp : agentObjects){
            AiWolker w = tmp.getAiWolker(mapBuilder, aiNode, activeNode, efectNode,/* this.lsObject, */activeDistance);
            activeNode.addChild(w);
            Ai.addTargetWolker(w);
        }

        if(aiChecks != null){
            for(AiCheckObject tmp : aiChecks){
                Location l = tmp.isLocation();
                if(l != null){
                    Point3f pp = tmp.getPoint();
                    NodeMap nm = mapBuilder.getNodeMapAt(pp);
                    l._setDestination(nm.getY25Triangle());
                }
            }
        }

        if(onDettect != null)Ai.setOnDettect(onDettect);

        mapObject = null;
        agentObjects = null;
    }

    public final AiControl control(){
        return new AiControl();
    }
}
