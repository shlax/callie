package ws.joint;

import ws.joint.acelerator.AcceleratedValue;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class ActiveTransformBhone extends AceleratedBhone{

    public ActiveTransformBhone(TransformGroup tg, Transform3D tgTrans,
                                Vector3f mov, Bhone sub[], Point3f points[], Point3f pointsSrc[], Vector3f normals[], Vector3f normalsSrc[], Tuple3f angles[], AcceleratedValue Rx, AcceleratedValue Ry, AcceleratedValue Rz) {
        super(mov, sub, points, pointsSrc, normals, normalsSrc, angles, Rx, Ry, Rz);

        this.tg = tg;
        this.tgTrans = tgTrans;
    }

    private final TransformGroup tg;
	private final Transform3D tgTrans;

	private boolean active = true;

	public final void setActive(boolean a){
    //    System.out.println("active "+a);
        this.active = a;
	}
    
	@Override
	public final void update(Transform3D tmp, Transform3D tmpRot, float time) {
		super.update(tmp, tmpRot, time);
        if(active){
            dstTrans.mul(tgTrans);
			tg.setTransform(this.dstTrans); // hack
		}
	}

	/* public final Transform3D transform(){
		return dstTrans;
	} */
}
