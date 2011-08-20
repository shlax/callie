package ws.camera;

import com.ardor3d.scenegraph.Node;
import wa.Gui;
import ws.camera.animation.Animation;
import ws.camera.animation.cover.CoverAnimation;
import ws.camera.areas.Colision;
import ws.map.Y25Map;
import ws.map.Y25Triangle;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CoverCamera extends AnimationCamera{

    private final ArrayList<CoverAnimation> covers;

    public CoverCamera(ArrayList<CoverAnimation> covers,
                      ArrayList<Animation> anim,
                      float heightUp, float heightDown, Character c, float jumpSpeed, float jumpSpeedStart, float runSpeed, float walkSpeed, float actionDuration,
                      float userColideRadius, float angleAceleration, float speedAceleration, Colision[] col, Y25Map mapa,
                      Node colide, Vector3f startPosition, Y25Triangle startTriangle, float minDistance, float maxDistance, float defMaxMinDistance, float height, float maxSide) {
        super(anim,
              heightUp, heightDown, c, jumpSpeed, jumpSpeedStart, runSpeed, walkSpeed, actionDuration,
              userColideRadius, angleAceleration, speedAceleration, col, mapa,
              colide, startPosition, startTriangle, minDistance, maxDistance, defMaxMinDistance, height, maxSide);

        this.covers = covers;

    }

    private CoverAnimation actual = null;

    public static boolean isActionPosible = false;

    @Override
    protected boolean process(float time, float duration) {
        boolean isAnimation = actual != null;

        if(!isAnimation){

            if(this.status == RIFLE_STAND && this.newStatus == RIFLE_STAND){
                CoverAnimation a = null;

                for(CoverAnimation aTmp : this.covers) if(aTmp.isActive(this.objectPosition)){
                    a = aTmp;
                    break;
                }

                if( a!= null && Gui.keys.contains(KeyEvent.VK_E) ){
                    isActionPosible = true;
                    this.reqAngele = a.getSourceAnge();

                    this.reqV = 0f;
                    this.v = 0f;

                    //this.status = STAND;
                    //this.newStatus = STAND;

                    this.actual = a;
                    sideSign = a.getSide();
                    //System.out.println(sideSign);
                    synchronized (covers){
                        shotPosition = a.getShotSource();
                      //  targetPosition = a.getTargetPosition();
                    }

                    isAnimation = true;
                }else isActionPosible = a != null;

                //if(isAnimation && actual.removeAfterPlay()) this.animations.remove(actual);
            }else isActionPosible = false;
        }

        if(isAnimation){
            isActionPosible = true;

            if(this.objectY != reqAngele){ // angle
                // v smere
                float dx;
                if(reqAngele > objectY) dx = reqAngele - objectY;
                else dx = PIf*2f - ( objectY - reqAngele );

                // v protismere
                float dy;
                if(reqAngele > objectY) dy = PIf*2f - ( reqAngele - objectY );
                else dy = objectY - reqAngele;

                float dz = Math.min(dx, dy);
                float s = angleAceleration*duration;

                if(dz < s) this.objectY = reqAngele;
                else this.objectY = normalizeAngle(this.objectY, (dx < dy ? 1f : -1f)*s ); // += (dx < dy ? 1f : -1f)*(dz < s ? dz : s );
            }

            float rt = this.actual.animation(time, this.objectPosition);
            if( rt < 0f ){
                //Y25Triangle triangle = this.actual.getEndTriangle();
                UserCamera.setUserPosition(this.objectPosition);
                //this.mapa.setLastY25Triangle(triangle);

                this.actual = null;

                sideSign = 1f;
                synchronized (covers){
                    shotPosition = null; // a.getShotSource();
                 //   targetPosition = null;
                }

                this.start = time;
                this.predTime = time;

                this.motTrans.set(this.objectPosition);
                this.angTrans.rotY(objectY+PIf); // look back
                this.motTrans.mul(this.angTrans);

                c.process(1f, time,  this.motTrans, null);
                c.setNewPose(RIFLE_STAND);
            }else{
                this.motTrans.set(this.objectPosition);
                this.angTrans.rotY(objectY+PIf); // look back
                this.motTrans.mul(this.angTrans);

                c.process(rt, time,  this.motTrans, actual.getTarget(), shotPosition);
            }

            return true;
        }else return super.process(time, duration);
    }

    private Point3f shotPosition = null;
    protected final Tuple3f getShotPosition() {
        synchronized (covers){
            //System.out.println(shotPosition);
            return shotPosition;
        }
    }

    //private Point3f targetPosition = null;
    protected final Tuple3f getTargetPosition(){
        if(actual != null) return actual.getTargetPosition();
        return null;
    }
}
