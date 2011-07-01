package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.AmbientLightObject;

import java.util.Map;

public final class AmbientLightElement extends LightElement {

    @Override
    public final Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AmbientLightObject l = new AmbientLightObject(value, attributes);
        //l.setColor();
        //System.out.println(value);
        this.processLight(l, value, attributes);

        attributes.clear();
        return l;
    }
    
}
