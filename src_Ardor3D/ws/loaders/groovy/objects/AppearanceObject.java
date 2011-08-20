package ws.loaders.groovy.objects;

import com.ardor3d.image.Texture;
import com.ardor3d.image.TextureStoreFormat;
import com.ardor3d.math.ColorRGBA;
import com.ardor3d.renderer.state.*;
import wa.Appearance;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.tools.TextureLoader;

import java.util.ArrayList;
import java.util.Map;

public final class AppearanceObject /* extends TextureObject */ extends FactoryElement {

    private final TextureLoader textureLoader;
    public AppearanceObject(Object value, Map attributes, TextureLoader textureLoader) {
        super(value, attributes);
        this.textureLoader = textureLoader;
    }

    // -----------------------------------------------------------------------------------------------------
    private ArrayList<String> texures = null;
    public void addTexture(String texture){
        if(texures == null) texures = new ArrayList<String>();
        texures.add(texture);
    }

    private boolean test = false;
    public void setAlphaTest(boolean test) {
        this.test = test;
    }

    private boolean cull = true;
    public void setCull(boolean cull) {
        this.cull = cull;
    }



    public final Appearance getAppearance() {
        Appearance a = new Appearance();

        if(texures != null){
            TextureState ts = new TextureState();
            ts.setEnabled(true);
            for(int i = 0;i < texures.size(); i++){
                ts.setTexture(textureLoader.getTexture(texures.get(i), Texture.MinificationFilter.NearestNeighborLinearMipMap, test ? TextureStoreFormat.RGBA8 : TextureStoreFormat.RGB8), i);
            }

            a.textureState = ts;
        }

        if(test){
            BlendState bs = new BlendState();
            bs.setTestEnabled(true);
            bs.setTestFunction(BlendState.TestFunction.GreaterThan);
            bs.setReference(0.5f);

            a.blendState = bs;
        }

        if(!cull){
            CullState cs = new CullState();
            cs.setCullFace(CullState.Face.None);
            a.cullState = cs;
        }

        if(ambient != null || diffuse != null || specular != null || emissive != null || shininess != null){
            MaterialState ms = new MaterialState();
            ms.setEnabled(true);

            ms.setAmbient(ambient == null ? _ambient : ambient);
            ms.setDiffuse(diffuse == null ? _diffuse : diffuse);
            ms.setSpecular(specular == null ? _specular : specular);
            ms.setEmissive(emissive == null ? _emissive : emissive);

            ms.setShininess( shininess == null ? 0f : shininess );

            a.materialState = ms;
        }

        if(globalAmbient != null || ligths != null){
            LightState ls = new LightState();
            ls.setEnabled(true);
            if(globalAmbient != null) ls.setGlobalAmbient(globalAmbient);
            else ls.setGlobalAmbient(_globalAmbient);

            if(ligths != null)for (LightObj o : ligths)ls.attach(o.getLight());

            a.lightState = ls;
        }

        return a;
    }

    private static final ColorRGBA _ambient = new ColorRGBA(0.4f, 0.4f, 0.4f, 1f);
    private ColorRGBA ambient = null; // new ColorRGBA(0.4f, 0.4f, 0.4f, 1f);

    private static final  ColorRGBA _diffuse = new ColorRGBA(1f, 1f, 1f, 1f);
    private ColorRGBA diffuse = null; // new ColorRGBA(1f, 1f, 1f, 1f);

    private static final  ColorRGBA _specular = new ColorRGBA(0f, 0f, 0f, 1f);
    private ColorRGBA specular = null; // new ColorRGBA(0f, 0f, 0f, 1f);

    private static final  ColorRGBA _emissive = new ColorRGBA(0f, 0f, 0f, 0f);
    private ColorRGBA emissive = null; // new ColorRGBA(0f, 0f, 0f, 0f);

    private Float shininess = null;

    private static final  ColorRGBA _globalAmbient = new ColorRGBA(1f, 1f, 1f, 1f);
    private ColorRGBA globalAmbient = null; // new ColorRGBA(1f, 1f, 1f, 1f);

    private ArrayList<LightObj> ligths = null;
    public void addLight(LightObj o){
        if(ligths == null) ligths = new ArrayList<LightObj>();
        ligths.add(o);
    }

    public void setGlobalAmbient(ColorRGBA globalAmbient) {
        this.globalAmbient = globalAmbient;
    }

    public void setAmbient(ColorRGBA ambient) {
        this.ambient = ambient;
    }

    public void setDiffuse(ColorRGBA diffuse) {
        this.diffuse = diffuse;
    }

    public void setSpecular(ColorRGBA specular) {
        this.specular = specular;
    }

    public void setEmissive(ColorRGBA emissive) {
        this.emissive = emissive;
    }

    public void setShininess(Float shininess) {
        this.shininess = shininess;
    }
}
