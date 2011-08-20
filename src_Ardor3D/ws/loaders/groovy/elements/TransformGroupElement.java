package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;

import javax.vecmath.Matrix4f;
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
        else if(value instanceof Matrix4f)o.setTransform3D((Matrix4f) value);

        if(attributes != null){
            /* Object tmp = attributes.get(SceneBuilder.lsSystem);
            if(tmp != null) o.setTransform3D( ((LsObject)tmp).getUserEndPosition() ); */

            Object tmp = attributes.get(SceneBuilder.transform);
            if(tmp != null){
                if(tmp instanceof TransformObject)o.setTransformObject( (TransformObject)tmp );
                if(tmp instanceof Matrix4f)o.setTransform3D((Matrix4f) tmp);
            }

            tmp = attributes.get(SceneBuilder.appearance);
            if(tmp != null) o.setAppearanceObject((AppearanceObject)tmp);
        }
    }



    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof TransformGroupObject && child instanceof AppearanceObject){
            TransformGroupObject g = (TransformGroupObject)parent;
            AppearanceObject so = (AppearanceObject)child;
            g.setAppearanceObject(so);
        }else if(parent instanceof TransformGroupObject && child instanceof TransformObject){
            TransformGroupObject g = (TransformGroupObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTransformObject(so);
        }else if(parent instanceof TransformGroupObject && child instanceof InterpolatorObj){
            TransformGroupObject g = (TransformGroupObject)parent;
            InterpolatorObj so = (InterpolatorObj)child;
            g.setInterpolator(so);
        }else super.setChild(builder, parent, child);
    }

}
