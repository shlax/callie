package ws.map;

import javax.vecmath.*;

/**
 * 2.5D trojuholnik v rovine xz
 */
public class Y25Triangle{
	private final Type typ;

	private final Y25Triangle near[];
	private final Y25Triangle fear[];

	public final Y25Triangle[] getNear(){
		return this.near;
	}

	public final Y25Triangle[] getFear(){
		return this.fear;
	}

    public final Type getTyp() {
        return typ;
    }

    private final Point3f center;
    public final Point3f getCenter(){
        return center;
    }

    public Y25Triangle(Point3f a, Point3f b, Point3f c, Type typ, Y25Triangle n[], Y25Triangle f[], Point3f cen){
		this.typ = typ;

        this.near = n;
		this.fear = f;

        this.a = new Point2f(a.x, a.z);
		this.b = new Point2f(b.x, b.z);
		this.c = new Point2f(c.x, c.z);

		ab = new Vector2f(b.x-a.x, b.z-a.z );
		bc = new Vector2f(c.x-b.x, c.z-b.z );
		ca = new Vector2f(a.x-c.x, a.z-c.z );

		Vector3f tmp = new Vector3f(b.x-a.x, b.y-a.y, b.z-a.z );
		tmp.cross(new Vector3f(c.x-a.x, c.y-a.y, c.z-a.z ), tmp);

		ta = tmp.x;
		tb = tmp.y;
		tc = tmp.z;
		td = -((ta*c.x)+(tb*c.y)+(tc*c.z));

        center = cen; //new Point3f( (a.x + b.x + c.x)/3f, (a.y + b.y + c.y)/3f, (a.z + b.z + c.z)/3f );
	}

    private final Vector2f ab;
	private final Vector2f bc;
	private final Vector2f ca;

	private final Point2f a;
	private final Point2f b;
	private final Point2f c;

	private final float ta;
	private final float tb;
	private final float tc;
	private final float td;

	public boolean getY(Tuple3f p){
        float na = ((p.x-a.x)*ab.y)-(ab.x*(p.z-a.y));
		float nb = ((p.x-b.x)*bc.y)-(bc.x*(p.z-b.y));
		float nc = ((p.x-c.x)*ca.y)-(ca.x*(p.z-c.y));

		if( ( na > 0 && nb > 0 && nc > 0 ) || ( na < 0 && nb < 0 && nc < 0 ) ){
			p.y = -(((ta*p.x)+(tc*p.z)+td)/tb);
			return true;
		}
		return false;
	}
}

