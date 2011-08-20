package ws.loaders.groovy.objects;

import com.sun.j3d.utils.behaviors.interpolators.RotPosScaleTCBSplinePathInterpolator;
import com.sun.j3d.utils.behaviors.interpolators.TCBKeyFrame;

import javax.media.j3d.TransformGroup;
import java.util.ArrayList;
import java.util.Map;

public final class IntTCBSplineObj extends PathInterpolatorObj{

    //private  interpolator;

    public IntTCBSplineObj(Object value, Map attributes) {
        super(value, attributes);
    }

    @Override
    public final TransformGroup getNode() {
        TransformGroup tg = super.getNode();

        ArrayList<KeyFrameObj> frames = this.getFrames();
        TCBKeyFrame[] positions = new TCBKeyFrame[frames.size()];

        for(int i = 0; i < positions.length; i++){
            KeyFrameObj f = frames.get(i);
            positions[i] = f.getTCBKeyFrame();
        }

        //frames = null;

        RotPosScaleTCBSplinePathInterpolator interpolator = new RotPosScaleTCBSplinePathInterpolator(getAlpha(), tg, getAxis(), positions );
        interpolator.setSchedulingBounds(bound);

        tg.addChild(interpolator);
        //System.out.println("a");
        return tg;
        //g.addChild(getTransformGroup());
    }

    private RotPosScaleTCBSplinePathInterpolator interpolator;

}
