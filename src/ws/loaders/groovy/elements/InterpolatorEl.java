package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.Interpolator;
import ws.loaders.groovy.objects.TimerObj;
import ws.loaders.groovy.objects.TransformObject;

import javax.media.j3d.Alpha;
import javax.media.j3d.Transform3D;
import java.util.Map;

public abstract class InterpolatorEl extends TransformGroupElement {

    protected final void processInt(Interpolator o, Object value, Map<?, ?> attributes){
        setSceneObjectType(o, value, attributes);

        if(value instanceof TransformObject)o.setTransformObject( (TransformObject)value );
        else if(value instanceof Transform3D)o.setTransform3D((Transform3D) value);
        else if(value instanceof TimerObj) o.setAlpha( ((TimerObj)value).getAlpha() );
        else if(value instanceof Alpha) o.setAlpha( (Alpha)value );

        if(attributes != null){
            /* Object tmp = attributes.get(SceneBuilder.lsSystem);
            if(tmp != null) o.setTransform3D( ((LsObject)tmp).getUserEndPosition() ); */

            Object tmp = attributes.get(SceneBuilder.transform);
            if(tmp != null){
                if(tmp instanceof TransformObject)o.setTransformObject( (TransformObject)tmp );
                if(tmp instanceof Transform3D)o.setTransform3D((Transform3D) tmp);
            }

            tmp = attributes.get(SceneBuilder.time);
            //System.out.println(tmp);
            if(tmp != null){
                if(tmp instanceof TimerObj)o.setAlpha(((TimerObj) tmp).getAlpha());
                if(tmp instanceof Alpha)o.setAlpha((Alpha) tmp);
            }

            attributes.clear();
        }



       // processGroup(inter, value, attributes);
        //super.setSceneObjectType(inter, value, attributes);
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof Interpolator && child instanceof TransformObject){
            Interpolator g = (Interpolator)parent;
            TransformObject so = (TransformObject)child;
            g.setTransformObject(so);
        }else if(parent instanceof Interpolator && child instanceof TimerObj){
            Interpolator g = (Interpolator)parent;
            TimerObj so = (TimerObj)child;
            g.setAlpha(so.getAlpha());
        /*}else if(parent instanceof Interpolator && child instanceof NodeObject){
            Interpolator g = (Interpolator)parent;
            NodeObject so = (NodeObject)child;
            //System.out.println(parent.getClass()+" "+child.getClass());
            g.addNodeObject(so); */
        }else super.setChild(builder, parent, child);
    }

}
