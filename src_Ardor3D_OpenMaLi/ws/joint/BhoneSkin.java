package ws.joint;

import com.ardor3d.scenegraph.Node;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;
import wa.Utils;
import ws.joint.acelerator.AcceleratedValue;

import javax.sound.sampled.Clip;

public final class BhoneSkin {

    private final Bhone bhone;
	private final Skin skin;
    
	public BhoneSkin(Bhone bhone, Skin s, float t1, Node tg, Clip sounds[], Vector3f[] offsets) {
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
		x.setTarget(v.getX());
		y.setTarget(v.getY());
		z.setTarget(v.getZ());

		this.bhone.setNewValues(index);
	}

    private final Node tg; // = new TransformGroupObject();
    private final Matrix4f tmp = new Matrix4f();
    private final Vector3f actual = new Vector3f();

	public final void update(float time, Matrix4f globalPos){
        actual.setX( x.getValue(time) );
        actual.setY( y.getValue(time) );
        actual.setZ( z.getValue(time) );

        this.tmp.set(this.actual);
        tmp.mul(globalPos, tmp);
        //this.tg.setTransform(this.tmp);
        this.tg.setMatrix(Utils.getMatrix3(this.tmp));

        //TODO delay one frame
        //this.skin.update();
        this.skin.updateData();

        tmp.setIdentity();
        bhone.update(tmp, tmp, time);

        //return actual;
	}
    
	public final void update(float time, Matrix4f globalPos, Point3f target, Point3f dst){
        actual.setX( x.getValue(time) );
        actual.setY( y.getValue(time) );
        actual.setZ( z.getValue(time) );

        this.tmp.set(this.actual);
        tmp.mul(globalPos, tmp);
        //this.tg.setTransform(this.tmp);
        this.tg.setMatrix( Utils.getMatrix3(this.tmp));
        synchronized (dst){
            tmp.transform(target, dst);
        }

        //TODO delay one frame
        //this.skin.update();
        this.skin.updateData();

        tmp.setIdentity();
        bhone.update(tmp, tmp, time);

        //return actual;
	}

}
