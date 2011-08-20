package ws.ai.agents;

import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.extension.SwitchNode;
import ws.ai.Ai;
import ws.map.Y25Triangle;
import ws.map.ai.NodeMap;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public class PathWolker extends AiBehavior{

    private final float speedA;
    private final float rotateA;
    private final float rotateCicleA;
    private final float maxV;
    private final float maxV2;

    /**
     * speedA < 1
     * speedA < maxV  
     */
    protected PathWolker(Y25Triangle actualTriangle, Tuple3f startPosition, float startAngle, float speedA, float rotateA, float rotateCicleA, float maxV,
                         Node pickNode, float distanceForce, SwitchNode sw) {
        super(pickNode, distanceForce, sw);

        this.speedA = speedA;
        this.rotateA = rotateA;
        this.rotateCicleA = rotateCicleA;
        this.maxV = maxV;
        this.maxV2 = maxV*maxV;

        this.actualTriangle = actualTriangle;
        this.position.set( startPosition );
        this.angle = startAngle;
    }

    private NodeMap path[] = null;
    private int pi = 0;
    private boolean rotate = false;
    private float toAngle = 0;

    //private boolean afterPi = false;
    //private boolean updateNew = false;
    //private NodeMap newPath[] = null;
    //private boolean newRotate = false;
    //private float newToAngle = 0;

    public synchronized boolean processing(){
        //System.out.println(rotate);
        //System.out.println(path);
        return (!rotate && this.angle != this.toAngle ) || path != null || angles != null;
    }

    public synchronized final boolean secondaryProcessing(){
        return this.rotate;
    }

    private int angleIndex = 0;
    private Float angles[] = null;

    public synchronized final boolean moveTo(NodeMap[] path, Float[] angles, boolean rotation){
        //System.out.println(path+ " "+angle+" "+rotation);
        // if(rotation) throw new RuntimeException();
        // if(path != null)System.out.println(path.length);
            //updateNew = true;

        boolean ret = path != null && path.length > 0 && this.actualTriangle == path[0].getY25Triangle();

        pi = 0;
        rotateSeek = 0f;

        this.path = ret ? this.path = path : null;
        this.rotate = rotation;

        this.angles = angles == null || angles.length == 1 ? null : angles;
        if(angles == null) this.toAngle = ((float)Math.random())*2f*PIf;
        else{
            toAngle = angles[0];
            angleIndex = 1;

            if(toAngle < 0f) toAngle += 2*PIf;
            else if(toAngle > 2f*PIf) toAngle -= 2f*PIf;
        }

        //System.out.println(ret);

        return ret;
    }

    public final Y25Triangle getActualTriangle(){
        return this.actualTriangle;
    }

    private Y25Triangle actualTriangle; 
    protected float angle = 0f;

    private final boolean transformAngle(float reqAngele, float duration){
        if(reqAngele < 0f) reqAngele += 2*PIf;
        else if(reqAngele > 2f*PIf) reqAngele -= 2f*PIf;

        if(reqAngele == angle) return true;
        // v smere
        float dx;
        if(reqAngele > angle) dx = reqAngele - angle;
        else dx = PIf*2f - ( angle - reqAngele );

        // v protismere
        float dy;
        if(reqAngele > angle) dy = PIf*2f - ( reqAngele - angle );
        else dy = angle - reqAngele;

        float dz = Math.min(dx, dy);
        float s = rotateA*duration;

        if(dz < s) this.angle = reqAngele;
        else{
            angle += (dx < dy ? 1f : -1f)*s;

            if(angle < 0f) angle += 2f*PIf;
            else if(angle > 2f*PIf) angle -= 2f*PIf;                        
        }

        return false;
    }

    private final boolean isInDestination(Point3f last){
        float lx = this.position.getX() - last.getX();
        float ly = this.position.getY() - last.getY();
        float lz = this.position.getZ() - last.getZ();

        return (lx*lx)+(ly*ly)+(lz*lz) < Ai.EPSILON;
    }

    private float rotateSeek = 0f;

    @Override
    protected synchronized boolean process(float time, float duration, boolean force) {
        /* synchronized (this){
            if(this.updateNew){
                this.updateNew = false;

                if(newPath == null){ // ak sa nema hybat
                    path = null;
                    pi = 0;
                    //afterPi = false;
                    rotate = this.newRotate;
                    toAngle = this.newToAngle;
                }else if(newPath[0].getY25Triangle() == this.actualTriangle){ // ak sa ma hybat musi zacat od aktualneho
                    path = newPath;
                    pi = 0;
                    //afterPi = false;
                    rotate = this.newRotate;
                    toAngle = this.newToAngle;
                }

                rotateSeek = 0f;
            }
        } */

        if(path == null || isInDestination(path[path.length -1].getPoint())){
            this.path = null;
            v.set(0f, 0f, 0f);

            if(angles != null){
                boolean indst = transformAngle(toAngle, duration);
                //System.out.println(angleIndex+" "+toAngle);
                if(indst){
                    toAngle = angles[angleIndex];

                    if(toAngle < 0f) toAngle += 2*PIf;
                    else if(toAngle > 2f*PIf) toAngle -= 2f*PIf;

                    angleIndex ++;
                    if(angleIndex >= angles.length) angles = null;
                }
                return true;
            }

            if(rotate){
                rotateSeek += duration;
                if(rotateSeek >= rotateCicleA){
                    float tmpAng = toAngle + (((float)Math.random())*PIf)+(PIf/2f);
                    if(tmpAng > 2f*PIf) tmpAng -= 2f*PIf;
                    toAngle = tmpAng;
                    rotateSeek = 0f;
                }
            }//else AiObject.notifiAI();
            return !transformAngle(toAngle, duration);

            //boolean finAngTrans = ;
           // System.out.println(finAngTrans+" "+angles);

        }

        NodeMap an = path[pi];
        NodeMap nn = pi == path.length -1 ? null : path[pi+1];
        Point3f next = nn == null ? an.getPoint() : an.getPoint( nn );
        
        direction.sub(next, position);
        float distanceSq = direction.lengthSquared();

        transformAngle( (float)Math.atan2(direction.getX(), direction.getZ()) , duration);

        float s  = direction.length();
        if(s > 0f){
            direction.scale(duration*speedA/s);

            v.add(direction);
            if(v.lengthSquared() > maxV2) v.scale(maxV/v.length());            
        }

        if(nn == null && distanceSq < maxV*maxV) step.scale(duration*(speedA + (1f-speedA)*((float)Math.sqrt(distanceSq))/maxV), v);
        else step.scale(duration, v);

        if(step.lengthSquared() > distanceSq){
            //afterPi = true;
            if(nn == null) direction.set(next);
            else direction.add(position, step);
        } else direction.add(position, step);
                
        if(an.getY25Triangle().getY(direction)) position.set(direction);
        else{
            if(nn != null){
                if(nn.getY25Triangle().getY(direction)){
                    pi ++;
                    //afterPi = false;
                    position.set(direction);
                    actualTriangle = nn.getY25Triangle();
                } else v.set(0f, 0f, 0f); // System.out.println(position+" "+direction+" "+next);
            }else v.set(0f, 0f, 0f);
        }
        
        return true;
    }

    protected final Vector3f v = new Vector3f();
    
    private final Vector3f direction = new Vector3f();
    private final Vector3f step = new Vector3f();

    protected final static float PIf = (float)Math.PI;
}
