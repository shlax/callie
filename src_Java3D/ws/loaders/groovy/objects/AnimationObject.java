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

    private final ArrayList<BhoneSkinFrameObject> animationFrameObjects = new ArrayList<BhoneSkinFrameObject>();
    public final void addAnimationFrameObject(BhoneSkinFrameObject o){
        this.animationFrameObjects.add(o);
    }

    public final ArrayList<BhoneSkinFrameObject> getAnimationFrameObjects() {
        return animationFrameObjects;
    }

    private Float keyFrameRatio = 175f;
    private Float sourceRadius = 0.5f;

    private Float sourceAngleTolerantion = 90f;


    public final Float getSourceAngleTolerantion() {
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
        this.keyFrameRatio = keyFrameRatio * 1000f;
    }
    
    private Point3f destination;

    public final Point3f getDestination() {
        return destination == null ? new Point3f() : destination;
    }

    public final void setDestination(Point3f destination) {
        this.destination = destination;
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
