package ws.camera;

import ws.ai.Target;
import ws.joint.ActiveTransformBhone;
import ws.joint.BhoneSkin;
import ws.tools.Shot;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;

public final class Character {

    private final BhoneSkin bs;
    private final ActiveTransformBhone tbDisabled;
    private final ActiveTransformBhone tbEnabled;
    private final Point3f target;

   //TransformGroup tg;

    public Character(BhoneSkin bs, ActiveTransformBhone tbWeaponDisabled, ActiveTransformBhone tbWeaponEnabled, Shot shot, Point3f source, Point3f target/*, TransformGroup tg*/) {
        this.bs = bs;
        this.tbEnabled = tbWeaponEnabled;
        this.tbDisabled = tbWeaponDisabled;
        this.shot = shot;
        this.source = source;
        this.target = target;

        tbWeaponEnabled.setActive(false);
        tbWeaponDisabled.setActive(true);

      //  this.tg = tg;
    }

    private boolean weaponEnabled = false;

    public final void setWeaponEnabled(boolean e){
        //System.out.println(e);
        if(this.weaponEnabled == e)return;
        this.weaponEnabled = e;

        tbDisabled.setActive(!e);
        tbEnabled.setActive(e);
        if(!e) shot.unfire();
    }

    private final Shot shot;
    private final Point3f source;
    
    private final Point3f dst = new Point3f();
    //private final Transform3D tmp = new Transform3D();



    public final void getTarget(Tuple3f t) {
        synchronized (dst){
            t.set(dst);
            //dst.set(t);
        }
    }

/*    public final void getTargetPoint(Transform3D trans, Point3f destination){
        trans.transform(this.target, destination);
        //return this.target;
    }*/

    public final void process(float relativeTime, float time, Matrix4f trans, Target t){
//        System.out.println();
//        System.out.println(time);
//        System.out.println();
        //System.out.println(trans);
        this.bs.update(relativeTime, trans, target, dst);
        /* synchronized (dst){
            tmp.transform(target, dst);
            //System.out.println(dst);
        } */
        //System.out.println(target);
        /*{
            Transform3D pokus = new Transform3D();
            pokus.set(new Vector3f(dst));
            tg.setTransform(pokus);
        } */

        if(t != null){            
            trans.transform(source, dst);
            this.shot.fire(dst, t, time);
        }
        
        this.shot.processFire(time);
    }


    public final void process(float relativeTime, float time, Matrix4f trans, Target t, Point3f s){
//        System.out.println();
//        System.out.println(time);
//        System.out.println();
        //System.out.println(trans);
        this.bs.update(relativeTime, trans, target, dst);
        /* synchronized (dst){
            tmp.transform(target, dst);
            //System.out.println(dst);
        } */
        //System.out.println(target);
        /*{
            Transform3D pokus = new Transform3D();
            pokus.set(new Vector3f(dst));
            tg.setTransform(pokus);
        } */

        if(t != null){
           // trans.transform(source, dst);
            this.shot.fire(s, t, time);
        }

        this.shot.processFire(time);
    }

    public final void setNewPose(int pose){
        this.bs.setNewValues(pose);
    }

}
