package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AiItemObject;
import ws.loaders.groovy.objects.TransformObject;

import javax.vecmath.Matrix4f;
import java.util.Map;

public final class AiItemElement extends TransformGroupElement {

    @Override
    public final AiItemObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AiItemObject t = new AiItemObject(value, attributes);

        if(value !=null) t.setBhoneName(value instanceof String ? (String)value : value.toString());

        Object v = attributes.get(SceneBuilder.bhone);
        t.setBhoneName(v instanceof String ? (String)v : v.toString());

        v = attributes.get(SceneBuilder.transform);
        if(v != null){
            if(v instanceof TransformObject) t.setTransform( ((TransformObject)v).getTransform3D() );
            else if(v instanceof Matrix4f) t.setTransform( (Matrix4f)v );
        }

        attributes.clear();
        
        return t;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof AiItemObject && child instanceof TransformObject){
            AiItemObject g = (AiItemObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTransform(so.getTransform3D());
        }else super.setChild(builder, parent, child);

    }
}
