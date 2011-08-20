package ws.camera;

import ws.Gui;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.event.MouseEvent;

public abstract class Camera{


    private long beginTime;

    private final float maxSide;

    protected Camera(BranchGroup pickNode, Vector3f startPosition, float minDistance, float maxDistance, float defMaxMinDistance, float height, float maxSide){


     //   this.cm = cm;
    //	this.light = light;
        this.pickNode = pickNode;

        this.maxSide = maxSide;
        this.height = height;
        this.minDistance = minDistance + 0.1f;
        this.defMaxDistance = maxDistance + 0.1f;
        this.defMaxMinDistance = defMaxMinDistance; // (this.defMaxDistance+this.minDistance) / 2f;
        this.actMaxDistance = this.defMaxDistance;

        this.prevDistance = maxDistance;
        
        camPos.z = minDistance;

    //    System.out.println("C "+startPosition);

        this.objectPosition = startPosition;
        //this.objectTriangle = startTriangle;

        //this.colisionHeight = height - colisionHeight;

        // ------------------------------------------------------------

       /* tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.addChild(new Sphere(0.1f));
        pickNode.addChild(tg); */
    }

    boolean forceUpdate = false;
    public final void initialize(TransformGroup targetTG) {
        beginTime = System.nanoTime();
        forceUpdate = true;
        processStimulus(targetTG);
    }

    // -----------------------------------------------------
    protected final static float PIf = (float)Math.PI;

    protected float angleY = 0;
    private float angleX = 0;

    private float time = 0;

    protected final float normalizeAngle(float angle, float grad){
        angle += grad;
        if(angle < 0) angle += 2f*PIf;
        else if(angle > 2f*PIf) angle -= 2f*PIf;
        return angle; 
    }

    public final void processStimulus(TransformGroup targetTG) {
        float actTime = (System.nanoTime() - beginTime)/1000000f;
        float duration = actTime - time;

        boolean update = forceUpdate;
        forceUpdate = false;
        /*if(forceUpdate){
            this.angleY = (150f*PIf)/180f;
            this.angleX = (2f*PIf)-( (18f*PIf)/180f );
        }else{ */
        if(Gui.mouseX != 0){
            float tmp = Gui.mouseX;
            Gui.mouseX -= tmp;

         //   System.out.println(tmp);

            //float angleLimit = (duration*tmp)/150f;
            this.angleY = normalizeAngle(this.angleY,/*duration*/tmp*0.1f);
            update = true;
        }
        if(Gui.mouseY != 0){
            float tmp = Gui.mouseY;
            Gui.mouseY -= tmp;

            //System.out.println(tmp);

            this.angleX = normalizeAngle(this.angleX,/*duration*/ tmp*0.1f);

            if(this.angleX <= PIf && this.angleX > PIf/4f) this.angleX = PIf/4f;
            else if( this.angleX > PIf && this.angleX < 1.6f*PIf ) this.angleX = 1.6f*PIf;

            update = true;
        }
        if(Gui.scroolPercent != 0){
            float tmp = Gui.scroolPercent;
            Gui.scroolPercent -= tmp;

            actMaxDistance += tmp * 0.1;
            if(actMaxDistance < defMaxMinDistance) actMaxDistance = defMaxMinDistance;
            else if(actMaxDistance > defMaxDistance) actMaxDistance = defMaxDistance;
            //System.out.println(tmp);

            update = true;
        }
        // }

        //System.out.println(angleX+" "+angleX);

        // process object
        if( this.process(actTime, duration) ) update = true;

        /*if(targetDistance){
            if(this.prevDistance != this.maxDistance) update = true;
        }else{
            if(this.prevDistance != this.minDistance) update = true;
        }*/

        boolean weapon = UserCamera.hasWeapon() && Gui.buttons.contains(MouseEvent.BUTTON3);
        if(weapon){
            if(this.camPos.x != this.maxSide) update = true;
        }else{
            if(this.camPos.x != 0f) update = true;
        }

        if(update){
            forceUpdate = false;

            position.x = objectPosition.x;
            position.y = objectPosition.y+height;
            position.z = objectPosition.z;

         //   tgTd.set( new Vector3d(position) );
         //   tg.setTransform(tgTd);

            this.trans.rotY(angleY);
            this.tmpTrans.rotX(angleX);
            this.trans.mul(this.tmpTrans);

            this.tmpTrans.set(position);
            this.trans.mul(this.tmpTrans, trans);

            if(weapon){
                if(this.camPos.x < this.maxSide){
                    this.camPos.x += duration*0.00125f;
                    if(this.camPos.x > this.maxSide) this.camPos.x = this.maxSide;
                }
            }else{
                if(this.camPos.x > 0f){
                    this.camPos.x -= duration*0.00125f;
                    if(this.camPos.x < 0f) this.camPos.x = 0f;
                }
            }

            this.maxTrans.set(this.trans);

            this.camPos.z = this.prevDistance;
            this.tmpTrans.set(this.camPos);
            this.maxTrans.mul(this.tmpTrans);

            cameraPoint.set(0f, 0f, 0f);
            maxTrans.transform(cameraPoint);

            // System.out.println(position+" "+cameraPoint);
            direction.set(cameraPoint.x - position.x , cameraPoint.y - position.y, cameraPoint.z - position.z);
            cameraPoint.set(position);

            ray.set(cameraPoint, direction);
            PickInfo[] pickInfos = pickNode.pickAllSorted(PickInfo.PICK_GEOMETRY, PickInfo.NODE, ray);
            if(pickInfos != null){
                for(PickInfo pi : pickInfos){
                    if(pi.getNode().getUserData() == UserCamera.CHARACTER) continue;
                    float distance = (float)pi.getClosestDistance();


                    if(prevDistance < distance){

                        //forceUpdate = distance - prevDistance > 0.15f;
                        float diff = distance - prevDistance;
                        float step = duration * 0.01f;
                        if(diff < step){
                            prevDistance = distance;
                        }else {
                            forceUpdate = true;
                            prevDistance += step;
                        }

                    }else if(prevDistance > distance){
                        forceUpdate = true;
                        //forceUpdate = prevDistance - distance > 0.15f;
                        float diff = prevDistance - distance;
                        float step = duration * 0.01f;
                        if(diff < step){
                            prevDistance = distance;
                        }else{
                            forceUpdate = true;
                            prevDistance -= step;
                        }

                        //prevDistance -= Math.min(0.1f, prevDistance - distance);
                    }

                    if(prevDistance < this.minDistance){
                        forceUpdate = false;
                        prevDistance = this.minDistance;
                    }else if(prevDistance > this.actMaxDistance){
                        forceUpdate = false;
                        prevDistance = this.actMaxDistance;
                    }

                        //System.out.println(forceUpdate);

                    //else prevDistance = distance;

                    break;
                }
            }
            //TODO: camera out
            //if(Gui.keys.contains(KeyEvent.VK_Z)) prevDistance = 30f;
            //if(Gui.keys.contains(KeyEvent.VK_X))System.out.println(this.position);

            // System.out.println(distance);

                //else if(distance > this.maxDistance) prevDistance = this.maxDistance;

            /*if(isColode()){
                if(prevDistance > this.minDistance){
                    prevDistance -=  0.0075f*duration; //(targetDistance ? 0.0025f : -0.0075f )*duration;
                    if(prevDistance < this.minDistance){
                        prevDistance = this.minDistance;
                        process = false;
                    }else process = true;
                }else process = false;
            }else{
                if(prevDistance < this.maxDistance){
                    float backDistance = this.prevDistance;
                    //Y25Triangle backTriangle = this.cm.getLastY25Triangle();
                    prevDistance += 0.0075f*duration; //(targetDistance ? 0.0025f : -0.0075f )*duration;
                    if(prevDistance > this.maxDistance) prevDistance = this.maxDistance;

                    if(isColode()){
                        this.prevDistance = backDistance;
                        //this.cm.setLastY25Triangle(backTriangle);
                        process = false;
                    }else process = prevDistance != this.maxDistance;
                    //System.out.println(prevDistance+" < "+this.maxDistance+" = "+process);
                }else process = false;
            }*/

            //System.out.println(prevDistance);
            this.camPos.z = this.prevDistance - 0.5f;
            this.tmpTrans.set(this.camPos);
            this.trans.mul(this.tmpTrans);

            //System.out.println(this.camPos);

            targetTG.setTransform(this.trans);
            //forceUpdate = disableForce;
            //System.out.println(forceUpdate);
        }


        this.time = actTime;


    }

    private final Transform3D maxTrans = new Transform3D();

    private final BranchGroup pickNode;
    private final PickRay ray = new PickRay();
    private final Point3d cameraPoint = new Point3d(); 
    private final Vector3d direction = new Vector3d();

    //private final float colisionHeight;

   // private TransformGroup tg = new TransformGroup();
   // private Transform3D tgTd = new Transform3D();

   /* private final boolean isColode(){
        this.maxTrans.set(this.trans);
        
        this.camPos.z = this.prevDistance;
        this.tmpTrans.set(this.camPos);
        this.maxTrans.mul(this.tmpTrans);

        cameraPoint.set(0f, 0f, 0f);
        maxTrans.transform(cameraPoint);

        // System.out.println(position+" "+cameraPoint);
        direction.set(position.x - cameraPoint.x, position.y - cameraPoint.y - colisionHeight, position.z - cameraPoint.z);

        ray.set(cameraPoint, direction);
        PickInfo pickInfo = pickNode.pickClosest(PickInfo.PICK_GEOMETRY, PickInfo.NODE, ray);



        // if(pickInfo != null) System.out.println( pickInfo.getNode().getUserData() );
        return pickInfo == null ? true : pickInfo.getNode().getUserData() != UserCamera.CHARACTER;

        // cameraPoint

        / * float hk = cameraPoint.y;

        Type typ = this.cm.fastY(cameraPoint, EnumSet.allOf(Type.class));

        if( typ != null ) return hk < cameraPoint.y+0.1f;
        else{
            Y25Triangle backTriangle = this.cm.getLastY25Triangle();

            this.cm.setLastY25Triangle(objectTriangle);
            typ = this.cm.fastY(cameraPoint, EnumSet.allOf(Type.class));

            if(typ != null) return hk < cameraPoint.y+0.1f;
            else{
                this.cm.setLastY25Triangle(backTriangle);
                return true;
            }
        }  * /
    } */

    /**
     * aktualna pozicia na mape
     */
    //protected Y25Triangle objectTriangle = null;
    protected final Vector3f objectPosition;// = new Vector3f(0,0,0);
    protected abstract boolean process(float actTime, float duration);

    private final Vector3f position = new Vector3f();
    private final Vector3f camPos = new Vector3f();

    private final Transform3D tmpTrans = new Transform3D();
    private final Transform3D trans = new Transform3D();

    // private final Y25Map cm;

    private final float height;

    private float prevDistance;
    //private boolean updateDistanceAnim = false;
    //private boolean process = true;
    
    private final float minDistance;
    private final float defMaxDistance;
    private final float defMaxMinDistance;
    private float actMaxDistance;

}
