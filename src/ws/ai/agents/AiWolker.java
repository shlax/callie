package ws.ai.agents;

import ws.ai.Ai;
import ws.joint.BhoneSkin;
import ws.map.Y25Triangle;
import ws.map.ai.NodeMap;
import ws.tools.Shot;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Switch;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public final class AiWolker extends TargetWolker{

    private final NodeMap[] guardTriangles;
    private final NodeMap[] movingMap;

    public AiWolker(NodeMap[] movingMap, NodeMap[] guardTriangles,
                    float targetRadius, float targetHeight, float live, Shape3D s,
                    Shot shot, Point3f shotSource, Point3f lookAtSource, float lookDistance, float lookAngle,/* float criticalLookDistance, */
                    BhoneSkin bs, float frameWindow,
                    float colisionRadius,
                    Y25Triangle actualTriangle, Vector3f startPosition, float startAngle, float speedA, float rotateA, float rotateCicleA, float maxV,
                    BranchGroup pickNode, float dstForce, Switch sw) {

        super(targetRadius, targetHeight, live, s, 
              shot, shotSource, lookAtSource, lookDistance, lookAngle, /* criticalLookDistance, */
              bs, frameWindow,
              colisionRadius,
              actualTriangle, startPosition, startAngle, speedA, rotateA, rotateCicleA, maxV,
              pickNode, dstForce, sw);

        this.movingMap = movingMap;
        this.guardTriangles = guardTriangles;
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

