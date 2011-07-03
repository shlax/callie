package ws.loaders.groovy.elements;


import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.IntRotPosPathObj;

import java.util.Map;

public final class RotPosPathEl extends PathInterpolatorEl{

    @Override
    public final IntRotPosPathObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntRotPosPathObj o = new IntRotPosPathObj(value, attributes);
        processInt(o, value, attributes);
        if(attributes != null)attributes.clear();
        return o;
    }

}
