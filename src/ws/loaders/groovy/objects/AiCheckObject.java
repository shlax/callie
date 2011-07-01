package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;

import javax.vecmath.Point3f;
import java.util.Map;

public final class AiCheckObject extends FactoryElement {

    public AiCheckObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private Point3f point;
    public final void setPoint(Point3f point) {
        this.point = point;
    }

    public final Point3f getPoint() {
        return point;
    }
}
