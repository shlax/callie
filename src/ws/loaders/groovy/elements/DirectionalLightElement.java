package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.DirectionalLightObject;
import ws.loaders.groovy.objects.Point;
import ws.loaders.groovy.objects.Vector;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class DirectionalLightElement extends LightElement {

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        DirectionalLightObject l = new DirectionalLightObject(value, attributes);
        this.processLight(l, value, attributes);
        if(attributes != null){
            Object val = attributes.get(SceneBuilder.direction);
            if(val != null){
                if(val instanceof Vector3f) l.setDirection( (Vector3f)val );
                else if(val instanceof Tuple3f) l.setDirection( new Vector3f((Tuple3f)val) );
                else if(val instanceof Point) l.setDirection( ((Point)val).getVector3f() );
                else if(val instanceof Vector) l.setDirection( ((Vector)val).getVector3f() );
            }
        }
        return l;
    }   

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof DirectionalLightObject && child instanceof Point){
            DirectionalLightObject g = (DirectionalLightObject)parent;
            Point so = (Point)child;
            g.setDirection(so.getVector3f() );
        }else if(parent instanceof DirectionalLightObject && child instanceof Vector){
            DirectionalLightObject g = (DirectionalLightObject)parent;
            Vector so = (Vector)child;
            g.setDirection(so.getVector3f());
        }else super.setChild(builder, parent, child);
    }

}
