package ws.loaders.tools.joint;

import ws.joint.Bhone;
import ws.joint.BhoneSkin;
import ws.joint.Skin;

import javax.media.j3d.Appearance;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.sound.sampled.Clip;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

public final class LoadedBhoneSkin {

    private final LoadedBhone loadedBhone;
    private final LoadedSkin loadedSkin;
    private final Appearance appearance;

    public LoadedBhoneSkin(LoadedBhone loadedBhone, LoadedSkin loadedSkin, Appearance appearance) {
        this.loadedBhone = loadedBhone;
        this.loadedSkin = loadedSkin;
        this.appearance = appearance;
    }

    public final LoadedBhone getLoadedBhone(){
        return this.loadedBhone;
    }

    public final Bhone getBhone(String name){
        return loadedBhone.getBhone(name);
    }

    public final void setToTransform(String name, TransformGroup tg, Transform3D tgTrans, boolean active){
        this.loadedBhone.setToTransform(name, tg, tgTrans, active);
    }

    public final void setToTransform(String name, Point3f tgTrans){
        this.loadedBhone.setToTransform(name, tgTrans);
    }

    private final ArrayList<Vector3f> off = new ArrayList<Vector3f>();
    private final ArrayList<Clip> clips = new ArrayList<Clip>();

    public final void addLoadedBhoneFrame(LoadedBhoneFrame f, Clip c){
        off.add(f.applyTo(this.loadedBhone, true));
        clips.add(c);
    }

    private float t1 = 0.3f;
    public final void setOffsetStartTime(float time){
        this.t1 = time;
    }
    
    // -----------------------------------------------------------------------------------------------------------------

    private BhoneSkin bs = null;
    /*public final BhoneSkin getBhoneSkin(TransformGroup tg){
        return getBhoneSkin(tg, tg);
    }*/

    public final BhoneSkin getBhoneSkin(TransformGroup tg){
        if(bs == null){
            Skin s = loadedSkin.getSkin();
            Bhone bhone = loadedBhone.getBhone(loadedSkin);

            Clip sounds[] = this.clips.toArray(new Clip[this.clips.size()]);
            Vector3f[] offsets = this.off.toArray(new Vector3f[off.size()]);

            shape = new Shape3D(this.loadedSkin.getGeometry(), this.appearance);
            //shape.setUserData("BhoneSkinObject");
            tg.addChild(shape);

            bs = new BhoneSkin(bhone, s, t1, tg, sounds, offsets);
        }
        return bs;
    }

    private Shape3D shape;
    public final void setUserData(Object userData){
        //System.out.println(userData+"\t"+shape);
        this.shape.setUserData(userData);
    }

    public final Shape3D getShape(){
        return this.shape;
    }


}
