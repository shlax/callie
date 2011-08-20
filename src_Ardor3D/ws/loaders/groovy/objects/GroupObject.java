package ws.loaders.groovy.objects;

import com.ardor3d.scenegraph.Node;

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

    protected abstract Node getGroup();

    //protected javax.media.j3d.Group bg = null;

    private Node g = null;

    @Override
    public Node getNode(){
//        new Exception().printStackTrace();
        if(g == null){
            g = getGroup();
            for(NodeObject no : nodes)g.attachChild(no.getNode());
            nodes = null;
        }

        return g;
        //g.addChild(bg);
    }

}
