package ws.loaders.groovy.objects;

import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import java.util.Map;

public final class IntRotationObj extends SimpleInterpolator {


    public IntRotationObj(Object value, Map attributes) {
        super(value, attributes);
    }

    @Override
    public final TransformGroup getNode() {
        TransformGroup tg = super.getNode();

        RotationInterpolator interpolator = new RotationInterpolator(getAlpha(), tg );

        if(max != null) interpolator.setMaximumAngle( (float)((Math.PI * max) / 180d) );
        if(min != null) interpolator.setMinimumAngle( (float)((Math.PI * min) / 180d) );

        Transform3D axis = getAxis();
        if(axis != null)interpolator.setTransformAxis(axis);

        interpolator.setSchedulingBounds(bound);
        tg.addChild(interpolator);

        //System.out.println("a");

        //g.addChild(getTransformGroup());
        return tg;
    }
}
