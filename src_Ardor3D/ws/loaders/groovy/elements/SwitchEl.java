package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.ObjSwitch;

import java.util.Map;

public final class SwitchEl extends GroupElement {

    @Override
    public final ObjSwitch newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ObjSwitch sw = new ObjSwitch(value, attributes);
        setSceneObjectType(sw, value, attributes);

        if(value instanceof Boolean) sw.setEnabled( (Boolean)value );

        Object typ = attributes.get(SceneBuilder.enabled);
        if(typ != null) sw.setEnabled( typ instanceof Boolean ? (Boolean) typ : Boolean.parseBoolean(typ.toString()) );

        return sw;
    }


}
