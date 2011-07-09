package wa;

import com.ardor3d.math.Matrix3;
import org.openmali.vecmath2.Matrix3f;
import org.openmali.vecmath2.Matrix4f;

public class Utils {

    public static Matrix3 getMatrix3(Matrix4f m){
        return new Matrix3(m.m00(), m.m01(), m.m02(), m.m10(), m.m11(), m.m12(), m.m20(), m.m21(), m.m22());
    }

}
