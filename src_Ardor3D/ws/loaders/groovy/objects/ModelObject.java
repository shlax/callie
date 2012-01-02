package ws.loaders.groovy.objects;

import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.MeshData;
import com.ardor3d.scenegraph.controller.SpatialController;
import wa.Appearance;
import ws.loaders.tools.GeometryLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public final class ModelObject extends NodeObject {
    private final GeometryLoader geometryLoader; // = new GeometryLoader();

    public ModelObject(Object value, Map attributes, GeometryLoader geometryLoader) {
        super(value, attributes);
        this.geometryLoader = geometryLoader;
    }


    private String file = null;

//    private boolean allowStripes = true;
//    private boolean orient = false;
    private Object userData = null;

    protected Appearance appearance = null;
    public void setAppearance(Appearance appearanceObject) {
        this.appearance = appearanceObject;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private ArrayList<InterpolatorObj> interpolators = null;
    public void setInterpolator(InterpolatorObj interpolator) {
        if(interpolators == null) interpolators = new ArrayList<InterpolatorObj>();
        this.interpolators.add(interpolator);
    }

/*    public void setStrip(boolean s){
        this.allowStripes = s;
    }

    public void setOrient(boolean s){
        this.allowStripes = s;
    } */

    public void setUserData(Object userData) {
        this.userData = userData;
    }

    @Override
    public final Mesh getNode() {
        try {
            MeshData ta = this.geometryLoader.getIndexedTriangleArray(this.file);
            /* if(this.allowStripes){
                GeometryInfo gi = new GeometryInfo(ta);
                new Stripifier().stripify(gi);
                ta = gi.getGeometryArray();
            } */
            // Appearance ap = appearanceObject.getAppearance();
            //Shape3D s = orient ? new OrientedShape3D(ta, appearance, OrientedShape3D.ROTATE_ABOUT_AXIS, new Vector3f(0.0f, 1.0f, 0.0f)) : new Shape3D(ta, appearance);
            Mesh s = appearance.getRenderState(ta);
            if(userData != null) s.setUserData(userData);

            if(interpolators != null) for(InterpolatorObj i : interpolators) for(SpatialController<?> c : i.getControler()){
                //System.out.println(c);
                // Create our controller

                s.addController(c);
            }





            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
