package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.TransformGroupObject;
import ws.loaders.groovy.objects.TransformObject;

import javax.media.j3d.Transform3D;
import java.util.Map;

public final class TransformGroupElement extends GroupElement {

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TransformGroupObject o = new TransformGroupObject(value, attributes);

        this.setSceneObjectType(o, value, attributes);

        if(attributes != null){
            /* Object tmp = attributes.get(SceneBuilder.lsSystem);
            if(tmp != null) o.setTransform3D( ((LsObject)tmp).getUserEndPosition() ); */

            Object tmp = attributes.get(SceneBuilder.transform);
            if(tmp != null){
                if(tmp instanceof TransformObject)o.setTransformObject( (TransformObject)tmp );
                if(tmp instanceof Transform3D)o.setTransform3D((Transform3D) tmp);
            }

            attributes.clear();
        }

        return o;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(parent instanceof TransformGroupObject && child instanceof TransformObject){
            TransformGroupObject g = (TransformGroupObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTransformObject(so);
        }else super.setChild(builder, parent, child);
    }
}
