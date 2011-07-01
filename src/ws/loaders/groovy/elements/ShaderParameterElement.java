package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.ShaderParameterObject;
import ws.loaders.groovy.objects.TextureObject;

import java.util.Map;


public final class ShaderParameterElement extends AbstractFactory {

    public final ShaderParameterObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ShaderParameterObject o = new ShaderParameterObject(value, attributes);

        if(value != null && value instanceof String) o.setName((String)value);

        if(attributes != null){
            
            Object tmp  = attributes.get(SceneBuilder.value); 
            if(tmp != null) o.setValue(tmp);

            tmp = attributes.get(SceneBuilder.name);
            if(tmp != null) o.setName(tmp.toString());

            attributes.clear();
        }
        return o;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof ShaderParameterObject && child instanceof TextureObject){
            ShaderParameterObject g = (ShaderParameterObject)parent;
            TextureObject so = (TextureObject)child;
            g.setTextureObject(so);
        }else System.err.println(parent+" -> "+child);
    }

}
