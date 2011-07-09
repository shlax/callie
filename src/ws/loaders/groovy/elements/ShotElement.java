package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AppearanceObject;
import ws.loaders.groovy.objects.ShotObject;
import ws.loaders.tools.SoundLoader;

import java.util.Map;

public final class ShotElement extends AbstractFactory implements Doc{

    private final SoundLoader soundLoader;
    public ShotElement(SoundLoader soundLoader) {
        this.soundLoader = soundLoader;
    }

    @Override
    public String docDescription() {
        return null;
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "shot(appearance:shotMat, clip:\"data/amy/sounds/shot.wav\" )",
            "shot(appearance:shotMat, firePower:1f, cadence:0.5f,\n" +
            "     clip:\"data/soldier/sounds/shot.wav\" )",
        };
    }

    @Override
    public String docValue() {
        return "as: |appearance|firePower|";
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "appearance", "[0..1]")
        };
    }

    @Override
    public DocAction[] docActions() {
        return null;
    }

    @Override
    public DocControl[] docControl() {
        return null;
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "appearance", "appearance"),
            new DocAttr(null, "firePower", "Float", "1f", null),
            new DocAttr(null, "cadence", "Float", "0.15f", "//sec"),
            new DocAttr(null, "clip", "String", "path to sound file"),
            new DocAttr(null, "minDistance", "Float", "10f", "//meter"),
            new DocAttr(null, "maxDistance", "Float", "50f", "//meter"),
            new DocAttr(null, "shotDuration", "Float", "0.05f", "//sec"),
            new DocAttr(null, "shotRadius", "Float", "0.001f", "//meter"),
        };
    }

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ShotObject so = new ShotObject(soundLoader);

        if(value != null){
            if(value instanceof AppearanceObject) so.setAppearance( (AppearanceObject)value );
            if(value instanceof Float) so.setFirePower((Float) value);
        }

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

            tmp = attributes.get(SceneBuilder.clip);
            if(tmp != null) so.setClip(tmp.toString() );

            attributes.clear();
        }
        return so;
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof ShotObject && child instanceof AppearanceObject){
            ShotObject g = (ShotObject)parent;
            AppearanceObject so = (AppearanceObject)child;
            g.setAppearance(so);
        }else System.err.println(parent+" -> "+child);
    }
}
