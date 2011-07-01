package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.TextureObject;
import ws.loaders.groovy.objects.TextureSourceObject;
import ws.loaders.groovy.objects.TransformObject;
import ws.loaders.tools.TextureLoader;

import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color4f;
import java.util.Map;

public final class TextureElement extends AbstractFactory {
    private final TextureLoader textureLoader; // = new TextureLoader();

    public TextureElement(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }

    @Override
    public final TextureObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TextureObject o = new TextureObject(value, attributes, this.textureLoader);
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

            tmp = attributes.get(SceneBuilder.textureAttributes);
            if(tmp != null){
                if(tmp instanceof TextureAttributes) o.setTextureAttributes((TextureAttributes)tmp);
            }

            tmp = attributes.get(SceneBuilder.blendColor);
            if(tmp != null)o.setBlendColor((Color4f)tmp);

            attributes.clear();
        }
        return o;
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof TextureObject && child instanceof TextureSourceObject){
            TextureObject g = (TextureObject)parent;
            TextureSourceObject so = (TextureSourceObject)child;
            g.addTextureSourceObject(so);
        }else if(parent instanceof TextureObject && child instanceof TransformObject){
            TextureObject g = (TextureObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTextureTransform(so.getTransform3D());
        }else System.err.println(parent+" -> "+child);
    }
}
