package ws.loaders.groovy.objects;

import com.ardor3d.scenegraph.Spatial;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;

import java.util.Map;

//import javax.media.j3d.Group;

public abstract class NodeObject extends FactoryElement {

    protected NodeObject(Object value, Map attributes) {
        super(value, attributes);
    }

    //public abstract void getNode(Group g);
    public abstract Spatial getNode();

    private SceneBuilder.SceneObjectType sceneObjectType = SceneBuilder.SceneObjectType.NORMAL;
    
    public final SceneBuilder.SceneObjectType getSceneObjectType() {
        return sceneObjectType;
    }
    public final void setSceneObjectType(SceneBuilder.SceneObjectType sceneObjectType) {
        this.sceneObjectType = sceneObjectType;
    }
}
