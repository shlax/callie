package ws.joint;

import ws.joint.acelerator.AcceleratedValue;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class TargetBhone extends AceleratedBhone{

	public TargetBhone(Point3f tg,
                          Vector3f mov, Bhone[] sub, Point3f[] points, Point3f[] pointsSrc, Vector3f[] normals, Vector3f[] normalsSrc, Tuple3f[] angles, AcceleratedValue Rx, AcceleratedValue Ry, AcceleratedValue Rz) {
		super(mov, sub, points, pointsSrc, normals, normalsSrc, angles, Rx, Ry, Rz);
        this.tg = tg;
	}

	private final Point3f tg;
    private static final Point3f source = new Point3f();

	@Override
	public final boolean update(Matrix4f tmp, Matrix4f tmpRot, float time, boolean pred) {
		boolean ret = super.update(tmp, tmpRot, time, pred);
        //dstTrans.mul(tgTrans);
        dstTrans.transform(source, tg);
        //System.out.println(tg);
//    	tg.setTransform(this.dstTrans); // hack

        return ret;
	}
}
