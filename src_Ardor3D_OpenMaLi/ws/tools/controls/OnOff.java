package ws.tools.controls;

import com.ardor3d.scenegraph.extension.SwitchNode;

public final class OnOff {

    private final SwitchNode sw;

    public OnOff(SwitchNode sw) {
        this.sw = sw;
    }

    public final void on(){
        //sw.setWhichChild(Switch.CHILD_ALL);
        sw.setAllVisible();
    }

    public final void off(){
        //sw.setWhichChild(Switch.CHILD_NONE);
        sw.setAllNonVisible();
    }

}
