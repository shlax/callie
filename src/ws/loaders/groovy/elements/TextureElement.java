package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Color;
import ws.loaders.groovy.objects.TextureObject;
import ws.loaders.groovy.objects.TextureSourceObject;
import ws.loaders.groovy.objects.TransformObject;
import ws.loaders.tools.TextureLoader;

import javax.media.j3d.Transform3D;
import javax.vecmath.Color4f;
import java.util.Collection;
import java.util.Map;

//import ws.loaders.groovy.objects.TextureSourceObject;
//import javax.media.j3d.TextureAttributes;

public class TextureElement extends AbstractFactory implements Doc {
    protected final TextureLoader textureLoader; // = new TextureLoader();

    public TextureElement(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }

    @Override
    public TextureObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TextureObject o = new TextureObject(value, attributes, this.textureLoader);
        process(o, value, attributes);

        if(attributes != null)attributes.clear();

        return o;
    }

    @Override
    public String docDescription() {
        return "texture";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "texture(texture:\"data/house/floor.png\",mipMap:true);"
        };
    }

    @Override
    public String docValue() {
        return "as: |texture|";
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
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr("*", "texture", "String", "path to image"),
            new DocAttr(null, "mipMap", "Boolean", "false", null),
            new DocAttr(null, "format", "{formatRGBA, formatRGBA4, formatRGB5_A1, formatRGB, formatRGB4, formatRGB5, formatR3_G3_B2, formatLUM8_ALPHA8, formatLUM4_ALPHA4, formatLUMINANCE, formatALPHA}", "formatRGB", null),
            new DocAttr(null, "textureMode", "{modulate,decal,blend,replace,combine}", "modulate", null),
            new DocAttr(null, "combineRgbMode", "{combineReplace, combineModulate, combineAdd, combineAddSigned, combineSubstract, combineInterpolate, combineDot3}"),
            new DocAttr(null, "combineAlphaMode", "{combineReplace, combineModulate, combineAdd, combineAddSigned, combineSubstract, combineInterpolate, combineDot3}"),
            new DocAttr(null, "combineRgbScale", "Integer", "{1,2,4}"),
            new DocAttr(null, "combineAlphaScale", "Integer", "{1,2,4}"),
            new DocAttr(null, "textureTransform", "transform"),
            new DocAttr(null, "textureCombine", "textureCombine[]"),
            new DocAttr(null, "blendColor", "color"),
        };
    }

    protected final void process(TextureObject o, Object value, Map attributes) throws InstantiationException, IllegalAccessException {

        if(value instanceof String)o.setTexture( (String)value );

        if(attributes != null){

            Object tmp = attributes.get(SceneBuilder.mipMap);
            if(tmp != null) o.setMipMap(tmp instanceof Boolean ? (Boolean)tmp : Boolean.parseBoolean(tmp.toString()));

            tmp = attributes.get(SceneBuilder.format);
            if(tmp != null) o.setFormat(tmp.toString());

            tmp = attributes.get(SceneBuilder.texture);
            if(tmp != null) o.setTexture( tmp.toString());

            tmp = attributes.get(SceneBuilder.textureMode);
            if(tmp != null) o.setTextureMode( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.combineRgbMode);
            if(tmp != null) o.setCombineRgbMode( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.combineAlphaMode);
            if(tmp != null) o.setCombineAlphaMode( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.combineRgbScale);
            if(tmp != null) o.setCombineRgbScale( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.combineAlphaScale);
            if(tmp != null) o.setCombineAlphaScale( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.textureTransform);
            if(tmp != null){
                if(tmp instanceof Transform3D) o.setTextureTransform((Transform3D)tmp);
                else if(tmp instanceof TransformObject) o.setTextureTransform(((TransformObject)tmp).getTransform3D());
            }

            tmp = attributes.get(SceneBuilder.textureCombine);
            if(tmp != null){
                if(tmp instanceof TextureSourceObject) o.addTextureSourceObject((TextureSourceObject)tmp);
                else if(tmp instanceof Collection) for(Object q : (Collection)tmp) o.addTextureSourceObject((TextureSourceObject)q);
            }

            tmp = attributes.get(SceneBuilder.blendColor);
            if(tmp != null){
                if(tmp instanceof Color4f)o.setBlendColor((Color4f)tmp);
                if(tmp instanceof Color)o.setBlendColor( ((Color)tmp).getColor4f() );
            }

            attributes.clear();
        }
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "transform", "[0..1]"),
            new DocSubNode(null, "textureCombine", "[0.N]"),
            new DocSubNode(null, "color", "[0.1]"),
        };
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof TextureObject && child instanceof TextureSourceObject){
            TextureObject g = (TextureObject)parent;
            TextureSourceObject so = (TextureSourceObject)child;
            g.addTextureSourceObject(so);
        }else if(parent instanceof TextureObject && child instanceof TransformObject){
            TextureObject g = (TextureObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTextureTransform(so.getTransform3D());
        }else if(parent instanceof TextureObject && child instanceof Color){
            TextureObject g = (TextureObject)parent;
            Color so = (Color)child;
            g.setBlendColor(so.getColor4f());
        }else System.err.println(parent+" -> "+child);
    }
}
