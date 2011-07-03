package ws.loaders.groovy.objects;

import javax.media.j3d.SharedGroup;
import java.util.Map;

public final class SharedGroupObject extends GroupObject {

    public SharedGroupObject(Object value, Map attributes) {
        super(value, attributes);
        this.use();
    }

    @Override
    protected final SharedGroup getGroup() {
        return new SharedGroup();
    }

    private SharedGroup sharedGroup = null;

    @Override
    public SharedGroup getNode() {
        if(sharedGroup == null){
            sharedGroup = (SharedGroup)super.getNode();
            sharedGroup.compile();
        }
        return sharedGroup;
    }
}
