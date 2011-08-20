package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.BhoneSkinFrameObject;
import ws.loaders.tools.SoundLoader;
import ws.loaders.tools.joint.BhoneFrameLoader;
import ws.loaders.tools.joint.FrameType;

import java.util.Map;

public final class BhoneSkinFrameElement extends AbstractFactory implements Doc{
    private final BhoneFrameLoader bhoneFrameLoader;
    private final SoundLoader soundLoader; // = new SoundLoader();

    public BhoneSkinFrameElement(BhoneFrameLoader bhoneFrameLoader, SoundLoader soundLoader) {
        this.bhoneFrameLoader = bhoneFrameLoader;        
        this.soundLoader = soundLoader;
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr("*", "file", "String", "path to (*.ang) file"),
            new DocAttr("*/-", "name", "String", "for <a href=\"control.php\">control</a>: \"STAND\", \"RUN1\", \"RUN2\", \"RUN3\", \"RUN4\"\"JUMPL1\", \"JUMPL2\", \"JUMPL3\", \"JUMPR1\", \"JUMPR2\", \"JUMPR3\", \"RIFLE_STAND\", \"RIFLE_RUN1\", \"RIFLE_RUN2\", \"RIFLE_RUN3\", \"RIFLE_RUN4\", \"RIFLE_FIRE\", \"RIFLE_PICK\", \"DOWN\", \"STAND\", \"DEAD_SHOT\", \"DEAD_JUMP\"<br>for <a href=\"agent.php\">agent</a>: \"STAND\", \"RUN1\", \"RUN2\", \"RUN3\" ,\"RUN4\", \"ROTATE1\", \"ROTATE2\", \"DEAD\""),
            new DocAttr(null, "clip", "String", "path to sound file"),
            new DocAttr(null, "keyCode", "Character"),
            new DocAttr(null, "frameType", "{enableWeapon,disableWeapon}"),
        };
    }


    @Override
    public final BhoneSkinFrameObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        BhoneSkinFrameObject o = new BhoneSkinFrameObject(bhoneFrameLoader, soundLoader);

        if(value != null) o.setBhoneFrame(value.toString() );

        if(attributes != null){
            Object tmp = attributes.get(SceneBuilder.name);
            if(tmp != null) o.setName(tmp.toString());

            tmp = attributes.get(SceneBuilder.file);
            if(tmp != null) o.setBhoneFrame(tmp.toString() );

            tmp = attributes.get(SceneBuilder.clip);
            if(tmp != null) o.setClip(tmp.toString() );

            tmp = attributes.get(SceneBuilder.keyCode);
            if(tmp != null)o.setKeyCode( (Character)tmp );

            tmp = attributes.get(SceneBuilder.frameType);
            if(tmp != null)o.setFrameType( tmp instanceof FrameType ? (FrameType)tmp : FrameType.valueOf(tmp.toString()) );

            attributes.clear();
        }

        return o;
    }

    @Override
    public String docDescription() {
        return "character skeleton pose";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "frame(\"data/amy/keys/stand.ang\", name:\"STAND\");",
            "frame(\"data/amy/keys/run1.ang\", name:\"RUN1\",\n" +
                    "      clip:\"data/amy/sounds/step.wav\");"
        };
    }

    @Override
    public String docValue() {
        return "as: |file|";
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return null;
    }

    @Override
    public DocAction[] docActions() {
        return null;
    }

    @Override
    public DocControl[] docControl() {
        return null;
    }
}
