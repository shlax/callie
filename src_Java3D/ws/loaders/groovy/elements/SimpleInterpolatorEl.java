package ws.loaders.groovy.elements;

import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.SimpleInterpolator;

import java.util.Map;

public abstract class SimpleInterpolatorEl extends InterpolatorEl{

    protected final void processSimpleInt(SimpleInterpolator inter, Object value, Map<?, ?> attributes){

        if(attributes != null){
            Object o = attributes.get(SceneBuilder.max);
            if(o != null) inter.setMax( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

            o = attributes.get(SceneBuilder.min);
            if(o != null) inter.setMin( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

            //attributes.clear();
        }

        this.processInt(inter, value, attributes);
    }
}
