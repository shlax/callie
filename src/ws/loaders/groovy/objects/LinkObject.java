package ws.loaders.groovy.objects;

import javax.media.j3d.Group;
import javax.media.j3d.Link;
import javax.media.j3d.SharedGroup;
import java.util.Map;

public final class LinkObject extends NodeObject {

    public LinkObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private SharedGroup sg;
    public final void setSharedGroup(SharedGroup sg) {
        this.sg = sg;
    }

    @Override
    public final void getNode(Group g) {
        g.addChild(new Link(sg));
    }
}
