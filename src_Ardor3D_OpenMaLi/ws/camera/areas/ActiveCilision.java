package ws.camera.areas;

import org.openmali.vecmath2.Tuple3f;
import ws.loaders.groovy.OnOfInterface;

public final class ActiveCilision implements Colision, OnOfInterface{

    private final Colision c;
    private boolean active = true;

    public ActiveCilision(Colision c) {
        this.c = c;
    }

    @Override
    public final void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public final boolean colide(Tuple3f pos) {
        return active && c.colide(pos);
    }
}
