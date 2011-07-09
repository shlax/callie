package ws.ai.agents;

import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.extension.SwitchNode;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector3f;
import ws.ai.Ai;
import ws.joint.BhoneSkin;
import ws.map.Y25Triangle;
import ws.map.ai.NodeMap;
import ws.tools.Shot;
import ws.tools.controls.Location;

public final class AiWolker extends TargetWolker{

    private final NodeMap[] guardTriangles;
    private final NodeMap[] movingMap;

    public AiWolker(NodeMap[] movingMap, NodeMap[] guardTriangles,
                    float targetRadius, float targetHeight, float live, Mesh s,
                    Shot shot, Point3f shotSource, Point3f lookAtSource, float lookDistance, float lookAngle, float lookAngleVertical, float shotDistance,
                    BhoneSkin bs, float frameWindow,
                    float colisionRadius,
                    Y25Triangle actualTriangle, Vector3f startPosition, float startAngle, float speedA, float rotateA, float rotateCicleA, float maxV,
                    Node pickNode, float dstForce, SwitchNode sw) {

        super(targetRadius, targetHeight, live, s, 
              shot, shotSource, lookAtSource, lookDistance, lookAngle, lookAngleVertical, shotDistance,
              bs, frameWindow,
              colisionRadius,
              actualTriangle, startPosition, startAngle, speedA, rotateA, rotateCicleA, maxV,
              pickNode, dstForce, sw);

        this.movingMap = movingMap;
        this.guardTriangles = guardTriangles;
    }

    private volatile boolean aiContoled = true;
    public boolean isAiContoled() {
        return aiContoled;
    }
    public void setAiContoled(boolean aiContoled) {
        this.aiContoled = aiContoled;
        if(aiContoled) Ai.notifiAI();
    }

    private Location toPoint = null;
    public synchronized final void navigate(Location l){
        toPoint = l;
        Ai.notifiAI();
    }
    public synchronized final void navigateBack(Location l){
        toPoint = l;
    }
    public synchronized final Location navigateTo(){
        if(toPoint == null)return  null;
        Location q = toPoint;
        toPoint = null;
        return q;
    }


    @Override
    protected final boolean process(float time, float duration, boolean force) {
        boolean ret = super.process(time, duration, force);
        if(!this.processing() && !this.secondaryProcessing()) Ai.notifiAI();
        return ret;
    }

    public final NodeMap[] getMovingMap(){
        return this.movingMap;
    }


    private int ind = 0;
    public final NodeMap getGuardTriangle(){
        if(this.guardTriangles == null) return null;
        NodeMap tmp = this.guardTriangles[ind];
        ind ++;
        if(ind >= guardTriangles.length) ind = 0;
        return tmp;
    }
}

