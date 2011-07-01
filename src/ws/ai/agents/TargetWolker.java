package ws.ai.agents;

import ws.ai.Ai;
import ws.ai.Target;
import ws.camera.UserCamera;
import ws.joint.BhoneSkin;
import ws.map.Y25Triangle;
import ws.tools.Shot;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Switch;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public class TargetWolker extends AtakWolker implements Target{

    private final float targetRadius;
    private final float targetHeight;
    private final Shape3D s;
    private volatile float live;

    public TargetWolker(float targetRadius, float targetHeight, float live, Shape3D s,
                        Shot shot, Point3f shotSource, Point3f lookAtSource, float lookDistance, float lookAngle, /* float criticalLookDistance, */
                        BhoneSkin bs, float frameWindow,
                        float colisionRadius,
                        Y25Triangle actualTriangle, Vector3f startPosition, float startAngle, float speedA, float rotateA, float rotateCicleA, float maxV,
                        BranchGroup pickNode, float distanceForce, Switch sw) {

        super(shot, shotSource, lookAtSource, lookDistance, lookAngle,/* criticalLookDistance, */
              bs, frameWindow,
              colisionRadius,
              actualTriangle, startPosition, startAngle, speedA, rotateA, rotateCicleA, maxV,
              pickNode, distanceForce, sw);

        this.targetRadius = targetRadius;
        this.targetHeight = targetHeight;
        this.live = live;
        this.s = s;
    }

    @Override
    public float getTargetRadius() {
        return targetRadius;
    }

    @Override
    public final void getTargetPoint(Tuple3f t) {
        synchronized (colPoint){
            t.set(colPoint.x, colPoint.y + this.targetHeight, colPoint.z);
        }
    }

    @Override
    public void getTargetPoint(Tuple3d t) {
        synchronized (colPoint){
            t.set(colPoint.x, colPoint.y + this.targetHeight, colPoint.z);
        }
    }

    @Override
    public final boolean isTargetActive() {
        return live > 0f;
    }

    private final Point3f userPosition = new Point3f();

    @Override
    public final void hit(float power) {
        live -= power;
        if(live <= 0f){
            s.setUserData(null);
            Ai.removeTargetWolker(this); // je dead dalej ai nezaujima
        }
        else{
            synchronized (this){
                ataking = true;
            }
            Ai.userDetected();
            
            UserCamera.getUserPositionTo(this.userPosition);
            float distX = this.position.x - userPosition.x;
            float distZ = this.position.z - userPosition.z;
            this.moveTo(null, new Float[]{((float)Math.atan2(distX, distZ))+ PIf}, false);
        }
    }

    private float deadTime = -1f;

/*    @Override
    public boolean testColision(Tuple3f a, float r) {
        return live > 0f && super.testColision(a, r);
    } */

    @Override
    protected boolean process(float time, float duration, boolean force) {
        if(force && live <= 0f){
            if(deadTime < 0f){
                deadTime = predTime;
                this.bs.setNewValues(DEAD);
            }

            boolean daing;

            float relativeTime = (time - this.deadTime) / this.frameWindow;
            if(relativeTime > 1f){
                relativeTime = 1f;
                daing = false;
            }else daing = true;
            
            bs.update(relativeTime, motTrans);
            this.shot.processFire(time);
            
            return daing;
        }else{
            return super.process(time, duration, force);
        }
    }

    private static final int DEAD = 7;
}
