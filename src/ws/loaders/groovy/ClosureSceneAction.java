package ws.loaders.groovy;

import groovy.lang.Closure;
import ws.tools.SceneAction;

public final class ClosureSceneAction implements SceneAction {

    private final Closure onEnter;
    private final Closure onExit;

    public ClosureSceneAction(Closure onEnter, Closure onExit) {
        this.onEnter = onEnter;
        this.onExit = onExit;
    }

    @Override
    public final void onEnter() {
        if (onEnter != null )onEnter.call();
    }

    @Override
    public final void onExit() {
        if(onExit != null)onExit.call();
    }
}
