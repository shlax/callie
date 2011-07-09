package ws.map;

import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Tuple3f;

public final class DynamicY25Triangle extends Y25Triangle{

    public DynamicY25Triangle(Point3f a, Point3f b, Point3f c, Type typ, Y25Triangle n[], Y25Triangle f[]) {
        super(a, b, c, typ, n, f);
    }

    private boolean enable = true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public final boolean getY(Tuple3f p) {
        if(enable)return super.getY(p);
        else return false;
    }
}
