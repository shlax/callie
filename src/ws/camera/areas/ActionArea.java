package ws.camera.areas;

import ws.SceneAction;

import javax.vecmath.Tuple3f;

public final class ActionArea {

    private final SceneAction sceneAction;
    private final Colision col;

    public ActionArea(SceneAction sceneAction, Colision col) {
        this.sceneAction = sceneAction;
        this.col = col;
    }

    private boolean oldVal = false;

    public final void check(Tuple3f t){
        boolean newVal = col.colide(t);
        if(oldVal != newVal){
            if(newVal) sceneAction.onEnter();
            else sceneAction.onExit();

            oldVal = newVal;
        }
    }
}
