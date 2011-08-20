package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Color;

import java.util.Map;

public final class ColorEl extends AbstractFactory implements Doc{

    @Override
    public final Color newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        Float r = 0f;
        Float g = 0f;
        Float b = 0f;
        Float a = 0f;
        if(value != null){
            float val = Float.parseFloat(value.toString());
            r = val;
            g = val;
            b = val;
        }

        Object o = attributes.get(SceneBuilder.r);
        if (o != null) r = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.g);
        if (o != null) g = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.b);
        if (o != null) b = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.a);
        if (o != null) a = Float.parseFloat(o.toString());

        Color col = a == null ? new Color(r, g, b, value, attributes) : new Color(r, g, b, a, value, attributes);

        attributes.clear();

        return col;
    }

    @Override
    public String docDescription() {
        return null;
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "color(1f);",
            "color(r:0.923f,g:1f,b:0.399f);",
        };
    }

    @Override
    public String docValue() {
        return "set r, g, b from value";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "r", "Float", "0f", "r //<0,1f>"),
            new DocAttr(null, "g", "Float", "0f", "g //<0,1f>"),
            new DocAttr(null, "b", "Float", "0f", "b //<0,1f>"),
            new DocAttr(null, "a", "Float", "0f", "a //<0,1f>"),
        };
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
