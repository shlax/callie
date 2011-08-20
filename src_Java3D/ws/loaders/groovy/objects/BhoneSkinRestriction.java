package ws.loaders.groovy.objects;

import ws.joint.acelerator.AcceleratedValue;

public final class BhoneSkinRestriction {
    public enum Axis{X, Y, Z}

    private AcceleratedValue value = null;
    public final AcceleratedValue getAcceleratedValue() {
        return value;
    }

    private Axis axis = null;
    public final Axis getAxis() {
        return axis;
    }

    private String name = null;
    public final String getName(){
        return name;
    }

    public final void setAcceleratedValue(AcceleratedValue value) {
        this.value = value;
    }

    public final void setAxis(Axis axis) {
        this.axis = axis;
    }

    public final void setName(String name) {
        this.name = name;
    }
}
