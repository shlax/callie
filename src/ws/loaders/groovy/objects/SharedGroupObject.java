package ws.loaders.groovy.objects;

import javax.media.j3d.SharedGroup;
import java.util.Map;

public final class SharedGroupObject extends GroupObject {

    public SharedGroupObject(Object value, Map attributes) {
        super(value, attributes);
    }

    @Override
    protected final SharedGroup getGroup() {
        return new SharedGroup();
    }

    public final SharedGroup getSharedGroup(){
        if(bg == null) bg = getGroup();
        return (SharedGroup)bg;
    }
}
