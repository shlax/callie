package ws.loaders.groovy.objects;

import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.extension.SwitchNode;
import ws.tools.controls.OnOff;

import java.util.Map;

public final class ObjSwitch extends GroupObject{

    public ObjSwitch(Object value, Map attributes) {
        super(value, attributes);
    }

    private boolean enabled = false;

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    @Override
    protected Node getGroup() {
        return new Node();
    }

    @Override
    public SwitchNode getNode() {
        Node n = super.getNode();
        sw.attachChild(n);

        if(enabled)sw.setAllVisible();
        else sw.setAllNonVisible();

        return sw;
    }

    private SwitchNode sw = new SwitchNode();
    private OnOff cnt = new OnOff(sw);
    public final OnOff control(){
        return cnt;
    }
}
