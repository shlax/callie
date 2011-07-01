package ws.tools;

import javax.media.j3d.Switch;

public final class OnOff {

    private final Switch sw;

    public OnOff(Switch sw) {
        this.sw = sw;
    }

    public final void on(){
        sw.setWhichChild(Switch.CHILD_ALL);
    }

    public final void off(){
        sw.setWhichChild(Switch.CHILD_NONE);
    }

}
