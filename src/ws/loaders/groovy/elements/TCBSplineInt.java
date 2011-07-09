package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.DocAction;
import ts.doc.DocAttr;
import ts.doc.DocControl;
import ts.doc.DocSubNode;
import ws.loaders.groovy.objects.IntTCBSplineObj;

import java.util.Map;

public final class TCBSplineInt extends PathInterpolatorEl{

    @Override
    public final IntTCBSplineObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        IntTCBSplineObj o = new IntTCBSplineObj(value, attributes);
        processInt(o, value, attributes);
        if(attributes != null)attributes.clear();
        return o;
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "key", "[1..N]", "key(knots,point,rotate,scale,tension,continuity,bias)"),

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
        return null;
    }

    @Override
    public String docValue() {
        return "as: |transform|time|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "transform", "transform", "transform axis"),
            new DocAttr(null, "time", "time"),
            new DocAttr(null, "key", "key[]", "key(knots,point,rotate,scale,tension,continuity,bias)"),
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
