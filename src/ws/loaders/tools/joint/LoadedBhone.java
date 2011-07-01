package ws.loaders.tools.joint;

import ws.joint.*;
import ws.joint.acelerator.AcceleratedValue;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

public final class LoadedBhone {
    public enum Type{Acelerated, Transform, ActiveTransform, Linear, Target}

    public final String name;

    private final int index[];
    private final LoadedBhone parent;

    private final Vector3f mov;
    private final Transform3D trans = new Transform3D();

    public final Vector3f getOffest() {
        return mov;
    }

    public LoadedBhone(String name, Vector3f mov, LoadedBhone parent, int index[], Type type, LinearBhone.Maping map[]) {
        this.parent = parent;
        
        this.mov = mov;
        this.trans.set(mov);

        this.name = name;
        this.index = index;

        this.type = type;
        this.map = map;
    }

    public LoadedBhone(LoadedBhone tmp, LoadedBhone parent){
        this.parent = parent;
        
        this.mov = tmp.mov;
        this.trans.set(mov);

        this.name = tmp.name;
        this.index = tmp.index;

        this.type = tmp.type;
        this.map = tmp.map;

        for(LoadedBhone t : tmp.sub) this.sub.add(new LoadedBhone(t, this));
    }

    private Type type;
    private TransformGroup tg = null;
    private Transform3D tgTrans = null;
    public final void setToTransform(String name, TransformGroup tg, Transform3D tgTrans, boolean active){
        if(name.equals(this.name)){
            this.tg = tg;
            this.tgTrans = tgTrans;
            this.type = active ? Type.ActiveTransform : Type.Transform;
        }else for(LoadedBhone tmp : this.sub) tmp.setToTransform(name, tg, tgTrans, active); 
    }

    public Point3f targetPoint = null;

    public final void setToTransform(String name, Point3f tgTrans){
        if(name.equals(this.name)){
            this.targetPoint = tgTrans;
            this.type = Type.Target;
        }else for(LoadedBhone tmp : this.sub) tmp.setToTransform(name, tgTrans);
    }

    private final ArrayList<LoadedBhone> sub = new ArrayList<LoadedBhone>();
    public final void addLoadedBhone(LoadedBhone lb){
        this.sub.add(lb);
    }

    private final ArrayList<Tuple3f> angles = new ArrayList<Tuple3f>();
    public final void addAngle(String name, Tuple3f s){
        if(name.equals(this.name)){
            this.angles.add(s);
        }else for(LoadedBhone tmp : this.sub)tmp.addAngle(name, s);
    }

    private AcceleratedValue avX = new AcceleratedValue(0.3f);
    public final void setXaccelerator(String name, AcceleratedValue a){
        if(a == null) return;
        if(name.equals(this.name)){
            this.avX = a;
        }else for(LoadedBhone tmp : this.sub) tmp.setXaccelerator(name, a);
    }

    private AcceleratedValue avY = new AcceleratedValue(0.3f);
    public final void setYaccelerator(String name, AcceleratedValue a){
        if(a == null) return;
        if(name.equals(this.name)){
            this.avY = a;
        }else for(LoadedBhone tmp : this.sub) tmp.setYaccelerator(name, a); 
    }

    private AcceleratedValue avZ = new AcceleratedValue(0.3f);
    public final void setZaccelerator(String name, AcceleratedValue a){
        if(a == null) return;
        if(name.equals(this.name)){
            this.avZ = a;
        }else for(LoadedBhone tmp : this.sub) tmp.setZaccelerator(name, a);
    }

    private final LinearBhone.Maping map[];

    private Bhone bi = null;

    public final Bhone getBhone(String name){
        if(this.name.equals(name)) return bi;
        for(LoadedBhone tmp : this.sub){
            Bhone r = tmp.getBhone(name);
            if(r != null) return r;
        }
        return null;
    }

    private final Bhone getBhone(LoadedSkin s, Transform3D x){
        if(bi == null){
            // this.bind(x, s);
            Transform3D tmpTrans = new Transform3D();

            if( this.type == Type.Linear ){
                tmpTrans.set(x);
            }else{
                tmpTrans.mul(x, this.trans);
            }

            Point3f p = new Point3f();
            tmpTrans.transform(p);

            Point3f[] points = new Point3f[index.length];
            Point3f[] pointsSrc = new Point3f[index.length];
            for(int i=0; i < index.length; i++){
                points[i] = s.getPoint(index[i]);
                pointsSrc[i] = new Point3f( points[i] );
                pointsSrc[i].sub(p);
            }

            ArrayList<Integer> normalIndex = new ArrayList<Integer>();
            for(Integer i : index){
                ArrayList<Integer> sn = s.getNormals(i);
                if(sn != null)normalIndex.addAll(sn);
                else System.out.println("no normals at : "+i);
            }

            Vector3f[] normals = new Vector3f[normalIndex.size()];
            Vector3f[] normalsSrc = new Vector3f[normalIndex.size()];
            for(int i=0; i < normalIndex.size(); i++){
                normals[i] = s.getVector(normalIndex.get(i));
                normalsSrc[i] = new Vector3f( normals[i] );
            }
            
            Bhone pod[] = (sub.isEmpty()) ? null : new Bhone[this.sub.size()];

            if(this.type == Type.Linear){                
                bi = new LinearBhone((AceleratedBhone)parent.bi, pod, points, pointsSrc, normals, normalsSrc, mov.x, mov.y, mov.z, map);
            }else{

                Tuple3f ang[] = this.angles.toArray(new Tuple3f[this.angles.size()]); 

                if(this.type == Type.Transform){
                    bi = new TransformBhone(tg, tgTrans, mov, pod, points, pointsSrc, normals, normalsSrc, ang, avX, avY, avZ);
                }else if(this.type == Type.Target){
                    bi = new TargetBhone(targetPoint, mov, pod, points, pointsSrc, normals, normalsSrc, ang, avX, avY, avZ);
                }else if(this.type == Type.ActiveTransform){
                    bi = new ActiveTransformBhone(tg, tgTrans, mov, pod, points, pointsSrc, normals, normalsSrc, ang, avX, avY, avZ);
                }else{
                    bi = new AceleratedBhone(mov, pod, points, pointsSrc, normals, normalsSrc, ang, avX, avY, avZ);
                }
            }
            for(int i =0; i < this.sub.size(); i++) pod[i] = this.sub.get(i).getBhone(s, tmpTrans);
        }

        return bi;
    }

    public final Bhone getBhone(LoadedSkin s){
        return getBhone(s, new Transform3D());
    }

}
