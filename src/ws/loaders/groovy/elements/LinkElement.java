package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.LinkObject;
import ws.loaders.groovy.objects.SharedGroupObject;

import java.util.Map;

public final class LinkElement extends NodeElement {

    @Override
    public final LinkObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        LinkObject o = new LinkObject(value, attributes);

        /*if(value instanceof SharedGroup){
            o.setSharedGroup((SharedGroup)value);
        }else */
        if(value instanceof SharedGroupObject){
            o.setSharedGroup((SharedGroupObject)value);
        }

        return o;
    }
    
}
