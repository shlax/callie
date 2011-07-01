package ws.loaders.groovy.objects;

import ws.ai.Ai;
import ws.ai.agents.AiWolker;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.tools.map.NodeMapBuilder;
import ws.map.Type;

import javax.media.j3d.BranchGroup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class AiObject extends FactoryElement {

    public AiObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private final ArrayList<MapObject> mapObject = new ArrayList<MapObject>();
    public final void addMapObject(MapObject mapObject) {
        this.mapObject.add(mapObject);
    }

    private final ArrayList<AgentObject> agentObjects = new ArrayList<AgentObject>();
    public final void addAgentObject(AgentObject a){
        this.agentObjects.add(a);
    }

    private Type lsType = Type.TERAIN;
    public final void setLsType(Type asType) {
        this.lsType = asType;
    }

/*    private LsObject lsObject = null;
    public final void setLsObject(LsObject lsObject) {
        this.lsObject = lsObject;
    } */

    private Float activeDistance = null;
    public final void setActiveDistance(Float activeDistance) {
        this.activeDistance = activeDistance;
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
    }
}
