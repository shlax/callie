package ws.camera.areas;


import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;

public final class CircleColision implements Colision{

    private final Point3f stred;
    private final float radius;

    public CircleColision(Point3f p, float radius) {
        this.stred = p;
        this.radius = radius*radius;
    }

    public final boolean colide(Tuple3f pos){
        float dx = stred.getX()-pos.getX();
        float dy = stred.getY()-pos.getY();
        float dz = stred.getZ()-pos.getZ();
        return ( (dx*dx)+(dy*dy)+(dz*dz) < radius );
    }

}
