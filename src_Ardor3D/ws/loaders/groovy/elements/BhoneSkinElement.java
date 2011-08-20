package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import wa.Appearance;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AppearanceObject;
import ws.loaders.groovy.objects.BhoneSkinFrameObject;
import ws.loaders.groovy.objects.BhoneSkinObject;
import ws.loaders.tools.joint.BhoneLoader;
import ws.loaders.tools.joint.SkinLoader;

import java.util.Map;
import java.util.Set;

public final class BhoneSkinElement extends AbstractFactory{
    private final BhoneLoader bhoneLoader = new BhoneLoader();
    private final SkinLoader skinLoader = new SkinLoader();

    @Override
    public final BhoneSkinObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        BhoneSkinObject bso = new BhoneSkinObject(bhoneLoader, skinLoader);

        if(value != null){
            if(value instanceof AppearanceObject ) bso.setAppearance( ((AppearanceObject)value).getAppearance() );
            else if(value instanceof Appearance) bso.setAppearance( (Appearance)value );
        }

        if(attributes != null){
            Object tmp = attributes.get(SceneBuilder.appearance);
            if(tmp != null){
                if(tmp instanceof AppearanceObject ) bso.setAppearance( ((AppearanceObject)tmp).getAppearance() );
                else if(tmp instanceof Appearance) bso.setAppearance( (Appearance)tmp );  
            }

            tmp = attributes.get(SceneBuilder.scale);
            if(tmp != null) bso.setScale(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()));

            tmp = attributes.get(SceneBuilder.bhoneFile);
            if(tmp != null) bso.setBhoneFile( tmp.toString() );

            tmp = attributes.get(SceneBuilder.skinFile);
            if(tmp != null) bso.setSkinFile( tmp.toString() );

            for(Map.Entry<?, ?> e : (Set<Map.Entry<?, ?>>)attributes.entrySet()){
                Object val = e.getValue();
                if(val instanceof BhoneSkinFrameObject){
                    bso.addBhoneSkinFrame(e.getKey().toString(), (BhoneSkinFrameObject)val );
                }
            }
            
            attributes.clear();
        }

        return bso;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof BhoneSkinObject && child instanceof AppearanceObject){
            BhoneSkinObject g = (BhoneSkinObject)parent;
            AppearanceObject so = (AppearanceObject)child;
            g.setAppearance(so.getAppearance());
        }else if(parent instanceof BhoneSkinObject && child instanceof BhoneSkinFrameObject){
            BhoneSkinObject g = (BhoneSkinObject)parent;
            BhoneSkinFrameObject so = (BhoneSkinFrameObject)child;
            g.addBhoneSkinFrame(so.getName(), so);
        }else System.err.println(parent+" -> "+child);
    }
}
