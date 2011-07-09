package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Color;
import ws.loaders.groovy.objects.LightObject;

import javax.vecmath.Color3f;
import java.util.Map;

public abstract class LightElement extends NodeElement {

    protected final void processLight(LightObject lo, Object value, Map<?, ?> attributes){
        if(value != null){
            if(value instanceof Color3f) lo.setColor((Color3f)value);
            else if( value instanceof Color) lo.setColor( ((Color)value).getColor3f() );
        }else if(attributes != null){
            Object attr = attributes.get(SceneBuilder.color);
            if(attr != null){
                if (attr instanceof Color3f) lo.setColor( (Color3f)attr );
                else if (attr instanceof Color) lo.setColor( ((Color)attr).getColor3f() );
            }

            attr = attributes.get(SceneBuilder.r);
            if(attr != null) lo.setR( attr instanceof Float ? (Float)attr : Float.parseFloat(attr.toString()) );

            attr = attributes.get(SceneBuilder.g);
            if(attr != null) lo.setG( attr instanceof Float ? (Float)attr : Float.parseFloat(attr.toString()) );

            attr = attributes.get(SceneBuilder.b);
            if(attr != null) lo.setB( attr instanceof Float ? (Float)attr : Float.parseFloat(attr.toString()) );

            /*attr = attributes.get(SceneBuilder.region);
            if(attr != null && attr instanceof BoundingSphere) lo.setRegion( (BoundingSphere)attr ); */
        }
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof LightObject && child instanceof Color){
            LightObject g = (LightObject)parent;
            Color so = (Color)child;
            g.setColor(so.getColor3f());
        }else super.setChild(builder, parent, child);
    }

}
