package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.DocAction;
import ts.doc.DocAttr;
import ts.doc.DocControl;
import ts.doc.DocSubNode;
import ws.loaders.groovy.objects.IntRotationObj;

import java.util.Map;

public final class RotationEl extends SimpleInterpolatorEl{

    @Override
    public final IntRotationObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntRotationObj inter = new IntRotationObj(value, attributes);

        this.processSimpleInt(inter, value, attributes);

        return inter;
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "transform", "[0..1]", "transform axis"),
            new DocSubNode(null, "time", "[0..1]"),

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
        return new String[]{
            "wingTime = _time(time:0.2f, mode:incDec);\n" +
            "wingAxis = _transform(rotZ:90f);\n" +
            "rotation(wingTime, transform:wingAxis,\n" +
            "         min:1f, max:10f ){ link(wing); };"
        };
    }

    @Override
    public String docValue() {
        return "as: |transform|time|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "transform", "transform"),
            new DocAttr(null, "time", "time"),
            new DocAttr(null, "min", "Float", "0f", null),
            new DocAttr(null, "max", "Float", "0f", null),
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
}
