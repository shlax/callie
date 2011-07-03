package ws.loaders.groovy.objects;

import javax.media.j3d.Transform3D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public abstract class PathInterpolatorObj extends Interpolator {

    protected PathInterpolatorObj(Object value, Map attributes) {
        super(value, attributes);
    }

    private ArrayList<KeyFrameObj> frames = new ArrayList<KeyFrameObj>();

    public ArrayList<KeyFrameObj> getFrames(){
        ArrayList<KeyFrameObj> tmp = frames;
        Collections.sort(tmp);
        frames = null;
        return tmp;
    }

    public void addKeyFrameObj(KeyFrameObj f){
        frames.add(f);
        //System.out.println("l");
    }

    /* public final float[] getKnots(){
        float knots[] = new float[frames.size()];
        for(int i = 0; i < knots.length; i++){

        }
    } */

    protected final Transform3D getAxis() {
        super.getAxis();
        if(transform3D == null) transform3D = new Transform3D();
        return transform3D;
    }

}
