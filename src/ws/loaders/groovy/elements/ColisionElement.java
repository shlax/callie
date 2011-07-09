package ws.loaders.groovy.elements;

import groovy.lang.Closure;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.ColisionObject;
import ws.loaders.groovy.objects.Point;
import ws.loaders.groovy.objects.Tuple;
import ws.loaders.groovy.objects.Vector;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.Map;


public final class ColisionElement extends AbstractFactory implements Doc {

    @Override
    public String docDescription() {
        return "colision area";
    }

    @Override
    public String[] docExamples() {
        return null;
    }

    @Override
    public String docValue() {
        return "as: |point|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr("*1", "point", "point|vector" ),
            new DocAttr("*1", "colisionRadius", "Float" ),
            new DocAttr("*2", "minX", "Float" ),
            new DocAttr("*2", "maxX", "Float" ),
            new DocAttr("*2", "minY", "Float" ),
            new DocAttr("*2", "maxY", "Float" ),
            new DocAttr("*2", "minZ", "Float" ),
            new DocAttr("*2", "maxZ", "Float" ),
            new DocAttr(null, "active", "Boolean", "true/false", "default true / if is used <i>control</i> and ( <i>onEnter</i> or <i>onExit</i> ) false" ),
        };
    }

    public final ColisionObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ColisionObject o = new ColisionObject(value, attributes);

        if(value instanceof Point3f) o.setPoint((Point3f)value);
        else if(value instanceof Tuple3f) o.setPoint(new Point3f((Tuple3f)value));

        if(attributes != null){

            Object tmp = attributes.get(SceneBuilder.point);
            if(tmp instanceof Point3f) o.setPoint((Point3f)tmp);
            else if(tmp instanceof Tuple3f) o.setPoint(new Point3f((Tuple3f)tmp));
            else if(tmp instanceof Point) o.setPoint(((Point)tmp).getPoint3f());
            else if(tmp instanceof Vector) o.setPoint(((Vector)tmp).getPoint3f());

            tmp = attributes.get(SceneBuilder.colisionRadius);
            o.setRadius(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.minX);
            o.setMinX(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.maxX);
            o.setMaxX(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.minY);
            o.setMinY(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.maxY);
            o.setMaxY(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.minZ);
            o.setMinZ(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.maxZ);
            o.setMaxZ(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.active);
            o.setEnabled(tmp instanceof Boolean ? (Boolean)tmp : Boolean.parseBoolean(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.onEnter);
            if(tmp instanceof Closure)o.setOnEnter((Closure)tmp);

            tmp = attributes.get(SceneBuilder.onExit);
            if(tmp instanceof Closure)o.setOnExit((Closure)tmp);

            attributes.clear();
        }
        return o;
    }

    @Override
    public DocAction[] docActions() {
        return new DocAction[]{
            new DocAction("onEnter"),
            new DocAction("onExit"),
        };
    }

    @Override
    public DocControl[] docControl() {
        return new DocControl[]{
            new DocControl("on()", "activate colision area"),
            new DocControl("off()", "deactivate colision area"),
        };
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "point", "[1]", "as: |point|"),
            new DocSubNode(null, "vector", "[1]", "as: |point|")
        };
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof ColisionObject && child instanceof Tuple){
            ColisionObject g = (ColisionObject)parent;
            Tuple so = (Tuple)child;
            g.setPoint(so.getPoint3f());
        }else super.setChild(builder, parent, child);
    }
}
