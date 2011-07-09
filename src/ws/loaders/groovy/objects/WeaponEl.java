package ws.loaders.groovy.objects;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.elements.TransformGroupElement;

import java.util.Map;

public final class WeaponEl extends TransformGroupElement implements Doc {

    @Override
    public WeaponObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        WeaponObj o = new WeaponObj(value, attributes);

        processGroup(o, value, attributes);
        if(attributes != null)attributes.clear();

        return o;
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;
        if(parent instanceof WeaponObj && child instanceof TransformObject)return;

        super.setChild(builder, parent, child);
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
        return "player character weapon";
    }

    @Override
    public String[] docExamples() {
        return null;
    }

    @Override
    public String docValue() {
        return "as: |transform|";
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
