package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.IntPositionObj;

import java.util.Map;

public final class PositionEl extends SimpleInterpolatorEl{

    @Override
    public final IntPositionObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntPositionObj inter = new IntPositionObj(value, attributes);

        this.processSimpleInt(inter, value, attributes);

        return inter;
    }
}
