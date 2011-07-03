package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;
import ws.loaders.tools.joint.BhoneFrameLoader;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class ControlElement extends AbstractFactory {
    private final BhoneFrameLoader bhoneFrameLoader;

    public ControlElement(BhoneFrameLoader bhoneFrameLoader) {
        this.bhoneFrameLoader = bhoneFrameLoader;
    }

    @Override
    public final ControlObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ControlObject c = new ControlObject(value, attributes, this.bhoneFrameLoader);


        Object tmp = attributes.get(SceneBuilder.startPosition);
        if(tmp != null){
            if(tmp instanceof Vector3f)c.setStartPosition( (Vector3f)tmp );
            else if(tmp instanceof Tuple3f)c.setStartPosition( new Vector3f((Tuple3f)tmp) );
            else if(tmp instanceof Point)c.setStartPosition( ((Point)tmp).getVector3f() );
            else if(tmp instanceof Vector)c.setStartPosition( ((Vector)tmp).getVector3f() );
        }

        tmp = attributes.get(SceneBuilder.shotSource);
        if(tmp instanceof Point3f)c.setShotSource( (Point3f)tmp );
        else if(tmp instanceof Tuple3f)c.setShotSource( new Point3f((Tuple3f)tmp) );
        else if(tmp instanceof Point)c.setShotSource( ((Point)tmp).getPoint3f() );
        else if(tmp instanceof Vector)c.setShotSource( ((Vector)tmp).getPoint3f() );
        //c.setShotSource( (Point3f)attributes.get(SceneBuilder.shotSource) );

        tmp = attributes.get(SceneBuilder.disArmedTransform);
        if(tmp != null)c.setDisArmedTransform( ((TransformObject)tmp).getTransform3D() );

        tmp = attributes.get(SceneBuilder.armedTransform);
        if(tmp != null) c.setArmedTransform( ((TransformObject)tmp).getTransform3D() );

        tmp = attributes.get(SceneBuilder.map);
        if(tmp != null) c.addMapObject((MapObject)tmp);

        /* tmp = attributes.get(SceneBuilder.lsSystem);
        if(tmp != null) c.setLsObject((LsObject)tmp); */

        c.setTargetBhone((String)attributes.get(SceneBuilder.targetBhone));
        c.setDisArmedBhone((String)attributes.get(SceneBuilder.disArmedBhone));
        c.setArmedBhone((String)attributes.get(SceneBuilder.armedBhone));

        tmp = attributes.get(SceneBuilder.jumpSpeed);
        if(tmp != null)c.setJumpSpeed(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.jumpSpeedToRun);
        if(tmp != null)c.setJumpSpeedRun(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.runSpeed);
        if(tmp != null)c.setRunSpeed(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.heightUp);
        if(tmp != null)c.setHeightUp(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.heightDown);
        if(tmp != null)c.setHeightDown(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.walkSpeed);
        if(tmp != null)c.setWalkSpeed(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.frameWindow);
        if(tmp != null)c.setFrameWindow(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.colisionRadius);
        if(tmp != null)c.setColisionRadius(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.acelerationRotate);
        if(tmp != null)c.setRotateAceleration(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.acelerationSpeed);
        if(tmp != null)c.setSpeedAceleration(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cameraMinDistance);
        if(tmp != null)c.setCameraMinDistance(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cameraMaxDistance);
        if(tmp != null)c.setCameraMaxDistance(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cameraHeight);
        if(tmp != null)c.setCameraHeight(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cameraSide);
        if(tmp != null)c.setCameraSide(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.pickWeapon);
        if(tmp != null)c.setWeaponPick(tmp instanceof Boolean ? (Boolean)tmp : Boolean.parseBoolean(tmp.toString()));

        attributes.clear();
        return c;
    }
    
    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof ControlObject && child instanceof BhoneSkinObject){
            ControlObject g = (ControlObject)parent;
            BhoneSkinObject so = (BhoneSkinObject)child;
            g.setBhoneSkinObject(so);
        }else if(parent instanceof ControlObject && child instanceof MapObject){
            ControlObject g = (ControlObject)parent;
            MapObject so = (MapObject)child;
            g.addMapObject(so);
        }else if(parent instanceof ControlObject && child instanceof Point){
            ControlObject g = (ControlObject)parent;
            Point so = (Point)child;
            g.setStartPosition(so.getVector3f());
        }else if(parent instanceof ControlObject && child instanceof Vector){
            ControlObject g = (ControlObject)parent;
            Vector so = (Vector)child;
            g.setStartPosition(so.getVector3f());
        }else if(parent instanceof ControlObject && child instanceof ShotObject){
            ControlObject g = (ControlObject)parent;
            ShotObject so = (ShotObject)child;
            g.setShotObject(so);
        }else if(parent instanceof ControlObject && child instanceof ColisionObject){
            ControlObject g = (ControlObject)parent;
            ColisionObject so = (ColisionObject)child;
            g.addColisionObject(so);
        }else if(parent instanceof ControlObject && child instanceof AnimationTransformObject){
            ControlObject g = (ControlObject)parent;
            AnimationTransformObject so = (AnimationTransformObject)child;
            g.addAnimationObject(so);
        }else if(parent instanceof ControlObject && child instanceof TransformGroupObject){
            ControlObject g = (ControlObject)parent;
            TransformGroupObject so = (TransformGroupObject)child;
            g.setItem(so);
        }else if(parent instanceof AgentObject && child instanceof AiItemObject){
            AgentObject g = (AgentObject)parent;
            AiItemObject so = (AiItemObject)child;
            g.addAiItemObject(so);
        }else if(parent instanceof ControlObject && child instanceof AiItemObject){
            ControlObject g = (ControlObject)parent;
            AiItemObject so = (AiItemObject)child;
            g.addAiItemObject(so);
        }else if(parent instanceof ControlObject && child instanceof TransformObject){
            ControlObject g = (ControlObject)parent;
            TransformObject c = (TransformObject)child;
            if(SceneBuilder.armedTransform.equals(c.getName()))g.setArmedTransform(c.getTransform3D());
            else if(SceneBuilder.disArmedTransform.equals(c.getName()))g.setDisArmedTransform(c.getTransform3D());
            else System.err.println(parent+" -> "+child+" : "+c.getName());
        }else if(parent instanceof ControlObject && child instanceof NodeObject){
            ControlObject g = (ControlObject)parent;
            NodeObject c = (NodeObject)child;
            g.addNodeObject(c);
        }else System.err.println(parent+" -> "+child);
    }
}
