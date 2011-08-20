package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.DirectionalLightObject;
import ws.loaders.groovy.objects.Point;
import ws.loaders.groovy.objects.Tuple;
import ws.loaders.groovy.objects.Vector;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class DirectionalLightElement extends LightElement implements Doc{

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        DirectionalLightObject l = new DirectionalLightObject(value, attributes);
        this.processLight(l, value, attributes);
        if(attributes != null){
            Object val = attributes.get(SceneBuilder.direction);
            if(val != null){
                if(val instanceof Vector3f) l.setDirection( (Vector3f)val );
                else if(val instanceof Tuple3f) l.setDirection( new Vector3f((Tuple3f)val) );
                else if(val instanceof Point) l.setDirection( ((Point)val).getVector3f() );
                else if(val instanceof Vector) l.setDirection( ((Vector)val).getVector3f() );
            }

            val = attributes.get(SceneBuilder.x);
            if(val != null)l.setX( val instanceof Float ? (Float)val : Float.parseFloat(val.toString()));

            val = attributes.get(SceneBuilder.y);
            if(val != null)l.setY( val instanceof Float ? (Float)val : Float.parseFloat(val.toString()));

            val = attributes.get(SceneBuilder.z);
            if(val != null)l.setZ( val instanceof Float ? (Float)val : Float.parseFloat(val.toString()));

        }
        return l;
    }   

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof DirectionalLightObject && child instanceof Tuple){
            DirectionalLightObject g = (DirectionalLightObject)parent;
            Point so = (Point)child;
            g.setDirection(so.getVector3f() );
        }else super.setChild(builder, parent, child);
    }

    @Override
    public String docDescription() {
        return "Directional light";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "directionalLight( _color(0.6f) );"
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
            new DocAttr(null, "direction", "vector|point", "(-1f, -1f, -1f)", null),
            new DocAttr(null, "r", "Float", "1f", null),
            new DocAttr(null, "g", "Float", "1f", null),
            new DocAttr(null, "b", "Float", "1f", null),
            new DocAttr(null, "x", "Float", "-1f", null),
            new DocAttr(null, "y", "Float", "-1f", null),
            new DocAttr(null, "z", "Float", "-1f", null),
        };
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "color", "[1]"),
            new DocSubNode(null, "vector", "[1]", "as: direction"),
            new DocSubNode(null, "point", "[1]", "as: direction"),
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
