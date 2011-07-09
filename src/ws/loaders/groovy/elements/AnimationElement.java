package ws.loaders.groovy.elements;

import groovy.lang.Closure;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AnimationObject;
import ws.loaders.groovy.objects.BhoneSkinFrameObject;
import ws.loaders.groovy.objects.Tuple;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.Collection;
import java.util.Map;

public final class AnimationElement extends AbstractFactory implements Doc {

    @Override
    public String docDescription() {
        return null;
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "up = _animation( _point(x:0f,y: 1.278f,z: 0.509f) ){ \n" +
                    "    for ( i in 1..8 ) frame(\"animations/up/\"+i+\".ang\"); \n" +
                    "};"
        };
    }

    @Override
    public String docValue() {
        return "as: |destination|";
    }

    @Override
    public final AnimationObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AnimationObject o = new AnimationObject(value, attributes);

        if(value != null){
            if(value instanceof Point3f) o.setDestination((Point3f)value);
            else if(value instanceof Tuple3f) o.setDestination( new Point3f((Tuple3f)value) );
            else if(value instanceof Tuple) o.setDestination( ((Tuple)value).getPoint3f() );

        }

        if(attributes != null){

            Object tmp = attributes.get(SceneBuilder.destination);
            if(tmp != null)o.setDestination( tmp instanceof Point3f ? (Point3f)tmp : new Point3f((Tuple3f)tmp));

            tmp = attributes.get(SceneBuilder.onEnter);
            if(tmp != null)o.setOnEnter((Closure)tmp );

            tmp = attributes.get(SceneBuilder.onExit);
            if(tmp != null)o.setOnExit((Closure)tmp );

            tmp = attributes.get(SceneBuilder.frameWindow);
            if(tmp != null)o.setKeyFrameRatio( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.sourceRadius);
            if(tmp != null)o.setSourceRadius( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.sourceAngle);
            if(tmp != null)o.setSourceAngleTolerantion( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

            tmp = attributes.get(SceneBuilder.frame);
            if(tmp != null){
                if(tmp instanceof BhoneSkinFrameObject) o.addAnimationFrameObject( (BhoneSkinFrameObject)tmp );
                else if(tmp instanceof Collection) for(Object q : (Collection)tmp ) o.addAnimationFrameObject( (BhoneSkinFrameObject)q );
                //o.setSourceAngleTolerantion( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );
            }

            attributes.clear();
        }
        return o;
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "destination", "point|vector", "(0f,0f,0f)"),
            new DocAttr(null, "frameWindow", "Float", "0.175", "//second"),
            new DocAttr(null, "sourceRadius", "Float", "0.5f", "maximum allowable deviation from start point //meter"),
            new DocAttr(null, "sourceAngle", "Float", "90f", "maximum allowable deviation from start angle //degrees"),
            new DocAttr("*1", "frame", "frame")
        };
    }

    @Override
    public DocAction[] docActions() {
        return new DocAction[]{
            new DocAction("onEnter"),
            new DocAction("onExit")
        };
    }

    @Override
    public DocControl[] docControl() {
        return null;
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode("*1", "frame", "[1..N]")
        };
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof AnimationObject && child instanceof BhoneSkinFrameObject){
            AnimationObject g = (AnimationObject)parent;
            BhoneSkinFrameObject so = (BhoneSkinFrameObject)child;
            g.addAnimationFrameObject(so);
        }else System.err.println(parent+" -> "+child);
    }

}
