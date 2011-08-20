package ws.loaders.tools.joint;

import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.Node;
import wa.Appearance;
import ws.joint.Bhone;
import ws.joint.BhoneSkin;
import ws.joint.Skin;

import javax.sound.sampled.Clip;
import javax.vecmath.Matrix4f;
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

    public final void setToTransform(String name, Node tg, Matrix4f tgTrans, boolean active){
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

    public final BhoneSkin getBhoneSkin(Node tg){
        if(bs == null){
            Skin s = loadedSkin.getSkin();
            Bhone bhone = loadedBhone.getBhone(loadedSkin);

            Clip sounds[] = this.clips.toArray(new Clip[this.clips.size()]);
            Vector3f[] offsets = this.off.toArray(new Vector3f[off.size()]);


            /*Mesh m = new Mesh();
            m.setMeshData(this.loadedSkin.getGeometry());
            tg.attachChild(m); */

            shape = appearance.getRenderState(this.loadedSkin.getGeometry());
            //shape.getSceneHints().setDataMode(DataMode.VBO);
            tg.attachChild(shape);

            //m.setRenderState(this.appearance.getRenderState());

            //shape = new Shape3D(this.loadedSkin.getGeometry(), this.appearance);
            //shape.setUserData("BhoneSkinObject");
            //tg.addChild(shape);


            bs = new BhoneSkin(bhone, s, t1, tg, sounds, offsets);
        }
        return bs;
    }

    private Mesh shape;
    /* public final void setUserData(Object userData){
        //System.out.println(userData+"\t"+shape);
        this.shape.setUserData(userData);
    } */

    public final Mesh getShape(){
        return this.shape;
    }


}
