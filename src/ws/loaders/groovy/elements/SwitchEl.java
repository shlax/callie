package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.objects.ObjSwitch;

import java.util.Map;

public class SwitchEl extends AbstractFactory {

    @Override
    public final ObjSwitch newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ObjSwitch sw = new ObjSwitch();

        return sw;
    }

}
