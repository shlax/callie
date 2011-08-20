package ws.loaders.groovy.objects;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import java.util.Map;

public abstract class Interpolator extends TransformGroupObject{
    protected static final BoundingSphere bound = new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE);

    protected Interpolator(Object value, Map attributes) {
        super(value, attributes);
    }

    private Alpha alpha = null;
    public void setAlpha(Alpha alpha) {
        this.alpha = alpha;
    }
    protected Alpha getAlpha(){
        if(alpha != null)return alpha;
        alpha = new Alpha();
        return alpha;
    }

    protected Transform3D getAxis() {
        if (transform3D != null) return transform3D;
        if(transformObject != null) transform3D = transformObject.getTransform3D();
        return transform3D;
    }

    protected final TransformGroup getGroup() {
        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        return tg;
    }

}
