package ws.loaders.groovy.objects;

import javax.media.j3d.Link;
import java.util.Map;

public final class LinkObject extends NodeObject {

    public LinkObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private SharedGroupObject sg;
    public final void setSharedGroup(SharedGroupObject sg) {
        this.sg = sg;
    }

    @Override
    public final Link getNode() {
        return new Link(sg.getNode());
    }
}
