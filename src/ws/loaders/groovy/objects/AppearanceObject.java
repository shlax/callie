package ws.loaders.groovy.objects;

import com.sun.j3d.utils.shader.StringIO;
import ws.loaders.tools.TextureLoader;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class AppearanceObject extends TextureObject {

    //private final TextureLoader textureLoader;
    public AppearanceObject(Object value, Map attributes, TextureLoader textureLoader) {
        super(value, attributes, textureLoader);
//        this.textureLoader = textureLoader;
    }

    // -----------------------------------------------------------------------------------------------------
/*
    private boolean mipMap = false;
    private File texture = null;

    public final void setMipMap(boolean mipMap) {
        this.mipMap = mipMap;
    }

    public final void setTexture(File texture) {
        this.texture = texture;
    }

    private Transform3D textureTransform = null;
    public final void setTextureTransform(Transform3D textureTransform) {
        this.textureTransform = textureTransform;
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

    private Color4f blendColor = null;
    public final void setBlendColor(Color4f blendColor) {
        this.blendColor = blendColor;
    }

    private TextureAttributes ta = null;
    private final TextureAttributes getTextureAttributes(){
        if(textureMode == null && combineRgbMode == null && combineAlphaMode == null && combineRgbScale == null && combineAlphaScale == null
                && textureSourceObjects == null && blendColor == null && textureTransform == null) return AppearanceObject.defaultTextureAttributes;
        if(ta == null){
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
        }
        return ta;
    }
*/
    // -----------------------------------------------------------------------------------------------------

    private PolygonAttributes pa = null;
    public final void setPolygonAttributes(PolygonAttributes p){
        this.pa = p;
    }

    private int cull = PolygonAttributes.CULL_BACK;
    public final void setCull(int cull){
        this.cull = cull;
    }

    private boolean allowMaterial = true;
    public final void setAllowMaterial(boolean allowMaterial) {
        this.allowMaterial = allowMaterial;
    }

    private final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
    private final Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
    private final Color3f gray = new Color3f(0.5f, 0.5f, 0.5f);
    private final Material materialWhiteBlack = new Material(white, black, gray, black, 64f);
    //private final Material materialWhiteBlack = new Material(white, black, white, black, 64f);

    private Material material = null;
    public final void setMaterial(Material material) {
        this.material = material;
    }

    public Color3f ambient = null;
    public Color3f emissive = null;
    public Color3f diffuse = null;
    public Color3f specular = null;
    private Float shininess = null;

    public final void setAmbient(Color3f ambient) {
        this.ambient = ambient;
    }

    public final void setEmissive(Color3f emissive) {
        this.emissive = emissive;
    }

    public final void setDiffuse(Color3f diffuse) {
        this.diffuse = diffuse;
    }

    public final void setSpecular(Color3f specular) {
        this.specular = specular;
    }

    public final void setShininess(Float shininess) {
        this.shininess = shininess;
    }

    private TransparencyAttributes transparencyAttributes = null;
    private Float transparencyTestValue = null;
    private Integer transparencyMode = null;
    private Integer srcBlendFunction = null;
    private Integer dstBlendFunction = null;

    public final void setTransparencyAttributes(TransparencyAttributes transparencyAttributes) {
        this.transparencyAttributes = transparencyAttributes;
    }

    public final void setTransparencyMode(Integer transparencyMode) {
        this.transparencyMode = transparencyMode;
    }                                     

    public final void setTransparencyTestValue(Float alphaTestValue) {
        this.transparencyTestValue = alphaTestValue;
    }

    public final void setSrcBlendFunction(Integer srcBlendFunction) {
        this.srcBlendFunction = srcBlendFunction;
    }

    public final void setDstBlendFunction(Integer dstBlendFunction) {
        this.dstBlendFunction = dstBlendFunction;
    }

    private RenderingAttributes renderingAttributes = null;
    private Float renderTestValue = null;
    private Integer alphaTestFunction = null;

    public final void setRenderingAttributes(RenderingAttributes renderingAttributes) {
        this.renderingAttributes = renderingAttributes;
    }

    public final void setRenderTestValue(Float renderTestValue) {
        this.renderTestValue = renderTestValue;
    }

    public final void setAlphaTestFunction(Integer alphaTestFunction) {
        this.alphaTestFunction = alphaTestFunction;
    }

    /*private TextureAttributes textureAttributes = null;
    public final void setTextureAttributes(TextureAttributes textureAttributes) {
        this.textureAttributes = textureAttributes;
    }*/

    private ArrayList<TextureObject> textureObjects = null;
    public final void addTextureObject(TextureObject o){
        if(textureObjects == null) textureObjects = new ArrayList<TextureObject>();
        textureObjects.add(o);
    }

    private String vert = null;
    private String frag = null;

    public final void setVert(String vert) {
        this.vert = vert;
    }
    public final void setFrag(String frag) {
        this.frag = frag;
    }

    private ArrayList<ShaderParameterObject> shaderParameterObjects = null;
    public final void addShaderParameterObject(ShaderParameterObject o){
        if(this.shaderParameterObjects == null) this.shaderParameterObjects = new ArrayList<ShaderParameterObject>();
        this.shaderParameterObjects.add(o);
    }

    private Appearance returnAppearance = null;
    
    public final Appearance getAppearance() {
        if(returnAppearance == null){
            // returnAppearance = new Appearance();
            
            if(frag != null && vert != null){ // SHADER
                try {
                    String  vertexProgram = StringIO.readFully(vert);
                    String fragmentProgram = StringIO.readFully(frag);

                    ArrayList<String> attrNames = new ArrayList<String>();
                    ShaderAttributeSet shaderAttributeSet = new ShaderAttributeSet();
                    ArrayList<TextureUnitState> tus = new ArrayList<TextureUnitState>();

                    if(shaderParameterObjects != null){
                        for(ShaderParameterObject o : this.shaderParameterObjects){
                            attrNames.add(o.getName());

                            Texture tTexture = o.getTexture();
                            if(tTexture != null){
                                TextureUnitState tmp = new TextureUnitState();
                                tmp.setTexture(tTexture);
                                tmp.setTextureAttributes(o.getTextureAttributes());

                                Integer index = tus.size();
                                shaderAttributeSet.put(new ShaderAttributeValue(o.getName(), index));
                                tus.add(tmp);
                            }


                            Object val = o.getValue();
                            if(val != null){
                                shaderAttributeSet.put(new ShaderAttributeValue(o.getName(), val));
                            }
                        }
                    }

                    Shader[] shaders = new Shader[]{
                        new SourceCodeShader(Shader.SHADING_LANGUAGE_GLSL, Shader.SHADER_TYPE_VERTEX, vertexProgram),
                        new SourceCodeShader(Shader.SHADING_LANGUAGE_GLSL, Shader.SHADER_TYPE_FRAGMENT, fragmentProgram)
                    };

                    ShaderAppearance sa = new ShaderAppearance();

                    if(tus.size() > 0) sa.setTextureUnitState(tus.toArray(new TextureUnitState[tus.size()]));
                    if(shaderAttributeSet.size() > 0) sa.setShaderAttributeSet(shaderAttributeSet);

                    GLSLShaderProgram shaderProgram = new GLSLShaderProgram();
                    if(!attrNames.isEmpty())shaderProgram.setShaderAttrNames(attrNames.toArray(new String[attrNames.size()]));
                    shaderProgram.setShaders(shaders);

                    sa.setShaderProgram(shaderProgram);

                    returnAppearance = sa;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }else{
                returnAppearance = new Appearance();
            }

            if(pa == null){
                PolygonAttributes tmp = new PolygonAttributes();
                //tmp.setPolygonMode(PolygonAttributes.POLYGON_LINE);
                tmp.setCullFace(this.cull);
                returnAppearance.setPolygonAttributes(tmp);
            }else returnAppearance.setPolygonAttributes(pa);

            if(textureObjects == null || textureObjects.size() == 1){
                Texture texture = getTexture();
                if(texture != null){
                    returnAppearance.setTexture(texture);
                    returnAppearance.setTextureAttributes(this.getTextureAttributes());
                }else if(textureObjects != null){
                    returnAppearance.setTexture(textureObjects.get(0).getTexture());
                    returnAppearance.setTextureAttributes(textureObjects.get(0).getTextureAttributes());
                }
            }else{

                //Box BoxObj = new Box(1.5f, 1.5f, 0.8f, Box.GENERATE_NORMALS| Box.GENERATE_TEXTURE_COORDS, ap, 2);

                TextureUnitState[] tus = new TextureUnitState[textureObjects.size()];
                for(int i = 0; i < tus.length; i++){
                    tus[i] = new TextureUnitState();
                    tus[i].setTexture( textureObjects.get(i).getTexture() );
                    tus[i].setTextureAttributes(textureObjects.get(i).getTextureAttributes());
                }
                returnAppearance.setTextureUnitState(tus);
            }

            if(allowMaterial){
                if(this.material != null) returnAppearance.setMaterial(this.material);
                else if(this.ambient == null && this.emissive == null && this.diffuse == null && this.specular == null && this.shininess == null) returnAppearance.setMaterial(this.materialWhiteBlack);
                else{
                    Color3f am = this.ambient == null ? this.white : this.ambient;
                    Color3f em = this.emissive == null ? this.black : this.emissive;
                    Color3f di = this.diffuse == null ? this.gray : this.diffuse;
                    Color3f sp = this.specular == null ? this.black : this.specular;
                    Float sh = this.shininess == null ? 64f : this.shininess;
                    //System.out.println(ambient);
                    returnAppearance.setMaterial(new Material(am, em, di, sp, sh));
                }
            }

            if(transparencyAttributes != null) returnAppearance.setTransparencyAttributes(this.transparencyAttributes);
            else if(transparencyMode != null || transparencyTestValue != null || srcBlendFunction != null || dstBlendFunction != null){
                float trans = transparencyTestValue == null ? 0f : transparencyTestValue;

                int trMode = TransparencyAttributes.BLENDED;
                if(this.transparencyMode != null) trMode = this.transparencyMode;

                int srcMode = TransparencyAttributes.BLEND_SRC_ALPHA;
                if(this.srcBlendFunction != null) srcMode = this.srcBlendFunction;

                int dstMode = TransparencyAttributes.BLEND_ONE_MINUS_SRC_ALPHA;
                if(this.dstBlendFunction != null) dstMode = this.dstBlendFunction;

                returnAppearance.setTransparencyAttributes(new TransparencyAttributes(trMode, trans, srcMode, dstMode));
            }

            if(renderingAttributes != null) returnAppearance.setRenderingAttributes(this.renderingAttributes);
            else if(renderTestValue != null || alphaTestFunction != null){
                RenderingAttributes ra = new RenderingAttributes();
                ra.setAlphaTestFunction(this.alphaTestFunction == null ? RenderingAttributes.ALWAYS : alphaTestFunction);
                ra.setAlphaTestValue(renderTestValue == null ? 0.5f : renderTestValue);
                returnAppearance.setRenderingAttributes(ra);
            }
        }
        
        return returnAppearance;
    }

}
