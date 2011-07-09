package ws.camera.areas;

import org.openmali.vecmath2.Tuple3f;

public final class BoxColision implements Colision{

    private final float xMin;
    private final float xMax;

    private final float yMin;
    private final float yMax;

    private final float zMin;
    private final float zMax;

    public BoxColision(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    @Override
    public final boolean colide(Tuple3f pos) {
        return pos.getX() > xMin && pos.getX() < xMax && pos.getY() > yMin && pos.getY() < yMax && pos.getZ() > zMin && pos.getZ() < zMax;
    }
}
