package ws.loaders.groovy.objects;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.elements.TransformGroupElement;

import java.util.Map;

public final class WeaponEl extends TransformGroupElement{

    @Override
    public WeaponObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        WeaponObj o = new WeaponObj(value, attributes);

        processGroup(o, value, attributes);
        if(attributes != null)attributes.clear();

        return o;
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;
        if(parent instanceof WeaponObj && child instanceof TransformObject)return;

        super.setChild(builder, parent, child);
    }

}
