package ws.tools;

import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.extension.SwitchNode;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;
import wa.Appearance;
import ws.ai.Target;

import javax.sound.sampled.Clip;

public final class Shot /*implements GeometryUpdater*/{

    private final Point3f points[];
	private final float fp[];
    //private final IndexedTriangleArray ar = new IndexedTriangleArray(6, IndexedTriangleArray.COORDINATES | IndexedTriangleArray.BY_REFERENCE | IndexedTriangleArray.TEXTURE_COORDINATE_2 , 6*3);

    private final Clip shot;
    private final float kadencia;
    private final float timeVisible;

    public Shot(Clip shot, float kadencia, float hit, float minDistance, float maxDistance, float timeVisible, float dst, Appearance ap, Node gr) {
        this.shot = shot;
        this.kadencia = kadencia;
        this.timeVisible = timeVisible;
        this.hit = hit;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;

        this.dstKoef = 1.0f/(maxDistance - minDistance);

        // -------------------------------------------------------------------------------------------------------------

/*
		0
	1		2

	3		4
		5
*/

        points = new Point3f[]{
				new Point3f(0, dst, 0),
				new Point3f(-dst, -dst, dst),
				new Point3f(dst, -dst, -dst),

				new Point3f(-dst, -dst, dst),
				new Point3f(dst, -dst, -dst),
				new Point3f(0, dst, 0),
			};

		fp = new float[]{
				0, dst, dst,
				-dst, -dst, -dst,
				dst, -dst, -dst,

				-dst, dst, dst,
				dst, dst, dst,
				0, -dst, -dst,
		};

/*
        2   3
        0   1
*/
        final float texCoords[] = new float[]{
                0, 0,
                1, 0,
                0, 1,
                1, 1,
        };

        final int indexes[] = new int[]{
			0, 3, 4,
			1, 5, 3,
			2, 4, 5,

			5, 1, 2,
			4, 2, 0,
			3, 0, 1,
		};

        final int textureIndexes[] = new int[]{
			3, 0, 1,
			3, 0, 1,
			3, 0, 1,

			2, 0, 3,
			2, 0, 3,
			2, 0, 3,
		};

        //TODO: shot geometry
		/*ar.setCapability(IndexedTriangleArray.ALLOW_REF_DATA_WRITE);

        ar.setTexCoordRefFloat(0, texCoords);
        ar.setTextureCoordinateIndices(0, 0, textureIndexes);

		ar.setCoordRefFloat(fp);
		ar.setCoordinateIndices(0, indexes);

		Shape3D shape = new Shape3D(ar, ap);
        sw.addChild(shape);*/
        // -------------------------------------------------------------------------------------------------------------

       // sw.setCapability(Switch.ALLOW_SWITCH_WRITE);
        //gr.addChild(sw);
        gr.attachChild(sw);
    }
    private final SwitchNode sw = new SwitchNode();

    public final void unfire(){
        if(lastFire) sw.setAllNonVisible(); //sw.setWhichChild(Switch.CHILD_NONE);
    }

    private boolean lastFire = false;
    
    private float time = 0f;
    
    public final void processFire(float time){
        if(lastFire){
            if(this.time + timeVisible < time){
                lastFire = false;
                this.time += kadencia;
                //sw.setWhichChild(Switch.CHILD_NONE);
                sw.setAllNonVisible();
            } /*else{
                act.interpolate(source, destination, (time - this.time)/timeVisible );
                td.set(this.act);
                tg.setTransform(td);
            } */            
        }
    }

    private final Vector3f source = new Vector3f();
    private final Point3f destination = new Point3f();
        
    private final float hit;
    private final float minDistance;
    private final float maxDistance;
    private final float dstKoef;

    public final void fire(Tuple3f source, Target destination, float time){
        if(!lastFire && time > this.time){
            if(shot != null){
                this.shot.setFramePosition(0);
                this.shot.start();                
            }
            lastFire = true;

            this.time = time;

            this.source.set(source);
            destination.getTargetPoint(this.destination);

            this.source.sub(this.destination, source);

            float dst = this.source.length();
            if(dst < this.minDistance){
                destination.hit(this.hit);
            }else if(dst < maxDistance){
                destination.hit(this.hit*(1f - dstKoef*(dst - minDistance)));
            }

            this.source.scale(destination.getTargetRadius()/dst);

            this.destination.sub(this.source);
            this.source.set(source);

            //ar.updateData(this);
            //sw.setWhichChild(Switch.CHILD_ALL);
            sw.setAllVisible();
        }
    }

    /*@Override
    public void updateData() {
        for(int i = 0; i < 3; i++){
			this.fp[i*3] = this.source.getX() + this.points[i].getX();
			this.fp[i*3 + 1] = this.source.getY() + this.points[i].getY();
			this.fp[i*3 + 2] = this.source.getZ() + this.points[i].getZ();
		}
		for(int i = 3; i < 6; i++){
			this.fp[i*3] = this.destination.getX() + this.points[i].getX();
			this.fp[i*3 + 1] = this.destination.getY() + this.points[i].getY();
			this.fp[i*3 + 2] = this.destination.getZ() + this.points[i].getZ();
		}
    }*/

}
