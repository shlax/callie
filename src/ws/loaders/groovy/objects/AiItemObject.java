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

    private TransformGroup transformGroup = null;
    public final void setTransformGroup(TransformGroup transformGroup) {
        this.transformGroup = transformGroup;
    }

    // ------------------------------------------------------------------------------------------------------

    public final TransformGroup getTransformGroup(){
        return this.transformGroup;
    }

    public final String getBhoneName() {
        return bhoneName;
    }

    public final Transform3D getTransform() {
        return transform;
    }
}
