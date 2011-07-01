package ws.map;

public final class ActiveMapGroup{

    private final DynamicY25Triangle[] triangles;

    public ActiveMapGroup(DynamicY25Triangle[] triangles) {
        this.triangles = triangles;
    }

    public final void setEnabled(boolean b){
        for(DynamicY25Triangle t : triangles) t.setEnable(b);
    }
}
