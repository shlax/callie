package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.IntRotPosScalePathObj;

import java.util.Map;

public final class RotPosScalePathEl extends PathInterpolatorEl{

    @Override
    public final IntRotPosScalePathObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntRotPosScalePathObj o = new IntRotPosScalePathObj(value, attributes);
        processInt(o, value, attributes);
        if(attributes != null)attributes.clear();
        return o;
    }
}
