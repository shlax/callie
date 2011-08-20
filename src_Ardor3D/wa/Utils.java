package wa;

import com.ardor3d.math.Matrix3;
import com.ardor3d.scenegraph.Spatial;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;

public final class Utils {

    public static void setMatrix3(Matrix4f m, Spatial s){
        Point3f p = new Point3f();
        Matrix3f rs = new Matrix3f();

        m.transform(p);
        m.getRotationScale(rs);

        s.setMatrix(new Matrix3(rs.m00, rs.m01, rs.m02, rs.m10, rs.m11, rs.m12, rs.m20, rs.m21, rs.m22));
        s.setTranslation(p.x, p.y, p.z);
    }

    /*
    public final Matrix4 glhPerspectivef2(float fovyInDegrees, float aspectRatio,
                      float znear, float zfar)
{
    float ymax, xmax;
    float temp, temp2, temp3, temp4;
    ymax = znear * (float)Math.tan(fovyInDegrees * Math.PI / 360.0);
    //ymin = -ymax;
    //xmin = -ymax * aspectRatio;
    xmax = ymax * aspectRatio;
    return glhFrustumf2(-xmax, xmax, -ymax, ymax, znear, zfar);
}

    public final Matrix4 glhFrustumf2(float left, float right, float bottom, float top,
                  float znear, float zfar)
{
    float temp, temp2, temp3, temp4;
    temp = 2.0f * znear;
    temp2 = right - left;
    temp3 = top - bottom;
    temp4 = zfar - znear;

    return

    matrix[0] = temp / temp2;
    matrix[1] = 0.0;
    matrix[2] = 0.0;
    matrix[3] = 0.0;
    matrix[4] = 0.0;
    matrix[5] = temp / temp3;
    matrix[6] = 0.0;
    matrix[7] = 0.0;
    matrix[8] = (right + left) / temp2;
    matrix[9] = (top + bottom) / temp3;
    matrix[10] = (-zfar - znear) / temp4;
    matrix[11] = -1.0;
    matrix[12] = 0.0;
    matrix[13] = 0.0;
    matrix[14] = (-temp * zfar) / temp4;
    matrix[15] = 0.0;
}
*/
}
