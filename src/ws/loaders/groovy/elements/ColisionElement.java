package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.Map;


public final class ColisionElement extends AbstractFactory {

    public final ColisionObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ColisionObject o = new ColisionObject(value, attributes);

        if(value instanceof Point3f) o.setPoint((Point3f)value);
        else if(value instanceof Tuple3f) o.setPoint(new Point3f((Tuple3f)value));

        if(attributes != null){

            Object tmp = attributes.get(SceneBuilder.point);
            if(tmp instanceof Point3f) o.setPoint((Point3f)tmp);
            else if(tmp instanceof Tuple3f) o.setPoint(new Point3f((Tuple3f)tmp));
            else if(tmp instanceof Point) o.setPoint(((Point)tmp).getPoint3f());
            else if(tmp instanceof Vector) o.setPoint(((Vector)tmp).getPoint3f());

            tmp = attributes.get(SceneBuilder.colisionRadius);
            o.setRadius(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            attributes.clear();
        }
        return o;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof ColisionObject && child instanceof Point){
            ColisionObject g = (ColisionObject)parent;
            Point so = (Point)child;
            g.setPoint(so.getPoint3f());
        }else if(parent instanceof ColisionObject && child instanceof Vector){
            ColisionObject g = (ColisionObject)parent;
            Vector so = (Vector)child;
            g.setPoint(so.getPoint3f());
        }else super.setChild(builder, parent, child);
    }
}
