package ws.loaders.groovy.objects;

import com.sun.j3d.utils.behaviors.interpolators.KBKeyFrame;
import com.sun.j3d.utils.behaviors.interpolators.TCBKeyFrame;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

public final class KeyFrameObj implements Comparable<KeyFrameObj>{
    private static final Point3f oseScale = new Point3f(1f,1f,1f);

    private float knost = 0f;
    private Point3f point;
    private Quat4f rot;
    private float scale = 1f;
    private Point3f scalePont;

    private float bias = 0f;
    private float continuity = 0f;
    private float tension = 0f;
    private boolean linear = false;

    private float bank = 0f;
    private float heading = 0f;
    private float pitch = 0f;

    public final KBKeyFrame getKBKeyFrame(){
        return new KBKeyFrame(knost,  linear ? 1 : 0, point, heading, pitch, bank, scalePont == null ? oseScale : scalePont, tension, continuity, bias);
    }

    public final TCBKeyFrame getTCBKeyFrame(){
        return new TCBKeyFrame(knost, linear ? 1 : 0, point, rot, scalePont == null ? oseScale : scalePont, tension, continuity, bias );
    }

    public final void setPitch(float pitch) {
        this.pitch = -(float)((pitch * Math.PI)/180d);
    }

    public final void setHeading(float heading) {
        this.heading = -(float)((heading * Math.PI)/180d);
    }

    public final void setBank(float bank) {
        this.bank = -(float)((bank * Math.PI)/180d);
    }

    public final void setScalePont(Point3f scalePont) {
        this.scalePont = scalePont;
    }

    public final void setLinear(boolean linear) {
        this.linear = linear;
    }

    public final void setTension(float tension) {
        this.tension = tension;
    }

    public final void setContinuity(float continuity) {
        this.continuity = continuity;
    }

    public final void setBias(float bias) {
        this.bias = bias;
    }

    public final float getScale() {
        return scale;
    }

    public final void setScale(float scale) {
        this.scale = scale;
    }

    public final void setQuat4f(Quat4f q){
        this.rot = q;
    }

    public final Quat4f getQuat4f(){
        return rot;
    }

    public final void setKnost(float knost) {
        this.knost = knost;
    }

    public final void setPoint(Point3f point) {
        this.point = point;
    }

    public final float getKnots(){
        return knost;
    }

    public final Point3f getPoint3f(){
        return point;
    }

    @Override
    public final int compareTo(KeyFrameObj o) {
        return Float.compare(this.knost, o.knost);
    }
}
