package ws.loaders.groovy.elements;

import com.ardor3d.scenegraph.controller.ComplexSpatialController;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.InterpolatorObj;
import ws.loaders.groovy.objects.Quat;
import ws.loaders.groovy.objects.Tuple;

import java.util.Map;

public final class InterpolatorElm extends AbstractFactory {

    @Override
    public final InterpolatorObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        InterpolatorObj obj = new InterpolatorObj(value, attributes);

        Object o = attributes.get(SceneBuilder.curve);
        if(o != null)obj.setCurve(o instanceof Boolean ? (Boolean)o : Boolean.parseBoolean(o.toString()));

        o = attributes.get(SceneBuilder.active);
        if(o != null)obj.setActive(o instanceof Boolean ? (Boolean) o : Boolean.parseBoolean(o.toString()));

        o = attributes.get(SceneBuilder.speed);
        if(o != null)obj.setSpeed(o instanceof Float ? (Float)o : Float.parseFloat(o.toString()));

        o = attributes.get(SceneBuilder.repeatType);
        if(o != null)obj.setRepeatType((ComplexSpatialController.RepeatType)o);

        attributes.clear();

        return obj;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof InterpolatorObj && child instanceof Quat){
            InterpolatorObj g = (InterpolatorObj)parent;
            Quat so = (Quat)child;
            g.addRot(so.getQuaternion());
        }else if(parent instanceof InterpolatorObj && child instanceof Tuple){
            InterpolatorObj g = (InterpolatorObj)parent;
            Tuple so = (Tuple)child;
            g.addPoint(so.getVector3());
        }else System.err.println(parent+" -> "+child);
    }

}
