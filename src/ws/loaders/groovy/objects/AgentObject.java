package ws.loaders.groovy.objects;

import ws.ai.agents.AiWolker;
import ws.joint.BhoneSkin;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.tools.joint.LoadedBhoneSkin;
import ws.loaders.tools.map.NodeMapBuilder;
import ws.map.Y25Triangle;
import ws.map.ai.NodeMap;
import ws.tools.Shot;

import javax.media.j3d.*;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Map;

public final class AgentObject extends FactoryElement {

    public AgentObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private BhoneSkinObject bhoneSkinObject = null;
    public final void setBhoneSkinObject(BhoneSkinObject bhoneSkinObject) {
        this.bhoneSkinObject = bhoneSkinObject;
    }

    private ShotObject shotObject = null;
    public final void setShotObject(ShotObject shotObject) {
        this.shotObject = shotObject;
    }

    private Point3f shotSource = null;
    public final void setShotSource(Point3f shotSource) {
        this.shotSource = shotSource;
    }

    private Float targetHeight = 1.3f;
    public final void setTargetHeight(Float targetHeight) {
        this.targetHeight = targetHeight;
    }
    private Float targetRadius = 0.5f;
    public final void setTargetRadius(Float targetRadius) {
        this.targetRadius = targetRadius;
    }

    private Float live = 1f;
    public final void setLive(Float live) {
        this.live = live;
    }

    private Float lookDistance = 50f;//T O D O: 12.5f;
    public final void setLookDistance(Float lookDistance) {
        this.lookDistance = lookDistance;
    }

    private Float lookAngle = 1f; //0.785f;
    public final void setLookAngle(Float lookAngle) {
        this.lookAngle = lookAngle;
    }

    /*private Float criticalLookDistance = 0.5f;
    public final void setCriticalLookDistance(Float criticalLookDistance) {
        this.criticalLookDistance = criticalLookDistance;
    }*/

    private Float frameWindow = 250f;
    public final void setFrameWindow(Float frameWindow) {
        this.frameWindow = frameWindow;
    }

    private Float colisionRadius = 0.5f;
    public final void setColisionRadius(Float colisionRadius) {
        this.colisionRadius = colisionRadius;
    }

    private Vector3f startPosition = null;
    public final void setStartPosition(Vector3f startPosition) {
        this.startPosition = startPosition;
    }

    private Float startAngle = 0f;
    public final void setStartAngle(Float startAngle) {
        this.startAngle = (float)((startAngle*Math.PI)/180d) ;
    }

    private Float speedAceleration = 0.001f;
    public final void setSpeedAceleration(Float speedAceleration) {
        this.speedAceleration = speedAceleration;
    }

    private Float rotateAceleration = 0.01f;
    public final void setRotateAceleration(Float rotateAceleration) {
        this.rotateAceleration = rotateAceleration;
    }

    private Float rotateDelay = 5000f;
    public final void setRotateDelay(Float rotateDelay) {
        this.rotateDelay = rotateDelay;
    }

    private Float maxSpeed = 0.002f;
    public final void setMaxSpeed(Float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    private Point3f lookAtSource = null;
    public final void setLookAtSource(Point3f lookAtSource) {
        this.lookAtSource = lookAtSource;
    }

    private final ArrayList<AiItemObject> aiItemObjects = new ArrayList<AiItemObject>();
    public final void addAiItemObject(AiItemObject o){
        this.aiItemObjects.add(o);
    }

    private final ArrayList<AiCheckObject> aiChecks = new ArrayList<AiCheckObject>();
    public final void addAiCheck(AiCheckObject c){
        this.aiChecks.add(c);
    }

    public final AiWolker getAiWolker(NodeMapBuilder mapBuilder, BranchGroup aiNode, BranchGroup activeNode, BranchGroup efectNode, /*LsObject lsObject, */Float activeDistance){
        // AiWolker
        NodeMap[] movingMap = mapBuilder.getMap();
        
        Shot shot = shotObject.getLoadedShot().getShot(efectNode); //shotLoader.load(se).getShot(shotNode);

        Vector3f sp = new Vector3f();
        float dstSp = 0f;

        ArrayList<NodeMap> check;
        // if(lsObject == null){
            if(aiChecks.isEmpty()){
                check = null;
            }else{
                check = new ArrayList<NodeMap>();
                for(AiCheckObject tmp : aiChecks){
                    Point3f pp = tmp.getPoint();
                    float len2 = (pp.x * pp.x) + (pp.y * pp.y) + (pp.z * pp.z);
                    if(len2 > dstSp){
                        dstSp = len2;
                        sp.set(pp);
                    }
                    check.add(mapBuilder.getNodeMapAt(pp));
                }
            }
        /* }else{
            Point3f p[] = lsObject.getAiPoints();
            //System.out.println(p);
            if(p != null){
                check = new ArrayList<NodeMap>(p.length);
                for(Point3f pp : p){
                    //System.out.println(pp);
                    float len2 = pp.distanceSquared(lsObject.getStartPosition());
                    //System.out.println(len2);
                    if(len2 > dstSp){
                        dstSp = len2;
                        sp.set(pp);
                    }

                    check.add(mapBuilder.getNodeMapAt(pp));
                }
            }else{
                check = null;
            }
        } */

        if(startPosition == null) this.startPosition = sp;

        //System.out.println(startPosition);

        NodeMap start = mapBuilder.getNodeMapAt(new Point3f(startPosition));
        startPosition.set(start.getPoint());

        Y25Triangle actualTriangle = start.getY25Triangle();
        actualTriangle.getY(startPosition);

        LoadedBhoneSkin bs = this.bhoneSkinObject.getLoadedBhoneSkin(new String[]{
                "STAND", // 0
                "RUN1", "RUN2", "RUN3" ,"RUN4", // 1 2 3 4
                "ROTATE1", "ROTATE2", // 5 6
                "DEAD" // 7
            });

        TransformGroup chracterTransform = new TransformGroup();
        chracterTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //BranchGroup bg = sw == null ? null : new BranchGroup();

        for(AiItemObject tmp : aiItemObjects){
            String name = tmp.getBhoneName();
            Transform3D trans = tmp.getTransform();

            TransformGroup itemTransform = tmp.getTransformGroup();
            itemTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            //if(bg == null){
            chracterTransform.addChild(itemTransform);
            /*}else{
                bg.addChild(itemTransform);
            }*/


            bs.setToTransform(name, itemTransform, trans, false);            
        }
        
        Switch sw; // = activeDistance == null ? null : new ObjSwitch(ObjSwitch.CHILD_NONE);;

        if(activeDistance != null){
            sw = new Switch(Switch.CHILD_NONE);
            sw.addChild(chracterTransform);
            sw.setCapability(Switch.ALLOW_SWITCH_WRITE);
            //bhoneSkin = bs.getBhoneSkin(chracterTransform);
            //chracterTransform.addChild(sw);
            activeNode.addChild(sw);
        }else{
            sw = null;
            activeNode.addChild(chracterTransform);
        }
        
        BhoneSkin bhoneSkin = bs.getBhoneSkin(chracterTransform);

        AiWolker aiW = new AiWolker(movingMap, check == null ? null : check.toArray(new NodeMap[check.size()]),
                                    targetRadius, targetHeight, live, bs.getShape(),
                                    shot, shotSource, lookAtSource, lookDistance, lookAngle ,/* criticalLookDistance, */
                                    bhoneSkin, frameWindow,
                                    colisionRadius,
                                    actualTriangle, startPosition, startAngle, speedAceleration, rotateAceleration, rotateDelay, maxSpeed,
                                    aiNode, activeDistance == null ? Float.MAX_VALUE : activeDistance, sw);



        //bs.setUserData(aiW);
        setUserData(chracterTransform, aiW);
        return aiW;
    }

    private void setUserData(Group g, Object val){
        for(int i = 0; i < g.numChildren(); i++){
            Node nod = g.getChild(i);
            if(nod instanceof Shape3D) nod.setUserData(val);
            else if(nod instanceof Group) setUserData((Group)nod, val);
        }
    }

}
