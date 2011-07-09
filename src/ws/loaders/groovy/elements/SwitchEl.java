package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.ObjSwitch;

import java.util.Map;

public final class SwitchEl extends GroupElement implements Doc{

    @Override
    public final ObjSwitch newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ObjSwitch sw = new ObjSwitch(value, attributes);
        setSceneObjectType(sw, value, attributes);

        if(value instanceof Boolean) sw.setEnabled( (Boolean)value );

        Object typ = attributes.get(SceneBuilder.enabled);
        if(typ != null) sw.setEnabled( typ instanceof Boolean ? (Boolean) typ : Boolean.parseBoolean(typ.toString()) );

        return sw;
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "model", "[0..N]"),
            new DocSubNode(null, "link", "[0..N]"),
            new DocSubNode(null, "group", "[0..N]"),

            new DocSubNode(null, "onOff", "[0..N]"),

            new DocSubNode(null, "translation", "[0..N]"),
            new DocSubNode(null, "rotation", "[0..N]"),
            new DocSubNode(null, "scaling", "[0..N]"),
            new DocSubNode(null, "posPath", "[0..N]"),
            new DocSubNode(null, "posRotPath", "[0..N]"),
            new DocSubNode(null, "posRotScalePath", "[0..N]"),
            new DocSubNode(null, "TCBpath", "[0..N]"),
            new DocSubNode(null, "KBpath", "[0..N]"),
            new DocSubNode(null, "rotPath", "[0..N]"),

            new DocSubNode(null, "pointLight", "[0..N]"),
            new DocSubNode(null, "spotLight", "[0..N]"),
        };
    }

    @Override
    public String docDescription() {
        return null;
    }

    @Override
    public String[] docExamples() {
        return null;
    }

    @Override
    public String docValue() {
        return null;
    }

    @Override
    public DocAttr[] docAtributes() {
        return null;
    }

    @Override
    public DocAction[] docActions() {
        return null;
    }

    @Override
    public DocControl[] docControl() {
        return new DocControl[]{
            new DocControl("on()"),
            new DocControl("off()")
        };
    }

}
