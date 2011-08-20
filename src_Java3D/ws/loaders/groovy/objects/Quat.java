package ws.loaders.groovy.objects;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4f;
import java.util.Map;

public final class Quat extends Tuple{

    public Quat(float x, float y, float z, Object value, Map attributes) {
        super(x, y, z, value, attributes);
    }

    private final static Quat4f createQuaternionFromAxisAndAngle(Vector3d axis, double angle) {
        double sin_a = Math.sin(angle / 2);
        double cos_a = Math.cos(angle / 2);

        // use a vector so we can call normalize
        Vector4f q = new Vector4f();

        q.x = (float) (axis.x * sin_a);
        q.y = (float) (axis.y * sin_a);
        q.z = (float) (axis.z * sin_a);
        q.w = (float) cos_a;

        // It is necessary to normalise the quaternion
        // in case any values are very close to zero.
        q.normalize();

        // convert to a Quat4f and return
        return new Quat4f(q);
    }

    // convert three rotations about the Euler axes to a Quaternion
    public final static Quat4f createQuaternionFromEuler(double angleX, double angleY, double angleZ) {
        // simply call createQuaternionFromAxisAndAngle
        // for each axis and multiply the results
        Quat4f qx = createQuaternionFromAxisAndAngle(new Vector3d(1, 0, 0), (angleX*Math.PI) / 180d);
        Quat4f qy = createQuaternionFromAxisAndAngle(new Vector3d(0, 1, 0), (angleY*Math.PI) / 180d);
        Quat4f qz = createQuaternionFromAxisAndAngle(new Vector3d(0, 0, 1), (angleZ*Math.PI) / 180d);

        // qx = qx * qy
        qx.mul(qy);

        // qx = qx * qz
        qx.mul(qz);

        return qx;
    }

    public final Quat4f getQuat4f(){
        return createQuaternionFromEuler( x, y, z );
    }

}
