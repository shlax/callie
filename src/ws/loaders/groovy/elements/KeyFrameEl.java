package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Tuple3f;
import java.util.Map;

public final class KeyFrameEl extends AbstractFactory {

    @Override
    public final KeyFrameObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        KeyFrameObj frameObj = new KeyFrameObj();

        if(value != null) frameObj.setKnost( value instanceof Float ? (Float)value : Float.parseFloat(value.toString()));

        Object a = attributes.get(SceneBuilder.point);
        if(a != null){
            if(a instanceof Tuple) frameObj.setPoint( ((Tuple)a).getPoint3f() );
            else if(a instanceof Point3f) frameObj.setPoint( (Point3f)a );
            else if(a instanceof Tuple3f) frameObj.setPoint( new Point3f((Tuple3f)a) );
        }

        Object x = attributes.get(SceneBuilder.x);
        Object y = attributes.get(SceneBuilder.y);
        Object z = attributes.get(SceneBuilder.z);

        if(x != null || y != null || z != null){
            Point3f p = frameObj.getPoint3f();
            if(p == null){
                p = new Point3f();
                frameObj.setPoint(p);
            }
            if(x != null)p.setX( x instanceof Float ? (Float)x : Float.parseFloat(x.toString()) );
            if(y != null)p.setY( y instanceof Float ? (Float)y : Float.parseFloat(y.toString()) );
            if(z != null)p.setZ( z instanceof Float ? (Float)z : Float.parseFloat(z.toString()) );
        }

        a = attributes.get(SceneBuilder.time);
        if(a != null) frameObj.setKnost( a instanceof Float ? (Float)a : Float.parseFloat(a.toString()) );

        a = attributes.get(SceneBuilder.rotate);

        Point3f actRot = null;
        if(a != null){
            if(a instanceof Quat4f)frameObj.setQuat4f( (Quat4f)a );
            else if(a instanceof Quat){
                actRot = ((Quat)a).getPoint3f();
                frameObj.setQuat4f( ((Quat)a).getQuat4f() );
            }
        }

        x = attributes.get(SceneBuilder.rotX);
        y = attributes.get(SceneBuilder.rotY);
        z = attributes.get(SceneBuilder.rotZ);
        if(x != null || y != null || z != null){
            if(actRot == null) actRot = new Point3f();
            if(x != null)actRot.setX( x instanceof Float ? (Float)x : Float.parseFloat(x.toString()) );
            if(y != null)actRot.setY( y instanceof Float ? (Float)y : Float.parseFloat(y.toString()) );
            if(z != null)actRot.setZ( z instanceof Float ? (Float)z : Float.parseFloat(z.toString()) );
            frameObj.setQuat4f( Quat.createQuaternionFromEuler(actRot.x, actRot.y, actRot.z) );
        }

        actRot = null;
        a = attributes.get(SceneBuilder.scale);
        if(a != null){

            if(a instanceof Float){
                frameObj.setScale((Float) a);
                actRot = new Point3f((Float) a, (Float) a, (Float) a);
            }else if(a instanceof Point3f){
                actRot = (Point3f)a;
            }else if(a instanceof Tuple3f){
                actRot = new Point3f( (Tuple3f)a );
            }else if(a instanceof Tuple){
                actRot = ((Tuple)a).getPoint3f();
            }
        }

        x = attributes.get(SceneBuilder.scaleX);
        y = attributes.get(SceneBuilder.scaleY);
        z = attributes.get(SceneBuilder.scaleZ);
        if(x != null || y != null || z != null){
            if(actRot == null) actRot = new Point3f(1f, 1f, 1f);
            if(x != null)actRot.setX( x instanceof Float ? (Float)x : Float.parseFloat(x.toString()) );
            if(y != null)actRot.setY( y instanceof Float ? (Float)y : Float.parseFloat(y.toString()) );
            if(z != null)actRot.setZ( z instanceof Float ? (Float)z : Float.parseFloat(z.toString()) );
        }
        if(actRot != null) frameObj.setScalePont(actRot);

        a = attributes.get(SceneBuilder.rotY);
        if(a != null) frameObj.setHeading(a instanceof Float ? (Float) a : Float.parseFloat(a.toString()));

        a = attributes.get(SceneBuilder.rotX);
        if(a != null) frameObj.setPitch(a instanceof Float ? (Float) a : Float.parseFloat(a.toString()));

        a = attributes.get(SceneBuilder.bias);
        if(a != null) frameObj.setBias(a instanceof Float ? (Float) a : Float.parseFloat(a.toString()));

        a = attributes.get(SceneBuilder.rotZ);
        if(a != null) frameObj.setBank(a instanceof Float ? (Float) a : Float.parseFloat(a.toString()));

        a = attributes.get(SceneBuilder.continuity);
        if(a != null) frameObj.setContinuity( a instanceof Float ? (Float)a : Float.parseFloat(a.toString()) );

        a = attributes.get(SceneBuilder.tension);
        if(a != null) frameObj.setTension( a instanceof Float ? (Float)a : Float.parseFloat(a.toString()) );

        a = attributes.get(SceneBuilder.linear);
        if(a != null) frameObj.setLinear( a instanceof Boolean ? (Boolean)a : Boolean.parseBoolean(a.toString()) );

        attributes.clear();

        return frameObj;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof KeyFrameObj && child instanceof Quat){
            KeyFrameObj g = (KeyFrameObj)parent;
            Quat so = (Quat)child;
            g.setQuat4f(so.getQuat4f());
        }else if(parent instanceof KeyFrameObj && child instanceof Scale){
            KeyFrameObj g = (KeyFrameObj)parent;
            Scale so = (Scale)child;
            g.setScalePont(so.getPoint3f());
        }else if(parent instanceof KeyFrameObj && child instanceof Point){
            KeyFrameObj g = (KeyFrameObj)parent;
            Point so = (Point)child;
            g.setPoint(so.getPoint3f());
        }else System.err.println(parent+" -> "+child);
    }

}
