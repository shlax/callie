package ws.loaders.groovy.objects;


import javax.media.j3d.Transform3D;
import java.util.Map;

public final class AiItemObject extends TransformGroupObject {

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

    // ------------------------------------------------------------------------------------------------------

    public final String getBhoneName() {
        return bhoneName;
    }

    public final Transform3D getTransform() {
        return transform == null ? new Transform3D(): transform;
    }
}
