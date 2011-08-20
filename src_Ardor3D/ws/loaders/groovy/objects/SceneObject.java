package ws.loaders.groovy.objects;

import com.ardor3d.light.DirectionalLight;
import com.ardor3d.math.ColorRGBA;
import com.ardor3d.renderer.state.CullState;
import com.ardor3d.renderer.state.LightState;
import com.ardor3d.renderer.state.MaterialState;
import com.ardor3d.scenegraph.Node;
import ws.camera.CharacterCamera;
import ws.loaders.groovy.FactoryElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class SceneObject extends FactoryElement {

    public SceneObject(Object value, Map attributes) {
        super(value, attributes);
    }

    /*private float backFlipDistance = Integer.MAX_VALUE;

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
    } */
    private  AppearanceObject appearanceObject = null;
    public final void setAppearanceObject(AppearanceObject appearanceObject) {
        this.appearanceObject = appearanceObject;
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
        
        for(NodeObject t : roots){
            this.aiNode.attachChild(t.getNode());
            //t.getNode(this.aiNode);
            //t.postProcess();
        }
        for(NodeObject t : effects){
            this.rootNode.attachChild(t.getNode());
//            t.getNode();
            //t.postProcess();
        }
        for(NodeObject t : cameraColisions){
            this.cameraNode.attachChild(t.getNode());
            //t.getNode(this.cameraNode);
            //t.postProcess();
        }
        if(aiObject != null)aiObject.buildAi(activeNode, this.aiNode, rootNode);

        activeNode.attachChild(aiNode);
        cameraNode.attachChild(activeNode);
        rootNode.attachChild(cameraNode);
       // rootNode.compile();

        if(appearanceObject != null){
            appearanceObject.getAppearance().getRenderState(rootNode);
        }else{
            MaterialState ms = new MaterialState();
            ms.setEnabled(true);
            ms.setAmbient(new ColorRGBA(0.4f, 0.4f, 0.4f, 1f));
            ms.setDiffuse(new ColorRGBA(1f, 1f, 1f, 1f));
            ms.setEmissive(new ColorRGBA(0f, 0f, 0f, 1f));
            ms.setSpecular(new ColorRGBA(0f, 0f, 0f, 1f));
            ms.setShininess(0f);
            rootNode.setRenderState(ms);

            LightState ls = new LightState();
            ls.setEnabled(true);
            ls.setGlobalAmbient(new ColorRGBA(1f, 1f, 1f, 1f));
            DirectionalLight dl = new DirectionalLight();
            dl.setDirection(-1, -1, -1);
            dl.setSpecular(new ColorRGBA(0.0f, 0.0f, 0.0f, 1f));
            dl.setAmbient(new ColorRGBA(0.0f, 0.0f, 0.0f, 1f));
            dl.setDiffuse(new ColorRGBA(0.6f, 0.6f, 0.6f, 1f));
            dl.setEnabled(true);
            ls.attach(dl);
            rootNode.setRenderState(ls);

            CullState cs = new CullState();
            cs.setEnabled(true);
            cs.setCullFace(CullState.Face.Back);
            rootNode.setRenderState(cs);
        }
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
        //System.out.println(characterCamera);
        return characterCamera;
    }

    private Node activeNode = new Node();
	public final Node getActiveNode() {
		return activeNode;
	}

	private Node rootNode = new Node();
	public final Node getRootNode() {
		return rootNode;
	}

	private Node cameraNode = new Node();
	public final Node getCameraNode() {
		return cameraNode;
	}

	private Node aiNode = new Node();
	public final Node getAiNode() {
		return aiNode;
	}

    public final void clean() {
        appearanceObject = null;
        cameraColisions = null;
        roots = null;
        effects = null;
        maps = null;

        controlObject = null;
        aiObject = null;
        characterCamera = null;
        activeNode = null;
        rootNode = null;
        cameraNode = null;
        aiNode = null;
    }
}
