package ws.camera;

import com.ardor3d.intersection.PickData;
import com.ardor3d.intersection.Pickable;
import com.ardor3d.intersection.PickingUtil;
import com.ardor3d.intersection.PrimitivePickResults;
import com.ardor3d.math.Ray3;
import com.ardor3d.math.Vector3;
import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.Node;
import wa.Gui;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.event.MouseEvent;

public abstract class Camera{
    private long beginTime;

    private final float maxSide;
    protected float sideSign = 1f;

    protected Camera(Node pickNode, Vector3f startPosition, float minDistance, float maxDistance, float defMaxMinDistance, float height, float maxSide){


     //   this.cm = cm;
    //	this.light = light;
        this.pickNode = pickNode;

        this.maxSide = maxSide;
        this.height = height;
        this.minDistance = minDistance + 0.5f;
        this.defMaxDistance = maxDistance + 0.5f;
        this.defMaxMinDistance = defMaxMinDistance + 0.5f; // (this.defMaxDistance+this.minDistance) / 2f;
        this.actMaxDistance = this.defMaxDistance;

        this.prevDistance = maxDistance + 0.5f;
        
        camPos.setZ( minDistance );

    //    System.out.println("C "+startPosition);

        this.objectPosition = startPosition;
        //System.out.println(objectPosition);
        //this.objectTriangle = startTriangle;

        //this.colisionHeight = height - colisionHeight;

        // ------------------------------------------------------------

       /* tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.addChild(new Sphere(0.1f));
        pickNode.addChild(tg); */
    }

    boolean forceUpdate = false;
    public final void initialize(com.ardor3d.renderer.Camera targetTG) {
        beginTime = System.nanoTime();
        processStimulus(targetTG);
        forceUpdate = true;
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

    public final void processStimulus(com.ardor3d.renderer.Camera camera) {
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

            this.angleY = normalizeAngle(this.angleY,tmp);
            update = true;
        }
        if(Gui.mouseY != 0){
            float tmp = Gui.mouseY;
            Gui.mouseY -= tmp;

            //System.out.println(tmp);

            this.angleX = normalizeAngle(this.angleX,tmp);

            if(this.angleX <= PIf && this.angleX > PIf/4f) this.angleX = PIf/4f;
            else if( this.angleX > PIf && this.angleX < 1.6f*PIf ) this.angleX = 1.6f*PIf;

            update = true;
        }
        if(Gui.scroolPercent != 0){
            float tmp = Gui.scroolPercent;
            Gui.scroolPercent -= tmp;

            actMaxDistance += tmp;
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
            if( Math.abs(this.camPos.getX()) != this.maxSide) update = true;
        }else{
            if(this.camPos.getX() != 0f) update = true;
        }

        //System.out.println(weapon);

        if(update){
          //  boolean disableForce = false;

            position.setX(objectPosition.getX());
            position.setY(objectPosition.getY()+height);
            position.setZ(objectPosition.getZ());


         //   tgTd.set( new Vector3d(position) );
         //   tg.setTransform(tgTd);

            this.trans.rotY(angleY);
            this.tmpTrans.rotX(angleX);
            this.trans.mul(this.tmpTrans);

            this.tmpTrans.set(position);
            this.trans.mul(this.tmpTrans, trans);

            if(weapon){
                if(sideSign > 0f){
                    if(this.camPos.getX() < sideSign*this.maxSide){
                        this.camPos.setX( this.camPos.getX() + (duration*0.00125f));
                        if(this.camPos.getX() > sideSign*this.maxSide) this.camPos.setX(sideSign*this.maxSide);
                    }
                }else{
                    if(this.camPos.getX() > sideSign*this.maxSide){
                        this.camPos.setX( this.camPos.getX() - (duration*0.00125f));
                        if(this.camPos.getX() < sideSign*this.maxSide) this.camPos.setX(sideSign*this.maxSide);
                    }
                }
            }else{
                if(sideSign > 0f){
                    if(this.camPos.getX() > 0f){
                        this.camPos.setX( this.camPos.getX() - duration*0.00125f );
                        if(this.camPos.getX() < 0f) this.camPos.setX(0f);
                    }
                }else {
                    if(this.camPos.getX() < 0f){
                        this.camPos.setX( this.camPos.getX() + duration*0.00125f );
                        if(this.camPos.getX() > 0f) this.camPos.setX(0f);
                    }
                }
            }

            this.maxTrans.set(this.trans);

            this.camPos.setZ( this.prevDistance );
            this.tmpTrans.set(this.camPos);
            this.maxTrans.mul(this.tmpTrans);

            cameraPoint.set(0f, 0f, 0f);
            maxTrans.transform(cameraPoint);

            // System.out.println(position+" "+cameraPoint);
            direction.set(cameraPoint.getX() - position.getX() , cameraPoint.getY() - position.getY(), cameraPoint.getZ() - position.getZ());
            //cameraPoint.set(position);

            //TODO: picking

            // Set up our pick ray
            pickRay.setOrigin(new Vector3(position.x, position.y, position.z));
            pickRay.setDirection( new Vector3(direction.x, direction.y, direction.z) );

            float distance = actMaxDistance;
            // do pick and move the sphere
            PrimitivePickResults pickResults = new PrimitivePickResults();
            //pickResults.setCheckDistance(true);
            PickingUtil.findPick(pickNode, pickRay, pickResults);

            //System.out.println( pickResults.getNumber() );
            for (int i = 0; i < pickResults.getNumber(); i++) {
                PickData tmp = pickResults.getPickData(i);
                Pickable p = tmp.getTarget();
              //  System.out.println(p);
                if(p instanceof Mesh){
                    Mesh s  = (Mesh)p;
                    if(s.getUserData() != UserCamera.CHARACTER){
                        float dist = (float )tmp.getIntersectionRecord().getClosestDistance();
                        distance = Math.min(distance, dist);
                    }
                }
            }
            //System.out.println(distance);

            /* ray.set(cameraPoint, direction);
            PickInfo[] pickInfos = pickNode.pickAllSorted(PickInfo.PICK_GEOMETRY, PickInfo.NODE, ray); */
            //if(distance != null /*pickInfos != null*/){
              //  System.out.println("distance "+distance);
                //for(PickInfo pi : pickInfos){
                    //if(pi.getNode().getUserData() == UserCamera.CHARACTER) continue;
                   // float distance = dst; // (float)pi.getClosestDistance();

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

                    //break;
                //}
            //}
            // T O D O : camera out
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
            this.camPos.setZ( this.prevDistance -0.5f );


      //      System.out.println(camPos);
            this.tmpTrans.set(this.camPos);
            this.trans.mul(this.tmpTrans);

            cameraPoint.set(0, 0, 0);
            this.trans.transform(cameraPoint);

           // System.out.println(cameraPoint.distance(new Point3f(position)));

        //    System.out.println(this.cameraPoint+" "+ position);
            camera.setLocation(this.cameraPoint.x, this.cameraPoint.y, this.cameraPoint.z);

            if(camPos.x != 0){
                tmpTrans.rotY(angleY);
                cameraPoint.set(camPos.x, 0, 0);
                tmpTrans.transform(cameraPoint);

                camera.lookAt(position.getX()+cameraPoint.x, position.getY(), position.getZ()+cameraPoint.z, up);

            }else camera.lookAt(position.getX(), position.getY(), position.getZ(), up);
            //targetTG.setTransform(this.trans);
            //forceUpdate;
        }

        this.time = actTime;
    }

    private final Vector3 up = new Vector3(0,1,0);

    private final Matrix4f maxTrans = new Matrix4f();

    private final Node pickNode;
    //T O D O : picking -> private final PickRay ray = new PickRay();
    private final Point3f cameraPoint = new Point3f();
    private final Vector3f direction = new Vector3f();

    private final Ray3 pickRay = new Ray3();
        //PickingUtil
    /*

    if (sphere.getSceneHints().getCullHint() == CullHint.Dynamic) {
            // Set up our pick ray
            pickRay.setOrigin(camera.getLocation());
            pickRay.setDirection(camera.getDirection());

            // do pick and move the sphere
            final PrimitivePickResults pickResults = new PrimitivePickResults();
            pickResults.setCheckDistance(true);
            PickingUtil.findPick(_root, pickRay, pickResults);
            if (pickResults.getNumber() != 0) {
                final Vector3 intersectionPoint = pickResults.getPickData(0).getIntersectionRecord()
                        .getIntersectionPoint(0);
                sphere.setTranslation(intersectionPoint);

                final Vector3 intersectionNormal = pickResults.getPickData(0).getIntersectionRecord()
                        .getIntersectionNormal(0);
                final Matrix3 rotation = new Matrix3();
                rotation.lookAt(intersectionNormal, Vector3.UNIT_Z);
                arrow.setRotation(rotation);
                arrow.setTranslation(intersectionPoint);
            }
        }

     */

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

    private final Matrix4f tmpTrans = new Matrix4f();
    private final Matrix4f trans = new Matrix4f();

    // private final Y25Map cm;

    private final float height;

    private float prevDistance;
    //private boolean process = true;
    
    private final float minDistance;
    private final float defMaxDistance;
    private final float defMaxMinDistance;
    private float actMaxDistance;

}
