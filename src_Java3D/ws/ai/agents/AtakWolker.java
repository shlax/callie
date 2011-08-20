package ws.ai.agents;

import ws.ai.Ai;
import ws.camera.UserCamera;
import ws.joint.BhoneSkin;
import ws.map.Y25Triangle;
import ws.tools.Shot;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.Switch;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class AtakWolker extends BhoneSkinWolker{

    protected final Shot shot;
    private final Point3f shotSource;
    private final Point3f lookAtSource;

    private final float lookDistance;
    private final float lookAngleHorizontal;
    private final float lookAngleVertical;
    private final float shotDistance;

    protected AtakWolker(Shot shot, Point3f shotSource, Point3f lookAtSource, float lookDistance, float lookAngleHorizontal, float lookAngleVertical, float shotDistance,
                         BhoneSkin bs, float frameWindow,
                         float colisionRadius,
                         Y25Triangle actualTriangle, Vector3f startPosition, float startAngle, float speedA, float rotateA, float rotateCicleA, float maxV,
                         BranchGroup pickNode, float distanceForce, Switch sw) {
        super(bs, frameWindow,
              colisionRadius,
              actualTriangle, startPosition, startAngle, speedA, rotateA, rotateCicleA, maxV,
              pickNode, distanceForce, sw);

        this.shot = shot;
        this.shotSource = shotSource;
        this.lookAtSource = lookAtSource;

        this.lookDistance = lookDistance*lookDistance;
        this.shotDistance = shotDistance*shotDistance;
        this.lookAngleHorizontal = lookAngleHorizontal;
        this.lookAngleVertical = lookAngleVertical;

    }

    private final Point3f myPosition = new Point3f();
    private final Vector3f direction = new Vector3f();

    protected boolean ataking = false;
    protected Y25Triangle lastPos = null;

    public synchronized final boolean processing(){
        return this.ataking || super.processing();
    }

    /*public synchronized final boolean ataking(){
        return this.ataking;
    }*/

    public final synchronized void resetUserPosition(){
        lastPos = null;
    }

    public final synchronized boolean changeUserPosition(Y25Triangle t){
        if(ataking) return false;
        if(lastPos != t){
            lastPos = t;
            return true;
        }
        return false;
    }

    @Override
    protected boolean process(float time, float duration, boolean force) {

        if(force){
            float distX = this.position.x - userPosition.x;
            float distY = this.position.y/*+UserCamera.USER_TARGET*/ - userPosition.y;
            float distZ = this.position.z - userPosition.z;

            float vzdialenost = (distX*distX)+(distZ*distZ)+(distY*distY);
            if(vzdialenost < lookDistance){
                boolean see = false; //vzdialenost < this.criticalLookDistance;

                float dstAngle = (float)Math.atan2(distX, distZ);
                dstAngle += PIf;

                // v smere
                float dx;
                if(dstAngle > angle) dx = dstAngle - angle;
                else dx = PIf*2f - ( angle - dstAngle );

                // v protismere
                float dy;
                if(dstAngle > angle) dy = PIf*2f - ( dstAngle - angle );
                else dy = angle - dstAngle;

                if(Math.min(dx, dy) < lookAngleHorizontal){ // je spravne natoceny
                    this.motTrans.transform(lookAtSource, myPosition);
                    UserCamera.TARGET.getLookTargetPoint(userPosition);   //  += UserCamera.getUserHeight();
                    direction.sub(userPosition, myPosition);

                    dy = Math.abs(myPosition.y - userPosition.y);
                    dx = Math.abs((float)Math.asin(dy / direction.length()));
                    //System.out.println(myPosition);
                    //System.out.println(userPosition);

                   // System.out.println(dx+" "+lookAngleVertical);

                    if(dx < lookAngleVertical){
                        Node n = this.getColision(myPosition, direction);
                    //System.out.println(n);
                        see = (n != null && UserCamera.CHARACTER.equals(n.getUserData()));
                    }
                    //this.shot.fire(myPosition, UserCamera.TARGET, time);
                }

                if(see && UserCamera.isTargetActiveStatic()){

                    boolean canShot = vzdialenost < shotDistance;
                    if(canShot){

                        synchronized (this){
                            ataking = true;
                        }
                        Ai.userDetected();

                        this.moveTo(null, new Float[]{dstAngle}, false);
                        this.motTrans.transform(shotSource, myPosition);
                        this.shot.fire(myPosition, UserCamera.TARGET, time);
                    }else {
                        Ai.userDetected();
                    }

                }else{
                    boolean pred = ataking;
                    synchronized (this){
                        if(pred) lastPos = null;
                        ataking = false;
                    }
                    if(pred){
                        Ai.notifiAI();
                    }
                }

            }else{
                boolean pred = ataking;
                synchronized (this){
                    if(pred) lastPos = null;
                    ataking = false;
                }
                if(pred){
                    Ai.notifiAI();
                }
            }
            
            this.shot.processFire(time);
        }



        return super.process(time, duration, force);
    }

}
