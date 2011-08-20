package ws.loaders.groovy.objects;

import javax.media.j3d.PositionInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import java.util.Map;

public final class IntPositionObj extends SimpleInterpolator {

    public IntPositionObj(Object value, Map attributes) {
        super(value, attributes);
    }

    public final TransformGroup getNode() {
        TransformGroup tg = super.getNode();
        PositionInterpolator interpolator = new PositionInterpolator(getAlpha(), tg );

        if(min != null) interpolator.setStartPosition(min);
        if(max != null) interpolator.setEndPosition(max);

        Transform3D axis = getAxis();
        if(axis != null)interpolator.setTransformAxis(axis);

        interpolator.setSchedulingBounds(bound);
        tg.addChild(interpolator);

        //System.out.println("a");

        //g.addChild(getTransformGroup());
        return tg;
    }
}
