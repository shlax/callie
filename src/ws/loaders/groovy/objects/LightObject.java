package ws.loaders.groovy.objects;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Light;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import java.util.Map;

public abstract class LightObject extends NodeObject {
    private final BoundingSphere maxRegion = new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE);
    private final Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

    protected LightObject(Object value, Map attributes) {
        super(value, attributes);
    }

    /*private BoundingSphere region = null;
    public void setRegion(BoundingSphere region) {
        this.region = region;
    } */

    private Color3f color = null;
    public final void setColor(Color3f color) {
        this.color = color;
    }

    public final void setR(float r){
        if(color == null)color = new Color3f(white);
        color.setX(r);
    }

    public final void setG(float r){
        if(color == null)color = new Color3f(white);
        color.setY(r);
    }

    public final void setB(float r){
        if(color == null)color = new Color3f(white);
        color.setZ(r);
    }

    @Override
    public Light getNode() {
        Light l = getLight(color == null ? white :color);
        l.setInfluencingBounds(this.maxRegion);
        return l;
    }

    protected abstract Light getLight(Color3f c);
}
