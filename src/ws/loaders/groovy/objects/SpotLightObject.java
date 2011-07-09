package ws.loaders.groovy.objects;

import javax.media.j3d.SpotLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class SpotLightObject extends LightObject {

    public SpotLightObject(Object value, Map attributes) {
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

    private Vector3f direction = null;
    public final void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    private Float spreadAngle = null; // loadFloat(e, "spreadAngle", (float)Math.PI);
    public final void setSpreadAngle(Float spreadAngle) {
        if(spreadAngle != null) this.spreadAngle = spreadAngle;
    }

    private Float concentration = null; //  loadFloat(e, "concentration", 0.0f);
    public final void setConcentration(Float concentration) {
        if(concentration != null)this.concentration = concentration;
    }

    @Override
    protected final SpotLight getLight(Color3f c) {
        return new SpotLight(c, position == null? new Point3f() : position, attenuation == null ? new Point3f(1f,1f,1f): attenuation, direction == null ? new Vector3f(-1f, -1f, -1f) :direction, spreadAngle == null ? (float)Math.PI : spreadAngle , concentration == null ? 0f : concentration);
    }
}
