package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.TransformGroupObject;
import ws.loaders.groovy.objects.TransformObject;

import javax.media.j3d.Transform3D;
import java.util.Map;

public class TransformGroupElement extends GroupElement {

    @Override
    public TransformGroupObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TransformGroupObject o = new TransformGroupObject(value, attributes);

        processGroup(o, value, attributes);
        if(attributes != null)attributes.clear();

        return o;
    }

    protected void processGroup(TransformGroupObject o, Object value, Map attributes){
        this.setSceneObjectType(o, value, attributes);

        if(value instanceof TransformObject)o.setTransformObject( (TransformObject)value );
        else if(value instanceof Transform3D)o.setTransform3D((Transform3D) value);

        if(attributes != null){
            /* Object tmp = attributes.get(SceneBuilder.lsSystem);
            if(tmp != null) o.setTransform3D( ((LsObject)tmp).getUserEndPosition() ); */

            Object tmp = attributes.get(SceneBuilder.transform);
            if(tmp != null){
                if(tmp instanceof TransformObject)o.setTransformObject( (TransformObject)tmp );
                if(tmp instanceof Transform3D)o.setTransform3D((Transform3D) tmp);
            }
        }
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof TransformGroupObject && child instanceof TransformObject){
            TransformGroupObject g = (TransformGroupObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTransformObject(so);
        }else super.setChild(builder, parent, child);
    }
}
