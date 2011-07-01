package ws.loaders.groovy.objects;

import ws.loaders.tools.map.LoadedTriangle;
import ws.loaders.tools.map.MapLoader;
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

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public final LoadedTriangle[] getTriangles() {
        if(tr == null){
            try {
                tr = mapLoader.getLoadedTriangle(file, t, dynamic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tr;
    }


}
