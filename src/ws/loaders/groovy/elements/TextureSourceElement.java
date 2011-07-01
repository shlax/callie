package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.TextureSourceObject;

import java.util.Map;

public final class TextureSourceElement extends AbstractFactory {
    @Override
    public final TextureSourceObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TextureSourceObject o = new TextureSourceObject();
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
