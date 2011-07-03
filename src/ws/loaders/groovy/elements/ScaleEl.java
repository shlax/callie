package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Scale;

import java.util.Map;

public final class ScaleEl extends AbstractFactory {

    @Override
    public final Scale newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        Float x = null;
        Float y = null;
        Float z = null;
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

        Scale v =  new Scale(x, y, z, value, attributes);
        attributes.clear();
        return v;
    }
}
