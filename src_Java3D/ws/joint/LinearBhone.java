package ws.joint;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public final class LinearBhone implements Bhone{
    public enum Maping{X, Y, Z}
    
	public LinearBhone(AceleratedBhone parent, Bhone[] sub, Point3f[] points, Point3f[] pointsSrc, Vector3f[] normals, Vector3f[] normalsSrc, float interX, float interY, float interZ, Maping map[]) {
	//	this.trans.set(mov);
        this.parent = parent;

		this.sub = sub;

		this.points = points;
		this.pointsSrc = pointsSrc;

	//	for(Point3f x : this.pointsSrc) System.out.println(x);

		this.normals = normals;
		this.normalsSrc = normalsSrc;

		this.interX = interX;
		this.interY = interY;
		this.interZ = interZ;

        this.mapX = map[0];
        this.mapY = map[1];
        this.mapZ = map[2];
	}

//	private Transform3D trans = new Transform3D();

	private final Transform3D dstTrans = new Transform3D();
	private final Transform3D tmpRotate = new Transform3D();

	private final AceleratedBhone parent;

	private final float interX;
	private final float interY;
	private final float interZ;

    private final Maping mapX;
    private final Maping mapY;
    private final Maping mapZ;

    private final double getValue(Maping m){
        if(m == Maping.X) return this.parent.getLastRotX();
        else if(m == Maping.Y) return this.parent.getLastRotY();
        return this.parent.getLastRotZ();
    }

	@Override
	public final void update(Transform3D tmp, Transform3D tmpRot, float time) {
        boolean init = false;
		if(this.interZ != 0f){
            tmpRotate.rotZ(getValue(mapZ)/this.interZ);
            init = true;
        }
		
        if(this.interY != 0f){
            if(init){
                dstTrans.rotY(getValue(mapY)/this.interY);
                this.tmpRotate.mul(dstTrans);
            }else{
                tmpRotate.rotY(getValue(mapY)/this.interY);
                init = true;
            }
        }

        if(this.interX != 0f){
            if(init){
                dstTrans.rotX(getValue(mapX)/this.interX);
                this.tmpRotate.mul(dstTrans);
            }else{
                tmpRotate.rotX(getValue(mapX)/this.interX);
            }
        }

		this.dstTrans.mul(tmp, tmpRotate);
	//	this.dstTrans.mul(tmpRotate);

		for(int i = 0; i < this.pointsSrc.length; i++)this.dstTrans.transform(pointsSrc[i], points[i]);

		tmpRotate.mul(tmpRot, tmpRotate);

		for(int i = 0; i < this.normalsSrc.length; i++)this.tmpRotate.transform(normalsSrc[i], normals[i]);

		if(sub != null) for(Bhone b : sub) b.update(this.dstTrans, this.tmpRotate, time);
	}

	private final Bhone sub[];

	private final Point3f points[];
	private final Point3f pointsSrc[];

	private final Vector3f normals[];
	private final Vector3f normalsSrc[];

	@Override
	public final void setNewValues(int index) {
		if(sub != null) for(Bhone b : sub) b.setNewValues(index);
	}
	/*
	@Override
	public void setNewTargetValues(int index, BhoneInterface b){
		LinearBhone lb = (LinearBhone)b;
		if(sub != null && lb.sub != null) for(int i = 0; i < this.sub.length; i++) if(lb.sub[i] != null) this.sub[i].setNewTargetValues(index, lb.sub[i]);
	}
	*/
}
