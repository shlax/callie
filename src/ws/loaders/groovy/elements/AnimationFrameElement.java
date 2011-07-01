package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AnimationFrameObject;
import ws.loaders.tools.joint.FrameType;

import java.util.Map;

public final class AnimationFrameElement extends AbstractFactory {

    @Override
    public final AnimationFrameObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AnimationFrameObject o = new AnimationFrameObject(value, attributes);

        if(value != null)o.setFile( value.toString() );

        if(attributes != null){
            Object tmp = attributes.get(SceneBuilder.file);
            if(tmp != null)o.setFile( tmp.toString() );

            tmp = attributes.get(SceneBuilder.keyCode);
            if(tmp != null)o.setKeyCode( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.frameType);
            if(tmp != null)o.setFrameType( tmp instanceof FrameType ? (FrameType)tmp : FrameType.valueOf(tmp.toString()) );
            
            attributes.clear();
        }

        return o;
    }
}
