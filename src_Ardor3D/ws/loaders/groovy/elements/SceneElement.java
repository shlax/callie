package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;

import java.util.Map;

public final class SceneElement extends AbstractFactory {

    @Override
    public final SceneObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        SceneObject o = new SceneObject(value, attributes);

        if(attributes != null){

            Object tmp = attributes.get(SceneBuilder.appearance);
            if(tmp != null) o.setAppearanceObject((AppearanceObject)tmp);

            attributes.clear();
        }

        return o;
    }


    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof SceneObject && child instanceof AppearanceObject){
            SceneObject g = (SceneObject)parent;
            AppearanceObject so = (AppearanceObject)child;
            g.setAppearanceObject(so);
        }else if(parent instanceof SceneObject && child instanceof AiObject){
            SceneObject s = (SceneObject)parent;
            AiObject so = (AiObject)child;
            s.setAiObject(so);
        }else if(parent instanceof SceneObject && child instanceof ControlObject){
            SceneObject s = (SceneObject)parent;
            ControlObject so = (ControlObject)child;
            s.setControlObject(so);
        }else if(parent instanceof SceneObject && child instanceof MapObject){
            SceneObject s = (SceneObject)parent;
            MapObject so = (MapObject)child;
            s.addMapObject(so);
        }else if(parent instanceof SceneObject && child instanceof NodeObject){
            SceneObject s = (SceneObject)parent;
            NodeObject so = (NodeObject)child;
            if(so.getSceneObjectType() == SceneBuilder.SceneObjectType.NORMAL) s.addRoot(so);
            else if(so.getSceneObjectType() == SceneBuilder.SceneObjectType.EFFECT) s.addEffect(so);
            else if(so.getSceneObjectType() == SceneBuilder.SceneObjectType.CAMERA) s.addCameraColisions(so);
            else System.err.println("Undefined type "+so.getSceneObjectType());
        }else System.err.println(parent+" -> "+child);

    }
}
