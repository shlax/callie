package ws.loaders.groovy;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.elements.*;
import ws.loaders.tools.GeometryLoader;
import ws.loaders.tools.SoundLoader;
import ws.loaders.tools.TextureLoader;
import ws.loaders.tools.joint.BhoneFrameLoader;
import ws.loaders.tools.map.MapLoader;
import ws.map.Type;

import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransparencyAttributes;
import java.util.HashMap;
import java.util.Map;

public final class SceneBuilder extends FactoryBuilderSupport {
    public static enum SceneObjectType{ NORMAL, EFFECT, CAMERA }
    public static final String sceneType = "sceneType";
    public static final SceneObjectType sceneTypeNORMAL = SceneObjectType.NORMAL;
    public static final SceneObjectType sceneTypeEFFECT = SceneObjectType.EFFECT;    
    public static final SceneObjectType sceneTypeCAMERA = SceneObjectType.CAMERA;
    public static final String name = "name";

    public static final String onEnter = "onEnter";
    public static final String onExit = "onExit";

    public static final String point = "point";
    public static final String vector = "vector";
    public static final String color = "color";

    public static final String r = "r";
    public static final String g = "g";
    public static final String b = "b";
    public static final String a = "a";

    public static final String region = "region";

    public static final String appearance = "appearance";
    public static final String file = "file";
    public static final String stripifier = "stripifier";
    public static final String orient = "orient";
    public static final String userData = "userData";
    
    public static final String polygonAttributes = "polygonAttributes";

    public static final String cull = "cull";
    public static final int cullBack = PolygonAttributes.CULL_BACK;
    public static final int cullFront = PolygonAttributes.CULL_FRONT;
    public static final int cullNone = PolygonAttributes.CULL_NONE;

    public static final String vertexShader = "vertexShader";
    public static final String fragmentShader = "fragmentShader";

    public static final String texture = "texture";
    public static final String mipMap = "mipMap";
    public static final String format = "format";
    public static final String formatRGBA = "RGBA";
    public static final String formatRGBA4 = "RGBA4";
    public static final String formatRGB5_A1 = "RGB5_A1";
    public static final String formatRGB = "RGB";
    public static final String formatRGB4 = "RGB4";
    public static final String formatRGB5 = "RGB5";
    public static final String formatR3_G3_B2 = "R3_G3_B2";
    public static final String formatLUM8_ALPHA8 = "LUM8_ALPHA8";
    public static final String formatLUM4_ALPHA4 = "LUM4_ALPHA4";
    public static final String formatLUMINANCE = "LUMINANCE";
    public static final String formatALPHA = "ALPHA";

    public static final String textureMode = "textureMode";
    public static final int modulate = TextureAttributes.MODULATE;
    public static final int decal = TextureAttributes.DECAL;
    public static final int blend = TextureAttributes.BLEND;
    public static final int replace = TextureAttributes.REPLACE;
    public static final int combine = TextureAttributes.COMBINE;

    public static final String combineRgbMode = "combineRgbMode";
    public static final String combineAlphaMode = "combineAlphaMode";
    public static final int combineReplace = TextureAttributes.COMBINE_REPLACE;
    public static final int combineModulate = TextureAttributes.COMBINE_MODULATE;
    public static final int combineAdd = TextureAttributes.COMBINE_ADD;
    public static final int combineAddSigned = TextureAttributes.COMBINE_ADD_SIGNED;
    public static final int combineSubstract= TextureAttributes.COMBINE_SUBTRACT;
    public static final int combineInterpolate = TextureAttributes.COMBINE_INTERPOLATE;
    public static final int combineDot3 = TextureAttributes.COMBINE_DOT3;


    public static final String combineRgbScale = "combineRgbScale";
    public static final String combineAlphaScale = "combineAlphaScale";

    public static final String blendColor = "blendColor";

    public static final String alphaSource = "alphaSource";
    public static final String rgbSource = "rgbSource";
    public static final int combineObjectColor = TextureAttributes.COMBINE_OBJECT_COLOR;
    public static final int combineTextureColor = TextureAttributes.COMBINE_TEXTURE_COLOR;
    public static final int combineConstantColor = TextureAttributes.COMBINE_CONSTANT_COLOR;
    public static final int combinePreviousTextureUnitState = TextureAttributes.COMBINE_PREVIOUS_TEXTURE_UNIT_STATE;

    public static final String rgbFunction = "rgbFunction";
    public static final int srcColor = TextureAttributes.COMBINE_SRC_COLOR;
    public static final int oneMinusSrcColor = TextureAttributes.COMBINE_ONE_MINUS_SRC_COLOR;
    public static final String alphaFunction = "alphaFunction";
    public static final int srcAlpha = TextureAttributes.COMBINE_SRC_ALPHA;
    public static final int oneMinusSrcAlpha = TextureAttributes.COMBINE_ONE_MINUS_SRC_ALPHA;

    public static final String material = "material";
    public static final String ambient = "ambient";
    public static final String emissive = "emissive";
    public static final String diffuse = "diffuse";
    public static final String specular = "specular";
    public static final String shininess = "shininess";

   // public static final String color = "color";
    public static final String direction = "direction";
    public static final String position = "position";
    public static final String attenuation = "attenuation";
    public static final String spreadAngle = "spreadAngle";
    public static final String concentration = "concentration";

    public static final String transparency = "transparency";
    public static final String transparencyMode = "transparencyMode";
    public static final int blended = TransparencyAttributes.BLENDED;
    public static final int screenDoor = TransparencyAttributes.SCREEN_DOOR;

    public static final String srcBlendFunction = "srcBlendFunction";
    public static final String dstBlendFunction = "dstBlendFunction";
    public static final int blendZero = TransparencyAttributes.BLEND_ZERO;
    public static final int blendOne = TransparencyAttributes.BLEND_ONE;
    public static final int blendSrcAlpha = TransparencyAttributes.BLEND_SRC_ALPHA;
    public static final int blendOneMinusSrcAlpha = TransparencyAttributes.BLEND_ONE_MINUS_SRC_ALPHA;

    public static final String rendering = "rendering";
    public static final String alphaTestFunction = "alphaTestFunction";
    public static final int always = RenderingAttributes.ALWAYS;
    public static final int never = RenderingAttributes.NEVER;
    public static final int equal = RenderingAttributes.EQUAL;
    public static final int noEqual = RenderingAttributes.NOT_EQUAL;
    public static final int less = RenderingAttributes.LESS;
    public static final int lessOrEqual = RenderingAttributes.LESS_OR_EQUAL;
    public static final int greater = RenderingAttributes.GREATER;
    public static final int greaterOrEqual = RenderingAttributes.GREATER_OR_EQUAL;

    public static final String textureAttributes = "textureAttributes";
    public static final String textureTransform = "textureTransform";

    //public static final String combine = "combine";
    public static final String shaderParameter = "shaderParameter";
    public static final String value = "value";

    public static final String x = "x";
    public static final String y = "y";
    public static final String z = "z";
    public static final String xyz = "xyz";
    public static final String rotX = "rotX";
    public static final String rotY = "rotY";
    public static final String rotZ = "rotZ";    
    public static final String scale = "scale";
    public static final String scaleX = "scaleX";
    public static final String scaleY = "scaleY";
    public static final String scaleZ = "scaleZ";    
    public static final String scaleXYZ = "scaleXYZ";
    
    public static final String mapType = "mapType";
    public static final Type jump = Type.JUMP;
    public static final Type terain = Type.TERAIN;
    //public static final String dynamic = "dynamic";
    public static final String active = "active";

    public static final String shotSource = "shotSource";
    public static final String lookAtSource = "lookAtSource";

    public static final String bhoneFile = "bhoneFile";
    public static final String skinFile = "skinFile";

    public static final String startPosition = "startPosition";
    public static final String startAngle = "startAngle";

    public static final String jumpSpeed = "jumpSpeed";
    public static final String jumpSpeedToRun = "jumpSpeedToRun";
    public static final String runSpeed = "runSpeed";
    public static final String walkSpeed = "walkSpeed";

    public static final String bhoneSkin = "bhoneSkin";
    public static final String disArmedBhone = "disArmedBhone";
    public static final String armedBhone = "armedBhone";
    public static final String disArmedTransform= "disArmedTransform";
    public static final String armedTransform = "armedTransform";
    public static final String targetBhone = "targetBhone";
    public static final String clip = "clip";

    public static final String include = "include";
    public static final String control = "control";
    public static final String ai = "ai";
    public static final String agent = "agent";
    public static final String item = "item";
    public static final String bhone = "bhone";
    public static final String check = "check";

    public static final String frame = "frame";
    public static final String animationFrame = "animationFrame";
    public static final String animation = "animation";
    public static final String animationTransform = "animationTransform";

    public static final String keyCode = "keyCode";
    public static final String frameType = "frameType";

    public static final String destination = "destination";
    public static final String source = "source";
    public static final String sourceRadius = "sourceRadius";
    public static final String sourceAngle = "sourceAngle";
    public static final String removeAfterPlay = "removeAfterPlay";

    public static final String frameWindow = "frameWindow";
    public static final String scene = "scene";
    public static final String backClipDistance = "backClipDistance";
    public static final String map = "map";
    public static final String model = "model";
    public static final String transform = "transform";
    public static final String transformGroup = "transformGroup";
    public static final String shot = "shot";

    public static final String colision = "colision";
    public static final String colisionRadius = "colisionRadius";

    public static final String live = "live";
    public static final String lookDistance = "lookDistance";
    public static final String lookAngle = "lookAngle";
    //public static final String criticalLookDistance = "criticalLookDistance";
    public static final String targetHeight = "targetHeight";
    public static final String targetRadius = "targetRadius";
    public static final String acelerationSpeed = "acelerationSpeed";
    public static final String acelerationRotate = "acelerationRotate";
    public static final String rotateDelay = "rotateDelay";
    public static final String maxSpeed = "maxSpeed";

    public static final String cameraMinDistance = "cameraMinDistance";
    public static final String cameraMaxDistance = "cameraMaxDistance";
    public static final String cameraHeight = "cameraHeight";
    public static final String cameraSide = "cameraSide";

    public static final String heightUp = "heightUp";
    public static final String heightDown = "heightDown";

    public static final String pickWeapon = "pickWeapon";

    public static final String firePower = "firePower";
    public static final String cadence = "cadence";
    public static final String minDistance = "minDistance";
    public static final String maxDistance = "maxDistance";
    public static final String shotDuration = "shotDuration";
    public static final String shotRadius = "shotRadius";

    public static final String ambientLight = "ambientLight";
    public static final String directionalLight = "directionalLight";
    public static final String pointLight = "pointLight";
    public static final String spotLight = "spotLight";

    public static final String activeDistance = "activeDistance";
    public static final String lsSystem = "lsSystem";
    public static final String lsBlok = "lsBlok";
    public static final String lsCell = "lsCell";
    public static final String index = "index";
    public static final String cell = "cell";
    public static final String size = "size";
    public static final String sizeY = "sizeY";

    public static final String minX = "minX";
    public static final String maxX = "maxX";
    public static final String minY = "minY";
    public static final String maxY = "maxY";
    public static final String minZ = "minZ";
    public static final String maxZ = "maxZ";

    public static final String group = "group";
    public static final String onOff = "onOff";
    public static final String enabled = "enabled";

    //public static final String destinationLocator = "destinationLocator";
    //public static final String enemyLocator = "enemyLocator";

    public static final String link = "link";
    public static final String shared = "shared";

//    public static final String xAction = "xAction";

    //private final HashMap<String, Element<?>> mapElements = new HashMap<String, Element<?>>();
    //private final ArrayList<Build> list = new ArrayList<Build>();
    
    public SceneBuilder(){
        BhoneFrameLoader bhoneFrameLoader = new BhoneFrameLoader();
        TextureLoader textureLoader = new TextureLoader();
        GeometryLoader geometryLoader = new GeometryLoader();
        MapLoader mapLoader = new MapLoader();
        SoundLoader soundLoader = new SoundLoader();

        HashMap<String, AbstractFactory> tmp = new HashMap<String, AbstractFactory>();
        tmp.put(include, new IncludeElement());

        tmp.put(colision, new ColisionElement());

        tmp.put(control, new ControlElement(bhoneFrameLoader));

        tmp.put(ai, new AiElement());
        tmp.put(agent, new AgentElment());
        tmp.put(item, new AiItemElement());
        tmp.put(check, new AiCheckElement());

        tmp.put(bhoneSkin, new BhoneSkinElement());
        tmp.put(frame, new BhoneSkinFrameElement(bhoneFrameLoader, soundLoader));

        tmp.put(animationFrame, new AnimationFrameElement());
        tmp.put(animation, new AnimationElement());
        tmp.put(animationTransform, new AnimationTransformElement());

        tmp.put(scene, new SceneElement());
        tmp.put(map, new MapElement(mapLoader));
        tmp.put(model, new ModelElement(geometryLoader));

        tmp.put(transform, new TransformElement());

        tmp.put(appearance, new AppearanceElement(textureLoader));
        tmp.put(texture, new TextureElement(textureLoader));
        tmp.put(shaderParameter, new ShaderParameterElement());
        
        tmp.put(group, new BranchGroupElement());

        tmp.put(transformGroup, new TransformGroupElement());

        tmp.put(link, new LinkElement());
        tmp.put(shared, new SharedGroupElement());

        tmp.put(ambientLight, new AmbientLightElement());
        tmp.put(directionalLight, new DirectionalLightElement());
        tmp.put(pointLight, new PointLightElement());
        tmp.put(spotLight, new SpotLightElement());

        tmp.put(shot, new ShotElement(soundLoader));

        tmp.put(onOff, new SwitchEl());

        tmp.put(point, new PointEl());
        tmp.put(vector, new VectorEl());
        tmp.put(color, new ColorEl());

        /* tmp.put(lsSystem, new LsElement(textureLoader, geometryLoader, mapLoader));
        tmp.put(lsBlok, new LsBlokElement());
        tmp.put(lsCell, new LsCellElement()); */

//        tmp.put(xAction, new PutActionElement());
        //tmp.put(destinationLocator, new DestinationLocatorElement());
//        tmp.put(enemyLocator, new EnemyLocatorElement());

        for(Map.Entry<String, AbstractFactory> e : tmp.entrySet()){
            String n = e.getKey();
            AbstractFactory v = e.getValue();
            this.registerFactory(n, v);
            this.registerFactory("_"+n, new Arg(v));
        }

    }
    
    /*
    @Override
    protected final void setParent(Object parent, Object child) {
        for(Build b : this.list) if(b.setParent((BuildObject)parent, (BuildObject)child)) return;
        System.err.println("canot find build for: "+parent+" "+child);
    }

    @Override
    protected final Object createNode(Object name) {
        return createNode(name, null, null);
    }

    @Override
    protected final Object createNode(Object name, Object value) {
        return createNode(name, null, value);
    }

    @Override
    protected final Object createNode(Object name, Map map) {
        return createNode(name, map, null);
    }

    @Override
    protected final Object createNode(Object name, Map map, Object value) {
        Element<?> e = this.mapElements.get(name);
        if(e == null) System.err.println(name+" is not valid element");
        Object o = e.process(value, map);
        if(o == null) System.err.println(name+" "+e+" element is missing");
        return o;
    }
    */
}
