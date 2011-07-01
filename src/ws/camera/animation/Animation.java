package ws.camera.animation;

import ws.map.Y25Triangle;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class Animation {

    private final Y25Triangle endTriangle;
    private final float sourceAnge;

    private final Point3f source;
    private final float sourceRadius;

    private final Point3f destination;
    
    private final float keyFrameRatio;
    private final KeyFrame[] frames;

    private final float sourceAngeTolerance;

    private final float animationDuration;

    public Animation(boolean removeAfterPlay, Point3f source, float sourceRadius, float sourceAnge, float sourceAngeTolerance, Point3f destination, Y25Triangle endTriangle, float keyFrameRatio, KeyFrame[] frames) {
        this.source = source;
        this.sourceAnge = sourceAnge;
        this.sourceRadius = sourceRadius*sourceRadius;
        this.sourceAngeTolerance = sourceRadius*sourceAngeTolerance;

        this.destination = destination;
        this.endTriangle = endTriangle;

        this.frames = frames;
        this.keyFrameRatio = keyFrameRatio;

        animationDuration = keyFrameRatio*((float)frames.length);
        this.removeAfterPlay = removeAfterPlay;
    }

    private final boolean removeAfterPlay;
    public final boolean removeAfterPlay(){
        return removeAfterPlay;
    }

    public final Y25Triangle getEndTriangle() {
        return endTriangle;
    }

    public final float getSourceAnge() {
        return sourceAnge;
    }

    private final float PIf = (float)Math.PI;

    public final boolean isActive(Tuple3f t, float objectY){
        float dx = source.x-t.x;
        float dy = source.y-t.y;
        float dz = source.z-t.z;
        if ((dx*dx+dy*dy+dz*dz) > this.sourceRadius) return false;

        //System.out.println(sourceAnge + " "+ objectY);

        // v smere
        if(sourceAnge > objectY) dx = sourceAnge - objectY;
        else dx = PIf*2f - ( objectY - sourceAnge );

        // v protismere
        if(sourceAnge > objectY) dy = PIf*2f - ( sourceAnge - objectY );
        else dy = objectY - sourceAnge;

        // minimum
        dz = Math.min(dx, dy);
        //System.out.println(dz+ " "+sourceAngeTolerance);

        if (dz > sourceAngeTolerance) return false;

        keyFrameIndex = -1;

        return true;
    }
    
    private int keyFrameIndex = -1;
    private float frameStartTime = 0f;

    private float startTime = 0f;
    private final Point3f realStartPosition = new Point3f();

    public final float animation(float time, Vector3f objectPosition){
        if(keyFrameIndex == -1){
            this.startTime = time;
            this.frameStartTime = time;
            this.realStartPosition.set(objectPosition);
            
            this.keyFrameIndex = 0;
            this.frames[0].updateBhoneSkyn();
            return 0f;
        }else{
            float actDur = time - this.startTime;
            if(actDur >= this.animationDuration){
                objectPosition.set(this.destination);
                return -1f; // skoncil
            }else{
                objectPosition.interpolate(this.realStartPosition, destination, actDur/this.animationDuration);
                
                actDur = time - this.frameStartTime;
                if(actDur > this.keyFrameRatio){
                    if(this.keyFrameIndex < this.frames.length) this.keyFrameIndex ++;
                    this.frameStartTime += this.keyFrameRatio;
                    actDur -= this.keyFrameRatio;

                    this.frames[this.keyFrameIndex].updateBhoneSkyn();                    
                }
                return actDur / this.keyFrameRatio;
            }
        }        
    }
}
