package ws.map;

public final class ActiveMapGroup{

    public ActiveMapGroup() {}

    public void setTriangles(DynamicY25Triangle[] triangles) {
        this.triangles = triangles;
    }

    private DynamicY25Triangle[] triangles;

    public final void on(){
        for(DynamicY25Triangle t : triangles) t.setEnable(true);
    }

    public final void off(){
        for(DynamicY25Triangle t : triangles) t.setEnable(false);
    }


}
