package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AiItemObject;
import ws.loaders.groovy.objects.TransformObject;

import javax.media.j3d.Transform3D;
import java.util.Map;

public final class AiItemElement extends TransformGroupElement implements Doc{

    @Override
    public String docValue() {
        return "as: |bhone|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr("*", "bhone", "String", "name of joint where it attaches"),
            new DocAttr("*", "transform", "transform")
        };
    }

    @Override
    public final AiItemObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AiItemObject t = new AiItemObject(value, attributes);

        if(value !=null) t.setBhoneName(value instanceof String ? (String)value : value.toString());

        Object v = attributes.get(SceneBuilder.bhone);
        t.setBhoneName(v instanceof String ? (String)v : v.toString());

        v = attributes.get(SceneBuilder.transform);
        if(v != null){
            if(v instanceof TransformObject) t.setTransform( ((TransformObject)v).getTransform3D() );
            else if(v instanceof Transform3D) t.setTransform( (Transform3D)v ); 
        }

        attributes.clear();
        
        return t;
    }

    @Override
    public String docDescription() {
        return "object attached to joint //weapon, bag, item";
    }

    @Override
    public String[] docExamples() {
        return null;
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
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "transform", "[1]"),

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

        if(parent instanceof AiItemObject && child instanceof TransformObject){
            AiItemObject g = (AiItemObject)parent;
            TransformObject so = (TransformObject)child;
            g.setTransform(so.getTransform3D());
        }else super.setChild(builder, parent, child);

    }
}
