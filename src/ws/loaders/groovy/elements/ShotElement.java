package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AppearanceObject;
import ws.loaders.groovy.objects.ShotObject;
import ws.loaders.tools.SoundLoader;

import java.util.Map;

public final class ShotElement extends AbstractFactory {

    private final SoundLoader soundLoader;
    public ShotElement(SoundLoader soundLoader) {
        this.soundLoader = soundLoader;
    }

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ShotObject so = new ShotObject(soundLoader);
        if(attributes != null){
            Object tmp = attributes.get(SceneBuilder.appearance);
            if(tmp != null) so.setAppearance((AppearanceObject)tmp);

            tmp = attributes.get(SceneBuilder.minDistance);
            if(tmp != null) so.setMinDistance(tmp instanceof Float ? (Float) tmp : Float.parseFloat(tmp.toString()));

            tmp = attributes.get(SceneBuilder.maxDistance);
            if(tmp != null) so.setMaxDistance(tmp instanceof Float ? (Float) tmp : Float.parseFloat(tmp.toString()));

            tmp = attributes.get(SceneBuilder.shotDuration);
            if(tmp != null) so.setShotDuration(tmp instanceof Float ? (Float) tmp : Float.parseFloat(tmp.toString()));

            tmp = attributes.get(SceneBuilder.shotRadius);
            if(tmp != null) so.setShotRadius(tmp instanceof Float ? (Float) tmp : Float.parseFloat(tmp.toString()));

            tmp = attributes.get(SceneBuilder.firePower);
            if(tmp != null) so.setFirePower(tmp instanceof Float ? (Float) tmp : Float.parseFloat(tmp.toString()));

            tmp = attributes.get(SceneBuilder.cadence);
            if(tmp != null) so.setCadence(tmp instanceof Float ? (Float) tmp : Float.parseFloat(tmp.toString()));

            tmp = attributes.get(SceneBuilder.cadence);
            if(tmp != null) so.setCadence(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.clip);
            if(tmp != null) so.setClip(tmp.toString() );

            attributes.clear();
        }
        return so;
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(parent instanceof ShotObject && child instanceof AppearanceObject){
            ShotObject g = (ShotObject)parent;
            AppearanceObject so = (AppearanceObject)child;
            g.setAppearance(so);
        }
    }
}
