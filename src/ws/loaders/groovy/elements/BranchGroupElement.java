package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.BranchGroupObject;

import java.util.Map;

public final class BranchGroupElement extends GroupElement {

    @Override
    public final Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        BranchGroupObject bo = new BranchGroupObject(value, attributes);
        setSceneObjectType(bo, value, attributes);        
        return bo;
    }
    
}
