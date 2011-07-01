package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;
import ws.loaders.tools.TextureLoader;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import java.util.Map;

public final class AppearanceElement extends AbstractFactory {
    private final TextureLoader textureLoader; // = new TextureLoader();

    public AppearanceElement(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }

    @Override
    public final AppearanceObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AppearanceObject a = new AppearanceObject(value, attributes, textureLoader);

        if(value != null){
            if( value instanceof Color3f){
                a.setAmbient((Color3f)value);
                a.setDiffuse((Color3f)value);
            }else if( value instanceof Color){
                Color3f cf = ((Color)value).getColor3f();
                a.setAmbient(cf);
                a.setDiffuse(cf);
            }
        }

        if(attributes != null ){
            Object o = attributes.get(SceneBuilder.texture);
            if(o != null) a.setTexture( o.toString());

            o = attributes.get(SceneBuilder.vertexShader);
            if(o != null) a.setVert( o.toString());

            o = attributes.get(SceneBuilder.fragmentShader);
            if(o != null) a.setFrag( o.toString());

            o = attributes.get(SceneBuilder.mipMap);
            if(o != null) a.setMipMap(o instanceof Boolean ? (Boolean)o : Boolean.parseBoolean(o.toString()));

            o = attributes.get(SceneBuilder.format);
            if(o != null) a.setFormat(o.toString());

            o = attributes.get(SceneBuilder.polygonAttributes);
            if(o != null) a.setPolygonAttributes((PolygonAttributes)o);

            o = attributes.get(SceneBuilder.cull);
            if(o != null) a.setCull( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()) );

            o = attributes.get(SceneBuilder.material);
            if(o != null){
                if(o instanceof Boolean) a.setAllowMaterial((Boolean)o);
                else if(o instanceof Material) a.setMaterial((Material)o);
                else a.setAllowMaterial(Boolean.parseBoolean(o.toString()));
            }

            o = attributes.get(SceneBuilder.ambient);
            if(o != null){
                if( o instanceof Color3f) a.setAmbient((Color3f)o);
                else if( o instanceof Color) a.setAmbient(((Color)o).getColor3f());
            }

            o = attributes.get(SceneBuilder.emissive);
            if(o != null){
                if( o instanceof Color3f) a.setEmissive((Color3f)o);
                else if( o instanceof Color) a.setEmissive(((Color)o).getColor3f());
            }

            o = attributes.get(SceneBuilder.diffuse);
            if(o != null){
                if( o instanceof Color3f) a.setDiffuse((Color3f)o);
                else if( o instanceof Color) a.setDiffuse(((Color)o).getColor3f());
            }

            o = attributes.get(SceneBuilder.specular);
            if(o != null){
                if( o instanceof Color3f) a.setSpecular((Color3f)o);
                else if( o instanceof Color) a.setSpecular(((Color)o).getColor3f());
            }

            o = attributes.get(SceneBuilder.shininess);
            if(o != null) a.setShininess( (o instanceof Float ? (Float)o : Float.parseFloat(o.toString())) );

            o = attributes.get(SceneBuilder.transparency);
            if(o != null){
                if(o instanceof TransparencyAttributes) a.setTransparencyAttributes((TransparencyAttributes)o);
                else if(o instanceof Float) a.setTransparencyTestValue( (Float)o );
                else a.setTransparencyTestValue( Float.parseFloat(o.toString()) );
            }

            o = attributes.get(SceneBuilder.textureAttributes);
            if(o != null){
                if(o instanceof TextureAttributes) a.setTextureAttributes((TextureAttributes)o);                
            }

            o = attributes.get(SceneBuilder.textureTransform);
            if(o != null){
                if(o instanceof Transform3D) a.setTextureTransform((Transform3D)o);
                else if(o instanceof TransformObject) a.setTextureTransform(((TransformObject)o).getTransform3D());
            }

            o = attributes.get(SceneBuilder.combineRgbMode);
            if(o != null) a.setCombineRgbMode( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()));

            o = attributes.get(SceneBuilder.combineAlphaMode);
            if(o != null) a.setCombineAlphaMode( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()));

            o = attributes.get(SceneBuilder.combineRgbScale);
            if(o != null) a.setCombineRgbScale( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()));

            o = attributes.get(SceneBuilder.combineAlphaScale);
            if(o != null) a.setCombineAlphaScale( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()));

                o = attributes.get(SceneBuilder.blendColor);
            if(o != null){
                if(o instanceof Color4f)a.setBlendColor((Color4f) o);
                else if(o instanceof Color)a.setBlendColor(((Color)o).getColor4f());
            }

            o = attributes.get(SceneBuilder.textureMode);
            if(o != null) a.setTextureMode( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()));

            o = attributes.get(SceneBuilder.transparencyMode);
            if(o != null) a.setTransparencyMode( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()) );

            o = attributes.get(SceneBuilder.srcBlendFunction);
            if(o != null) a.setSrcBlendFunction( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()) );

                    o = attributes.get(SceneBuilder.dstBlendFunction);
            if(o != null) a.setDstBlendFunction( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()) );

            o = attributes.get(SceneBuilder.rendering);
            if(o != null){
                if(o instanceof RenderingAttributes) a.setRenderingAttributes( (RenderingAttributes)o );
                else if( o instanceof Float) a.setRenderTestValue((Float)o);
                else a.setRenderTestValue( Float.parseFloat(o.toString()) );
            }

            o = attributes.get(SceneBuilder.alphaTestFunction);
            if(o != null) a.setAlphaTestFunction( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()) );
        }

        attributes.clear();
        return a;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;
        
        if(parent instanceof AppearanceObject && child instanceof TransformObject){
            AppearanceObject g = (AppearanceObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTextureTransform(so.getTransform3D());
        }else if(parent instanceof AppearanceObject && child instanceof TextureObject){
            AppearanceObject g = (AppearanceObject)parent;
            TextureObject so = (TextureObject)child;
            g.addTextureObject(so);
        }else if(parent instanceof AppearanceObject && child instanceof TextureSourceObject){
            AppearanceObject g = (AppearanceObject)parent;
            TextureSourceObject so = (TextureSourceObject)child;
            g.addTextureSourceObject(so);
        }else if(parent instanceof AppearanceObject && child instanceof ShaderParameterObject){
            AppearanceObject g = (AppearanceObject)parent;
            ShaderParameterObject so = (ShaderParameterObject)child;
            g.addShaderParameterObject(so);            
        }else System.err.println(parent+" -> "+child);
    }
}
