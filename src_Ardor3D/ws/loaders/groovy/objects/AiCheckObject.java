package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;
import ws.tools.controls.Location;

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

    private Location location = null;
    public final Location isLocation(){
        return location;
    }
    public final Location location(){
        if(location == null) location = new Location();
        return location;
    }
}
