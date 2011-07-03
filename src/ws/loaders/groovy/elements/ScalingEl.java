package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.IntScaleObj;

import java.util.Map;

public final class ScalingEl extends SimpleInterpolatorEl{

    @Override
    public final IntScaleObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntScaleObj inter = new IntScaleObj(value, attributes);

        this.processSimpleInt(inter, value, attributes);

        return inter;
    }
}
