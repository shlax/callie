package ws.loaders.groovy.objects;

import groovy.lang.Closure;
import ws.camera.areas.*;
import ws.loaders.groovy.ClosureSceneAction;
import ws.loaders.groovy.FactoryElement;
import ws.tools.controls.OnOffObject;

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

    private Closure<?> onEnter = null;
    private Closure<?> onExit = null;

    public void setOnEnter(Closure<?> onEnter) {
        this.onEnter = onEnter;
    }

    public void setOnExit(Closure<?> onExit) {
        this.onExit = onExit;
    }

    private boolean isAction(){
        return onEnter != null || onExit != null;
    }

    private Colision ret = null;

    public final Colision getColision(){
        if(ret == null){
            Colision cc = point != null ? new CircleColision(point, radius) : new BoxColision(minX, maxX, minY, maxY, minZ, maxZ);
            if(isAction()) cc = new ActionArea(new ClosureSceneAction(onEnter, onExit), cc);
            ret = active ? new ActiveCilision(cc) : cc;
        }
        return ret;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean enabled = true;
    private boolean active = false;

    public final OnOffObject control(){
        active = true;
        ActiveCilision actrLoc = (ActiveCilision)getColision();
        if(!enabled || isAction()) actrLoc.setActive(false);
        return new OnOffObject( actrLoc );
    }

}
