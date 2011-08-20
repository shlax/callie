package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Point;
import ws.loaders.groovy.objects.SpotLightObject;
import ws.loaders.groovy.objects.Vector;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class SpotLightElement extends LightElement implements Doc{

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        SpotLightObject l = new SpotLightObject(value, attributes);
        this.processLight(l, value, attributes);

        Object o = attributes.get(SceneBuilder.position);
        if(o instanceof Point3f) l.setPosition((Point3f)o );
        else if(o instanceof Tuple3f) l.setPosition( new Point3f((Tuple3f)o) );
        else if(o instanceof Point) l.setPosition(((Point)o).getPoint3f() );
        else if(o instanceof Vector) l.setPosition(((Vector)o).getPoint3f() );

        o = attributes.get(SceneBuilder.attenuation);
        if(o instanceof Point3f) l.setAttenuation((Point3f) o);
        else if(o instanceof Tuple3f) l.setAttenuation(new Point3f((Tuple3f) o));
        else if(o instanceof Point) l.setAttenuation(((Point) o).getPoint3f());
        else if(o instanceof Vector) l.setAttenuation(((Vector) o).getPoint3f());

        o = attributes.get(SceneBuilder.direction);
        if(o instanceof Vector3f) l.setDirection((Vector3f) o);
        else if(o instanceof Tuple3f) l.setDirection(new Vector3f((Tuple3f) o));
        else if(o instanceof Point) l.setDirection(((Point) o).getVector3f());
        else if(o instanceof Vector) l.setDirection(((Vector) o).getVector3f());

        l.setSpreadAngle((Float) attributes.get(SceneBuilder.spreadAngle));
        l.setConcentration((Float) attributes.get(SceneBuilder.concentration));
        
        return l;
    }

    @Override
    public String docDescription() {
        return "Spot light";
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
            new DocAttr(null, "direction", "vector|point", "(-1f,-1f,-1f)", null),
            new DocAttr(null, "spreadAngle", "Float", "PI", null),
            new DocAttr(null, "concentration", "Float", "0f", null),
            new DocAttr(null, "r", "Float", "1f", null),
            new DocAttr(null, "g", "Float", "1f", null),
            new DocAttr(null, "b", "Float", "1f", null),
        };
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "color", "[1]"),
            new DocSubNode(null, "point", "[1]", "as: position"),
            new DocSubNode(null, "vector", "[1]", "as: direction"),
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

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof SpotLightObject && child instanceof Point){
            SpotLightObject g = (SpotLightObject)parent;
            Point so = (Point)child;
            g.setPosition(so.getPoint3f());
        }else if(parent instanceof SpotLightObject && child instanceof Vector){
            SpotLightObject g = (SpotLightObject)parent;
            Vector so = (Vector)child;
            g.setDirection(so.getVector3f());
        }super.setChild(builder, parent, child);
    }

}
