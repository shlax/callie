package ws.loaders.groovy.objects;

import javax.media.j3d.Group;
import java.util.ArrayList;
import java.util.Map;

public abstract class GroupObject extends NodeObject {

    protected GroupObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private ArrayList<NodeObject> nodes = new ArrayList<NodeObject>();
    public final void addNodeObject(NodeObject s){
        nodes.add(s);
        //if(bg == null) bg = getGroup();
        //s.getNode(bg);
        //bg.addChild();
    }

    protected abstract javax.media.j3d.Group getGroup();

    //protected javax.media.j3d.Group bg = null;

    private Group g = null;

    @Override
    public Group getNode(){
//        new Exception().printStackTrace();
        if(g == null){
            g = getGroup();
            for(NodeObject no : nodes)g.addChild(no.getNode());
            nodes = null;
        }

        return g;
        //g.addChild(bg);
    }

}
