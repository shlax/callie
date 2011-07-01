package ws.map;

public final class Y25GroupMap extends Y25Map{

    private final MapGroup[] groups;

    public Y25GroupMap(Y25Triangle[] triangles, Y25Triangle last, MapGroup[] groups) {
        super(triangles, last);
        this.groups = groups;
    }

    public Y25GroupMap(Y25Map map, MapGroup[] groups) {
        super(map);
        this.groups = groups;
    }

    public final void setLastY25Triangle(Y25Triangle tr){
        if(last != tr){
            for(MapGroup g : groups) g.check(tr);
            super.setLastY25Triangle(tr);
        }
    }
}
