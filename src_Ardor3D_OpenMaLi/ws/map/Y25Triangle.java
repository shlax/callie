package ws.map;

import org.openmali.vecmath2.*;

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

    public Y25Triangle(Point3f a, Point3f b, Point3f c, Type typ, Y25Triangle n[], Y25Triangle f[]){
		this.typ = typ;

        this.near = n;
		this.fear = f;

        this.a = new Point2f(a.getX(), a.getZ());
		this.b = new Point2f(b.getX(), b.getZ());
		this.c = new Point2f(c.getX(), c.getZ());

		ab = new Vector2f(b.getX()-a.getX(), b.getZ()-a.getZ() );
		bc = new Vector2f(c.getX()-b.getX(), c.getZ()-b.getZ() );
		ca = new Vector2f(a.getX()-c.getX(), a.getZ()-c.getZ() );

		Vector3f tmp = new Vector3f(b.getZ()-a.getZ(), b.getY()-a.getY(), b.getZ()-a.getZ() );
		tmp.cross(new Vector3f(c.getZ()-a.getZ(), c.getY()-a.getY(), c.getZ()-a.getZ() ), tmp);

		ta = tmp.getX();
		tb = tmp.getY();
		tc = tmp.getZ();
		td = -((ta*c.getX())+(tb*c.getY())+(tc*c.getZ()));

        center = new Point3f( (a.getX() + b.getX() + c.getX())/3f, (a.getY() + b.getY() + c.getY())/3f, (a.getZ() + b.getZ() + c.getZ())/3f );
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
        float na = ((p.getX()-a.getX())*ab.getY())-(ab.getX()*(p.getZ()-a.getY()));
		float nb = ((p.getX()-b.getX())*bc.getY())-(bc.getX()*(p.getZ()-b.getY()));
		float nc = ((p.getX()-c.getX())*ca.getY())-(ca.getX()*(p.getZ()-c.getY()));

		if( ( na > 0 && nb > 0 && nc > 0 ) || ( na < 0 && nb < 0 && nc < 0 ) ){
			p.setY( -(((ta*p.getX())+(tc*p.getZ())+td)/tb) );
			return true;
		}
		return false;
	}
}

