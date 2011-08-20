package ws.camera;

import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.Node;
import ws.camera.animation.Animation;
import ws.camera.animation.cover.CoverAnimation;
import ws.camera.areas.Colision;
import ws.map.Y25Map;
import ws.map.Y25Triangle;

import javax.vecmath.Vector3f;
import java.util.ArrayList;

public class KillCamera extends CoverCamera{

    private final Mesh shape;

    public KillCamera(Mesh shape,
                      ArrayList<CoverAnimation> covers,
                      ArrayList<Animation> anim,
                      float heightUp, float heightDown, Character c, float jumpSpeed, float jumpSpeedStart, float runSpeed, float walkSpeed, float actionDuration,
                      float userColideRadius, float angleAceleration, float speedAceleration, Colision[] col, Y25Map mapa,
                      Node colide, Vector3f startPosition, Y25Triangle startTriangle, float minDistance, float maxDistance, float defMaxMinDistance, float height, float maxSide) {
        super(covers,
              anim,
              heightUp, heightDown, c, jumpSpeed, jumpSpeedStart, runSpeed, walkSpeed, actionDuration,
              userColideRadius, angleAceleration, speedAceleration, col, mapa,
              colide, startPosition, startTriangle, minDistance, maxDistance, defMaxMinDistance, height, maxSide);

        this.shape = shape; 

    }

    private float deadTime = -1f;

    @Override
    protected boolean process(float time, float duration) {
        if(deadTime > 0f){            
            this.motTrans.set(this.objectPosition);
            this.angTrans.rotY(objectY+PIf); // look back
            this.motTrans.mul(this.angTrans);

            float relativeTime = (time - this.deadTime) / this.actionDuration;
            if(relativeTime > 1f) relativeTime = 1f;

            c.process(relativeTime, time,  this.motTrans, null);

            return false;
        }else if(UserCamera.isWrongMove()){
            shape.setUserData(null);
            deadTime = time;

            this.motTrans.set(this.objectPosition);
            this.angTrans.rotY(objectY+PIf); // look back
            this.motTrans.mul(this.angTrans);

            c.setNewPose(DEAD_JUMP);

            float relativeTime = (time - this.deadTime) / this.actionDuration;
            if(relativeTime > 1f) relativeTime = 1f;

            c.process(relativeTime, time,  this.motTrans, null);

            return false;
        }else if(!UserCamera.isTargetActiveStatic()){
            shape.setUserData(null);
            deadTime = time;

            this.motTrans.set(this.objectPosition);
            this.angTrans.rotY(objectY+PIf); // look back
            this.motTrans.mul(this.angTrans);

            c.setNewPose(DEAD_SHOT);

            float relativeTime = (time - this.deadTime) / this.actionDuration;
            if(relativeTime > 1f) relativeTime = 1f;

            c.process(relativeTime, time,  this.motTrans, null);

            return false;
        }else return super.process(time, duration);
    }

    private static final int DEAD_SHOT = 20;
    private static final int DEAD_JUMP = 21;

}
