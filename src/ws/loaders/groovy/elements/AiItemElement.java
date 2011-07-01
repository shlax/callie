package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AiItemObject;
import ws.loaders.groovy.objects.TransformGroupObject;
import ws.loaders.groovy.objects.TransformObject;

import javax.media.j3d.Transform3D;
import java.util.Map;

public final class AiItemElement extends AbstractFactory {

    @Override
    public final Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AiItemObject t = new AiItemObject(value, attributes);

        Object v = attributes.get(SceneBuilder.bhone);
        t.setBhoneName(v instanceof String ? (String)v : v.toString());

        v = attributes.get(SceneBuilder.transform);
        if(v != null){
            if(v instanceof TransformObject) t.setTransform( ((TransformObject)v).getTransform3D() );
            else if(v instanceof Transform3D) t.setTransform( (Transform3D)v ); 
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
        }else if(parent instanceof AiItemObject && child instanceof TransformGroupObject){
            AiItemObject g = (AiItemObject)parent;
            TransformGroupObject so = (TransformGroupObject)child;
            g.setTransformGroup(so.getTransformGroup()); 
        }else System.err.println(parent+" -> "+child);

    }
}
