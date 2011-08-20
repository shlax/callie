package ws.loaders.groovy.objects;


import ws.loaders.groovy.FactoryElement;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public abstract class Tuple extends FactoryElement {

    protected final float x;
    protected final float y;
    protected final float z;

    public Tuple(float x, float y, float z, Object value, Map attributes) {
        super(value, attributes);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final Point3f getPoint3f(){
        return new Point3f(x, y, z);
    }

    public final Vector3f getVector3f(){
        return new Vector3f(x, y, z);
    }

}
