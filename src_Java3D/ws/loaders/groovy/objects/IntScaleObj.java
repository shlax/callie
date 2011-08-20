package ws.loaders.groovy.objects;

import javax.media.j3d.ScaleInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import java.util.Map;

public final class IntScaleObj extends SimpleInterpolator {


    public IntScaleObj(Object value, Map attributes) {
        super(value, attributes);
    }

    @Override
    public final TransformGroup getNode() {
        TransformGroup tg = super.getNode();

        ScaleInterpolator interpolator = new ScaleInterpolator(getAlpha(), tg );

        if(max != null) interpolator.setMaximumScale(max);
        if(min != null) interpolator.setMinimumScale(min);

        Transform3D axis = getAxis();
        if(axis != null)interpolator.setTransformAxis(axis);

        interpolator.setSchedulingBounds(bound);
        tg.addChild(interpolator);

        //System.out.println("a");
        return tg;
        //g.addChild(getTransformGroup());
    }
}
