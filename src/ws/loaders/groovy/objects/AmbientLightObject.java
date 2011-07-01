package ws.loaders.groovy.objects;

import javax.media.j3d.AmbientLight;
import javax.vecmath.Color3f;
import java.util.Map;

public final class AmbientLightObject extends LightObject {

    public AmbientLightObject(Object value, Map attributes) {
        super(value, attributes);
    }

    @Override
    protected AmbientLight getLight(Color3f c) {
        return new AmbientLight(c);
    }
}
