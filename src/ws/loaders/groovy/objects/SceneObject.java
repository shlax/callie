package ws.loaders.groovy.objects;

import ws.camera.CharacterCamera;
import ws.loaders.groovy.FactoryElement;

import javax.media.j3d.BranchGroup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class SceneObject extends FactoryElement {

    public SceneObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private float backFlipDistance = Integer.MAX_VALUE;

    public final void setBackFlipDistance(float backFlipDistance) {
        this.backFlipDistance = backFlipDistance;
    }

    public final float getBackFlipDistance() {
        return backFlipDistance;
    }

    private float detectDistance = 25f;

    public final float getDetectDistance() {
        return detectDistance;
    }

    public final void setDetectDistance(float detectDistance) {
        this.detectDistance = detectDistance;
    }

    private ArrayList<NodeObject> cameraColisions = new ArrayList<NodeObject>();
    private ArrayList<NodeObject> roots = new ArrayList<NodeObject>();
    private ArrayList<NodeObject> effects = new ArrayList<NodeObject>();

    private ArrayList<MapObject> maps = new ArrayList<MapObject>();

    public final void addMapObject(MapObject r){
        this.maps.add(r);
    }

    public final void addCameraColisions(NodeObject r){
        this.cameraColisions.add(r);
    }

    public final void addRoot(NodeObject r){
        this.roots.add(r);
    }

    public final void addEffect(NodeObject e){
        this.effects.add(e);
    }

/*    private LsObject lsObject = null;
    public final void setLsObject(LsObject lsObject) {
        this.lsObject = lsObject;
    } */

    public final void build() throws IOException {
        for(MapObject tmp : this.maps){
            if(controlObject != null)controlObject.addMapObject(tmp);
            if(aiObject != null)aiObject.addMapObject(tmp);
        }

      //  if(lsObject != null) lsObject.process(aiNode);

        characterCamera = controlObject.buildCharacterCamera(cameraNode, aiNode, rootNode);
        
        for(NodeObject t : roots)t.getNode(this.aiNode);
        for(NodeObject t : effects)t.getNode(this.rootNode);        
        for(NodeObject t : cameraColisions)t.getNode(this.cameraNode);
        if(aiObject != null)aiObject.buildAi(activeNode, this.aiNode, rootNode);

        activeNode.addChild(aiNode);
        cameraNode.addChild(activeNode);
        rootNode.addChild(cameraNode);
       // rootNode.compile();
    }

    private ControlObject controlObject = null;
    public final void setControlObject(ControlObject controlObject) {
        this.controlObject = controlObject;
    }

    private AiObject aiObject = null;
    public final void setAiObject(AiObject aiObject) {
        this.aiObject = aiObject;
    }

    private CharacterCamera characterCamera = null;
    public final CharacterCamera getCharacterCamera() {
        return characterCamera;
    }

    private final BranchGroup activeNode = new BranchGroup();
	public final BranchGroup getActiveNode() {
		return activeNode;
	}

	private final BranchGroup rootNode = new BranchGroup();
	public final BranchGroup getRootNode() {
		return rootNode;
	}

	private final BranchGroup cameraNode = new BranchGroup();
	public final BranchGroup getCameraNode() {
		return cameraNode;
	}

	private final BranchGroup aiNode = new BranchGroup();
	public final BranchGroup getAiNode() {
		return aiNode;
	}

}
