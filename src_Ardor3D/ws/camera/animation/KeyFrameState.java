package ws.camera.animation;

import wa.Gui;
import ws.camera.UserCamera;
import ws.joint.AceleratedBhone;
import ws.joint.BhoneSkin;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class KeyFrameState extends KeyFrame {

    public KeyFrameState(int keyCode,
                         BhoneSkin bhoneSkin, Vector3f offset, AceleratedBhone[] bhones, Tuple3f[] bhoneAngles) {
        super(bhoneSkin, offset, bhones, bhoneAngles);

        this.keyCode = keyCode;
    }

    private final Integer keyCode;

    @Override
    public final void updateBhoneSkyn() {
        if(!Gui.keys.contains(this.keyCode)) UserCamera.wrongMove();
        super.updateBhoneSkyn();
    }
}
