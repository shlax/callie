package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Color;

import java.util.Map;

public final class ColorEl extends AbstractFactory {

    @Override
    public final Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        Float r = 0f;
        Float g = 0f;
        Float b = 0f;
        Float a = 0f;
        if(value != null){
            float val = Float.parseFloat(value.toString());
            r = val;
            g = val;
            b = val;
        }

        Object o = attributes.get(SceneBuilder.r);
        if (o != null) r = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.g);
        if (o != null) g = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.b);
        if (o != null) b = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.a);
        if (o != null) a = Float.parseFloat(o.toString());

        Color col = a == null ? new Color(r, g, b, value, attributes) : new Color(r, g, b, a, value, attributes);

        attributes.clear();

        return col;
    }

}
