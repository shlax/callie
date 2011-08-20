package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.objects.GroupObject;
import ws.loaders.groovy.objects.NodeObject;

public abstract class GroupElement extends NodeElement {

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof GroupObject && child instanceof NodeObject){
            GroupObject g = (GroupObject)parent;
            NodeObject so = (NodeObject)child;
            //System.out.println(parent.getClass()+" "+child.getClass());
            g.addNodeObject(so);
        }else System.err.println(parent+" -> "+child);
    }
}
