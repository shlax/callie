
package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Point;

import java.util.Map;

public final class PointEl extends AbstractFactory implements Doc{

    @Override
    public final Point newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        Float x = 0f;
        Float y = 0f;
        Float z = 0f;
        if(value != null){
            float val = Float.parseFloat(value.toString());
            x = val;
            y = val;
            z = val;
        }

        Object o = attributes.get(SceneBuilder.x);
        if (o != null) x = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.y);
        if (o != null) y = Float.parseFloat(o.toString());

        o = attributes.get(SceneBuilder.z);
        if (o != null) z = Float.parseFloat(o.toString());


        Point p = new Point(x, y, z, value, attributes);

        attributes.clear();

        return p;
    }

    @Override
    public String docDescription() {
        return "(x, y, z)";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "point(1f);",
            "point(x:-4.923f,y:4.003f,z:-0.399f);",
        };
    }

    @Override
    public String docValue() {
        return "set x, y, z from value";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "x", "Float", "0f", "x"),
            new DocAttr(null, "y", "Float", "0f", "y"),
            new DocAttr(null, "z", "Float", "0f", "z"),
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
