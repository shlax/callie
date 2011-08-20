package ws.loaders.groovy.objects;

import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class DirectionalLightObject extends LightObject {
        private final Vector3f defDirection = new Vector3f(-1f, -1f, -1f);

    public DirectionalLightObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private Vector3f direction = null;
    public final void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public final void setX(float x){
        if (direction == null) direction = new Vector3f(defDirection);
        direction.setX(x);
    }

    public final void setY(float x){
        if (direction == null) direction = new Vector3f(defDirection);
        direction.setY(x);
    }

    public final void setZ(float x){
        if (direction == null) direction = new Vector3f(defDirection);
        direction.setZ(x);
    }

    @Override
    protected DirectionalLight getLight(Color3f c) {
        return new DirectionalLight(c, this.direction == null ? defDirection : this.direction);
    }
}
