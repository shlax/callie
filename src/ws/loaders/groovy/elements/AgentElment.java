package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class AgentElment extends AbstractFactory {

    @Override
    public final AgentObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AgentObject a = new AgentObject(value, attributes);

        Object tmp = attributes.get(SceneBuilder.shotSource);
        if(tmp instanceof Point3f) a.setShotSource( (Point3f)tmp );
        else if(tmp instanceof Tuple3f) a.setShotSource( new Point3f((Tuple3f)tmp) );
        else if(tmp instanceof Tuple) a.setShotSource( ((Tuple) tmp).getPoint3f() );


        tmp = attributes.get(SceneBuilder.lookAtSource);
        if(tmp instanceof Point3f) a.setLookAtSource( (Point3f)tmp );
        else if(tmp instanceof Tuple3f) a.setLookAtSource( new Point3f((Tuple3f)tmp) );
        else if(tmp instanceof Tuple) a.setLookAtSource( ((Tuple) tmp).getPoint3f() );


        tmp = attributes.get(SceneBuilder.startPosition);
        if(tmp != null){
            if(tmp instanceof Vector3f) a.setStartPosition( (Vector3f)tmp );
            else if(tmp instanceof Tuple3f) a.setStartPosition( new Vector3f((Tuple3f)tmp) );
            else if(tmp instanceof Tuple) a.setStartPosition( ((Tuple) tmp).getVector3f() );
        }

        tmp = attributes.get(SceneBuilder.live);
        if(tmp != null) a.setLive(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.lookDistance);
        if(tmp != null) a.setLookDistance(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.lookAngle);
        if(tmp != null) a.setLookAngle(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        /*tmp = attributes.get(SceneBuilder.criticalLookDistance);
        if(tmp != null) a.setCriticalLookDistance(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) ); */

        tmp = attributes.get(SceneBuilder.frameWindow);
        if(tmp != null) a.setFrameWindow(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.colisionRadius);
        if(tmp != null) a.setColisionRadius(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.startAngle);
        if(tmp != null) a.setStartAngle(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.targetHeight);
        if(tmp != null) a.setTargetHeight(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.targetRadius);
        if(tmp != null) a.setTargetRadius(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.acelerationSpeed);
        if(tmp != null) a.setSpeedAceleration(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.acelerationRotate);
        if(tmp != null) a.setRotateAceleration(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.rotateDelay);
        if(tmp != null) a.setRotateDelay(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.maxSpeed);
        if(tmp != null) a.setMaxSpeed(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        attributes.clear();
        return a;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof AgentObject && child instanceof BhoneSkinObject){
            AgentObject g = (AgentObject)parent;
            BhoneSkinObject so = (BhoneSkinObject)child;
            g.setBhoneSkinObject(so);
        }else if(parent instanceof AgentObject && child instanceof Point){
            AgentObject g = (AgentObject)parent;
            Point so = (Point)child;
            g.setStartPosition(so.getVector3f());
        }else if(parent instanceof AgentObject && child instanceof Vector){
            AgentObject g = (AgentObject)parent;
            Vector so = (Vector)child;
            g.setStartPosition(so.getVector3f());
        }else if(parent instanceof AgentObject && child instanceof ShotObject){
            AgentObject g = (AgentObject)parent;
            ShotObject so = (ShotObject)child;
            g.setShotObject(so);
        }else if(parent instanceof AgentObject && child instanceof AiItemObject){
            AgentObject g = (AgentObject)parent;
            AiItemObject so = (AiItemObject)child;
            g.addAiItemObject(so);
        }else if(parent instanceof AgentObject && child instanceof AiCheckObject){
            AgentObject g = (AgentObject)parent;
            AiCheckObject so = (AiCheckObject)child;
            g.addAiCheck(so);
        }else System.err.println(parent+" -> "+child);
    }
}
