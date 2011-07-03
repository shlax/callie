package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import java.util.Map;

public final class AiItemObject extends FactoryElement {

    public AiItemObject(Object value, Map attributes) {
        super(value, attributes);        
    }

    private String bhoneName = null;
    public final void setBhoneName(String bhoneName) {
        this.bhoneName = bhoneName;
    }
    
    private Transform3D transform = null;
    public final void setTransform(Transform3D transform) {
        this.transform = transform;
    }

    private TransformGroupObject transformGroupObj = null;
    public final void setTransformGroup(TransformGroupObject transformGroup) {
        this.transformGroupObj = transformGroup;
    }

    // ------------------------------------------------------------------------------------------------------

    public final TransformGroup getTransformGroup(){
        return this.transformGroupObj.getNode();
    }

    public final String getBhoneName() {
        return bhoneName;
    }

    public final Transform3D getTransform() {
        return transform;
    }
}
