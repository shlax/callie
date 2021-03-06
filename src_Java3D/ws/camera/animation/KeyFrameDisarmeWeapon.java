package ws.camera.animation;

import ws.camera.Character;
import ws.joint.AceleratedBhone;
import ws.joint.BhoneSkin;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class KeyFrameDisarmeWeapon extends KeyFrame{

    public KeyFrameDisarmeWeapon(Character c,
                                 BhoneSkin bhoneSkin, Vector3f offset, AceleratedBhone[] bhones, Tuple3f[] bhoneAngles) {
        super(bhoneSkin, offset, bhones, bhoneAngles);
        this.c = c;
    }

    private final Character c;

    @Override
    public final void updateBhoneSkyn() {
        c.setWeaponEnabled(false);
        super.updateBhoneSkyn();
    }

}
