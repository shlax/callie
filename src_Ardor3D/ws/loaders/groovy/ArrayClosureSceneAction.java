package ws.loaders.groovy;

import groovy.lang.Closure;
import ws.tools.SceneAction;

public final class ArrayClosureSceneAction implements SceneAction {

    private final Closure onEnter[];
    private final Closure onExit[];

    public ArrayClosureSceneAction(Closure onEnter[], Closure onExit[]) {
        this.onEnter = onEnter;
        this.onExit = onExit;
    }

    @Override
    public final void onEnter() {
        if (onEnter != null ) for( Closure c : onEnter)c.call();
    }

    @Override
    public final void onExit() {
        if(onExit != null) for( Closure c : onExit)c.call();
    }

}
