package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.objects.SharedGroupObject;

import java.util.Map;

public final class SharedGroupElement extends GroupElement implements Doc{

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        SharedGroupObject o = new SharedGroupObject(value, attributes);
        setSceneObjectType(o, value, attributes);
        return o;
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
        return "shared element";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "wing = _shared(){ model(file:\"data/butterfly/wing.mod\") }\n" +
            "\n" +
            "rotation(wingTime, transform:wingAxis, min:1f, max:10f ){\n" +
            "   link(wing); };\n" +
            "rotation(wingTime, transform:wingAxis, min:-1f, max:-10f ){\n" +
            "   link(wing); };"
        };
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
        return null;
    }
}
