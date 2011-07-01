package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.SharedGroupObject;

import java.util.Map;

public final class SharedGroupElement extends GroupElement {

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        SharedGroupObject o = new SharedGroupObject(value, attributes);
        setSceneObjectType(o, value, attributes);
        return o;
    }

}
