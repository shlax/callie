package ws.loaders.groovy.objects;

import com.sun.j3d.utils.behaviors.interpolators.KBKeyFrame;
import com.sun.j3d.utils.behaviors.interpolators.KBRotPosScaleSplinePathInterpolator;

import javax.media.j3d.TransformGroup;
import java.util.ArrayList;
import java.util.Map;

public final class IntKBSplineObj extends PathInterpolatorObj{

    //private  interpolator;

    public IntKBSplineObj(Object value, Map attributes) {
        super(value, attributes);
    }

    @Override
    public final TransformGroup getNode() {
        TransformGroup tg = super.getNode();

        ArrayList<KeyFrameObj> frames = this.getFrames();
        KBKeyFrame[] positions = new KBKeyFrame[frames.size()];

        for(int i = 0; i < positions.length; i++){
            KeyFrameObj f = frames.get(i);
            positions[i] = f.getKBKeyFrame();
        }

        //frames = null;

        KBRotPosScaleSplinePathInterpolator interpolator = new KBRotPosScaleSplinePathInterpolator(getAlpha(), tg, getAxis(), positions );
        interpolator.setSchedulingBounds(bound);

        tg.addChild(interpolator);
        //System.out.println("a");
        return tg;
        //g.addChild(getTransformGroup());
    }

}
