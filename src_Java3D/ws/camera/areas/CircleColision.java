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
        float dx = stred.x-pos.x;
        float dy = stred.y-pos.y;
        float dz = stred.z-pos.z;
        return ( (dx*dx)+(dy*dy)+(dz*dz) < radius );
    }

}
