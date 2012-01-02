package ws.camera;

import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.Node;
import ws.ai.Target;
import ws.camera.animation.Animation;
import ws.camera.animation.cover.CoverAnimation;
import ws.camera.areas.Colision;
import ws.map.Y25Map;
import ws.map.Y25Triangle;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

public final class UserCamera extends KillCamera implements Target {
    public static UserCamera TARGET;

    public static final String CHARACTER = "CHARACTER";
    //public static final float USER_TARGET = 1.5f;

    @Override
    public final float getTargetRadius() {
        return 0.33f;
    }

    public UserCamera(float shotHeight,// HitArea areas[],
                      Mesh shape,
                      ArrayList<CoverAnimation> covers,
                      ArrayList<Animation> anim,
                      float heightUp, float heightDown, Character c, float jumpSpeed, float jumpSpeedStart, float runSpeed, float walkSpeed, float actionDuration,
                      float userColideRadius, float angleAceleration, float speedAceleration, Colision[] col, Y25Map mapa,
                      Node colide, Vector3f startPosition, Y25Triangle startTriangle, float minDistance, float maxDistance, float defMaxMinDistance, float height, float maxSide) {
        super(// areas,
              shape,  
              covers,
              anim,
              heightUp, heightDown, c, jumpSpeed, jumpSpeedStart, runSpeed, walkSpeed, actionDuration,
              userColideRadius, angleAceleration, speedAceleration, col, mapa,
              colide, startPosition, startTriangle, minDistance, maxDistance, defMaxMinDistance, height, maxSide);

        this.shotHeight = shotHeight;

        TARGET = this;
    }

    private static volatile float live = 1.5f;

    @Override
    public final void hit(float power) {
        live -= multiply*power;
    }

    @Override
    public final boolean isTargetActive() {
        return live > 0f;
    }
    
    public final static boolean isTargetActiveStatic() {
        return live > 0f;
    }

    public static final float getLive(){
        return live;
    }

    private static float multiply = 1f;
    public static void reset(float l){
        multiply = 1f/l;
        //if(TARGET != null)TARGET.actual = null;
        AnimationCamera.isActionPosible = false;
        CoverCamera.isActionPosible = false;

        //System.out.println(multiply);
        wrongMove = false;
        live = 1.5f;
        userPosition.set(0, 0, 0);
        userY25Triangle = null;
    }

    public static final void heal(float power) {
        live = Math.min(power+live, 1.5f);
    }

    // -------------------------------------------------------------

    private static boolean wrongMove = false;

    public static final boolean isWrongMove(){
        return wrongMove;
    }

    public static final void wrongMove(){
        wrongMove = true;
        live = -1f;
    }

    // -------------------------------------------------------------

    private static boolean hasWeapon = false;
    public static final void equiptWeapon(boolean status){
        hasWeapon = status;
    }

    public static final boolean hasWeapon(){
        return hasWeapon; 
    }

    // --------------------------------------------------------------------------------------------------------------------------

    public final void getLookTargetPoint(Tuple3f t) {
        c.getTarget(t);
    }

    @Override
    public final void getTargetPoint(Tuple3f t) {
        Tuple3f q = getTargetPosition();
        if(q != null){
            t.set(q);
        }else{
            synchronized (userPosition){
                t.set(userPosition.getX(), userPosition.getY() + targerHeight, userPosition.getZ());
            }
        }
    }

/*    @Override
    public void getTargetPoint(Tuple3d t) {
        synchronized (userPosition){
            t.set(userPosition.x, userPosition.y + targerHeight, userPosition.z);
        }
    } */


/*    @Override
    public static final void getTargetPoint(Tuple3d t) {
        synchronized (userPosition){
            t.set(userPosition.x, userPosition.y + targerHeight, userPosition.z);
        }
    } */


    private static final Point3f userPosition = new Point3f();
    private static volatile Y25Triangle userY25Triangle = null;

    public static final void setUserPosition(Y25Triangle t, Tuple3f p){
        userY25Triangle = t;
        synchronized (userPosition){
            userPosition.set(p);
            //userPosition.x = p.x;
            //userPosition.y = p.y;
            //userPosition.z = p.z;
        }
	}

    public static final void setUserPosition(Tuple3f p){
        //userY25Triangle = t;
        synchronized (userPosition){
            userPosition.set(p);
            //userPosition.x = p.x;
            //userPosition.y = p.y;
            //userPosition.z = p.z;
        }
	}

    /*public static final float getUserHeight(){
        return targerHeight;
	}*/

	public static final void getUserPositionTo(Tuple3f p){
        synchronized (userPosition){
            p.set(userPosition);
        }
	}

    private final float shotHeight;

    public final void getUserShotPositionTo(Tuple3f p){
        Tuple3f q = getShotPosition();
        if(q != null){
            p.set(q);
        }else {
            synchronized (userPosition){
                p.set(userPosition.getX(), userPosition.getY() + shotHeight, userPosition.getZ());
            }
        }
	}

    public static final Y25Triangle getUserY25Triangle(){
        return userY25Triangle;
    }


}
