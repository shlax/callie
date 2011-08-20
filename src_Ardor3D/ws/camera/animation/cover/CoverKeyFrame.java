package ws.camera.animation.cover;

import ws.camera.animation.KeyFrame;
import ws.joint.AceleratedBhone;
import ws.joint.BhoneSkin;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class CoverKeyFrame extends KeyFrame{

    public CoverKeyFrame(BhoneSkin bhoneSkin, Vector3f offset, AceleratedBhone[] bhones, Tuple3f[] bhoneAngles, boolean isShot) {
        super(bhoneSkin, offset, bhones, bhoneAngles);

        this.shot = isShot;
    }

    private final boolean shot;

    public final boolean isShot() {
        return shot;
    }
}
