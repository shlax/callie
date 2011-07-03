package ws.loaders.groovy.objects;

import groovy.lang.Closure;
import ws.camera.areas.ActionArea;
import ws.camera.areas.BoxColision;
import ws.camera.areas.CircleColision;
import ws.camera.areas.Colision;
import ws.loaders.groovy.ClosureSceneAction;
import ws.loaders.groovy.FactoryElement;

import javax.vecmath.Point3f;
import java.util.Map;

public final class ColisionObject extends FactoryElement {

    public ColisionObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private Point3f point = null; // new Point3f();
    private float radius = 0.25f;

    public final void setPoint(Point3f point) {
        this.point = point;
    }

    public final void setRadius(Float radius) {
        this.radius = radius;
    }

    private float minX = 0;
    private float minY = 0;
    private float minZ = 0;
    private float maxX = 0;
    private float maxY = 0;
    private float maxZ = 0;

    public void setMinX(float minX) {
        this.minX = minX;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public void setMinZ(float minZ) {
        this.minZ = minZ;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public void setMaxZ(float maxZ) {
        this.maxZ = maxZ;
    }

    private Closure onEnter = null;
    private Closure onExit = null;

    public void setOnEnter(Closure onEnter) {
        this.onEnter = onEnter;
    }

    public void setOnExit(Closure onExit) {
        this.onExit = onExit;
    }

    public boolean isAction(){
        return onEnter != null || onExit != null;
    }

    public final ActionArea getActionArea(){
        return new ActionArea(new ClosureSceneAction(onEnter, onExit), getColision());
    }

    public final Colision getColision(){
        return point != null ? new CircleColision(point, radius) : new BoxColision(minX, maxX, minY, maxY, minZ, maxZ);
    }

}
