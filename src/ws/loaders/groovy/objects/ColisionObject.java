package ws.loaders.groovy.objects;

import ws.camera.areas.CircleColision;
import ws.camera.areas.Colision;
import ws.loaders.groovy.FactoryElement;

import javax.vecmath.Point3f;
import java.util.Map;

public final class ColisionObject extends FactoryElement {

    public ColisionObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private Point3f point = null; // new Point3f();
    private Float radius = 0.25f;

    public final void setPoint(Point3f point) {
        this.point = point;
    }

    public final void setRadius(Float radius) {
        this.radius = radius;
    }

    public final Colision getColision(){
        return new CircleColision(point, radius);
    }

}
