package ws.joint;

import ws.joint.acelerator.AcceleratedValue;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class TransformBhone extends AceleratedBhone{

	public TransformBhone(TransformGroup tg, Transform3D tgTrans,
                          Vector3f mov, Bhone[] sub, Point3f[] points, Point3f[] pointsSrc, Vector3f[] normals, Vector3f[] normalsSrc, Tuple3f[] angles, AcceleratedValue Rx, AcceleratedValue Ry, AcceleratedValue Rz) {
		super(mov, sub, points, pointsSrc, normals, normalsSrc, angles, Rx, Ry, Rz);
        if(tg == null) throw new IllegalArgumentException("tg is null");
        if(tgTrans == null) throw new IllegalArgumentException("tgTrans is null");

        this.tg = tg;
        this.tgTrans = tgTrans;
	}

	private final TransformGroup tg;
	private final Transform3D tgTrans;

	@Override
	public final void update(Transform3D tmp, Transform3D tmpRot, float time) {
		super.update(tmp, tmpRot, time);
        dstTrans.mul(tgTrans);
    	tg.setTransform(this.dstTrans); // hack
	}

}

