package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AiCheckObject;
import ws.loaders.groovy.objects.Point;
import ws.loaders.groovy.objects.Vector;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.Map;

public final class AiCheckElement extends AbstractFactory {
	
    @Override
    public final AiCheckObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AiCheckObject o = new AiCheckObject(value, attributes);

        if(value != null){
            if(value instanceof Point3f) o.setPoint((Point3f)value);
            else if(value instanceof Tuple3f) o.setPoint( new Point3f((Tuple3f)value) );
            else if(value instanceof Point)o.setPoint(((Point) value).getPoint3f());
            else if(value instanceof Vector)o.setPoint( ((Vector) value).getPoint3f() );
        }else{

            Object tmp = attributes.get(SceneBuilder.point);
            if(tmp != null){
                if(tmp instanceof Point3f) o.setPoint((Point3f)tmp);
                else if(tmp instanceof Tuple3f) o.setPoint( new Point3f((Tuple3f)tmp) );
                else if(tmp instanceof Point)o.setPoint(((Point) tmp).getPoint3f());
                else if(tmp instanceof Vector)o.setPoint( ((Vector) tmp).getPoint3f() );
            }

            Object x = attributes.get(SceneBuilder.x);
            Object y = attributes.get(SceneBuilder.y);
            Object z = attributes.get(SceneBuilder.z);

            float xx = x instanceof Float ? (Float)x : Float.parseFloat(x.toString());
            float yy = x instanceof Float ? (Float)y : Float.parseFloat(y.toString());
            float zz = x instanceof Float ? (Float)z : Float.parseFloat(z.toString());

            o.setPoint(new Point3f(xx, yy, zz));
        }

        if(attributes != null) attributes.clear();
        return o;
    }
    
    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof AiCheckObject && child instanceof Point){
            AiCheckObject g = (AiCheckObject)parent;
            Point so = (Point)child;
            g.setPoint(so.getPoint3f());
        }else if(parent instanceof AiCheckObject && child instanceof Vector){
            AiCheckObject g = (AiCheckObject)parent;
            Vector so = (Vector)child;
            g.setPoint(so.getPoint3f());
        }else System.err.println(parent+" -> "+child);
    }
    
}
