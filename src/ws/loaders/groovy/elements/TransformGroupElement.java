package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.TransformGroupObject;
import ws.loaders.groovy.objects.TransformObject;

import javax.media.j3d.Transform3D;
import java.util.Map;

public class TransformGroupElement extends GroupElement implements Doc{

    @Override
    public TransformGroupObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TransformGroupObject o = new TransformGroupObject(value, attributes);

        processGroup(o, value, attributes);
        if(attributes != null)attributes.clear();

        return o;
    }

    protected void processGroup(TransformGroupObject o, Object value, Map attributes){
        this.setSceneObjectType(o, value, attributes);

        if(value instanceof TransformObject)o.setTransformObject( (TransformObject)value );
        else if(value instanceof Transform3D)o.setTransform3D((Transform3D) value);

        if(attributes != null){
            /* Object tmp = attributes.get(SceneBuilder.lsSystem);
            if(tmp != null) o.setTransform3D( ((LsObject)tmp).getUserEndPosition() ); */

            Object tmp = attributes.get(SceneBuilder.transform);
            if(tmp != null){
                if(tmp instanceof TransformObject)o.setTransformObject( (TransformObject)tmp );
                if(tmp instanceof Transform3D)o.setTransform3D((Transform3D) tmp);
            }
        }
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof TransformGroupObject && child instanceof TransformObject){
            TransformGroupObject g = (TransformGroupObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTransformObject(so);
        }else super.setChild(builder, parent, child);
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "transform", "[0..1]"),

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
    public String docDescription() {
        return null;
    }

    @Override
    public String[] docExamples() {
        return null;
    }

    @Override
    public String docValue() {
        return "as: |transform|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "transform", "transform")
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
}
