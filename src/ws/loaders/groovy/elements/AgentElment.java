package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class AgentElment extends AbstractFactory implements Doc {

    @Override
    public String docDescription() {
        return "<div>character controled by ai</div>\n" +
               "<div style=\"text-align: center;\"><img src=\"img/agent.png\"/></div>";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "agent(shotSource: _point(x:-0.065f, y:1.322f, z:0.588f),\n" +
            "      lookAtSource: _point(x:-0.027f, y:1.66f, z:0.154f),\n" +
            "      startPosition:a.start){\n" +
            "    shot( appearance:shotMat,\n" +
            "          firePower:1f, cadence:500f,\n" +
            "          clip:\"data/soldier/sounds/shot.wav\" )\n" +
            "    bhoneSkin(appearance:soldierMat,\n" +
            "              bhoneFile:\"data/soldier/soldier.bon\",\n" +
            "              skinFile:\"data/soldier/soldier.skn\"){\n" +
            "        frame(\"data/soldier/keys/stand.ang\", name:\"STAND\");\n" +
            "        // ...\n" +
            "    }\n" +
            "    item(bhone:\"joint24\",\n" +
            "         transform:_transform(x:-0.144f, y:0.001f, z:0.03f,\n" +
            "                              rotX:5f, rotY:-1f, rotZ:-1f)){\n" +
            "            link(p90);\n" +
            "         };\n" +
            "    check(x:-5.138f, y:4.003f, z:2.594f);\n" +
            "};"
        };
    }

    @Override
    public String docValue() {
        return "as: |startPosition|";
    }

    @Override
    public final AgentObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AgentObject a = new AgentObject(value, attributes);

        if(value != null){
            if(value instanceof Vector3f) a.setStartPosition( (Vector3f)value );
            else if(value instanceof Tuple3f) a.setStartPosition( new Vector3f((Tuple3f)value) );
            else if(value instanceof Tuple) a.setStartPosition( ((Tuple) value).getVector3f() );
        }

        Object tmp = attributes.get(SceneBuilder.shotSource);
        if(tmp instanceof Point3f) a.setShotSource( (Point3f)tmp );
        else if(tmp instanceof Tuple3f) a.setShotSource( new Point3f((Tuple3f)tmp) );
        else if(tmp instanceof Tuple) a.setShotSource( ((Tuple) tmp).getPoint3f() );


        tmp = attributes.get(SceneBuilder.lookAtSource);
        if(tmp instanceof Point3f) a.setLookAtSource( (Point3f)tmp );
        else if(tmp instanceof Tuple3f) a.setLookAtSource( new Point3f((Tuple3f)tmp) );
        else if(tmp instanceof Tuple) a.setLookAtSource( ((Tuple) tmp).getPoint3f() );


        tmp = attributes.get(SceneBuilder.startPosition);
        if(tmp != null){
            if(tmp instanceof Vector3f) a.setStartPosition( (Vector3f)tmp );
            else if(tmp instanceof Tuple3f) a.setStartPosition( new Vector3f((Tuple3f)tmp) );
            else if(tmp instanceof Tuple) a.setStartPosition( ((Tuple) tmp).getVector3f() );
        }

        tmp = attributes.get(SceneBuilder.live);
        if(tmp != null) a.setLive(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.lookDistance);
        if(tmp != null) a.setLookDistance(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.lookAngleHorizontal);
        if(tmp != null) a.setLookAngleHorizontal(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.lookAngleVertical);
        if(tmp != null) a.setLookAngleVertical(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.shotDistance);
        if(tmp != null) a.setShotDistance(tmp instanceof Float ? (Float) tmp : Float.parseFloat(tmp.toString()));

        tmp = attributes.get(SceneBuilder.frameWindow);
        if(tmp != null) a.setFrameWindow(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.colisionRadius);
        if(tmp != null) a.setColisionRadius(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.startAngle);
        if(tmp != null) a.setStartAngle(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.targetHeight);
        if(tmp != null) a.setTargetHeight(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.targetRadius);
        if(tmp != null) a.setTargetRadius(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.acelerationSpeed);
        if(tmp != null) a.setSpeedAceleration(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.acelerationRotate);
        if(tmp != null) a.setRotateAceleration(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.rotateDelay);
        if(tmp != null) a.setRotateDelay(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.active);
        if(tmp != null) a.setEnabled(tmp instanceof Boolean ? (Boolean)tmp : Boolean.parseBoolean(tmp.toString()) );

        tmp = attributes.get(SceneBuilder.maxSpeed);
        if(tmp != null) a.setMaxSpeed(tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()) );

        attributes.clear();
        return a;
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr("*", "shotSource", "point|vector", "gun position"),
            new DocAttr("*", "lookAtSource", "point|vector", "position of eyes"),
            new DocAttr("*1", "startPosition", "point|vector", "start position"),
            new DocAttr(null, "startAngle", "Float", "//degrees"),
            new DocAttr(null, "live", "Float", "1f", "life"),
            new DocAttr(null, "lookDistance", "Float", "50f", "//meter"),
            new DocAttr(null, "shotDistance", "Float", "50f", "//meter"),
            new DocAttr(null, "lookAngleHorizontal", "Float", "57f", "//degrees"),
            new DocAttr(null, "lookAngleVertical", "Float", "57f", "//degrees"),
            new DocAttr(null, "frameWindow", "Float", "0.25f", "animation speed //second"),
            new DocAttr(null, "colisionRadius", "Float", "0.5f", "//meter"),
            new DocAttr(null, "targetHeight", "Float", "1.3f", "//meter"),
            new DocAttr(null, "targetRadius", "Float", "0.5f", "//meter"),
            new DocAttr(null, "acelerationSpeed", "Float", "1f", "//m/s2"),
            new DocAttr(null, "acelerationRotate", "Float", "10f", "//rad/s2"),
            new DocAttr(null, "rotateDelay", "Float", "5f", "//second"),
            new DocAttr(null, "maxSpeed", "Float", "2f", "//m/s"),
            new DocAttr(null, "active", "Boolean", "true"),
        };
    }

    @Override
    public DocAction[] docActions() {
        return null;
    }

    @Override
    public DocControl[] docControl() {
        return new DocControl[]{
            new DocControl("control()"),
            new DocControl("release()"),
            new DocControl("detected()"),
            new DocControl("navigate(#[<a href=\"location.php\">location</a>])"),
            new DocControl("#[<a href=\"location.php\">location</a>] location()"),
        };
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode("*", "bhoneSkin", "[1]"),
            new DocSubNode("*1", "point", "[1]", "as: |startPosition|"),
            new DocSubNode("*1", "vector", "[1]", "as: |startPosition|"),
            new DocSubNode("*", "shot", "[1]", "as: |startPosition|"),
            new DocSubNode(null, "item", "[0..N]", "add external geometry"),
            new DocSubNode(null, "check", "[0..N]", "add point to visiting path"),
        };
    }

    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof AgentObject && child instanceof BhoneSkinObject){
            AgentObject g = (AgentObject)parent;
            BhoneSkinObject so = (BhoneSkinObject)child;
            g.setBhoneSkinObject(so);
        }else if(parent instanceof AgentObject && child instanceof Point){
            AgentObject g = (AgentObject)parent;
            Point so = (Point)child;
            g.setStartPosition(so.getVector3f());
        }else if(parent instanceof AgentObject && child instanceof Vector){
            AgentObject g = (AgentObject)parent;
            Vector so = (Vector)child;
            g.setStartPosition(so.getVector3f());
        }else if(parent instanceof AgentObject && child instanceof ShotObject){
            AgentObject g = (AgentObject)parent;
            ShotObject so = (ShotObject)child;
            g.setShotObject(so);
        }else if(parent instanceof AgentObject && child instanceof AiItemObject){
            AgentObject g = (AgentObject)parent;
            AiItemObject so = (AiItemObject)child;
            g.addAiItemObject(so);
        }else if(parent instanceof AgentObject && child instanceof AiCheckObject){
            AgentObject g = (AgentObject)parent;
            AiCheckObject so = (AiCheckObject)child;
            g.addAiCheck(so);
        }else System.err.println(parent+" -> "+child);
    }
}
