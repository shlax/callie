package ws.ai.agents;

import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.extension.SwitchNode;
import ws.joint.BhoneSkin;
import ws.map.Y25Triangle;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

public class BhoneSkinWolker extends ColidePathWolker {

    protected final BhoneSkin bs;
    protected final float frameWindow;
    
    protected BhoneSkinWolker(BhoneSkin bs, float frameWindow,
                              float colisionRadius,
                              Y25Triangle actualTriangle, Vector3f startPosition, float startAngle, float speedA, float rotateA, float rotateCicleA, float maxV,
                              Node pickNode, float distanceForce, SwitchNode sw) {
        super(colisionRadius,
              actualTriangle, startPosition, startAngle, speedA, rotateA, rotateCicleA, maxV,
              pickNode, distanceForce, sw);

        this.bs = bs;
        this.frameWindow = frameWindow;

        this.motTrans.set(startPosition);
        this.angTrans.rotY(startAngle);
        this.motTrans.mul(this.angTrans);
    }

    private int status = STAND;
    private int newStatus = STAND;
    
    private float start = 0;
    protected float predTime = 0;

    @Override
    protected boolean process(float time, float duration, boolean force) {
        boolean movRot = super.process(time, duration, force); // ci sa pohyb || rotacia
        if(movRot){ // zmen poziciu
            this.motTrans.set(this.position);
            this.angTrans.rotY(this.angle);
            this.motTrans.mul(this.angTrans);
        }

        boolean timeOut = false;
        if(this.start + frameWindow < time){ // presiel cas
            this.status = newStatus; // aktualny je dalsi
            this.predTime = this.start + frameWindow; // kedy mal skoncit
            timeOut = true;
        }

        int nextStatus;
        if(movRot){
            if(v.lengthSquared() > 0f){ // move
                if(this.status == RUN1) nextStatus = RUN2;
                else if(this.status == RUN2) nextStatus = RUN3;
                else if(this.status == RUN3) nextStatus = RUN4;
                else nextStatus = RUN1;
            }else{ // rotate
                nextStatus = this.status == ROTATE1 ? ROTATE2 : ROTATE1;
            }
        }else{ // statnd
            nextStatus = STAND;
        }
        
        if(timeOut || nextStatus != newStatus){
            this.start = predTime;
            newStatus = nextStatus;
            bs.setNewValues(nextStatus);
        }

        float relativeTime = (time - this.start) / this.frameWindow;
        // if(relativeTime < 0f) relativeTime = 0f;
        if(relativeTime > 1f) relativeTime = 1f;

        if(force) bs.update(relativeTime, motTrans);

        predTime = time;

        return true;
    }

    private static final int STAND = 0;

    private static final int RUN1 = 1;
    private static final int RUN2 = 2;
    private static final int RUN3 = 3;
    private static final int RUN4 = 4;

    private static final int ROTATE1 = 5;
    private static final int ROTATE2 = 6;

    private final Matrix4f angTrans = new Matrix4f();
    protected final Matrix4f motTrans = new Matrix4f();
}
