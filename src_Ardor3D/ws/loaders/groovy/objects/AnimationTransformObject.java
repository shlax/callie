package ws.loaders.groovy.objects;

import groovy.lang.Closure;
import ws.loaders.groovy.ArrayClosureSceneAction;
import ws.loaders.groovy.ClosureSceneAction;
import ws.loaders.groovy.FactoryElement;
import ws.tools.SceneAction;
import ws.tools.controls.OnOffObject;

import javax.vecmath.Point3f;
import java.util.Map;

public final class AnimationTransformObject extends FactoryElement {

    public AnimationTransformObject(Object value, Map<?,?> attributes) {
        super(value, attributes);        
    }

    /*public AnimationTransformObject(AnimationTransformObject x, float angle, Matrix4f td){
        super(null,null);
        this.animationObject = x.animationObject;

        //System.out.println(x.sourceAnge +" "+angle);
        this.sourceAnge = x.sourceAnge + angle;

        float PIf = (float)(Math.PI);

        if(this.sourceAnge > PIf) this.sourceAnge -= 2f*PIf;
        else if(this.sourceAnge < -PIf) this.sourceAnge += 2f*PIf;
                
        this.source = new Point3f(x.source);
        td.transform(this.source);
    }*/



    private AnimationObject animationObject = null;

    public final AnimationObject getAnimationObject() {
        return animationObject;
    }

    public final void setAnimationObject(AnimationObject animationObject) {
        this.animationObject = animationObject;
    }

    // float sourceAnge = (float)((Math.PI*aniTrans.getFloatAtribute("sourceAngle"))/180d);
    private float sourceAnge = 0f;

    public final float getSourceAnge() {
        //System.out.println(sourceAnge + " "+ ((float)((Math.PI*sourceAnge)/180d)) );
        return this.sourceAnge;
    }

    private boolean removeAfterPlay = false;
    public final boolean isRemoveAfterPlay() {
        return removeAfterPlay;
    }

    public final void setRemoveAfterPlay(Boolean removeAfterPlay) {
        this.removeAfterPlay = removeAfterPlay;
    }

    private boolean autolay = false;
    public final boolean isAutolay() {
        return autolay;
    }
    public final void setAutolay(boolean autolay) {
        this.autolay = autolay;
    }

    public final void setSourceAnge(Float sourceAnge) {
        this.sourceAnge = (float)((Math.PI*sourceAnge)/180d);
    }

    private Point3f source = null;

    public final Point3f getSource() {
        return source == null ? new Point3f() : source;
    }

    public final void setSource(Point3f source) {
        this.source = source;
    }

    private Closure<?> onEnter = null;
    private Closure<?> onExit = null;

    public final void setOnEnter(Closure<?> onEnter) {
        this.onEnter = onEnter;
    }

    public final void setOnExit(Closure<?> onExit) {
        this.onExit = onExit;
    }

    private final Closure<?>[] getOnEnter() {
        Closure<?> pred = animationObject.getOnEnter();
        if(pred != null && onEnter != null) return new Closure[]{pred, onEnter};
        else if(pred != null) return new Closure[]{pred};
        else if(onEnter != null) return new Closure[]{onEnter};
        return null;
    }

    private final Closure<?>[] getOnExit() {
        Closure<?> pred = animationObject.getOnExit();
        if(pred != null && onExit != null) return new Closure[]{pred, onExit};
        else if(pred != null) return new Closure[]{pred};
        else if(onExit != null) return new Closure[]{onExit};
        return null;
    }

    public SceneAction getSceneAction(){
        Closure<?> ent[] = getOnEnter();
        Closure<?> exi[] = getOnExit();
        if(ent == null && exi == null) return null;
        boolean sim = true;
        if(ent != null && ent.length > 1) sim = false;
        if(exi != null && exi.length > 1) sim = false;
        if(!sim) return new ArrayClosureSceneAction(ent, exi);
        return new ClosureSceneAction(ent == null ? null : ent[0], exi == null ? null : exi[0] );
    }

    private boolean active = true;
    public final boolean isActive(){
        return active;
    }
    public final void setActive(boolean active){
        this.active = active;
    }

    private OnOffObject onOff = null;
    public final OnOffObject isOnOff(){
        return onOff;
    }

    public final OnOffObject control(){
        if(onOff == null) onOff = new OnOffObject();
        return onOff;
    }
}
