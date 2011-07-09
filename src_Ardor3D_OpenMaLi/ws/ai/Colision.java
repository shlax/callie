package ws.ai;

import org.openmali.vecmath2.Tuple3f;

public interface Colision {

    public boolean testColision(Tuple3f point, float radius);

    public void getPosition(Tuple3f p);
}
