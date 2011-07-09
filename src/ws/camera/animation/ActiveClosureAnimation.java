package ws.camera.animation;

import ws.loaders.groovy.OnOfInterface;
import ws.map.Y25Triangle;
import ws.tools.SceneAction;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;

public final class ActiveClosureAnimation extends ClosureAnimation implements OnOfInterface{

    public ActiveClosureAnimation(boolean auto, SceneAction action, boolean removeAfterPlay, Point3f source, float sourceRadius, float sourceAnge, float sourceAngeTolerance, Point3f destination, Y25Triangle endTriangle, float keyFrameRatio, KeyFrame[] frames) {
        super(auto, action, removeAfterPlay, source, sourceRadius, sourceAnge, sourceAngeTolerance, destination, endTriangle, keyFrameRatio, frames);
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
