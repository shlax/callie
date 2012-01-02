package ws.loaders.groovy.elements;

import com.ardor3d.math.ColorRGBA;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AppearanceObject;
import ws.loaders.groovy.objects.Color;
import ws.loaders.groovy.objects.LightObj;
import ws.loaders.tools.TextureLoader;

import java.util.Collection;
import java.util.Map;

public final class AppearanceElement extends AbstractFactory {
    private final TextureLoader textureLoader; // = new TextureLoader();

    public AppearanceElement(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }



    @Override
    public final AppearanceObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AppearanceObject a = new AppearanceObject(value, attributes, textureLoader);

        Object tmp = attributes.get(SceneBuilder.texture);
        if(tmp != null){
            if(tmp instanceof Collection) for(Object q : (Collection<?>)tmp)a.addTexture(q.toString());
            else a.addTexture(tmp.toString());
        }

        tmp = attributes.get(SceneBuilder.globalAmbient);
        if(tmp != null){
            if(tmp instanceof Color) a.setGlobalAmbient( ( (Color)tmp ).getColor3f() );
            if(tmp instanceof ColorRGBA) a.setGlobalAmbient( (ColorRGBA)tmp );
            else if(tmp instanceof Float) a.setGlobalAmbient( new ColorRGBA( (Float)tmp, (Float)tmp, (Float)tmp, 1f ));
        }

        tmp = attributes.get(SceneBuilder.ambient);
        if(tmp != null){
            if(tmp instanceof Color) a.setAmbient( ( (Color)tmp ).getColor3f() );
            if(tmp instanceof ColorRGBA) a.setAmbient( (ColorRGBA)tmp );
            else if(tmp instanceof Float) a.setAmbient( new ColorRGBA( (Float)tmp, (Float)tmp, (Float)tmp, 1f ));
        }

        tmp = attributes.get(SceneBuilder.diffuse);
        if(tmp != null){
            if(tmp instanceof Color) a.setDiffuse(((Color) tmp).getColor3f());
            if(tmp instanceof ColorRGBA) a.setDiffuse((ColorRGBA) tmp);
            else if(tmp instanceof Float) a.setDiffuse(new ColorRGBA((Float) tmp, (Float) tmp, (Float) tmp, 1f));
        }

        tmp = attributes.get(SceneBuilder.specular);
        if(tmp != null){
            if(tmp instanceof Color) a.setSpecular(((Color) tmp).getColor3f());
            if(tmp instanceof ColorRGBA) a.setSpecular((ColorRGBA) tmp);
            else if(tmp instanceof Float) a.setSpecular(new ColorRGBA((Float) tmp, (Float) tmp, (Float) tmp, 1f));
        }

        tmp = attributes.get(SceneBuilder.emissive);
        if(tmp != null){
            if(tmp instanceof Color) a.setEmissive(((Color) tmp).getColor3f());
            if(tmp instanceof ColorRGBA) a.setEmissive((ColorRGBA) tmp);
            else if(tmp instanceof Float) a.setEmissive(new ColorRGBA((Float) tmp, (Float) tmp, (Float) tmp, 1f));
        }

        tmp = attributes.get(SceneBuilder.shininess);
        if(tmp != null) a.setShininess( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.alphaTest);
        if(tmp != null) a.setAlphaTest( tmp instanceof Boolean ? (Boolean)tmp : Boolean.parseBoolean(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cull);
        if(tmp != null) a.setCull( tmp instanceof Boolean ? (Boolean)tmp : Boolean.parseBoolean(tmp.toString()) );

        attributes.clear();

        return a;
    }



    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;
        
        if(parent instanceof AppearanceObject && child instanceof LightObj){
            AppearanceObject g = (AppearanceObject)parent;
            LightObj so = (LightObj)child;
            g.addLight(so);
        } //else
        /* if(parent instanceof AppearanceObject && child instanceof ShaderParameterObject){
            AppearanceObject g = (AppearanceObject)parent;
            ShaderParameterObject so = (ShaderParameterObject)child;
            g.addShaderParameterObject(so);
        }else if(parent instanceof AppearanceObject && child instanceof TextureObject){
            AppearanceObject g = (AppearanceObject)parent;
            TextureObject so = (TextureObject)child;
            g.addTextureObject(so); */
/*        }else if(parent instanceof AppearanceObject && child instanceof TextureSourceObject){
            AppearanceObject g = (AppearanceObject)parent;
            TextureSourceObject so = (TextureSourceObject)child;
            g.addTextureSourceObject(so); } */
        else super.setChild(builder, parent, child);
    }
}
