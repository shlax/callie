package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.TextureSourceObject;

import java.util.Map;

public final class TextureSourceElement extends AbstractFactory implements Doc{

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
        return "as: |index|";
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return null;
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
            new DocAttr("*", "index", "Integer"),
            new DocAttr(null, "alphaSource", "{combineObjectColor, combineTextureColor, combineConstantColor, combinePreviousTextureUnitState}"),
            new DocAttr(null, "alphaFunction", "{srcAlpha, oneMinusSrcAlpha}"),
            new DocAttr(null, "rgbSource", "{combineObjectColor, combineTextureColor, combineConstantColor, combinePreviousTextureUnitState}"),
            new DocAttr(null, "rgbFunction", "{srcColor, oneMinusSrcColor}"),
        };
    }

    @Override
    public final TextureSourceObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TextureSourceObject o = new TextureSourceObject();

        if(value != null){
            o.setIndex( value instanceof Integer ? (Integer)value : Integer.parseInt(value.toString()));
        }

        if(attributes != null){

            Object tmp = attributes.get(SceneBuilder.index);
            if(tmp != null) o.setIndex( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.alphaSource);
            if(tmp != null) o.setAlphaSource( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.alphaFunction);
            if(tmp != null) o.setAlphaFunction( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.rgbSource);
            if(tmp != null) o.setRgbSource( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            tmp = attributes.get(SceneBuilder.rgbFunction);
            if(tmp != null) o.setRgbFunction( tmp instanceof Integer ? (Integer)tmp : Integer.parseInt(tmp.toString()));

            attributes.clear();
        }
        return o;
    }
}
