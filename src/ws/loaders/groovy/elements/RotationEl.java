package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.IntRotationObj;

import java.util.Map;

public final class RotationEl extends SimpleInterpolatorEl{

    @Override
    public final IntRotationObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntRotationObj inter = new IntRotationObj(value, attributes);

        this.processSimpleInt(inter, value, attributes);

        return inter;
    }
}
