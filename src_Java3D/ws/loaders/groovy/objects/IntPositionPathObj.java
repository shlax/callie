package ws.loaders.groovy.objects;

import javax.media.j3d.PositionPathInterpolator;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import java.util.ArrayList;
import java.util.Map;

public final class IntPositionPathObj extends PathInterpolatorObj{

    //private  interpolator;

    public IntPositionPathObj(Object value, Map attributes) {
        super(value, attributes);
    }

    @Override
    public final TransformGroup getNode() {
        TransformGroup tg = super.getNode();

        ArrayList<KeyFrameObj> frames = this.getFrames();
        float[] knots = new float[frames.size()];
        Point3f[] positions = new Point3f[frames.size()];

        for(int i = 0; i < knots.length; i++){
            KeyFrameObj f = frames.get(i);
            knots[i] = f.getKnots();
            //System.out.println(knots[i]);
            positions[i] = f.getPoint3f();
        }

//        frames = null;

        PositionPathInterpolator interpolator = new PositionPathInterpolator(getAlpha(), tg, getAxis(), knots, positions );
        interpolator.setSchedulingBounds(bound);

        tg.addChild(interpolator);
        //System.out.println("a");
        return tg;
        //g.addChild(getTransformGroup());
    }
}
