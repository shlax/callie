package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.IntPositionPathObj;

import java.util.Map;

public final class PositionPathInterpolatorEl extends PathInterpolatorEl{

    @Override
    public final IntPositionPathObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntPositionPathObj o = new IntPositionPathObj(value, attributes);
        processInt(o, value, attributes);
        if(attributes != null)attributes.clear();
        return o;
    }

}
