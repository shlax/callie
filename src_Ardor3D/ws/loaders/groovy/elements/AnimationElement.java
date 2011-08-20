package ws.loaders.groovy.elements;

import groovy.lang.Closure;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AnimationObject;
import ws.loaders.groovy.objects.BhoneSkinFrameObject;
import ws.loaders.groovy.objects.Tuple;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.Collection;
import java.util.Map;

public final class AnimationElement extends AbstractFactory{

    @Override
    public final AnimationObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AnimationObject o = new AnimationObject(value, attributes);

        if(value != null){
            if(value instanceof Point3f) o.setDestination((Point3f)value);
            else if(value instanceof Tuple3f) o.setDestination( new Point3f((Tuple3f)value) );
            else if(value instanceof Tuple) o.setDestination( ((Tuple)value).getPoint3f() );

        }

        if(attributes != null){

            Object tmp = attributes.get(SceneBuilder.destination);
            if(tmp != null){
                //o.setDestination( tmp instanceof Point3f ? (Point3f)tmp : new Point3f((Tuple3f)tmp));
                if(tmp instanceof Point3f) o.setDestination((Point3f)tmp);
                else if(tmp instanceof Tuple3f) o.setDestination( new Point3f((Tuple3f)tmp) );
                else if(tmp instanceof Tuple) o.setDestination( ((Tuple)tmp).getPoint3f() );
            }

            tmp = attributes.get(SceneBuilder.targetPosition);
            if(tmp != null){
                if(tmp instanceof Point3f) o.setTargetPosition((Point3f)tmp);
                else if(tmp instanceof Tuple3f) o.setTargetPosition( new Point3f((Tuple3f)tmp) );
                else if(tmp instanceof Tuple) o.setTargetPosition( ((Tuple)tmp).getPoint3f() );
                //o.setTargetPosition( tmp instanceof Point3f ? (Point3f)tmp : new Point3f((Tuple3f)tmp));
            }

            tmp = attributes.get(SceneBuilder.onEnter);
            if(tmp != null)o.setOnEnter((Closure)tmp );

            tmp = attributes.get(SceneBuilder.onExit);
            if(tmp != null)o.setOnExit((Closure)tmp );

            tmp = attributes.get(SceneBuilder.frameWindow);
            if(tmp != null)o.setKeyFrameRatio( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.sourceRadius);
            if(tmp != null)o.setSourceRadius( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.sourceAngle);
            if(tmp != null)o.setSourceAngleTolerantion( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.side);
            if(tmp != null)o.setSide( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.shotAngle);
            if(tmp != null)o.setShotAngle( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()));

            tmp = attributes.get(SceneBuilder.frame);
            if(tmp != null){
                if(tmp instanceof BhoneSkinFrameObject) o.addAnimationFrameObject( (BhoneSkinFrameObject)tmp );
                else if(tmp instanceof Collection) for(Object q : (Collection)tmp ) o.addAnimationFrameObject( (BhoneSkinFrameObject)q );
                //o.setSourceAngleTolerantion( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );
            }

            attributes.clear();
        }
        return o;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof AnimationObject && child instanceof BhoneSkinFrameObject){
            AnimationObject g = (AnimationObject)parent;
            BhoneSkinFrameObject so = (BhoneSkinFrameObject)child;
            g.addAnimationFrameObject(so);
        }else System.err.println(parent+" -> "+child);
    }

}
