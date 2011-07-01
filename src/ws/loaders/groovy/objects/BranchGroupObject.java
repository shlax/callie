package ws.loaders.groovy.objects;

import javax.media.j3d.BranchGroup;
import java.util.Map;

public final class BranchGroupObject extends GroupObject {

    public BranchGroupObject(Object value, Map attributes) {
        super(value, attributes);
    }

    @Override
    protected final BranchGroup getGroup() {
        return new BranchGroup();
    }

}
