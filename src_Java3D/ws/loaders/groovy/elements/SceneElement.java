package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.*;

import java.util.Map;

public final class SceneElement extends AbstractFactory implements Doc{

    @Override
    public String docDescription() {
        return "<div>Root element</div>\n" +
               "<h4>node types</h4>\n" +
               "<ul>\n" +
               "    <li><b>node</b>: use node in actual context</li>\n" +
               "    <li><b>_node</b>: create node out of context</li>\n" +
               "</ul>\n" +
               "<div>see: <a href=\"http://github.com/shlax/Callie/blob/master/data/house.groovy\">house.groovy</a></div>";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "scene = BUILDER._scene(){\n" +
            "    // scene content\n" +
            "};"
        };
    }

    @Override
    public String docValue() {
        return "as: |backClipDistance|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "backClipDistance", "Float")
        };
    }

    @Override
    public DocAction[] docActions() {
        return null;
    }

    @Override
    public DocControl[] docControl() {
        return null;
    }

    @Override
    public final SceneObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        SceneObject o = new SceneObject(value, attributes);

        if(value != null){
            o.setBackFlipDistance( value instanceof Float ? (Float)value : Float.parseFloat(value.toString()));
        }

        if(attributes != null){
            /* Object tmp = attributes.get(SceneBuilder.lsSystem);
            if(tmp != null) o.setLsObject((LsObject)tmp); */

            Object tmp = attributes.get(SceneBuilder.backClipDistance);
            if(tmp != null) o.setBackFlipDistance( tmp instanceof Float ? (Float)tmp : Float.parseFloat(tmp.toString()));

            attributes.clear();
        }

        return o;
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode("*", "control", "[1]"),
            new DocSubNode(null, "ai", "[1]"),
            new DocSubNode(null, "map", "[0..N]", "this map is send to <a href=\"control.php\">control</a> and <a href=\"ai.php\">ai</a>"),

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

            new DocSubNode(null, "spotLight", "[0..N]"),
            new DocSubNode(null, "pointLight", "[0..N]"),
            new DocSubNode(null, "directionalLight", "[0..N]"),
            new DocSubNode(null, "ambientLight", "[0..N]"),
        };
    }


    @Override
    public final void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;
        
        if(parent instanceof SceneObject && child instanceof AiObject){
            SceneObject s = (SceneObject)parent;
            AiObject so = (AiObject)child;
            s.setAiObject(so);
        }else if(parent instanceof SceneObject && child instanceof ControlObject){
            SceneObject s = (SceneObject)parent;
            ControlObject so = (ControlObject)child;
            s.setControlObject(so);
        }else if(parent instanceof SceneObject && child instanceof MapObject){
            SceneObject s = (SceneObject)parent;
            MapObject so = (MapObject)child;
            s.addMapObject(so);
        }else if(parent instanceof SceneObject && child instanceof NodeObject){
            SceneObject s = (SceneObject)parent;
            NodeObject so = (NodeObject)child;
            if(so.getSceneObjectType() == SceneBuilder.SceneObjectType.NORMAL) s.addRoot(so);
            else if(so.getSceneObjectType() == SceneBuilder.SceneObjectType.EFFECT) s.addEffect(so);
            else if(so.getSceneObjectType() == SceneBuilder.SceneObjectType.CAMERA) s.addCameraColisions(so);
            else System.err.println("Undefined type "+so.getSceneObjectType());
        }else System.err.println(parent+" -> "+child);

    }
}
