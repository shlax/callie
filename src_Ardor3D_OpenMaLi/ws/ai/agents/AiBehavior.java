package ws.ai.agents;

import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.extension.SwitchNode;
import org.openmali.vecmath2.*;
import ws.camera.UserCamera;

public abstract class AiBehavior {/*extends Behavior */
    //private final WakeupOnElapsedFrames wk = new WakeupOnElapsedFrames(0);

    //TODO: picking
    private final Node pickNode;
    protected final Node getColision(Tuple3f s, Tuple3f d){
        return null;
    }
    /*private final Point3f source = new Point3f();
	private final Vector3f direction = new Vector3f();
    private final PickRay ray = new PickRay();

    protected final Node getColision(Tuple3f s, Tuple3f d){
        this.source.set(s);
        this.direction.set(d);
        ray.set(source, direction);
        PickInfo pickInfo = pickNode.pickClosest(PickInfo.PICK_GEOMETRY, PickInfo.NODE, ray);
        return pickInfo == null ? null : pickInfo.getNode();
	}*/
    
    protected AiBehavior(Node pickNode, float forceDistance, SwitchNode sw) {
        //this.setSchedulingBounds(new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE));
        this.pickNode = pickNode;
        this.forceSistanceSquared = forceDistance * forceDistance;
        this.sw = sw;
    }

    private final SwitchNode sw;
    private boolean active = false;

    private final float forceSistanceSquared;
    private long beginTime;

    //@Override
    public final void initialize() {
        beginTime = System.nanoTime();
		processStimulus();
    }
        
    private float predTime = 0;
    boolean process = true;

    //private static transient int act = 0;

    //@Override
    public final void processStimulus() {
        // ak je ciel na dohlad
        UserCamera.getUserPositionTo(this.userPosition);

        float distX = this.position.getX() - userPosition.getX();
        float distY = this.position.getY()/*+UserCamera.USER_TARGET*/ - userPosition.getY();
        float distZ = this.position.getZ() - userPosition.getZ();

        float vzdialenost = (distX*distX)+(distZ*distZ)+(distY*distY);
        boolean newActive = vzdialenost < forceSistanceSquared;

        if(sw == null){

            float time = (System.nanoTime() - beginTime)/1000000f;
            float duration = time - predTime;

            if( process = this.process(time, duration, newActive) ){
                this.predTime = time;
                //this.wakeupOn(wk);
            }

        }else{
            if(active != newActive){
                active = newActive;
                //System.out.println(act += newActive ? 1 : -1);
                //sw.setWhichChild(newActive ? Switch.CHILD_ALL : Switch.CHILD_NONE);
                if(newActive)sw.setAllVisible();
                else sw.setAllNonVisible();
            }

            if(process){
                float time = (System.nanoTime() - beginTime)/1000000f;
                float duration = time - predTime;

                if( process = this.process(time, duration, newActive) ) this.predTime = time;
            }
            //this.wakeupOn(wk);
        }
    }

    protected final Point3f userPosition = new Point3f();
    protected final Vector3f position  = new Vector3f();

    protected abstract boolean process(float time ,float duration, boolean force) ;

}
