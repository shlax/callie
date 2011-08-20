package ws.joint;

import ws.joint.acelerator.AcceleratedValue;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.sound.sampled.Clip;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public final class BhoneSkin {

    private final Bhone bhone;
	private final Skin skin;
    
	public BhoneSkin(Bhone bhone, Skin s, float t1, TransformGroup tg, Clip sounds[], Vector3f[] offsets) {
		this.bhone = bhone;
		this.skin = s;
        this.sounds = sounds;
        this.offsets = offsets;

        x = new AcceleratedValue(t1);
        y = new AcceleratedValue(t1);
        z = new AcceleratedValue(t1);

        this.tg = tg; //tg.setCapability(TransformGroupObject.ALLOW_TRANSFORM_WRITE);
	}
    
	private final Tuple3f offsets[];
    private final Clip sounds[];

	private final AcceleratedValue x;// = new AcceleratedValue();
	private final AcceleratedValue y;// = new AcceleratedValue();
	private final AcceleratedValue z;// = new AcceleratedValue();

    public final void replaceValue(int index, Tuple3f t){
        this.offsets[index] = t;
    }

	public final void setNewValues(int index){
    	if(sounds != null && sounds[index] != null){
            sounds[index].setFramePosition(0);
			sounds[index].start();
		}

		Tuple3f v = this.offsets[index];
		x.setTarget(v.x);
		y.setTarget(v.y);
		z.setTarget(v.z);

		this.bhone.setNewValues(index);
	}

    private final TransformGroup tg; // = new TransformGroupObject();
    private final Transform3D tmp = new Transform3D();
    private final Vector3f actual = new Vector3f();

	public final void update(float time, Transform3D globalPos){
        actual.x = x.getValue(time);
        actual.y = y.getValue(time);
        actual.z = z.getValue(time);

        this.tmp.set(this.actual);
        tmp.mul(globalPos, tmp);
        this.tg.setTransform(this.tmp);

        //TODO delay one frame
        this.skin.update();

        tmp.setIdentity();
        bhone.update(tmp, tmp, time);

        //return actual;
	}
    
	public final void update(float time, Transform3D globalPos, Point3f target, Point3f dst){
        actual.x = x.getValue(time);
        actual.y = y.getValue(time);
        actual.z = z.getValue(time);

        this.tmp.set(this.actual);
        tmp.mul(globalPos, tmp);
        this.tg.setTransform(this.tmp);
        synchronized (dst){
            tmp.transform(target, dst);
        }

        //TODO delay one frame
        this.skin.update();

        tmp.setIdentity();
        bhone.update(tmp, tmp, time);

        //return actual;
	}

}
