package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.ShaderParameterObject;
import ws.loaders.tools.TextureLoader;

import java.util.Map;


public final class ShaderParameterElement extends TextureElement implements Doc{

    public ShaderParameterElement(TextureLoader textureLoader) {
        super(textureLoader);
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "name", "String"),
            new DocAttr(null, "value", "Object"),
            new DocAttr(null, "texture", "String", "path to image"),
            new DocAttr(null, "mipMap", "Boolean", "false", null),
            new DocAttr(null, "format", "{formatRGBA, formatRGBA4, formatRGB5_A1, formatRGB,formatRGB4, formatRGB5, formatR3_G3_B2, formatLUM8_ALPHA8, formatLUM4_ALPHA4, formatLUMINANCE, formatALPHA}", "formatRGB", null),
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

    public final ShaderParameterObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ShaderParameterObject o = new ShaderParameterObject(value, attributes, textureLoader);

        //if(value != null && value instanceof String) o.setName((String)value);



        if(attributes != null){
            
            Object tmp  = attributes.get(SceneBuilder.value); 
            if(tmp != null) o.setValue(tmp);

            tmp = attributes.get(SceneBuilder.name);
            if(tmp != null) o.setName(tmp.toString());

            //attributes.clear();
        }

        super.process(o, value, attributes);

        return o;
    }

    @Override
    public String docDescription() {
        return null;
    }

    @Override
    public String[] docExamples() {
        return null;
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
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "transform", "[0..1]"),
            new DocSubNode(null, "textureCombine", "[0.N]"),
            new DocSubNode(null, "color", "[0.1]"),
        };
    }

    /*
    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof ShaderParameterObject && child instanceof TextureObject){
            ShaderParameterObject g = (ShaderParameterObject)parent;
            TextureObject so = (TextureObject)child;
            g.setTextureObject(so);
        }else System.err.println(parent+" -> "+child);
    }
    */

}
