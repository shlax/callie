package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;

import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import java.util.Map;


public final class ShaderParameterObject  extends FactoryElement {

    public ShaderParameterObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private TextureObject textureObject = null;
    public final void setTextureObject(TextureObject textureObject) {
        this.textureObject = textureObject;
    }

    public final Texture getTexture(){
        if(textureObject == null) return null;
        return this.textureObject.getTexture();
    }

    public final TextureAttributes getTextureAttributes(){
        if(textureObject == null) return null;
        return this.textureObject.getTextureAttributes();
    }

    private Object value = null;
    public final void setValue(Object value) {
        this.value = value;
    }
    public final Object getValue() {
        return value;
    }

    private String name;
    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }
}
