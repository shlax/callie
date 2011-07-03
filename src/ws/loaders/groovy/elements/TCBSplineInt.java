package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.IntTCBSplineObj;

import java.util.Map;

public final class TCBSplineInt extends PathInterpolatorEl{

    @Override
    public final IntTCBSplineObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntTCBSplineObj o = new IntTCBSplineObj(value, attributes);
        processInt(o, value, attributes);
        if(attributes != null)attributes.clear();
        return o;
    }
}
