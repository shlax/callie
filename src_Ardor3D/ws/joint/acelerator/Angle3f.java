package ws.joint.acelerator;

import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple3f;

public final class Angle3f extends Tuple3f{
	private static final long serialVersionUID = 1L;

	public Angle3f() {
		super();
	}

	public Angle3f(float x, float y, float z) {
		super(x, y, z);
	}

	public Angle3f(float[] t) {
		super(t);
	}

	public Angle3f(Tuple3d t1) {
		super(t1);
	}

	public Angle3f(Tuple3f t1) {
		super(t1);
	}

}
