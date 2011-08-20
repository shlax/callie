package ws.loaders.groovy.objects;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import java.util.Map;

public class TransformGroupObject extends GroupObject {

    public TransformGroupObject(Object value, Map attributes) {
        super(value, attributes);
    }

    protected TransformObject transformObject = null;
    public final void setTransformObject(TransformObject transformObject) {
        this.transformObject = transformObject;
    }

    protected Transform3D transform3D = null;
    public final void setTransform3D(Transform3D transformObject) {
        this.transform3D = transformObject;
    }

    @Override
    protected TransformGroup getGroup() {
        TransformGroup tg = new TransformGroup();
        if(this.transformObject != null)tg.setTransform(this.transformObject.getTransform3D());
        if(this.transform3D != null)tg.setTransform(this.transform3D);
        return tg;
    }

    @Override
    public TransformGroup getNode() {
        return (TransformGroup)super.getNode();
    }

    /*public final TransformGroup getTransformGroup(){
        if(bg == null) bg = getGroup();
        return (TransformGroup)bg;
    }*/

}
