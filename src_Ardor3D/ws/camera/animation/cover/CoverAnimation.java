package ws.camera.animation.cover;

import wa.Gui;
import ws.ai.Target;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.awt.event.MouseEvent;

public final class CoverAnimation {

    private final Point3f source;
    private final float sourceAnge;

    private final float sourceRadius;

    private final Point3f targetPosition;

    private final Point3f shotSource;
    private final float shotAnge;
    private final float shotAngeTolerantion;

    private final float keyFrameRatio;
    private final CoverKeyFrame[] frames;

    private final float side; // = 1/-1;

    //private final float animLen;

    public CoverAnimation(Point3f source, float sourceAnge, float sourceRadius, Point3f shotSource, float shotAnge, float shotAngeTolerantion, float keyFrameRatio, CoverKeyFrame[] frames, float side, Point3f targetPosition) {
        this.source = source;
        this.sourceAnge = sourceAnge;
        this.sourceRadius = sourceRadius;
        this.shotSource = shotSource;
        this.shotAnge = shotAnge;
        this.keyFrameRatio = keyFrameRatio;
        this.frames = frames;
        this.shotAngeTolerantion = shotAngeTolerantion;
        this.targetPosition = targetPosition;

        this.side = side;

        //System.out.println(shotAnge);
        //System.out.println("shotAnge "+shotAnge);

      //  animLen = keyFrameRatio*((float)frames.length);
    }

    // -------------------------------------------------------------------------------------------------------

    private int keyFrameIndex = -1;
    private float frameStartTime = 0f;

    //private float startTime = 0f;
    private Target t = null;
    private Target sT = null;

    public final Target getTarget(){
        return sT;
    }

    private boolean init = false;
    private final static Point3f realStartPosition = new Point3f();

    private final static Point3f tragetPosition = new Point3f();

    protected final static float PIf = (float)Math.PI;

    public final float animation(float time, Vector3f objectPosition){
        // change shot logic
        if(keyFrameIndex == -1){
            init = true;
            realStartPosition.set(objectPosition);

            this.frameStartTime = time;
            //this.frameStartTime = time;
            //this.realStartPosition.set(objectPosition);

            this.keyFrameIndex = 0;
            this.frames[0].updateBhoneSkyn();

            return 0f;
        }else if(keyFrameIndex == 0){
            float actDur = time - this.frameStartTime;
            if(actDur >= keyFrameRatio){
                if(init){
                    init = false;
                    objectPosition.set(this.source);
                }

                if(! Gui.buttons.contains(MouseEvent.BUTTON3) ){
                    keyFrameIndex = this.frames.length - 1;
                    this.frameStartTime = time;
                    this.frames[this.frames.length - 1].updateBhoneSkyn();

                    return 0f;
                }

                //TODO: shot logic
                t = Gui.getPicked();
                if(t != null && t.isTargetActive()){
                    //TODO angle check

                    t.getTargetPoint(tragetPosition);

                    float distX = tragetPosition.getX() - source.getX();
                    float distZ = tragetPosition.getZ() - source.getZ();

                    float dstAngle = (float)Math.atan2(distX, distZ);
                    dstAngle += PIf;

                   // System.out.println("angle "+dstAngle);

                    if(shotAnge > dstAngle) distX = shotAnge - dstAngle;
                    else distX = PIf*2f - ( dstAngle - shotAnge );

                    // v protismere
                    if(shotAnge > dstAngle) distZ = PIf*2f - ( shotAnge - dstAngle );
                    else distZ = dstAngle - shotAnge;

                    // minimum
                    distX = Math.min(distX, distZ);

                   // System.out.println("minimum "+distX);

                    if(distX < shotAngeTolerantion){
                        keyFrameIndex = 1;
                        this.frames[1].updateBhoneSkyn();

                        this.frameStartTime = time;
                        return 0f;
                    }
                }

                return 1f;
            }else{
                float intr = actDur / this.keyFrameRatio;
                if(init) objectPosition.interpolate(CoverAnimation.realStartPosition, source, intr);
                return intr;
            }
        }else{
            //float actDur = time - this.startTime;
            float actDur = time - this.frameStartTime;
            if(actDur > this.keyFrameRatio){
                if(this.keyFrameIndex == this.frames.length - 1){
                    this.keyFrameIndex = -1;

                //    t = null;
                //    sT = null;

                    return -1f;
                }

                if(this.frames[this.keyFrameIndex].isShot()){
                    if(!t.isTargetActive() || !Gui.buttons.contains(MouseEvent.BUTTON3) ){
                        this.keyFrameIndex ++;
                        t = null;
                        sT = null;

                    }else sT = t;
                }else this.keyFrameIndex ++;

                if(this.keyFrameIndex == this.frames.length - 1) this.keyFrameIndex = 0;

                this.frameStartTime += this.keyFrameRatio;
                actDur -= this.keyFrameRatio;



                this.frames[this.keyFrameIndex].updateBhoneSkyn();

            }
            return actDur / this.keyFrameRatio;

            //}
        }
    }

    // -------------------------------------------------------------------------------------------------------

    public final boolean isActive(Tuple3f t){
        float dx = source.getX()-t.getX();
        float dy = source.getY()-t.getY();
        float dz = source.getZ()-t.getZ();
        if ((dx*dx+dy*dy+dz*dz) > this.sourceRadius) return false;

        return true;
    }

    public final float getSourceAnge() {
        return sourceAnge;
    }

    public final float getSide(){
        return side;
    }

    public final Point3f getShotSource() {
        return shotSource;
    }

    public final Point3f getTargetPosition() {
        if(keyFrameIndex == -1) return null;
        if(this.frames[this.keyFrameIndex].isShot()) return targetPosition;
        return null;
    }
}
