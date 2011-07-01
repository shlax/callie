package ws.loaders.groovy.objects;

import javax.media.j3d.PointLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import java.util.Map;

public final class PointLightObject extends LightObject {

    public PointLightObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private Point3f position = null;
    public final void setPosition(Point3f position) {
        this.position = position;
    }

    private Point3f attenuation = null;
    public final void setAttenuation(Point3f attenuation) {
        this.attenuation = attenuation;
    }

    @Override
    protected PointLight getLight(Color3f c) {
        return new PointLight(c, position, attenuation);
    }
}
