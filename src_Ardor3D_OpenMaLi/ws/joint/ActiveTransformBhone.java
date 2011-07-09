package ws.joint;

import com.ardor3d.scenegraph.Node;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;
import wa.Utils;
import ws.joint.acelerator.AcceleratedValue;

public final class ActiveTransformBhone extends AceleratedBhone{

    public ActiveTransformBhone(Node tg, Matrix4f tgTrans,
                                Vector3f mov, Bhone sub[], Point3f points[], Point3f pointsSrc[], Vector3f normals[], Vector3f normalsSrc[], Tuple3f angles[], AcceleratedValue Rx, AcceleratedValue Ry, AcceleratedValue Rz) {
        super(mov, sub, points, pointsSrc, normals, normalsSrc, angles, Rx, Ry, Rz);

        this.tg = tg;
        this.tgTrans = tgTrans;
    }

    private final Node tg;
	private final Matrix4f tgTrans;

	private boolean active = true;

	public final void setActive(boolean a){
    //    System.out.println("active "+a);
        this.active = a;
	}
    
	@Override
	public final void update(Matrix4f tmp, Matrix4f tmpRot, float time) {
		super.update(tmp, tmpRot, time);
        if(active){
            dstTrans.mul(tgTrans);
            //tg.setTransform(this.dstTrans); // hack
            tg.setMatrix(Utils.getMatrix3(this.dstTrans));
		}
	}

	/* public final Transform3D transform(){
		return dstTrans;
	} */
}
