package ws.loaders.groovy.elements;

import groovy.lang.Closure;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AgentObject;
import ws.loaders.groovy.objects.AiCheckObject;
import ws.loaders.groovy.objects.AiObject;
import ws.loaders.groovy.objects.MapObject;

import java.util.Collection;
import java.util.Map;

public final class AiElement extends AbstractFactory {


    @Override
    public final AiObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AiObject ai = new AiObject(value, attributes);

        if(value != null){
            if(value instanceof MapObject){
                ai.addMapObject((MapObject)value);
            }else if(value instanceof Collection){
                for(Object o : (Collection)value)ai.addMapObject( (MapObject)o );
            }else if(value instanceof Float){
                ai.setActiveDistance( (Float)value );
            }
        }

        if(attributes !=  null){
            Object tmp = attributes.get(SceneBuilder.map);
            if(tmp != null){
                if(tmp instanceof MapObject){
                    ai.addMapObject((MapObject)tmp);
                }else if(tmp instanceof Collection){
                    for(Object o : (Collection)tmp)ai.addMapObject( (MapObject)o );
                }
            }

          /*  tmp = attributes.get(SceneBuilder.lsSystem);
            if(tmp != null) ai.setLsObject((LsObject)tmp); */

         //   tmp = attributes.get(SceneBuilder.mapType);
         //   if(tmp != null) ai.setLsType((Type)tmp);

            tmp = attributes.get(SceneBuilder.onDettect);
            if(tmp != null) ai.setOnDettect((Closure) tmp);

            tmp = attributes.get(SceneBuilder.activeDistance);
            if(tmp != null) ai.setActiveDistance(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()));

            attributes.clear();
        }
        
        return ai;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;
        
        if(parent instanceof AiObject && child instanceof AgentObject){
            AiObject s = (AiObject)parent;
            AgentObject a = (AgentObject)child;
            s.addAgentObject(a);
        }else if(parent instanceof AiObject && child instanceof MapObject){
            AiObject s = (AiObject)parent;
            MapObject a = (MapObject)child;
            s.addMapObject(a);
        }else if(parent instanceof AiObject && child instanceof AiCheckObject){
            AiObject s = (AiObject)parent;
            AiCheckObject a = (AiCheckObject)child;
            s.addAiCheck(a);
        }else System.err.println(parent+" -> "+child);
    }
    
}
