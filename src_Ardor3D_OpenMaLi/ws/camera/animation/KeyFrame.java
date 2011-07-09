package ws.camera.animation;

import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;
import ws.camera.AnimationCamera;
import ws.joint.AceleratedBhone;
import ws.joint.BhoneSkin;

public class KeyFrame {

    private final BhoneSkin bhoneSkin;
    private final Vector3f offset;

    private final AceleratedBhone[] bhones;
    private final Tuple3f[] bhoneAngles;

    public KeyFrame(BhoneSkin bhoneSkin, Vector3f offset, AceleratedBhone[] bhones, Tuple3f[] bhoneAngles) {
        this.bhoneSkin = bhoneSkin;
        this.offset = offset;
        this.bhones = bhones;
        this.bhoneAngles = bhoneAngles;
    }

    public void updateBhoneSkyn(){
        this.bhoneSkin.replaceValue(AnimationCamera.ANIMATION, this.offset);
        for(int i = 0; i < bhones.length; i++) bhones[i].replaceValue(AnimationCamera.ANIMATION, bhoneAngles[i]);
        this.bhoneSkin.setNewValues(AnimationCamera.ANIMATION);
    }
}
