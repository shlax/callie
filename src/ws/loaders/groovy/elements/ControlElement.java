package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;
import ws.loaders.tools.joint.BhoneFrameLoader;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Collection;
import java.util.Map;

public final class ControlElement extends AbstractFactory implements Doc{
    private final BhoneFrameLoader bhoneFrameLoader;

    public ControlElement(BhoneFrameLoader bhoneFrameLoader) {
        this.bhoneFrameLoader = bhoneFrameLoader;
    }

    @Override
    public String docValue() {
        return "as: |startPosition|map|";
    }

    @Override
    public final ControlObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ControlObject c = new ControlObject(value, attributes, this.bhoneFrameLoader);


        Object tmp = attributes.get(SceneBuilder.startPosition);
        if(tmp != null){
            if(tmp instanceof Vector3f)c.setStartPosition( (Vector3f)tmp );
            else if(tmp instanceof Tuple3f)c.setStartPosition( new Vector3f((Tuple3f)tmp) );
            else if(tmp instanceof Point)c.setStartPosition( ((Point)tmp).getVector3f() );
            else if(tmp instanceof Vector)c.setStartPosition( ((Vector)tmp).getVector3f() );
        }

        tmp = attributes.get(SceneBuilder.map);
        if(tmp != null){
            if(tmp instanceof MapObject){
                c.addMapObject((MapObject)tmp);
            }else if(tmp instanceof Collection){
                for(Object o : (Collection)tmp)c.addMapObject( (MapObject)o );
            }
        }

        tmp = attributes.get(SceneBuilder.shotSource);
        if(tmp instanceof Point3f)c.setShotSource( (Point3f)tmp );
        else if(tmp instanceof Tuple3f)c.setShotSource( new Point3f((Tuple3f)tmp) );
        else if(tmp instanceof Point)c.setShotSource( ((Point)tmp).getPoint3f() );
        else if(tmp instanceof Vector)c.setShotSource( ((Vector)tmp).getPoint3f() );
        //c.setShotSource( (Point3f)attributes.get(SceneBuilder.shotSource) );

        tmp = attributes.get(SceneBuilder.disArmedTransform);
        if(tmp != null)c.setDisArmedTransform( ((TransformObject)tmp).getTransform3D() );

        tmp = attributes.get(SceneBuilder.armedTransform);
        if(tmp != null) c.setArmedTransform( ((TransformObject)tmp).getTransform3D() );

        tmp = attributes.get(SceneBuilder.map);
        if(tmp != null) c.addMapObject((MapObject)tmp);

        /* tmp = attributes.get(SceneBuilder.lsSystem);
        if(tmp != null) c.setLsObject((LsObject)tmp); */

        c.setTargetBhone((String)attributes.get(SceneBuilder.targetBhone));
        c.setDisArmedBhone((String)attributes.get(SceneBuilder.disArmedBhone));
        c.setArmedBhone((String)attributes.get(SceneBuilder.armedBhone));

        tmp = attributes.get(SceneBuilder.jumpSpeed);
        if(tmp != null)c.setJumpSpeed(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.runToJumpSpeed);
        if(tmp != null)c.setJumpSpeedRun(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.runSpeed);
        if(tmp != null)c.setRunSpeed(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.heightUp);
        if(tmp != null)c.setHeightUp(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.heightDown);
        if(tmp != null)c.setHeightDown(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.walkSpeed);
        if(tmp != null)c.setWalkSpeed(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.frameWindow);
        if(tmp != null)c.setFrameWindow(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.colisionRadius);
        if(tmp != null)c.setColisionRadius(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.acelerationRotate);
        if(tmp != null)c.setRotateAceleration(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.acelerationSpeed);
        if(tmp != null)c.setSpeedAceleration(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cameraMinDistance);
        if(tmp != null)c.setCameraMinDistance(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cameraMaxDistance);
        if(tmp != null)c.setCameraMaxDistance(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cameraMaxMinDistance);
        if(tmp != null)c.setCameraMaxMinDistance(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cameraHeight);
        if(tmp != null)c.setCameraHeight(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.cameraSide);
        if(tmp != null)c.setCameraSide(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.pickWeapon);
        if(tmp != null)c.setWeaponPick(tmp instanceof Boolean ? (Boolean)tmp : Boolean.parseBoolean(tmp.toString()));

        tmp = attributes.get(SceneBuilder.colision);
        if(tmp != null){
            if(tmp instanceof ColisionObject)c.addColisionObject( (ColisionObject)tmp );
            else if(tmp instanceof Collection) for(Object q : (Collection)tmp) c.addColisionObject((ColisionObject)q);
        }

        tmp = attributes.get(SceneBuilder.bhoneSkin);
        if(tmp != null){
            if(tmp instanceof BhoneSkinObject)c.setBhoneSkinObject( (BhoneSkinObject)tmp );
        }

        tmp = attributes.get(SceneBuilder.weapon);
        if(tmp != null){
            if(tmp instanceof WeaponObj)c.setItem( (WeaponObj)tmp );
        }

        tmp = attributes.get(SceneBuilder.shot);
        if(tmp != null){
            if(tmp instanceof ShotObject)c.setShotObject( (ShotObject)tmp );
        }

        tmp = attributes.get(SceneBuilder.map);
        if(tmp != null){
            if(tmp instanceof MapObject)c.addMapObject( (MapObject)tmp );
            else if(tmp instanceof Collection) for(Object q : (Collection)tmp) c.addMapObject((MapObject)q);
        }

        tmp = attributes.get(SceneBuilder.item);
        if(tmp != null){
            if(tmp instanceof AiItemObject)c.addAiItemObject( (AiItemObject)tmp );
            else if(tmp instanceof Collection) for(Object q : (Collection)tmp) c.addAiItemObject((AiItemObject)q);
        }

        tmp = attributes.get(SceneBuilder.animation);
        if(tmp != null){
            if(tmp instanceof AnimationTransformObject)c.addAnimationObject( (AnimationTransformObject)tmp );
            else if(tmp instanceof Collection) for(Object q : (Collection)tmp) c.addAnimationObject((AnimationTransformObject)q);
        }


        attributes.clear();
        return c;
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr("*", "startPosition", "point|vector"),
            new DocAttr("*", "shotSource", "point|vector"),

            new DocAttr("*", "targetBhone", "String"),
            new DocAttr("*", "armedBhone", "String"),
            new DocAttr("*", "armedTransform", "transform"),
            new DocAttr("*", "disArmedBhone", "String"),
            new DocAttr("*", "disArmedTransform", "transform"),
            new DocAttr("*", "disArmedTransform", "transform"),

            new DocAttr(null, "pickWeapon", "Boolean", "false", null),

            new DocAttr(null, "cameraSide", "Float", "0.25f", null),
            new DocAttr(null, "cameraHeight", "Float", "1.65f", null),
            new DocAttr(null, "cameraHeight", "Float", "1.65f", null),
            new DocAttr(null, "cameraMaxDistance", "Float", "5f", null),
            new DocAttr(null, "cameraMaxMinDistance", "Float", "2.5f", null),
            new DocAttr(null, "cameraMinDistance", "Float", "1f", null),

            new DocAttr(null, "acelerationSpeed", "Float", "1f", "//m/s2"),
            new DocAttr(null, "acelerationRotate", "Float", "10f", "//degrees/s"),

            new DocAttr(null, "walkSpeed", "Float", "1.5f", "//m/s"),
            new DocAttr(null, "runSpeed", "Float", "3f", "//m/s"),
            new DocAttr(null, "jumpSpeed", "Float", "2f", "//m/s"),
            new DocAttr(null, "runToJumpSpeed", "Float", "3.75f", "//m/s"),

            new DocAttr(null, "colisionRadius", "Float", "0.5f", "//meter"),
            new DocAttr(null, "frameWindow", "Float", "0.25f", "//sec"),

            new DocAttr(null, "heightDown", "Float", "0.8f"),
            new DocAttr(null, "heightUp", "Float", "1.4f"),

            new DocAttr("*1", "bhoneSkin", "bhoneSkin"),
            new DocAttr("*2", "weapon", "weapon"),
            new DocAttr("*3", "shot", "shot"),

            new DocAttr(null, "map", "map[]"),
            new DocAttr(null, "item", "item[]"),
            new DocAttr(null, "animation", "animationTransform[]"),
            new DocAttr(null, "colision", "colisionp[]"),
        };
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode("*1", "bhoneSkin", "[1]"),
            new DocSubNode("*2", "weapon", "[1]"),
            new DocSubNode("*3", "shot", "[1]"),
            new DocSubNode(null, "item", "[0..N]"),
            new DocSubNode(null, "animationTransform", "[0..N]"),
            new DocSubNode(null, "colision", "[0..N]"),
            new DocSubNode(null, "map", "[0..N]"),

            new DocSubNode(null, "point", "[0..1]", "as: |startPosition|"),
            new DocSubNode(null, "vector", "[0..1]", "as: |startPosition|"),

            new DocSubNode(null, "model", "[0..N]"),
            new DocSubNode(null, "link", "[0..N]"),
            new DocSubNode(null, "group", "[0..N]"),

            new DocSubNode(null, "onOff", "[0..N]"),

            new DocSubNode(null, "translation", "[0..N]"),
            new DocSubNode(null, "rotation", "[0..N]"),
            new DocSubNode(null, "scaling", "[0..N]"),
            new DocSubNode(null, "posPath", "[0..N]"),
            new DocSubNode(null, "posRotPath", "[0..N]"),
            new DocSubNode(null, "posRotScalePath", "[0..N]"),
            new DocSubNode(null, "TCBpath", "[0..N]"),
            new DocSubNode(null, "KBpath", "[0..N]"),
            new DocSubNode(null, "rotPath", "[0..N]"),

            new DocSubNode(null, "pointLight", "[0..N]"),
            new DocSubNode(null, "spotLight", "[0..N]"),
        };
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof ControlObject && child instanceof BhoneSkinObject){
            ControlObject g = (ControlObject)parent;
            BhoneSkinObject so = (BhoneSkinObject)child;
            g.setBhoneSkinObject(so);
        }else if(parent instanceof ControlObject && child instanceof MapObject){
            ControlObject g = (ControlObject)parent;
            MapObject so = (MapObject)child;
            g.addMapObject(so);
        }else if(parent instanceof ControlObject && child instanceof Point){
            ControlObject g = (ControlObject)parent;
            Point so = (Point)child;
            g.setStartPosition(so.getVector3f());
        }else if(parent instanceof ControlObject && child instanceof Vector){
            ControlObject g = (ControlObject)parent;
            Vector so = (Vector)child;
            g.setStartPosition(so.getVector3f());
        }else if(parent instanceof ControlObject && child instanceof ShotObject){
            ControlObject g = (ControlObject)parent;
            ShotObject so = (ShotObject)child;
            g.setShotObject(so);
        }else if(parent instanceof ControlObject && child instanceof ColisionObject){
            ControlObject g = (ControlObject)parent;
            ColisionObject so = (ColisionObject)child;
            g.addColisionObject(so);
        }else if(parent instanceof ControlObject && child instanceof AnimationTransformObject){
            ControlObject g = (ControlObject)parent;
            AnimationTransformObject so = (AnimationTransformObject)child;
            g.addAnimationObject(so);
        }else if(parent instanceof ControlObject && child instanceof WeaponObj){
            ControlObject g = (ControlObject)parent;
            WeaponObj so = (WeaponObj)child;
            g.setItem(so);
        }else if(parent instanceof AgentObject && child instanceof AiItemObject){
            AgentObject g = (AgentObject)parent;
            AiItemObject so = (AiItemObject)child;
            g.addAiItemObject(so);
        }else if(parent instanceof ControlObject && child instanceof TransformObject){
            ControlObject g = (ControlObject)parent;
            TransformObject c = (TransformObject)child;
            if(SceneBuilder.armedTransform.equals(c.getName()))g.setArmedTransform(c.getTransform3D());
            else if(SceneBuilder.disArmedTransform.equals(c.getName()))g.setDisArmedTransform(c.getTransform3D());
            else System.err.println(parent+" -> "+child+" : "+c.getName());
        }else if(parent instanceof ControlObject && child instanceof NodeObject){
            ControlObject g = (ControlObject)parent;
            NodeObject c = (NodeObject)child;
            g.addNodeObject(c);
        }else System.err.println(parent+" -> "+child);
    }



    @Override
    public String docDescription() {
        return "define player chcracter";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "control(startPosition: _point(x:-4.923f,y:4.003f,z:-0.399f),\n" +
            "        shotSource:_point(x:-0.033f,y: 1.301f,z: 0.588f),\n" +
            "        targetBhone:\"jnt3\", disArmedBhone:\"jnt30\", armedBhone:\"jnt24\",\n" +
            "        armedTransform:_transform(x:-0.07f, y:-0.01f, z:0.04f,\n" +
            "                                  rotX:75f, rotY:-13f, rotZ:-84f),\n" +
            "        disArmedTransform:_transform(x:-0.1f, y:-0.1f, z:-0.1f,\n" +
            "                                     rotX:103f, rotY:0f, rotZ:4f)){\n" +
            "    shot( appearance:shotMat, clip:\"data/amy/sounds/shot.wav\" )\n" +
            "    bhoneSkin(appearance:_appearance(texture:\"data/amy/amy.png\"),\n" +
            "              bhoneFile:\"data/amy/amy.bon\",\n" +
            "              skinFile:\"data/amy/amy.skn\" ){\n" +
            "        frame(\"data/amy/keys/stand.ang\", name:\"STAND\");\n" +
            "        // ...\n" +
            "    };\n" +
            "\n" +
            "    weapon(){\n" +
            "        model(file:\"data/amy/pistol/pistol.mod\",\n" +
            "              appearance:_appearance(texture:\"gun.png\"));\n" +
            "    };\n" +
            "};"
        };  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public DocAction[] docActions() {
        return null;
    }

    @Override
    public DocControl[] docControl() {
        return new DocControl[]{
            new DocControl("hit([Float])", "remove life //%"),
            new DocControl("kill()"),
            new DocControl("heal([Float])", "add life //%"),
            new DocControl("#[<a href=\"location.php\">location</a>] location()", "player location"),
        };
    }
}
