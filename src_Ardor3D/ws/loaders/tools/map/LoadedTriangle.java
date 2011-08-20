package ws.loaders.tools.map;

import ws.map.DynamicY25Triangle;
import ws.map.Type;
import ws.map.Y25Triangle;
import ws.map.ai.NodeMap;

import javax.vecmath.Point3f;
import java.util.ArrayList;

public final class LoadedTriangle {

    private final Point3f a;
    private final Point3f b;
    private final Point3f c;
    private final Type t;

    private final boolean dynamic;

    public final Type getType(){
        return this.t;
    }

    /* public LoadedTriangle(Matrix4f v, LoadedTriangle t, boolean dynamic) {

        this.a = new Point3f(t.a.getX(), t.a.getY(), t.a.getZ());
        this.b = new Point3f(t.b.getX(), t.b.getY(), t.b.getZ());
        this.c = new Point3f(t.c.getX(), t.c.getY(), t.c.getZ());

        v.transform(a);
        v.transform(b);
        v.transform(c);

        this.t = t.t;
        this.dynamic = dynamic;

        loc = new Point3f((a.getX() + b.getX() + c.getX() )/3f, (a.getY() + b.getY() + c.getY() )/3f, (a.getZ() + b.getZ() + c.getZ() )/3f);
    } */


    public LoadedTriangle(Point3f a, Point3f b, Point3f c, Type t, boolean dynamic) {
        this.a = a;
        this.b = b;
        this.c = c;

        this.t = t;
        this.dynamic = dynamic;

        loc = new Point3f((a.getX() + b.getX() + c.getX() )/3f, (a.getY() + b.getY() + c.getY() )/3f, (a.getZ() + b.getZ() + c.getZ() )/3f);
    }

    private final Point3f loc;

    /* public final Point3f getLocation(){
        return this.loc;
    } */

    public final float getDistance(Point3f o){
        return this.loc.distance(o);
    }

    private final ArrayList<LoadedTriangle> locale = new ArrayList<LoadedTriangle>();
    public final void addLoacation(LoadedTriangle x){
        this.locale.add(x);
    }

    private NodeMap nm = null;
    public final NodeMap getNodeMap(NodeMap mapa[]){
        if(nm == null){
            NodeMap[] local = new NodeMap[locale.size()];
            Point3f nextPos[] = new Point3f[locale.size()];
            float[] len = new float[locale.size()];
            nm = new NodeMap(loc,this.getY25Triangle(), local, nextPos, len, mapa);
            for(int i = 0; i < this.locale.size(); i++){
                LoadedTriangle tmp = this.locale.get(i);
                local[i] = tmp.getNodeMap(mapa);
                len[i] = this.loc.distance(tmp.loc);

                Point3f a = new Point3f(this.loc);
                Point3f b = new Point3f(this.loc);
                getLine(a, b, tmp);
                a.interpolate(b, 0.5f);

                nextPos[i] = a; 
            }
        }
        return nm;
    }

    private final void getLine(Point3f a, Point3f b, LoadedTriangle tmp){
        Point3f[] tmpA =  { this.a, this.b, this.c };
        Point3f[] tmpB =  { tmp.a, tmp.b, tmp.c };
        int zhoda = 0;
        for(Point3f pa : tmpA){
            for(Point3f pb : tmpB){
                if(isEq(pa, pb)){
                    if(zhoda == 0){
                        a.set(pa);
                        zhoda ++;
                    }else if(zhoda == 1){
                        b.set(pa);
                        zhoda ++;
                    }else throw new RuntimeException("Wrong poly structure"); 
                }
            }
        }
    }

    public final NodeMap getNodeMap(){
        return this.nm;
    }

    private final ArrayList<LoadedTriangle> n = new ArrayList<LoadedTriangle>();
    public final void addNear(LoadedTriangle x){
        this.n.add(x);
    }

    private final ArrayList<LoadedTriangle> f = new ArrayList<LoadedTriangle>();
    public final void addFear(LoadedTriangle x){
        this.f.add(x);
    }

    private Y25Triangle tr = null;
    public final Y25Triangle getY25Triangle(){
        if(tr == null){
            Y25Triangle nn[] = new Y25Triangle[n.size()];
            Y25Triangle ff[] = new Y25Triangle[f.size()];
            tr = dynamic ? new DynamicY25Triangle(a, b, c, t, nn, ff, loc) : new Y25Triangle(a, b, c, t, nn, ff, loc);
            for(int i = 0; i < n.size(); i++)nn[i] = n.get(i).getY25Triangle();
            for(int i = 0; i < f.size(); i++)ff[i] = f.get(i).getY25Triangle();
        }
        return tr;
    }

    public final int pocSame(LoadedTriangle t){
		int pocSame = 0;
		if( isEq(a, t.a)) pocSame++;
		if( isEq(a, t.b)) pocSame++;
		if( isEq(a, t.c)) pocSame++;

		if( isEq(b, t.a)) pocSame++;
		if( isEq(b, t.b)) pocSame++;
		if( isEq(b, t.c)) pocSame++;

		if( isEq(c, t.a)) pocSame++;
		if( isEq(c, t.b)) pocSame++;
		if( isEq(c, t.c)) pocSame++;
		return pocSame;
	}

	private final boolean isEq(Point3f a, Point3f b){
		return a.distance(b) < 0.1;
	}

}
