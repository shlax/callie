package ws.joint;

import com.ardor3d.scenegraph.Node;
import wa.Utils;
import ws.joint.acelerator.AcceleratedValue;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class TransformBhone extends AceleratedBhone{

	public TransformBhone(Node tg, Matrix4f tgTrans,
                          Vector3f mov, Bhone[] sub, Point3f[] points, Point3f[] pointsSrc, Vector3f[] normals, Vector3f[] normalsSrc, Tuple3f[] angles, AcceleratedValue Rx, AcceleratedValue Ry, AcceleratedValue Rz) {
		super(mov, sub, points, pointsSrc, normals, normalsSrc, angles, Rx, Ry, Rz);
        if(tg == null) throw new IllegalArgumentException("tg is null");
        if(tgTrans == null) throw new IllegalArgumentException("tgTrans is null");

        this.tg = tg;
        this.tgTrans = tgTrans;
	}

	private final Node tg;
	private final Matrix4f tgTrans;

	@Override
	public final boolean update(Matrix4f tmp, Matrix4f tmpRot, float time, boolean pred) {
		boolean  ret = super.update(tmp, tmpRot, time, pred);
        dstTrans.mul(tgTrans);
    	//tg.setTransform(this.dstTrans); // hack
        Utils.setMatrix3(this.dstTrans, tg);

        return ret;
	}

}

