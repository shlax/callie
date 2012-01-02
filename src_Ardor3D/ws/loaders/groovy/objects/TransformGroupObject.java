package ws.loaders.groovy.objects;

import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.controller.SpatialController;
import wa.Utils;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.Map;

public class TransformGroupObject extends GroupObject {

    public TransformGroupObject(Object value, Map attributes) {
        super(value, attributes);
    }

    protected TransformObject transformObject = null;
    public final void setTransformObject(TransformObject transformObject) {
        this.transformObject = transformObject;
    }

    protected Matrix4f transform3D = null;
    public final void setTransform3D(Matrix4f transformObject) {
        this.transform3D = transformObject;
    }

    private ArrayList<InterpolatorObj> interpolators = null;
    public void setInterpolator(InterpolatorObj interpolator) {
        if(interpolators == null) interpolators = new ArrayList<InterpolatorObj>();
        this.interpolators.add(interpolator);
    }

    private  AppearanceObject appearanceObject = null;
    public final void setAppearanceObject(AppearanceObject appearanceObject) {
        this.appearanceObject = appearanceObject;
    }

    @Override
    protected Node getGroup() {
        Node tg = new Node();
        if(this.transformObject != null) Utils.setMatrix3(this.transformObject.getTransform3D(), tg); //tg.setWorldTransform(Utils.getMatrix3()); //tg.setTransform(this.transformObject.getTransform3D());
        if(this.transform3D != null) Utils.setMatrix3(this.transform3D, tg); //tg.setWorldTransform(Utils.getMatrix3(this.transform3D)); //tg.setTransform(this.transform3D);
        if(appearanceObject != null)appearanceObject.getAppearance().getRenderState(tg);

        if(interpolators != null) for(InterpolatorObj i : interpolators) for(SpatialController<?> c : i.getControler())tg.addController(c);

        return tg;
    }

    /*public final TransformGroup getTransformGroup(){
        if(bg == null) bg = getGroup();
        return (TransformGroup)bg;
    }*/

}
