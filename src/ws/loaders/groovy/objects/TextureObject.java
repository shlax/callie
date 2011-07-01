package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;
import ws.loaders.tools.TextureLoader;

import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color4f;
import java.util.ArrayList;
import java.util.Map;

public class TextureObject extends FactoryElement {
    private static final TextureAttributes defaultTextureAttributes = new TextureAttributes();
    static{
        defaultTextureAttributes.setTextureMode(TextureAttributes.MODULATE);
    }

    protected final TextureLoader textureLoader;
    public TextureObject(Object value, Map attributes, TextureLoader textureLoader) {
        super(value, attributes);
        this.textureLoader = textureLoader;
    }

    private boolean mipMap = false;
    private String texture = null;
    private String format = "RGB";

    public final void setMipMap(boolean mipMap) {
        this.mipMap = mipMap;
    }

    public final void setTexture(String texture) {
        this.texture = texture;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    private Texture textu = null;
    public final Texture getTexture(){
        if(texture == null) return null;
        if(textu == null) textu = this.textureLoader.getTexture(this.texture, format, mipMap ? com.sun.j3d.utils.image.TextureLoader.GENERATE_MIPMAP : 0);
        return textu;
    }

    private Integer textureMode = null;
    public final void setTextureMode(Integer textureMode) {
        this.textureMode = textureMode;
    }

    private Integer combineRgbMode = null;
    public final void setCombineRgbMode(Integer combineRgbMode) {
        this.combineRgbMode = combineRgbMode;
    }

    private Integer combineAlphaMode = null;
    public final void setCombineAlphaMode(Integer combineAlphaMode) {
        this.combineAlphaMode = combineAlphaMode;
    }

    private Integer combineRgbScale = null;
    public final void setCombineRgbScale(Integer combineRgbScale) {
        this.combineRgbScale = combineRgbScale;
    }

    private Integer combineAlphaScale = null;
    public final void setCombineAlphaScale(Integer combineAlphaScale) {
        this.combineAlphaScale = combineAlphaScale;
    }

    private ArrayList<TextureSourceObject> textureSourceObjects = null;
    public final void addTextureSourceObject(TextureSourceObject o){
        if(this.textureSourceObjects == null) this.textureSourceObjects = new ArrayList<TextureSourceObject>();
        this.textureSourceObjects.add(o);
    }

    private Transform3D textureTransform = null;
    public final void setTextureTransform(Transform3D textureTransform) {
        this.textureTransform = textureTransform;
    }

    private Color4f blendColor = null;
    public final void setBlendColor(Color4f blendColor) {
        this.blendColor = blendColor;
    }

    private TextureAttributes ta = null;

    public final void setTextureAttributes(TextureAttributes ta) {
        this.ta = ta;
    }

    public final TextureAttributes getTextureAttributes(){
        if(ta != null) return ta;
        if(textureMode == null && combineRgbMode == null && combineAlphaMode == null && combineRgbScale == null && combineAlphaScale == null && textureSourceObjects == null && blendColor == null && textureTransform == null) return defaultTextureAttributes;

        ta = new TextureAttributes();

        if(textureMode == null) ta.setTextureMode(TextureAttributes.MODULATE);
        else ta.setTextureMode(textureMode);

        if(combineRgbMode == null) ta.setCombineRgbMode(TextureAttributes.COMBINE_MODULATE);
        else ta.setCombineRgbMode(this.combineRgbMode);

        if(combineAlphaMode == null) ta.setCombineAlphaMode(TextureAttributes.COMBINE_MODULATE);
        else ta.setCombineAlphaMode(this.combineAlphaMode);

        if(combineRgbScale != null) ta.setCombineRgbScale(combineRgbScale);
        if(combineAlphaScale != null) ta.setCombineAlphaScale(combineAlphaScale);

        if(this.textureSourceObjects != null){
            for(TextureSourceObject o : this.textureSourceObjects){
                Integer tmp = o.getAlphaSource();
                if(tmp != null) ta.setCombineAlphaSource(o.getIndex(), tmp);

                tmp = o.getAlphaFunction();
                if(tmp != null) ta.setCombineAlphaFunction(o.getIndex(), tmp);

                tmp = o.getRgbSource();
                if(tmp != null) ta.setCombineRgbSource(o.getIndex(), tmp);

                tmp = o.getRgbFunction();
                if(tmp != null) ta.setCombineRgbFunction(o.getIndex(), tmp);
            }
        }

        if(blendColor != null) ta.setTextureBlendColor(blendColor);
        if(textureTransform != null) ta.setTextureTransform(textureTransform);

        return ta;
    }

}
