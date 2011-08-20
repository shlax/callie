package ws.loaders.groovy.elements;

import com.ardor3d.math.ColorRGBA;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Color;
import ws.loaders.groovy.objects.LightObj;
import ws.loaders.groovy.objects.Tuple;

import java.util.Map;

public class LightElm extends AbstractFactory {

    @Override
    public final LightObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        LightObj light = new LightObj(value, attributes);

        Object o = attributes.get(SceneBuilder.ambient);
        if(o != null){
            if(o instanceof Color) light.setAmbient( ( (Color)o ).getColor3f() );
            if(o instanceof ColorRGBA) light.setAmbient( (ColorRGBA)o );
            else if(o instanceof Float) light.setAmbient( new ColorRGBA( (Float)o, (Float)o, (Float)o, 1f ));
        }

        o = attributes.get(SceneBuilder.diffuse);
        if(o != null){
            if(o instanceof Color) light.setDiffuse(((Color) o).getColor3f());
            if(o instanceof ColorRGBA) light.setDiffuse((ColorRGBA) o);
            else if(o instanceof Float) light.setDiffuse(new ColorRGBA((Float) o, (Float) o, (Float) o, 1f));
        }

        o = attributes.get(SceneBuilder.specular);
        if(o != null){
            if(o instanceof Color) light.setSpecular(((Color) o).getColor3f());
            if(o instanceof ColorRGBA) light.setSpecular((ColorRGBA) o);
            else if(o instanceof Float) light.setSpecular(new ColorRGBA((Float) o, (Float) o, (Float) o, 1f));
        }

        o = attributes.get(SceneBuilder.position);
        if(o != null){
            if(o instanceof Tuple) light.setLoacation( ((Tuple)o).getVector3() );
        }

        o = attributes.get(SceneBuilder.direction);
        if(o != null){
            if(o instanceof Tuple) light.setDirection( ((Tuple)o).getVector3() );
        }

        o = attributes.get(SceneBuilder.attenuate);
        if(o != null) light.setAttenuate( o instanceof Boolean ? (Boolean)o : Boolean.parseBoolean(o.toString()) );

        o = attributes.get(SceneBuilder.constant);
        if(o != null) light.setConstant(o instanceof Float ? (Float) o : Float.parseFloat(o.toString()));

        o = attributes.get(SceneBuilder.linear);
        if(o != null) light.setLinear(o instanceof Float ? (Float) o : Float.parseFloat(o.toString()));

        o = attributes.get(SceneBuilder.quadratic);
        if(o != null) light.setQuadratic(o instanceof Float ? (Float) o : Float.parseFloat(o.toString()));

        o = attributes.get(SceneBuilder.angle);
        if(o != null) light.setAngle(o instanceof Float ? (Float) o : Float.parseFloat(o.toString()));

        o = attributes.get(SceneBuilder.exponent);
        if(o != null) light.setExponent(o instanceof Float ? (Float) o : Float.parseFloat(o.toString()));

        return light;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        System.err.println(parent+" -> "+child);
    }

}
