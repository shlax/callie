package ws.loaders.tools.joint;

import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;

import java.util.ArrayList;

public final class LoadedBhoneFrame {

    private final Vector3f off = new Vector3f(); // = new ArrayList<Vector3f>();
    private final Vector3f permanent;  // = new ArrayList<Vector3f>();

    public LoadedBhoneFrame(Vector3f off) {
        this.permanent = off;
        this.off.set(off);
        this.ang = new ArrayList<Angles>();
    }

    public LoadedBhoneFrame(LoadedBhoneFrame tmp){
        this.permanent = tmp.permanent;
        this.off.set(tmp.permanent);
        this.ang = tmp.ang;
    }

    public final Vector3f applyTo(LoadedBhone lb, boolean addAngels){
        off.sub(lb.getOffest());
        if(addAngels)for(Angles a : this.ang) lb.addAngle(a.getName(), a.getAngle());
        return this.off;
    }

    private final ArrayList<Angles> ang; // = new ArrayList<Angles>();

    public final void addAngle(String name, Tuple3f a){
        this.ang.add(new Angles(name, a));
    }

    public final Vector3f getOffset(){
        return this.off;
    }

    public final ArrayList<Angles> getAngles(){
        return this.ang;
    }

}
