package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AnimationFrameObject;
import ws.loaders.groovy.objects.AnimationObject;
import ws.loaders.groovy.objects.Tuple;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.Map;

public final class AnimationElement extends AbstractFactory {

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
            if(tmp != null)o.setDestination( tmp instanceof Point3f ? (Point3f)tmp : new Point3f((Tuple3f)tmp));

            tmp = attributes.get(SceneBuilder.frameWindow);
            if(tmp != null)o.setKeyFrameRatio( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.sourceRadius);
            if(tmp != null)o.setSourceRadius( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.sourceAngle);
            if(tmp != null)o.setSourceAngleTolerantion( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.removeAfterPlay);
            if(tmp != null)o.setRemoveAfterPlay( tmp instanceof Boolean ? (Boolean)tmp : Boolean.parseBoolean(tmp.toString()) );

            attributes.clear();
        }
        return o;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof AnimationObject && child instanceof AnimationFrameObject){
            AnimationObject g = (AnimationObject)parent;
            AnimationFrameObject so = (AnimationFrameObject)child;
            g.addAnimationFrameObject(so);
        }else System.err.println(parent+" -> "+child);
    }

}
