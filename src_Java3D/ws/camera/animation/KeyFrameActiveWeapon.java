package ws.camera.animation;

import ws.camera.Character;
import ws.camera.UserCamera;
import ws.joint.AceleratedBhone;
import ws.joint.BhoneSkin;

import javax.media.j3d.Switch;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class KeyFrameActiveWeapon extends KeyFrame{

    public KeyFrameActiveWeapon(Character c, Switch sw,
                                BhoneSkin bhoneSkin, Vector3f offset, AceleratedBhone[] bhones, Tuple3f[] bhoneAngles) {
        super(bhoneSkin, offset, bhones, bhoneAngles);

        this.c = c;
        this.sw = sw;
    }

    private final Character c;
    private final Switch sw;

    @Override
    public final void updateBhoneSkyn() {
        UserCamera.equiptWeapon(true);
        c.setWeaponEnabled(true);
        sw.setWhichChild(Switch.CHILD_ALL);
        super.updateBhoneSkyn();
    }

}
