package ws.joint;

import com.ardor3d.scenegraph.Node;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;
import wa.Utils;
import ws.joint.acelerator.AcceleratedValue;


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
	public final void update(Matrix4f tmp, Matrix4f tmpRot, float time) {
		super.update(tmp, tmpRot, time);
        dstTrans.mul(tgTrans);
    	//tg.setTransform(this.dstTrans); // hack
        tg.setMatrix(Utils.getMatrix3(this.dstTrans));
	}

}

