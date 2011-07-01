package ws.loaders.groovy.objects;

import javax.media.j3d.Group;
import java.util.Map;

public abstract class GroupObject extends NodeObject {

    protected GroupObject(Object value, Map attributes) {
        super(value, attributes);
    }

    public final void addNodeObject(NodeObject s){
        if(bg == null) bg = getGroup();
        s.getNode(bg);
        //bg.addChild();
    }

    protected abstract javax.media.j3d.Group getGroup();

    protected javax.media.j3d.Group bg = null;

    @Override
    public void getNode(Group g){
        if(bg == null) bg = getGroup();                    
        g.addChild(bg);
    }

}
