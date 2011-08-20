package ws.loaders.groovy.objects;

import groovy.lang.Closure;
import ws.loaders.groovy.FactoryElement;

import javax.vecmath.Point3f;
import java.util.ArrayList;
import java.util.Map;

public final class AnimationObject extends FactoryElement {

    public AnimationObject(Object value, Map attributes) {
        super(value, attributes);
        
    }

    private float shotAngle = 0f;
    public final float getShotAngle() {
        return shotAngle * (float)(Math.PI/180d);
    }
    public final void setShotAngle(Float shotAngle) {
        this.shotAngle = shotAngle;
    }

    private final ArrayList<BhoneSkinFrameObject> animationFrameObjects = new ArrayList<BhoneSkinFrameObject>();
    public final void addAnimationFrameObject(BhoneSkinFrameObject o){
        this.animationFrameObjects.add(o);
    }

    public final ArrayList<BhoneSkinFrameObject> getAnimationFrameObjects() {
        return animationFrameObjects;
    }

    public final boolean isShot() {
        for(BhoneSkinFrameObject t : animationFrameObjects)if(t.isShot()) return true;
        return false;
    }

    private Float keyFrameRatio = 175f;
    private Float sourceRadius = 0.5f;



    private Float side = 1f;
    public final Float getSide() {
        return side;
    }
    public final void setSide(Float side) {
        this.side = side;
    }

    private Float sourceAngleTolerantion = null;// 90f;

    public final Float getSourceAngleTolerantion() {
        if(sourceAngleTolerantion == null) sourceAngleTolerantion = isShot() ? 45f : 90f;
        return sourceAngleTolerantion * (float)(Math.PI/180d);
    }

    public final void setSourceAngleTolerantion(Float sourceAngleTolerantion) {
        this.sourceAngleTolerantion = sourceAngleTolerantion;
    }

    public final Float getSourceRadius() {
        return sourceRadius;
    }

    public final void setSourceRadius(Float sourceRadius) {
        this.sourceRadius = sourceRadius;
    }

    public final Float getKeyFrameRatio() {
        return keyFrameRatio;
    }

    public final void setKeyFrameRatio(Float keyFrameRatio) {
        //System.out.println(keyFrameRatio);
        this.keyFrameRatio = keyFrameRatio * 1000f;
    }
    
    private Point3f destination;
    public final Point3f getDestination() {
        return destination == null ? new Point3f() : destination;
    }
    public final void setDestination(Point3f destination) {
        this.destination = destination;
    }

    private Point3f targetPosition;
    public final Point3f getTargetPosition() {
        return destination == null ? new Point3f() : destination;
    }
    public final void setTargetPosition(Point3f destination) {
        this.targetPosition = destination;
    }

    private Closure onEnter = null;
    private Closure onExit = null;

    public final void setOnEnter(Closure onEnter) {
        this.onEnter = onEnter;
    }

    public final void setOnExit(Closure onExit) {
        this.onExit = onExit;
    }

    public final Closure getOnEnter() {
        return onEnter;
    }

    public final Closure getOnExit() {
        return onExit;
    }
}
