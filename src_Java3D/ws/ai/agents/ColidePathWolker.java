package ws.ai.agents;

import ws.ai.Colision;
import ws.map.Y25Triangle;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;

public class ColidePathWolker extends PathWolker implements Colision {

    private final float colisionRadius;

    /**
     * speedA < 1
     * speedA < maxV
     */
    protected ColidePathWolker(float colisionRadius,
                               Y25Triangle actualTriangle, Tuple3f startPosition, float startAngle, float speedA, float rotateA, float rotateCicleA, float maxV,
                               BranchGroup pickNode, float distanceForce, Switch sw) {
        super(actualTriangle, startPosition, startAngle, speedA, rotateA, rotateCicleA, maxV,
              pickNode, distanceForce, sw);
        
        this.colisionRadius = colisionRadius*colisionRadius;
    }

    @Override
    protected boolean process(float time, float duration, boolean force) {
        boolean ret = super.process(time, duration, force);
        synchronized (colPoint){
            colPoint.set(this.position);
        }
        return ret;
    }

    protected final Point3f colPoint = new Point3f();

    @Override
    public final boolean testColision(Tuple3f a, float r) {
        synchronized (colPoint){
            float dstX = a.x - colPoint.x;
            float dstY = a.y - colPoint.y;
            float dstZ = a.z - colPoint.z;
            return ((dstX * dstX) + (dstY * dstY) + (dstZ * dstZ)) < r + colisionRadius;
        }
    }

    @Override
    public final void getPosition(Tuple3f p) {
        synchronized (colPoint){
            p.set(colPoint);
        }        
    }
}
