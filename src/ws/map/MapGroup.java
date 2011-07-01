package ws.map;

import ws.SceneAction;

public final class MapGroup {

    private final Y25Triangle[] triangles;
    private final SceneAction act;

    public MapGroup(Y25Triangle[] triangles, SceneAction action) {
        this.triangles = triangles;
        this.act = action;
    }

    private boolean isIn = false;

    public void check(Y25Triangle t){
        boolean nIsIn = false;
        for(Y25Triangle tmp : triangles) if(t == tmp){
            nIsIn = true;
            break;
        }
        if(isIn != nIsIn){
            if(nIsIn) act.onEnter();
            else act.onExit();

            isIn = nIsIn;
        }
    }
}
