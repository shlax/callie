package ws.loaders.groovy.objects;

import groovy.lang.Closure;
import ws.tools.controls.OnTime;

import javax.media.j3d.BoundingSphere;
import javax.vecmath.Point3d;
import java.util.Map;

public final class BehaviorObj extends NodeObject {
    private static final BoundingSphere bound = new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE);

    public BehaviorObj(Object value, Map attributes) {
        super(value, attributes);
    }

    private Closure closure;
    public final void setClosure(Closure closure) {
        this.closure = closure;
    }

    @Override
    public final OnTime getNode() {
        OnTime tmp = new OnTime(closure);
        tmp.setSchedulingBounds(bound);
        return tmp;
    }
}
