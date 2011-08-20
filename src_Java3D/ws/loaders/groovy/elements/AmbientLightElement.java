package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.objects.AmbientLightObject;

import java.util.Map;

public final class AmbientLightElement extends LightElement implements Doc {

    @Override
    public final Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AmbientLightObject l = new AmbientLightObject(value, attributes);
        //l.setColor();
        //System.out.println(value);
        this.processLight(l, value, attributes);

        attributes.clear();
        return l;
    }

    @Override
    public String docDescription() {
        return "Ambient light";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "ambientLight( _color(0.4f));"
        };
    }

    @Override
    public String docValue() {
        return "as: |color|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "color", "color", "1f", null),
            new DocAttr(null, "r", "Float", "1f", null),
            new DocAttr(null, "g", "Float", "1f", null),
            new DocAttr(null, "b", "Float", "1f", null),
        };
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "color", "[1]")
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
