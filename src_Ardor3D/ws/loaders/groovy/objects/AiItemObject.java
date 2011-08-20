package ws.loaders.groovy.objects;


import javax.vecmath.Matrix4f;
import java.util.Map;

public final class AiItemObject extends TransformGroupObject {

    public AiItemObject(Object value, Map attributes) {
        super(value, attributes);        
    }

    private String bhoneName = null;
    public final void setBhoneName(String bhoneName) {
        this.bhoneName = bhoneName;
    }
    
    private Matrix4f transform = null;
    public final void setTransform(Matrix4f transform) {
        this.transform = transform;
    }

    // ------------------------------------------------------------------------------------------------------

    public final String getBhoneName() {
        return bhoneName;
    }

    public final Matrix4f getTransform() {
        return transform == null ? new Matrix4f(): transform;
    }
}
