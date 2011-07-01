package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Point;
import ws.loaders.groovy.objects.TransformObject;
import ws.loaders.groovy.objects.Vector;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class TransformElement extends AbstractFactory {

    @Override
    public final TransformObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TransformObject t = new TransformObject(value, attributes);

        if(value != null && value instanceof String) t.setName((String)value);

        if(attributes != null){
            
            Object o = attributes.get(SceneBuilder.rotX);
            if(o != null) t.setRotX( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

            o = attributes.get(SceneBuilder.rotY);
            if(o != null) t.setRotY( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

            o = attributes.get(SceneBuilder.rotZ);
            if(o != null) t.setRotZ( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

            o = attributes.get(SceneBuilder.xyz);

            Object a = attributes.get(SceneBuilder.x);
            Object b = attributes.get(SceneBuilder.y);
            Object c = attributes.get(SceneBuilder.z);

            if(o != null || a !=null || b != null || c != null){
                Vector3f v; // = null; // o == null ? new Vector3f() : o  (Vector3f)o;
                if(o instanceof Vector3f) v = (Vector3f)o;
                else if(o instanceof Tuple3f) v = new Vector3f((Tuple3f)o);
                else if(o instanceof Point) v = ((Point)o).getVector3f();
                else if(o instanceof Vector) v = ((Vector)o).getVector3f();
                else v = new Vector3f();

                if(a != null) v.x = a instanceof Float ? (Float)a : Float.parseFloat(a.toString());
                if(b != null) v.y = b instanceof Float ? (Float)b : Float.parseFloat(b.toString());
                if(c != null) v.z = c instanceof Float ? (Float)c : Float.parseFloat(c.toString());

                t.setX(v.x);
                t.setY(v.y);
                t.setZ(v.z);
            }

            o = attributes.get(SceneBuilder.scaleXYZ);
            Object s = attributes.get(SceneBuilder.scale);

            a = attributes.get(SceneBuilder.scaleX);
            b = attributes.get(SceneBuilder.scaleY);
            c = attributes.get(SceneBuilder.scaleZ);

            if(o != null || a !=null || b != null || c != null || s != null){
                Vector3f v; // = null; // o == null ? new Vector3f(1f, 1f, 1f) : (Vector3f)o;
                if(o instanceof Vector3f) v = (Vector3f)o;
                else if(o instanceof Tuple3f) v = new Vector3f((Tuple3f)o);
                else if(o instanceof Point) v = ((Point)o).getVector3f();
                else if(o instanceof Vector) v = ((Vector)o).getVector3f();
                else v = new Vector3f(1f, 1f, 1f);

                if(s != null) v.scale( s instanceof Float ? (Float)s : Float.parseFloat(s.toString()) );

                if(a != null) v.x = a instanceof Float ? (Float)a : Float.parseFloat(a.toString());
                if(b != null) v.y = b instanceof Float ? (Float)b : Float.parseFloat(b.toString());
                if(c != null) v.z = c instanceof Float ? (Float)c : Float.parseFloat(c.toString());

                t.setScaleX(v.x);
                t.setScaleY(v.y);
                t.setScaleZ(v.z);
            }

            attributes.clear();
        }
        return t;
    }
   
}
