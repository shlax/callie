package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.NodeObject;

import java.util.Map;

public abstract class NodeElement extends AbstractFactory {

    protected final void setSceneObjectType(NodeObject sceneObject, Object value, Map attributes){
        if(value != null && value instanceof SceneBuilder.SceneObjectType){
            sceneObject.setSceneObjectType( (SceneBuilder.SceneObjectType)value );
        }else if(attributes != null){
            Object val = attributes.get(SceneBuilder.sceneType);
            if(val != null && val instanceof SceneBuilder.SceneObjectType){
                sceneObject.setSceneObjectType( (SceneBuilder.SceneObjectType)val );
            }
        }
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;
        System.err.println(parent+" -> "+child);
    }
}
