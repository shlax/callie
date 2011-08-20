package ws.camera.animation;

import ws.loaders.groovy.OnOfInterface;
import ws.map.Y25Triangle;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;


public final class ActiveAnimation extends Animation implements OnOfInterface {

    public ActiveAnimation(boolean autopay, boolean removeAfterPlay, Point3f source, float sourceRadius, float sourceAnge, float sourceAngeTolerance, Point3f destination, Y25Triangle endTriangle, float keyFrameRatio, KeyFrame[] frames) {
        super(autopay, removeAfterPlay, source, sourceRadius, sourceAnge, sourceAngeTolerance, destination, endTriangle, keyFrameRatio, frames);
    }

    private boolean enabled = true;
    @Override
    public final void setActive(boolean b) {
        enabled = b;
    }

    @Override
    public final boolean isActive(Tuple3f t, float objectY) {
        return enabled && super.isActive(t, objectY);
    }
}
