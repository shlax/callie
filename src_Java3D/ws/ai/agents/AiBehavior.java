package ws.ai.agents;

import ws.camera.UserCamera;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.util.Enumeration;

public abstract class AiBehavior extends Behavior {
    private final WakeupOnElapsedFrames wk = new WakeupOnElapsedFrames(0);

    private final Point3d source = new Point3d();
	private final Vector3d direction = new Vector3d();
    private final PickRay ray = new PickRay();
    private final BranchGroup pickNode;

    protected final Node getColision(Tuple3f s, Tuple3f d){
        this.source.set(s);
        this.direction.set(d);
        ray.set(source, direction);
        PickInfo pickInfo = pickNode.pickClosest(PickInfo.PICK_GEOMETRY, PickInfo.NODE, ray);
        return pickInfo == null ? null : pickInfo.getNode();
	}
    
    protected AiBehavior(BranchGroup pickNode, float forceDistance, Switch sw) {
        this.setSchedulingBounds(new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE));
        this.pickNode = pickNode;
        this.forceSistanceSquared = forceDistance * forceDistance;
        this.sw = sw;
    }

    private final Switch sw;
    private boolean active = false;

    private final float forceSistanceSquared;
    private long beginTime;

    @Override
    public final void initialize() {
        beginTime = System.nanoTime();
		processStimulus(null);
    }
        
    private float predTime = 0;
    boolean process = true;

    //private static transient int act = 0;

    @Override
    public final void processStimulus(Enumeration enumeration) {
        // ak je ciel na dohlad
        UserCamera.getUserPositionTo(this.userPosition);

        float distX = this.position.x - userPosition.x;
        float distY = this.position.y/*+UserCamera.USER_TARGET*/ - userPosition.y;
        float distZ = this.position.z - userPosition.z;

        float vzdialenost = (distX*distX)+(distZ*distZ)+(distY*distY);
        boolean newActive = vzdialenost < forceSistanceSquared;

        if(sw == null){

            float time = (System.nanoTime() - beginTime)/1000000f;
            float duration = time - predTime;

            if( process = this.process(time, duration, newActive) ){
                this.predTime = time;
                this.wakeupOn(wk);
            }

        }else{
            if(active != newActive){
                active = newActive;
                //System.out.println(act += newActive ? 1 : -1);
                sw.setWhichChild(newActive ? Switch.CHILD_ALL : Switch.CHILD_NONE);
            }

            if(process){
                float time = (System.nanoTime() - beginTime)/1000000f;
                float duration = time - predTime;

                if( process = this.process(time, duration, newActive) ) this.predTime = time;
            }
            this.wakeupOn(wk);
        }
    }

    protected final Point3f userPosition = new Point3f();
    protected final Vector3f position  = new Vector3f();

    protected abstract boolean process(float time ,float duration, boolean force) ;

}
