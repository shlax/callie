package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Point;
import ws.loaders.groovy.objects.PointLightObject;
import ws.loaders.groovy.objects.Tuple;
import ws.loaders.groovy.objects.Vector;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.Map;

public final class PointLightElement extends LightElement implements Doc{

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        PointLightObject l = new PointLightObject(value, attributes);
        this.processLight(l, value, attributes);

        Object o = attributes.get(SceneBuilder.position);
        if(o instanceof Point3f) l.setPosition((Point3f)o );
        else if(o instanceof Tuple3f) l.setPosition( new Point3f((Tuple3f)o) );
        else if(o instanceof Tuple) l.setPosition(((Tuple)o).getPoint3f() );

        o = attributes.get(SceneBuilder.attenuation);
        if(o instanceof Point3f) l.setAttenuation((Point3f) o);
        else if(o instanceof Tuple3f) l.setAttenuation(new Point3f((Tuple3f) o));
        else if(o instanceof Tuple) l.setAttenuation(((Tuple) o).getPoint3f());

        return l;
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "color", "[1]"),
            new DocSubNode(null, "point", "[1]", "as: position"),
            new DocSubNode(null, "vector", "[1]", "as: attenuation"),
        };
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof PointLightObject && child instanceof Point){
            PointLightObject g = (PointLightObject)parent;
            Point so = (Point)child;
            g.setPosition(so.getPoint3f());
        }else if(parent instanceof PointLightObject && child instanceof Vector){
            PointLightObject g = (PointLightObject)parent;
            Vector so = (Vector)child;
            g.setAttenuation(so.getPoint3f());
        }super.setChild(builder, parent, child);
    }

    @Override
    public String docDescription() {
        return "Point light";
    }

    @Override
    public String[] docExamples() {
        return null;
    }

    @Override
    public String docValue() {
        return "as: |color|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "color", "color", "1f", null),
            new DocAttr(null, "position", "point|vector", "(0f,0f,0f)", null),
            new DocAttr(null, "attenuation", "vector|point", "(1f,1f,1f)", null),
            new DocAttr(null, "r", "Float", "1f", null),
            new DocAttr(null, "g", "Float", "1f", null),
            new DocAttr(null, "b", "Float", "1f", null),
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
