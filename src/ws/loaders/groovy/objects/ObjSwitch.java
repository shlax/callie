package ws.loaders.groovy.objects;

import javax.media.j3d.Switch;

public class ObjSwitch {

    Switch sw = new Switch();

    public ObjSwitch() {
    }

    public final Switch getSwitch(){
        return sw;
    }

    public final void on(){
        getSwitch().setWhichChild(Switch.CHILD_ALL);
    }

    public final void off(){
        getSwitch().setWhichChild(Switch.CHILD_NONE);
    }
}
