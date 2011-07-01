package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AnimationObject;
import ws.loaders.groovy.objects.AnimationTransformObject;
import ws.loaders.groovy.objects.Point;
import ws.loaders.groovy.objects.Vector;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.Map;

public final class AnimationTransformElement extends AbstractFactory {

    @Override
    public final AnimationTransformObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AnimationTransformObject o = new AnimationTransformObject(value, attributes);

        if(value != null){
            if(value instanceof AnimationObject) o.setAnimationObject( (AnimationObject)value );
            else if( value instanceof Point3f ) o.setSource( (Point3f)value );
            else if( value instanceof Tuple3f ) o.setSource( new Point3f((Tuple3f)value) );
            else if( value instanceof Point) o.setSource( ((Point) value).getPoint3f() );
            else if( value instanceof Vector) o.setSource( ((Vector) value).getPoint3f() );

            else if(value instanceof Float) o.setSourceAnge((Float)value);
        }

        if(attributes != null){

            Object tmp = attributes.get(SceneBuilder.sourceAngle);
            if(tmp != null)o.setSourceAnge( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()));

            tmp = attributes.get(SceneBuilder.source);
            if(tmp != null){
                if( tmp instanceof Point3f ) o.setSource( (Point3f)tmp );
                else if( tmp instanceof Tuple3f ) o.setSource( new Point3f((Tuple3f)tmp) );
                else if( tmp instanceof Point) o.setSource( ((Point) tmp).getPoint3f() );
                else if( tmp instanceof Vector) o.setSource( ((Vector) tmp).getPoint3f() );
            }else{
                Float x = null;
                Float y = null;
                Float z = null;

                Object s = attributes.get(SceneBuilder.x);
                if(s != null) x = Float.parseFloat(s.toString());

                s = attributes.get(SceneBuilder.y);
                if(s != null) y = Float.parseFloat(s.toString());

                s = attributes.get(SceneBuilder.z);
                if(s != null) z = Float.parseFloat(s.toString());

                if (x != null || y != null || z != null)o.setSource( new Point3f( x == null ? 0 : x, y == null ? 0 : y, z == null ? 0 : z ) );
            }

            attributes.clear();
        }

        return o;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof AnimationTransformObject && child instanceof Point){
            AnimationTransformObject g = (AnimationTransformObject)parent;
            Point so = (Point)child;
            g.setSource(so.getPoint3f());
        }else if(parent instanceof AnimationTransformObject && child instanceof Vector){
            AnimationTransformObject g = (AnimationTransformObject)parent;
            Vector so = (Vector)child;
            g.setSource(so.getPoint3f());
        }else if(parent instanceof AnimationTransformObject && child instanceof AnimationObject){
            AnimationTransformObject g = (AnimationTransformObject)parent;
            AnimationObject so = (AnimationObject)child;
            g.setAnimationObject(so);
        }else System.err.println(parent+" -> "+child);
    }

}
