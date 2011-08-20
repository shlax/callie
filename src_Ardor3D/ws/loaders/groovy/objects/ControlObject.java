package ws.loaders.groovy.objects;

import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.Spatial;
import com.ardor3d.scenegraph.extension.SwitchNode;
import ws.camera.Character;
import ws.camera.CharacterCamera;
import ws.camera.UserCamera;
import ws.camera.animation.*;
import ws.camera.animation.cover.CoverAnimation;
import ws.camera.animation.cover.CoverKeyFrame;
import ws.camera.areas.Colision;
import ws.joint.AceleratedBhone;
import ws.joint.ActiveTransformBhone;
import ws.joint.BhoneSkin;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.OnOfInterface;
import ws.loaders.tools.joint.*;
import ws.loaders.tools.map.Y25MapBuilder;
import ws.map.Type;
import ws.map.Y25Map;
import ws.map.Y25Triangle;
import ws.tools.SceneAction;
import ws.tools.Shot;
import ws.tools.controls.CharacterControl;
import ws.tools.controls.OnOffObject;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class ControlObject extends FactoryElement {
    private final BhoneFrameLoader bhoneFrameLoader;

    public ControlObject(Object value, Map attributes, BhoneFrameLoader bhoneFrameLoader) {
        super(value, attributes);
        this.bhoneFrameLoader = bhoneFrameLoader;
    }

    private ArrayList<AiItemObject> aiItemObjects = null; // = new ArrayList<AiItemObject>();
    public final void addAiItemObject(AiItemObject o){
        if(aiItemObjects == null) aiItemObjects = new ArrayList<AiItemObject>();
        this.aiItemObjects.add(o);
    }

    private ArrayList<MapObject> mapObject = null; // new ArrayList<MapObject>();
    public final void addMapObject(MapObject mapObject) {
        if(this.mapObject == null) this.mapObject = new ArrayList<MapObject>();
        //System.out.println(mapObject);
        this.mapObject.add(mapObject);
    }

/*    private LsObject lsObject = null;
    public final void setLsObject(LsObject lsObject) {
        this.lsObject = lsObject;
    } */
        
    private Vector3f startPosition = null;
    public final void setStartPosition(Vector3f startPosition) {
        this.startPosition = startPosition;
    }

    private float jumpSpeed = 0.002f;
    public final void setJumpSpeed(float jumpSpeed) {
        this.jumpSpeed = jumpSpeed * 1000f;
    }

    private float jumpSpeedRun = 0.00375f;
    public final void setJumpSpeedRun(float jumpSpeedRun) {
        this.jumpSpeedRun = jumpSpeedRun * 1000f;
    }

    private float runSpeed = 0.003f;
    public final void setRunSpeed(float runSpeed) {
        this.runSpeed = runSpeed * 1000f;
    }

    private Float walkSpeed = 0.0015f;
    public final void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed * 1000f;
    }

    private float frameWindow = 250f;
    public final void setFrameWindow(float frameWindow) {
        this.frameWindow = frameWindow * 1000f;
    }

    private float colisionRadius = 0.5f;
    public final void setColisionRadius(float colisionRadius) {
        this.colisionRadius = colisionRadius;
    }

    private float rotateAceleration = 0.01f;
    public final void setRotateAceleration(Float rotateAceleration) {
        this.rotateAceleration = rotateAceleration * 1000f;
    }

    private float speedAceleration = 0.001f;
    public final void setSpeedAceleration(float speedAceleration) {
        this.speedAceleration = speedAceleration * 1000f;
    }

    private float cameraMinDistance = 1f;
    public final void setCameraMinDistance(float cameraMinDistance) {
        this.cameraMinDistance = cameraMinDistance;
    }

    private float cameraMaxDistance = 4f;
    public final void setCameraMaxDistance(float cameraMaxDistance) {
        this.cameraMaxDistance = cameraMaxDistance;
    }

    private float defMaxMinDistance = 2f;
    public final void setCameraMaxMinDistance(float cameraMaxDistance) {
        this.defMaxMinDistance = cameraMaxDistance;
    }

    private float cameraHeight = 1.65f;
    public final void setCameraHeight(float cameraHeight) {
        this.cameraHeight = cameraHeight;
    }

    private float cameraSide = 0.25f;
    public final void setCameraSide(float cameraSide) {
        this.cameraSide = cameraSide;
    }
    private float heightUp = 1.4f;
    public void setHeightUp(float heightUp) {
        this.heightUp = heightUp;
    }

    private float heightDown = 0.8f;
    public void setHeightDown(Float heightDown) {
        this.heightDown = heightDown;
    }

    private ShotObject shotObject = null;
    public final void setShotObject(ShotObject shotObject) {
        this.shotObject = shotObject;
    }

    private Point3f shotSource = null;
    public final void setShotSource(Point3f shotSource) {
        this.shotSource = shotSource;
    }

    private BhoneSkinObject bhoneSkinObject = null;
    public final void setBhoneSkinObject(BhoneSkinObject bhoneSkinObject) {
        this.bhoneSkinObject = bhoneSkinObject;
    }

    private Boolean weaponPick = false;
    public final void setWeaponPick(Boolean weaponPick) {
        this.weaponPick = weaponPick;
    }

    private WeaponObj itemObj = null;
    public final void setItem(WeaponObj item) {
        this.itemObj = item;
    }

    private String disArmedBhone = null;
    public final void setDisArmedBhone(String disArmedBhone) {
        this.disArmedBhone = disArmedBhone;
    }

    private Matrix4f disArmedTransform = null;
    public final void setDisArmedTransform(Matrix4f disArmedTransform) {
        this.disArmedTransform = disArmedTransform;
    }

    private String armedBhone = null;
    public final void setArmedBhone(String armedBhone) {
        this.armedBhone = armedBhone;
    }

    private String targetBhone = null;
    public final void setTargetBhone(String targetBhone) {
        this.targetBhone = targetBhone;
    }

    private Matrix4f armedTransform = null;
    public final void setArmedTransform(Matrix4f armedTransform) {
        this.armedTransform = armedTransform;
    }

    private ArrayList<ColisionObject> colisionObjects = null; // = new ArrayList<ColisionObject>();
    public final void addColisionObject(ColisionObject o){
        if(colisionObjects == null) colisionObjects = new ArrayList<ColisionObject>();
        this.colisionObjects.add(o);
    }

    /*private final ArrayList<HitAreaObject> hitAreaObjects = new ArrayList<HitAreaObject>();
    public final void addHitAreaObject(HitAreaObject o){
        hitAreaObjects.addAnimationFrameObject(o);
    }*/

    private ArrayList<AnimationTransformObject> animationObjects = null;// new ArrayList<AnimationTransformObject>();
    public final void addAnimationObject(AnimationTransformObject o){
        if(animationObjects == null) animationObjects = new ArrayList<AnimationTransformObject>();
        this.animationObjects.add(o);
    }

    // private final TransformGroup chracterTransform = new TransformGroup();
    private ArrayList<NodeObject> nodes = null; // new ArrayList<NodeObject>();
    public final void addNodeObject(NodeObject o){
        if(nodes == null) nodes = new ArrayList<NodeObject>();
        nodes.add(o);
        //o.getNode(chracterTransform);
        //chracterTransform.addChild(o.getNode());
    }

    public final CharacterCamera buildCharacterCamera(Node cameraNode, Node activeNode, Node efectNode) throws IOException {
        Y25MapBuilder mapBuilder = new Y25MapBuilder();
      //  if(lsObject != null) mapBuilder.addLoadedMap(lsObject.getTriangles());
        if(mapObject != null)for(MapObject tmp : mapObject)mapBuilder.addLoadedMap(tmp.getTriangles(), tmp.getSceneAction());
        Y25Map mapa = mapBuilder.getMap();
        if(mapObject != null)for(MapObject tmp : mapObject) tmp.postProcess();

        // ---------------------------------------------------------------------------------------------------------------------------------------------------

        Y25Triangle startTriangle;
        /*if(startPosition == null && this.lsObject != null){
        //    System.out.println("A "+lsObject.getStartPosition());
            startPosition = new Vector3f(lsObject.getStartPosition());
            LoadedTriangle lt = mapBuilder.getNear(new Point3f(startPosition), Type.TERAIN);
            startPosition.set(lt.getLocation());

            startTriangle = lt.getY25Triangle();
        }else{
            startTriangle = mapBuilder.getNear(new Point3f(startPosition), Type.TERAIN).getY25Triangle();
        }*/
        startTriangle = mapBuilder.getNear(new Point3f(startPosition), Type.TERAIN).getY25Triangle();

        //System.out.println(startPosition);

        startTriangle.getY(startPosition);

        //System.out.println(startPosition);
//        System.out.println("B "+startPosition);

        Shot shot = shotObject.getLoadedShot().getShot(efectNode);

        LoadedBhoneSkin bs = bhoneSkinObject.getLoadedBhoneSkin(new String[]{
                    "STAND", // 0
                    "RUN1", "RUN2", "RUN3" ,"RUN4", // 1 2 3 4
                    "JUMPL1", "JUMPL2", "JUMPL3", // 5 6 7
                    "JUMPR1", "JUMPR2", "JUMPR3", // 8 9 10
                    "RIFLE_STAND", // 11
                    "RIFLE_RUN1", "RIFLE_RUN2", "RIFLE_RUN3" ,"RIFLE_RUN4", // 12 13 14 15
                    "RIFLE_FIRE", "RIFLE_PICK", // 16 17
                    "DOWN", // 18    
                    "STAND", // 19
                    "DEAD_SHOT", "DEAD_JUMP" // 20 21
                });


        Node chracterTransform = new Node();
        if(nodes != null)for(NodeObject no : nodes) chracterTransform.attachChild(no.getNode());

       // chracterTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        activeNode.attachChild(chracterTransform);

        /*{
            //final float inf = f;
            PointLight light = new PointLight(new Color3f(1f, 1f, 1f), new Point3f(0f, 1.6f, 0f), new Point3f(0.25f, 0.25f, 0.25f));
            light.setInfluencingBounds( bsp );
            chracterTransform.addChild(light);
        }{
            PointLight light = new PointLight(new Color3f(0.3f, 0.2f, 0.3f), new Point3f(-0.5f, 1.7f, -0.5f), new Point3f(inf, inf, inf));
            light.setInfluencingBounds( bsp );
            chracterTransform.addChild(light);
        }{
            PointLight light = new PointLight(new Color3f(0.3f, 0.3f, 0.3f), new Point3f(-0.5f, 1.3f, 0.5f), new Point3f(inf, inf, inf));
            light.setInfluencingBounds( bsp );
            chracterTransform.addChild(light);
        }{
            PointLight light = new PointLight(new Color3f(0.3f, 0.3f, 0.3f), new Point3f(0.5f, 1.7f, -0.5f), new Point3f(inf, inf, inf));
            light.setInfluencingBounds( bsp );
            chracterTransform.addChild(light);
        }{
            BoundingSphere bsp = new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE);
            final float psInf = 0.05f;
            SpotLight sp = new SpotLight(new Color3f(1f, 1f, 1f), new Point3f(0f, 1.6f, 0f), new Point3f(psInf,psInf,psInf),new Vector3f(0f, 0f, 1f), 0.4f, 32f);
            sp.setInfluencingBounds( bsp );
            chracterTransform.addChild(sp);
        }*/

        Node item = itemObj.getNode();
       // item.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
               
        SwitchNode sw;
        if(weaponPick){
            sw = new SwitchNode(/*Switch.CHILD_NONE*/);
            sw.setAllNonVisible();
            //sw.setCapability(Switch.ALLOW_SWITCH_WRITE);
            sw.attachChild(item);
            chracterTransform.attachChild(sw);
        }else{
            sw = null;
            UserCamera.equiptWeapon(true);
            chracterTransform.attachChild(item);
        }

        Matrix4f disArmendTrans = this.disArmedTransform;//.getTransform3D();
        Matrix4f armendTrans = this.armedTransform;//.getTransform3D();
        Point3f target = new Point3f(); //this.armedTransform;//.getTransform3D();

        bs.setToTransform(this.disArmedBhone, item, disArmendTrans, true);
        bs.setToTransform(this.armedBhone, item, armendTrans, true);
        bs.setToTransform(this.targetBhone, target);

        // Character
        BhoneSkin bhoneSkin = bs.getBhoneSkin(chracterTransform);
        //bs.setUserData(UserCamera.CHARACTER);

        ActiveTransformBhone tbWeaponDisabled = (ActiveTransformBhone)bs.getBhone(this.disArmedBhone);
        ActiveTransformBhone tbWeaponEnabled = (ActiveTransformBhone)bs.getBhone(this.armedBhone);
        //TargetBhone targetBhone = (TargetBhone)bs.getBhone(this.targetBhone);

       // System.out.println(tbWeaponEnabled +" "+this.armedBhone);
        /*TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.addChild(new Sphere(0.25f));
        efectNode.addChild(tg);*/

        if(aiItemObjects != null)for(AiItemObject tmp : aiItemObjects){
            String name = tmp.getBhoneName();
            Matrix4f trans = tmp.getTransform();

            Node itemTransform = tmp.getNode();
           // itemTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            //if(bg == null){
            chracterTransform.attachChild(itemTransform);
            /*}else{
                bg.addChild(itemTransform);
            }*/


            bs.setToTransform(name, itemTransform, trans, false);
        }

        Character c = new Character(bhoneSkin, tbWeaponDisabled, tbWeaponEnabled, shot, shotSource, target/*, tg*/);

        // animations
        ArrayList<Animation> ani = new ArrayList<Animation>(this.animationObjects.size());

        // cover animations
        ArrayList<CoverAnimation> covers = new ArrayList<CoverAnimation>();

        if(animationObjects != null && !animationObjects.isEmpty()){

            for(AnimationTransformObject tmp : animationObjects){

                if(tmp.getAnimationObject().isShot()){
                    // loadCover
                    covers.add(loadCoverAnimation(/*c, sw, */bs, tmp/*, mapBuilder*/));
                }else{
                    ani.add(loadAnimation(c, sw, bs, tmp, mapBuilder));
                }
            }
            //for(AnimationTransformObject tmp : this.lsObject.processAnimations()) ani.addKeyFrameObj(loadAnimation(c, sw, bs, tmp, mapBuilder));
        }
        
        ArrayList<Colision> aCol = new ArrayList<Colision>();
    //    ArrayList<ActionArea> aAra = new ArrayList<ActionArea>();
        if(colisionObjects != null && !this.colisionObjects.isEmpty()){
            for(ColisionObject tmp : colisionObjects){
              //  if (tmp.isAction())aAra.add(tmp.getActionArea());
                //if( tmp.isColision() )
                aCol.add(tmp.getColision());
            }
        }


        /* ArrayList<HitArea> aHa = null;
        if(!hitAreaObjects.isEmpty()){
            aHa = new ArrayList<HitArea>(hitAreaObjects.size());
            for(HitAreaObject tmp : hitAreaObjects) aHa.addAnimationFrameObject(tmp.getHitArea());
        } */

        UserCamera uc = new UserCamera(shotSource.y, // aHa == null ? new HitArea[0] : aHa.toArray(new HitArea[aHa.size()]),
                               bs.getShape(),
                               covers,
                               ani,
                               heightUp, heightDown, c, jumpSpeed, jumpSpeedRun, runSpeed, walkSpeed, frameWindow,
                               colisionRadius, rotateAceleration, speedAceleration,  aCol.toArray(new Colision[aCol.size()]),  mapa,
                               cameraNode, startPosition, startTriangle, cameraMinDistance, cameraMaxDistance, defMaxMinDistance, cameraHeight, cameraSide);

        setUserData(chracterTransform);

        aiItemObjects = null;
        mapObject = null;
        animationObjects = null;
        nodes = null;
        colisionObjects = null;

        return uc;
    }

    public final CharacterControl control(){
        return new CharacterControl();
    }

    private void setUserData(Node g){
        for(Spatial nod : g.getChildren()){
            if(nod instanceof Mesh) nod.setUserData(UserCamera.CHARACTER);
            else if(nod instanceof Node) setUserData((Node)nod);
        }
    }

    private final Animation loadAnimation(Character c, SwitchNode sw, LoadedBhoneSkin loadedBhoneSkin, AnimationTransformObject ato, Y25MapBuilder mapBuilder) {
        try {
            Float keyFrameRatio = ato.getAnimationObject().getKeyFrameRatio();

            Float sourceRadius = ato.getAnimationObject().getSourceRadius();

            Float sourceAngleTolerantion = ato.getAnimationObject().getSourceAngleTolerantion();

            boolean removeAfterPlay = ato.isRemoveAfterPlay();
            boolean autoplay = ato.isAutolay();

            Point3f destination = new Point3f(ato.getAnimationObject().getDestination());

            Matrix4f tmpTrans = new Matrix4f();
            tmpTrans.rotY(ato.getSourceAnge());
            tmpTrans.transform(destination);

            destination.add(ato.getSource());

            Y25Triangle endTriangle = mapBuilder.getNear(destination, Type.TERAIN).getY25Triangle();
            endTriangle.getY(destination);

            ArrayList<BhoneSkinFrameObject> keyFrames = ato.getAnimationObject().getAnimationFrameObjects();
            KeyFrame[] frames = new KeyFrame[keyFrames.size() + 1];

            for(int i = 0; i < frames.length; i++){
                LoadedBhoneFrame lbf = i == frames.length - 1 ? bhoneFrameLoader.load( bhoneSkinObject.getBhoneSkinFrameObject("STAND").getFile() , 1f) : bhoneFrameLoader.load( keyFrames.get(i).getFile(), 1f);
                lbf.applyTo(loadedBhoneSkin.getLoadedBhone(), false);

                // BhoneSkinObject bhoneSkin, Vector3f offset, AceleratedBhone[] bhones, Angle3f[] bhoneAngles
                AceleratedBhone[] bhones = new AceleratedBhone[lbf.getAngles().size()];
                Tuple3f[] bhoneAngles  = new Tuple3f[lbf.getAngles().size()];
                for(int j = 0; j < lbf.getAngles().size(); j++){
                    Angles tmp = lbf.getAngles().get(j);
                    bhones[j] = (AceleratedBhone)loadedBhoneSkin.getBhone(tmp.getName());
                    bhoneAngles[j] = tmp.getAngle();
                }

                FrameType type = i == frames.length - 1 ? null : keyFrames.get(i).getFrameType();
                if(type == FrameType.EnableWeapon){
                    frames[i] = new KeyFrameActiveWeapon(c, sw, loadedBhoneSkin.getBhoneSkin(null), lbf.getOffset(), bhones, bhoneAngles);
                }else if(type == FrameType.DisableWeapon){
                    frames[i] = new KeyFrameDisarmeWeapon(c, loadedBhoneSkin.getBhoneSkin(null), lbf.getOffset(), bhones, bhoneAngles);
                }else{
                    Integer keyCode = i == frames.length - 1 ? null : keyFrames.get(i).getKeyCode();
                    if(keyCode != null){
                        frames[i] = new KeyFrameState(keyCode,loadedBhoneSkin.getBhoneSkin(null), lbf.getOffset(), bhones, bhoneAngles);
                    }else{
                        frames[i] = new KeyFrame(loadedBhoneSkin.getBhoneSkin(null), lbf.getOffset(), bhones, bhoneAngles);
                    }
                }
            }

            SceneAction sa = ato.getSceneAction();
            OnOffObject of = ato.isOnOff();

            float srcAngle = ato.getSourceAnge()+(float)Math.PI;
            if(srcAngle < 0f)srcAngle += 2f*(float)Math.PI;
            else if(srcAngle >  2f*(float)Math.PI)srcAngle -= 2f*(float)Math.PI;

            if(of == null){
                if(sa != null) return new ClosureAnimation(autoplay, sa, removeAfterPlay, ato.getSource(), sourceRadius, srcAngle, sourceAngleTolerantion, destination, endTriangle, keyFrameRatio, frames);
                else return new Animation(autoplay, removeAfterPlay, ato.getSource(), sourceRadius, srcAngle, sourceAngleTolerantion, destination, endTriangle, keyFrameRatio, frames);
            }else{
                Animation ani = sa != null ? new ActiveClosureAnimation(autoplay, sa, removeAfterPlay, ato.getSource(), sourceRadius, srcAngle, sourceAngleTolerantion, destination, endTriangle, keyFrameRatio, frames) : new ActiveAnimation(autoplay, removeAfterPlay, ato.getSource(), sourceRadius, srcAngle, sourceAngleTolerantion, destination, endTriangle, keyFrameRatio, frames);
                OnOfInterface inter = (OnOfInterface) ani;
                if(!ato.isActive())inter.setActive(false);
                of.setCon(inter);
                return ani;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
      //  return null;
    }

    private final CoverAnimation loadCoverAnimation(/*Character c, SwitchNode sw, */LoadedBhoneSkin loadedBhoneSkin, AnimationTransformObject ato/*, Y25MapBuilder mapBuilder*/) {
        try {
            float keyFrameRatio = ato.getAnimationObject().getKeyFrameRatio();

            float sourceRadius = ato.getAnimationObject().getSourceRadius();

            float sourceAngleTolerantion = ato.getAnimationObject().getSourceAngleTolerantion();

          //  boolean removeAfterPlay = ato.isRemoveAfterPlay();
          //  boolean autoplay = ato.isAutolay();

            Point3f destination = new Point3f(ato.getAnimationObject().getDestination());
            Point3f targetPosition = new Point3f(ato.getAnimationObject().getTargetPosition());

            Matrix4f tmpTrans = new Matrix4f();
            tmpTrans.rotY(ato.getSourceAnge());
            tmpTrans.transform(destination);
            tmpTrans.transform(targetPosition);

            destination.add(ato.getSource());
            targetPosition.add(ato.getSource());

            //Y25Triangle endTriangle = mapBuilder.getNear(destination, Type.TERAIN).getY25Triangle();
            //endTriangle.getY(destination);

            ArrayList<BhoneSkinFrameObject> keyFrames = ato.getAnimationObject().getAnimationFrameObjects();
            CoverKeyFrame[] frames = new CoverKeyFrame[keyFrames.size() + 1];

            for(int i = 0; i < frames.length; i++){
                LoadedBhoneFrame lbf = i == frames.length - 1 ? bhoneFrameLoader.load( bhoneSkinObject.getBhoneSkinFrameObject("RIFLE_STAND").getFile() , 1f) : bhoneFrameLoader.load( keyFrames.get(i).getFile(), 1f);
                lbf.applyTo(loadedBhoneSkin.getLoadedBhone(), false);

                // BhoneSkinObject bhoneSkin, Vector3f offset, AceleratedBhone[] bhones, Angle3f[] bhoneAngles
                AceleratedBhone[] bhones = new AceleratedBhone[lbf.getAngles().size()];
                Tuple3f[] bhoneAngles  = new Tuple3f[lbf.getAngles().size()];
                for(int j = 0; j < lbf.getAngles().size(); j++){
                    Angles tmp = lbf.getAngles().get(j);
                    bhones[j] = (AceleratedBhone)loadedBhoneSkin.getBhone(tmp.getName());
                    bhoneAngles[j] = tmp.getAngle();
                }

                /*FrameType type = i == frames.length - 1 ? null : keyFrames.get(i).getFrameType();
                if(type == FrameType.EnableWeapon){
                    frames[i] = new KeyFrameActiveWeapon(c, sw, loadedBhoneSkin.getBhoneSkin(null), lbf.getOffset(), bhones, bhoneAngles);
                }else if(type == FrameType.DisableWeapon){
                    frames[i] = new KeyFrameDisarmeWeapon(c, loadedBhoneSkin.getBhoneSkin(null), lbf.getOffset(), bhones, bhoneAngles);
                }else{
                    Integer keyCode = i == frames.length - 1 ? null : keyFrames.get(i).getKeyCode();
                    if(keyCode != null){
                        frames[i] = new KeyFrameState(keyCode,loadedBhoneSkin.getBhoneSkin(null), lbf.getOffset(), bhones, bhoneAngles);
                    }else{ */
                frames[i] = new CoverKeyFrame(loadedBhoneSkin.getBhoneSkin(null), lbf.getOffset(), bhones, bhoneAngles, i == keyFrames.size() ? false : keyFrames.get(i).isShot());
                /*    }
                } */
            }

            //SceneAction sa = ato.getSceneAction();
            //OnOffObject of = ato.isOnOff();


            float srcAngle = ato.getSourceAnge()+(float)Math.PI;
            if(srcAngle < 0f)srcAngle += 2f*(float)Math.PI;
            else if(srcAngle >  2f*(float)Math.PI)srcAngle -= 2f*(float)Math.PI;

            //System.out.println(ato.getAnimationObject().getShotAngle()+" "+srcAngle);

            float shotAngle = ato.getAnimationObject().getShotAngle() + srcAngle;
            //shotAngle += srcAngle;

            if(shotAngle < 0f) shotAngle += 2f*(float)Math.PI;
            else if(shotAngle > 2f*(float)Math.PI) shotAngle -= 2f*(float)Math.PI;

            return new CoverAnimation(ato.getSource(), srcAngle, sourceRadius, destination, shotAngle,sourceAngleTolerantion, keyFrameRatio, frames, ato.getAnimationObject().getSide(), targetPosition );
            /*if(of == null){
                if(sa != null) return new ClosureAnimation(autoplay, sa, removeAfterPlay, ato.getSource(), sourceRadius, ato.getSourceAnge()+(float)Math.PI, sourceAngleTolerantion, destination, endTriangle, keyFrameRatio, frames);
                else return new Animation(autoplay, removeAfterPlay, ato.getSource(), sourceRadius, ato.getSourceAnge()+(float)Math.PI, sourceAngleTolerantion, destination, endTriangle, keyFrameRatio, frames);
            }else{
                Animation ani = sa != null ? new ActiveClosureAnimation(autoplay, sa, removeAfterPlay, ato.getSource(), sourceRadius, ato.getSourceAnge()+(float)Math.PI, sourceAngleTolerantion, destination, endTriangle, keyFrameRatio, frames) : new ActiveAnimation(autoplay, removeAfterPlay, ato.getSource(), sourceRadius, ato.getSourceAnge()+(float)Math.PI, sourceAngleTolerantion, destination, endTriangle, keyFrameRatio, frames);
                OnOfInterface inter = (OnOfInterface) ani;
                if(!ato.isActive())inter.setActive(false);
                of.setCon(inter);
                return ani;
            }*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
      //  return null;
    }

}
