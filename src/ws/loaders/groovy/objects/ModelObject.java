package ws.loaders.groovy.objects;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.Stripifier;
import ws.loaders.tools.GeometryLoader;

import javax.media.j3d.*;
import javax.vecmath.Vector3f;
import java.io.IOException;
import java.util.Map;

public final class ModelObject extends NodeObject {
    private final GeometryLoader geometryLoader; // = new GeometryLoader();

    public ModelObject(Object value, Map attributes, GeometryLoader geometryLoader) {
        super(value, attributes);
        this.geometryLoader = geometryLoader;
    }

    private Appearance appearance = null;
    private String file = null;

    private boolean allowStripes = true;
    private boolean orient = false;
    private Object userData = null;

    public void setAppearance(Appearance appearanceObject) {
        this.appearance = appearanceObject;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setStrip(boolean s){
        this.allowStripes = s;
    }

    public void setOrient(boolean s){
        this.allowStripes = s;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }

    @Override
    public final void getNode(Group g) {
        try {
            GeometryArray ta = this.geometryLoader.getIndexedTriangleArray(this.file);
            if(this.allowStripes){
                GeometryInfo gi = new GeometryInfo(ta);
                new Stripifier().stripify(gi);
                ta = gi.getGeometryArray();
            }
            // Appearance ap = appearanceObject.getAppearance();
            Shape3D s = orient ? new OrientedShape3D(ta, appearance, OrientedShape3D.ROTATE_ABOUT_AXIS, new Vector3f(0.0f, 1.0f, 0.0f)) : new Shape3D(ta, appearance);
            if(userData != null) s.setUserData(userData);
            g.addChild(s);
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
}
