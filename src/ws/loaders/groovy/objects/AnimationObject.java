package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;

import javax.vecmath.Point3f;
import java.util.ArrayList;
import java.util.Map;

public final class AnimationObject extends FactoryElement {

    public AnimationObject(Object value, Map attributes) {
        super(value, attributes);
        
    }

    private final ArrayList<AnimationFrameObject> animationFrameObjects = new ArrayList<AnimationFrameObject>();
    public final void addAnimationFrameObject(AnimationFrameObject o){
        this.animationFrameObjects.add(o);
    }

    public final ArrayList<AnimationFrameObject> getAnimationFrameObjects() {
        return animationFrameObjects;
    }

    private Float keyFrameRatio = 175f;
    private Float sourceRadius = 0.5f;

    private Float sourceAngleTolerantion = 90f;

    private Boolean removeAfterPlay = false;

    public final Boolean isRemoveAfterPlay() {
        return removeAfterPlay;
    }

    public final void setRemoveAfterPlay(Boolean removeAfterPlay) {
        this.removeAfterPlay = removeAfterPlay;
    }

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
        this.keyFrameRatio = keyFrameRatio;
    }
    
    private Point3f destination;

    public final Point3f getDestination() {
        return destination;
    }

    public final void setDestination(Point3f destination) {
        this.destination = destination;
    }

}
