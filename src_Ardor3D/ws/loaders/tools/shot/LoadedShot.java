package ws.loaders.tools.shot;

import com.ardor3d.scenegraph.Node;
import wa.Appearance;
import ws.tools.Shot;

import javax.sound.sampled.Clip;

public final class LoadedShot {

    private Clip shot = null;

    private final float kadencia;
    private final float hit;
    private final float minDistance;
    private final float maxDistance;
    private final float timeVisible;
    private final float radius;
    private final Appearance ap;

    public LoadedShot(float kadencia, float hit, float minDistance, float maxDistance, float timeVisible, float radius, Appearance ap) {
        this.kadencia = kadencia;
        this.hit = hit;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.timeVisible = timeVisible;
        this.radius = radius;
        this.ap = ap;
    }

    public final void setShot(Clip shot) {
        this.shot = shot;
    }

    public final Shot getShot(Node gr){
        return new Shot(shot, kadencia, hit, minDistance, maxDistance, timeVisible, radius, ap, gr);
    }
}
