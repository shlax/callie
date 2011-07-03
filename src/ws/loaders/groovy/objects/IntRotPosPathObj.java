package ws.loaders.groovy.objects;

import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import java.util.ArrayList;
import java.util.Map;

public final class IntRotPosPathObj extends PathInterpolatorObj{

    //private  interpolator;

    public IntRotPosPathObj(Object value, Map attributes) {
        super(value, attributes);
    }

    @Override
    public final TransformGroup getNode() {
        TransformGroup tg = super.getNode();

        ArrayList<KeyFrameObj> frames = this.getFrames();
        float[] knots = new float[frames.size()];
        Point3f[] positions = new Point3f[frames.size()];
        Quat4f[] rot = new Quat4f[frames.size()];

        for(int i = 0; i < knots.length; i++){
            KeyFrameObj f = frames.get(i);
            knots[i] = f.getKnots();
            positions[i] = f.getPoint3f();
            rot[i] = f.getQuat4f();
        }

        RotPosPathInterpolator interpolator = new RotPosPathInterpolator(getAlpha(), tg, getAxis(), knots, rot, positions );
        interpolator.setSchedulingBounds(bound);

        tg.addChild(interpolator);
        //System.out.println("a");
        return tg;
        //g.addChild(getTransformGroup());
    }
}
