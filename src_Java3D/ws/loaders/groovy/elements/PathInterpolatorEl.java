package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.KeyFrameObj;
import ws.loaders.groovy.objects.PathInterpolatorObj;

import java.util.Collection;
import java.util.Map;

public abstract class PathInterpolatorEl extends InterpolatorEl{

    protected final void processInt(PathInterpolatorObj o, Object value, Map<?, ?> attributes){
        if(attributes != null){
            Object q = attributes.get(SceneBuilder.key);
            if(q instanceof KeyFrameObj)o.addKeyFrameObj( (KeyFrameObj)q );
            else if(q instanceof Collection) for (Object w : (Collection)q)o.addKeyFrameObj( (KeyFrameObj)w );
        }
        super.processInt(o, value, attributes);
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof PathInterpolatorObj && child instanceof KeyFrameObj){
            PathInterpolatorObj g = (PathInterpolatorObj)parent;
            KeyFrameObj so = (KeyFrameObj)child;
            g.addKeyFrameObj(so);
        }else super.setChild(builder, parent, child);
    }
}
