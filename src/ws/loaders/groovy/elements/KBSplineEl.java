package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.IntKBSplineObj;

import java.util.Map;

public final class KBSplineEl extends PathInterpolatorEl{

    @Override
    public final IntKBSplineObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntKBSplineObj o = new IntKBSplineObj(value, attributes);
        processInt(o, value, attributes);
        if(attributes != null)attributes.clear();
        return o;
    }
}
