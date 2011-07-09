package ws.map;

import org.openmali.vecmath2.Tuple3f;

import java.util.EnumSet;

/**
 * 2.5D mapa v rovine xz
 */
public class Y25Map {

    private final Y25Triangle[] triangles;

	public Y25Map(Y25Triangle[] triangles, Y25Triangle last) {
        this.triangles = triangles;
		this.last = last;
    }

	/**
	 * copy constructor
     */
	public Y25Map(Y25Map map){
        this.triangles = map.triangles;
		this.last = map.last;
	}

	protected Y25Triangle last;

	public final Y25Triangle getLastY25Triangle(){
		return this.last;
	}

	public void setLastY25Triangle(Y25Triangle tr){
        //for(MapGroup g : groups)g.check(tr);
        this.last = tr;
	}

    /**
	 * skusi najskor fast potom prehlada cele
	 */
	public final Type getY(Tuple3f p, EnumSet<Type> set){
        Type r = fastY(p, set);
        if(r != null) return r;

		for (Y25Triangle t : triangles){
            if( set.contains(t.getTyp()) && t.getY(p)){
                setLastY25Triangle(t);
               // last = t;
                return r;
            }
        }

        return null;
	}

    public final Type fastY(Tuple3f p, EnumSet<Type> set){
        if(set.contains(this.last.getTyp()) && last.getY(p)) return this.last.getTyp();

        //System.out.println("c "+last.getNear().length);
		for(Y25Triangle t : last.getNear()) if(set.contains(t.getTyp()) && t.getY(p)){
            // last = t;
            setLastY25Triangle(t);
            //System.out.println("a "+t.getTyp());
            return t.getTyp();                           
		}

        //System.out.println("d "+last.getFear().length);
        for(Y25Triangle t : last.getFear()) if(set.contains(t.getTyp()) && t.getY(p)){
            // last = t;
            setLastY25Triangle(t);
            //System.out.println("b "+t.getTyp());
            return t.getTyp();                            
		}
        return null;
    }

}
