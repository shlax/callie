package ws.camera.areas;

import org.openmali.vecmath2.Tuple3f;
import ws.tools.SceneAction;

public final class ActionArea implements Colision{

    private final SceneAction sceneAction;
    private final Colision col;

    public ActionArea(SceneAction sceneAction, Colision col) {
        this.sceneAction = sceneAction;
        this.col = col;
    }

    private boolean oldVal = false;

    @Override
    public boolean colide(Tuple3f pos) {
        boolean newVal = col.colide(pos);
        if(oldVal != newVal){
            if(newVal) sceneAction.onEnter();
            else sceneAction.onExit();

            oldVal = newVal;
        }

        return false;
    }
}
