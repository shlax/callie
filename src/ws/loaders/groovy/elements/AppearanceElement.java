package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AppearanceObject;
import ws.loaders.groovy.objects.Color;
import ws.loaders.groovy.objects.ShaderParameterObject;
import ws.loaders.groovy.objects.TextureObject;
import ws.loaders.tools.TextureLoader;

import javax.media.j3d.Material;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import java.util.Map;

public final class AppearanceElement extends TextureElement implements Doc {
    //private final TextureLoader textureLoader; // = new TextureLoader();

    public AppearanceElement(TextureLoader textureLoader) {
        super(textureLoader);
    }

    @Override
    public String docDescription() {
        return "material/texture";
    }

    @Override
    public String[] docExamples() {
        return null;
    }

    @Override
    public String docValue() {
        return "as: |ambient+diffuse|texture|";
    }

    @Override
    public DocAction[] docActions() {
        return null;
    }

    @Override
    public DocControl[] docControl() {
        return null;
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
        //    Object o = attributes.get(SceneBuilder.texture);
        //    if(o != null) a.setTexture( o.toString());

            Object o = attributes.get(SceneBuilder.vertexShader);
            if(o != null) a.setVert( o.toString());

            o = attributes.get(SceneBuilder.fragmentShader);
            if(o != null) a.setFrag( o.toString());

        //    o = attributes.get(SceneBuilder.mipMap);
        //    if(o != null) a.setMipMap(o instanceof Boolean ? (Boolean)o : Boolean.parseBoolean(o.toString()));

        //    o = attributes.get(SceneBuilder.format);
        //    if(o != null) a.setFormat(o.toString());

            //o = attributes.get(SceneBuilder.polygonAttributes);
            //if(o != null) a.setPolygonAttributes((PolygonAttributes)o);

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

            o = attributes.get(SceneBuilder.transparencyMode);
            if(o != null) a.setTransparencyMode( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()) );

            o = attributes.get(SceneBuilder.srcBlendFunction);
            if(o != null) a.setSrcBlendFunction( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()) );

                    o = attributes.get(SceneBuilder.dstBlendFunction);
            if(o != null) a.setDstBlendFunction( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()) );

            o = attributes.get(SceneBuilder.alphaTestValue);
            if(o != null){
                if(o instanceof RenderingAttributes) a.setRenderingAttributes( (RenderingAttributes)o );
                else if( o instanceof Float) a.setRenderTestValue((Float)o);
                else a.setRenderTestValue( Float.parseFloat(o.toString()) );
            }

            o = attributes.get(SceneBuilder.alphaTestFunction);
            if(o != null) a.setAlphaTestFunction( o instanceof Integer ? (Integer)o : Integer.parseInt(o.toString()) );
        }

        this.process(a, value, attributes);

        return a;
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "material", "Boolean", "true", "use default material"),
            new DocAttr(null, "ambient", "color", "(1f,1f,1f)" ,null),
            new DocAttr(null, "emissive", "color", "(0f,0f,0f)" ,null),
            new DocAttr(null, "diffuse", "color", "(0.5f,0.5f,0.5f)" ,null),
            new DocAttr(null, "specular", "color", "(0f,0f,0f)" ,null),
            new DocAttr(null, "shininess", "Float", "64f" ,null),

            new DocAttr(null, "texture", "String", "path to image"),
            new DocAttr(null, "mipMap", "Boolean", "false", null),
            new DocAttr(null, "format", "{formatRGBA, formatRGBA4, formatRGB5_A1, formatRGB, formatRGB4, formatRGB5, formatR3_G3_B2, formatLUM8_ALPHA8, formatLUM4_ALPHA4, formatLUMINANCE, formatALPHA}", "formatRGB", null),

            new DocAttr(null, "cull", "{cullBack,cullFront,cullNone}", "cullBack", null),

            new DocAttr(null, "alphaTestFunction", "{always, never, equal, noEqual, less, lessOrEqual, greater, greaterOrEqual}"),
            new DocAttr(null, "alphaTestValue", "Float", "0.5f", null),

            new DocAttr(null, "vertexShader", "String"),
            new DocAttr(null, "fragmentShader", "String"),

            new DocAttr(null, "textureMode", "{modulate,decal,blend,replace,combine}", "modulate", null),
            new DocAttr(null, "combineRgbMode", "{combineReplace, combineModulate, combineAdd, combineAddSigned, combineSubstract, combineInterpolate, combineDot3}"),
            new DocAttr(null, "combineAlphaMode", "{combineReplace, combineModulate, combineAdd, combineAddSigned, combineSubstract, combineInterpolate, combineDot3}"),
            new DocAttr(null, "combineRgbScale", "Integer", "{1,2,4}"),
            new DocAttr(null, "combineAlphaScale", "Integer", "{1,2,4}"),
            new DocAttr(null, "textureTransform", "transform"),
            new DocAttr(null, "textureCombine", "textureCombine[]"),
            new DocAttr(null, "blendColor", "color"),

            new DocAttr(null, "transparency", "Float", "0f" ,"<0,1>"),
            new DocAttr(null, "transparencyMode", "{blended,screenDoor}"),
            new DocAttr(null, "srcBlendFunction", "{blendZero, blendOne, blendSrcAlpha, blendOneMinusSrcAlpha}"),
            new DocAttr(null, "dstBlendFunction", "{blendZero, blendOne, blendSrcAlpha, blendOneMinusSrcAlpha}"),
        };
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "transform", "[0..1]"),
            new DocSubNode(null, "texture", "[0..N]"),
            new DocSubNode(null, "textureCombine", "[0..N]"),
            new DocSubNode(null, "shaderParameter", "[0..N]"),
            new DocSubNode(null, "color", "[0.1]"),
        };
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;
        
/*        if(parent instanceof AppearanceObject && child instanceof TransformObject){
            AppearanceObject g = (AppearanceObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTextureTransform(so.getTransform3D());
        }else */
        if(parent instanceof AppearanceObject && child instanceof ShaderParameterObject){
            AppearanceObject g = (AppearanceObject)parent;
            ShaderParameterObject so = (ShaderParameterObject)child;
            g.addShaderParameterObject(so);
        }else if(parent instanceof AppearanceObject && child instanceof TextureObject){
            AppearanceObject g = (AppearanceObject)parent;
            TextureObject so = (TextureObject)child;
            g.addTextureObject(so);
/*        }else if(parent instanceof AppearanceObject && child instanceof TextureSourceObject){
            AppearanceObject g = (AppearanceObject)parent;
            TextureSourceObject so = (TextureSourceObject)child;
            g.addTextureSourceObject(so); */
        }else super.setChild(builder, parent, child);
    }
}
