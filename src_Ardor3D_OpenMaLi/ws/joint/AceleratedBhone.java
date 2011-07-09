package ws.joint;

import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;
import ws.joint.acelerator.AcceleratedValue;


public class AceleratedBhone implements Bhone{

    public AceleratedBhone(Vector3f mov, Bhone sub[], Point3f points[], Point3f pointsSrc[], Vector3f normals[], Vector3f normalsSrc[], Tuple3f angles[], AcceleratedValue Rx, AcceleratedValue Ry, AcceleratedValue Rz){
		trans.set(mov);

        this.Rx = Rx;
        this.Ry = Ry;
        this.Rz = Rz;

		this.sub = sub;

		this.points = points;
		this.pointsSrc = pointsSrc;

		this.normals = normals;
		this.normalsSrc = normalsSrc;

		this.angles = angles;
		// if(sub != null)	for(Bhone i : sub) if(i instanceof LinearBhone) ((LinearBhone)i).setParent(this);
	}

	private final Bhone sub[];

	private final Point3f points[];
	private final Point3f pointsSrc[];

	private final Vector3f normals[];
	private final Vector3f normalsSrc[];
/*
	public Bhone(float movX, float movY, float movZ, Bhone sub[], int index[], int normalIndex[]){
		this.index = index;
		this.normalIndex = normalIndex;
		this.sub = sub;

		trans.set(new Vector3f(movX, movY, movZ));
	}

	private Bhone sub[] = null;

	private Point3f points[] = null;
	private Point3f pointsSrc[] = null;

	private Vector3f normals[] = null;
	private Vector3f normalsSrc[] = null;

	private int index[];
	private int normalIndex[];

	public void bind(Transform3D tmp, Skin s){
		tmpTrans.mul(tmp, this.trans);

		Point3f p = new Point3f();
		tmpTrans.transform(p);

	//	System.out.println(p);

		points = new Point3f[index.length];
		pointsSrc = new Point3f[index.length];
		for(int i=0; i < index.length; i++){
			points[i] = s.getPoint(index[i]);
			pointsSrc[i] = new Point3f( points[i] );
			pointsSrc[i].sub(p);
		}

		normals = new Vector3f[normalIndex.length];
		normalsSrc = new Vector3f[normalIndex.length];
		for(int i=0; i < normalIndex.length; i++){
			normals[i] = s.getVector(normalIndex[i]);
			normalsSrc[i] = new Vector3f( normals[i] );
		}


		if(sub != null)	for(Bhone b : sub) b.bind(this.tmpTrans, s);
	}
	*/
	private final AcceleratedValue Rx;// = new AcceleratedValue();
	private final AcceleratedValue Ry;// = new AcceleratedValue();
	private final AcceleratedValue Rz;// = new AcceleratedValue();

	private final Tuple3f angles[];

    public final void replaceValue(int index, Tuple3f ang){
        this.angles[index] = ang;
    }

	public final void setNewValues(int index){
		Tuple3f ae = angles[index];
		Rx.setTarget(ae.getX());
		Ry.setTarget(ae.getY());
		Rz.setTarget(ae.getZ());
		if(sub != null) for(Bhone b : sub) b.setNewValues(index);
	}

	private final Matrix4f trans = new Matrix4f();

	final Matrix4f dstTrans = new Matrix4f(); // hack
	private final Matrix4f tmpRotate = new Matrix4f();

	/**
	 * time s intervalu <0,1>
	 */
	@Override
	public void update(Matrix4f tmp, Matrix4f tmpRot, float time){
        // local transformations
//		System.out.println("\t\t"+this.Rz.getValue(time)+" "+this.Ry.getValue(time)+" "+this.Rx.getValue(time));

		lastRotZ = this.Rz.getValue(time);
        //System.out.println(time+" "+lastRotZ);
		tmpRotate.rotZ(lastRotZ);
		//this.tmpRotate.mul(tmpUpdate);

		lastRotY = this.Ry.getValue(time);
        //System.out.println(lastRotY);
		dstTrans.rotY(lastRotY);
		this.tmpRotate.mul(dstTrans);

		lastRotX = this.Rx.getValue(time);
        //System.out.println(lastRotX);
		dstTrans.rotX(lastRotX);
		this.tmpRotate.mul(dstTrans);

		this.dstTrans.mul(tmp, this.trans);
        this.dstTrans.mul(tmpRotate);

		for(int i = 0; i < this.pointsSrc.length; i++)this.dstTrans.transform(pointsSrc[i], points[i]);

		tmpRotate.mul(tmpRot, tmpRotate);

		for(int i = 0; i < this.normalsSrc.length; i++)this.tmpRotate.transform(normalsSrc[i], normals[i]);
	//	for(int i = 0; i < this.normalsSrc.length; i++)normals[i].set(normalsSrc[i]);

		if(sub != null) for(Bhone b : sub) b.update(this.dstTrans, this.tmpRotate, time);

	}

	private float lastRotX = 0;
	private float lastRotY = 0;
	private float lastRotZ = 0;

	public final float getLastRotX() {
		return lastRotX;
	}

	public final float getLastRotY() {
		return lastRotY;
	}

	public final float getLastRotZ() {
		return lastRotZ;
	}


}
