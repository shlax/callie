package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Vector;

import java.util.Map;

public final class VectorEl extends AbstractFactory {

    @Override
    public final Vector newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        Float x = 0f;
        Float y = 0f;
        Float z = 0f;
        if(value != null){
            float val = Float.parseFloat(value.toString());
            x = val;
            y = val;
            z = val;
        }

        Object o = attributes.get(SceneBuilder.x);
        if (o != null) x = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.y);
        if (o != null) y = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.z);
        if (o != null) z = Float.parseFloat(o.toString());

        Vector v =  new Vector(x, y, z, value, attributes);
        attributes.clear();
        return v;
    }

}
