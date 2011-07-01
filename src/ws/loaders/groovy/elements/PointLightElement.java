package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Point;
import ws.loaders.groovy.objects.PointLightObject;
import ws.loaders.groovy.objects.SpotLightObject;
import ws.loaders.groovy.objects.Vector;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.Map;

public final class PointLightElement extends LightElement {

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        PointLightObject l = new PointLightObject(value, attributes);
        this.processLight(l, value, attributes);

         Object o = attributes.get(SceneBuilder.position);
        if(o instanceof Point3f) l.setPosition((Point3f)o );
        else if(o instanceof Tuple3f) l.setPosition( new Point3f((Tuple3f)o) );
        else if(o instanceof Point) l.setPosition(((Point)o).getPoint3f() );
        else if(o instanceof Vector) l.setPosition(((Vector)o).getPoint3f() );

        o = attributes.get(SceneBuilder.attenuation);
        if(o instanceof Point3f) l.setAttenuation((Point3f) o);
        else if(o instanceof Tuple3f) l.setAttenuation(new Point3f((Tuple3f) o));
        else if(o instanceof Point) l.setAttenuation(((Point) o).getPoint3f());
        else if(o instanceof Vector) l.setAttenuation(((Vector) o).getPoint3f());

        return l;
    }

     @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof PointLightObject && child instanceof Point){
            PointLightObject g = (PointLightObject)parent;
            Point so = (Point)child;
            g.setPosition(so.getPoint3f());
        }super.setChild(builder, parent, child);
    }
    
}
