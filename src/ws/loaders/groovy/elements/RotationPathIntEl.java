package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.IntRotationPathObj;

import java.util.Map;

public final class RotationPathIntEl extends PathInterpolatorEl{

    @Override
    public final IntRotationPathObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntRotationPathObj o = new IntRotationPathObj(value, attributes);
        processInt(o, value, attributes);
        if(attributes != null)attributes.clear();
        return o;
    }

}
