package ws.tools.controls;

import ws.map.Y25Triangle;

public final class Location {
    private Y25Triangle tmp;
    public final Y25Triangle _getDestination(){
        return tmp;
    }
    public final void _setDestination(Y25Triangle t){
        this.tmp = t;
    }
    public Location() {}
    public Location(Y25Triangle tmp) {
        this.tmp = tmp;
    }

    public final float x(){
        return tmp.getCenter().x;
    }
    public final float y(){
        return tmp.getCenter().y;
    }
    public final float z(){
        return tmp.getCenter().z;
    }
}
