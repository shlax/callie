package ws.camera.animation;

import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector3f;
import ws.map.Y25Triangle;
import ws.tools.SceneAction;

public class ClosureAnimation extends Animation{

    private final SceneAction action;

    public ClosureAnimation(boolean auto, SceneAction action, boolean removeAfterPlay, Point3f source, float sourceRadius, float sourceAnge, float sourceAngeTolerance, Point3f destination, Y25Triangle endTriangle, float keyFrameRatio, KeyFrame[] frames) {
        super(auto, removeAfterPlay, source, sourceRadius, sourceAnge, sourceAngeTolerance, destination, endTriangle, keyFrameRatio, frames);
        this.action = action;
    }

    @Override
    public final float getSourceAnge() {
        action.onEnter();
        return super.getSourceAnge();
    }

    @Override
    public final float animation(float time, Vector3f objectPosition) {
        float ret = super.animation(time, objectPosition);
        if( ret < 0f ) action.onExit();
        return ret;
    }
}
