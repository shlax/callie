package ws.loaders.groovy.objects;

import ws.tools.OnOff;

import javax.media.j3d.Group;
import javax.media.j3d.Switch;
import java.util.Map;

public class ObjSwitch extends GroupObject{

    public ObjSwitch(Object value, Map attributes) {
        super(value, attributes);
    }

    private boolean enabled = false;

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    protected Group getGroup() {
        sw.setCapability(Switch.ALLOW_SWITCH_WRITE);
        if(enabled)cnt.on();
        return sw;
    }

    private Switch sw = new Switch();
    private OnOff cnt = new OnOff(sw);

    public final OnOff control(){
        return cnt;
    }
}
