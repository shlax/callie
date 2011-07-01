package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import java.util.Map;

public final class AnimationTransformObject extends FactoryElement {

    public AnimationTransformObject(Object value, Map attributes) {
        super(value, attributes);        
    }

    public AnimationTransformObject(AnimationTransformObject x, float angle, Transform3D td){
        super(null,null);
        this.animationObject = x.animationObject;

        //System.out.println(x.sourceAnge +" "+angle);
        this.sourceAnge = x.sourceAnge + angle;

        float PIf = (float)(Math.PI);

        if(this.sourceAnge > PIf) this.sourceAnge -= 2f*PIf;
        else if(this.sourceAnge < -PIf) this.sourceAnge += 2f*PIf;
                
        this.source = new Point3f(x.source);
        td.transform(this.source);
    }

    private AnimationObject animationObject = null;

    public final AnimationObject getAnimationObject() {
        return animationObject;
    }

    public final void setAnimationObject(AnimationObject animationObject) {
        this.animationObject = animationObject;
    }

    // float sourceAnge = (float)((Math.PI*aniTrans.getFloatAtribute("sourceAngle"))/180d);
    private Float sourceAnge = null;

    public final Float getSourceAnge() {
        //System.out.println(sourceAnge + " "+ ((float)((Math.PI*sourceAnge)/180d)) );
        return this.sourceAnge;
    }

    public final void setSourceAnge(Float sourceAnge) {
        this.sourceAnge = (float)((Math.PI*sourceAnge)/180d);
    }

    private Point3f source = null;

    public final Point3f getSource() {
        return source;
    }

    public final void setSource(Point3f source) {
        this.source = source;
    }
}
