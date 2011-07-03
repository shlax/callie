package ws.loaders.groovy.objects;

import groovy.lang.Closure;
import ws.loaders.groovy.ClosureSceneAction;
import ws.loaders.tools.map.LoadedTriangle;
import ws.loaders.tools.map.MapLoader;
import ws.map.ActiveMapGroup;
import ws.map.DynamicY25Triangle;
import ws.map.Type;

import java.io.IOException;

public final class MapObject {
    private final MapLoader mapLoader;

    public MapObject(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }

    private Type t = Type.TERAIN;

    public final void setType(Type t) {
        this.t = t;
    }

    private String file = null;

    public final void setFile(String file) {
        this.file = file;
    }

    private LoadedTriangle[] tr = null;

    private boolean dynamic = false;
    private boolean active = true;

    public void setActive(boolean active) {
        this.active = active;
    }

    private Closure onEnter = null;
    private Closure onExit = null;

    public void setOnEnter(Closure onEnter) {
        this.onEnter = onEnter;
    }

    public void setOnExit(Closure onExit) {
        this.onExit = onExit;
    }

    public final LoadedTriangle[] getTriangles() {
        if(tr == null){
            try {
                //System.out.println("get");
                tr = mapLoader.getLoadedTriangle(file, t, dynamic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tr;
    }

    public final ClosureSceneAction getSceneAction(){
        if(onEnter == null && onExit == null) return null;
        return new ClosureSceneAction(onEnter, onExit);
    }

    private ActiveMapGroup activeMapGroup = null;
    public ActiveMapGroup control(){
        //System.out.println("control");
        dynamic = true;
        if (activeMapGroup == null) activeMapGroup = new ActiveMapGroup();
        return activeMapGroup;
    }

    /**
     * call affter build map
     */
    public void postProcess(){
        if(activeMapGroup != null){
            DynamicY25Triangle[] triangles = new DynamicY25Triangle[tr.length];
            for(int i = 0; i < tr.length; i++)triangles[i] = (DynamicY25Triangle)tr[i].getY25Triangle();
            activeMapGroup.setTriangles(triangles);
            if(!active) activeMapGroup.off();
        }
        tr = null;
        onEnter = null;
        onExit = null;
    }
}
