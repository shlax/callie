package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.objects.KeyFrameObj;
import ws.loaders.groovy.objects.PathInterpolatorObj;

public abstract class PathInterpolatorEl extends InterpolatorEl{

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
