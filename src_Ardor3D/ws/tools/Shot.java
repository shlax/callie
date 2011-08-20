package ws.tools;

import com.ardor3d.renderer.IndexMode;
import com.ardor3d.scenegraph.MeshData;
import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.extension.SwitchNode;
import com.ardor3d.util.geom.BufferUtils;
import wa.Appearance;
import ws.ai.Target;

import javax.sound.sampled.Clip;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class Shot /*implements GeometryUpdater*/{

    private final Point3f points[];
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

        final int indexes[] = new int[]{
			0, 3, 4,
			1, 5, 3,
			2, 4, 5,

			5, 1, 2,
			4, 2, 0,
			3, 0, 1,
		};

        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indexes.length);
        indexBuffer.put(indexes);


        FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(indexes.length);
        for(int i : indexes){
            Point3f p = points[i];
            vertexBuffer.put(p.x).put(p.y).put(p.z);
        }

        md.setVertexBuffer(vertexBuffer);
        md.setIndexBuffer(indexBuffer);
        md.setIndexMode(IndexMode.Triangles);

        sw.attachChild(ap.getRenderState(md));


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

    private final MeshData md = new MeshData();

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

            FloatBuffer storeVerts = md.getVertexBuffer();
            storeVerts.rewind();

            for(int i = 0; i < 3; i++){
                storeVerts.put( this.source.getX() + this.points[i].getX() );
                storeVerts.put( this.source.getY() + this.points[i].getY() );
                storeVerts.put( this.source.getZ() + this.points[i].getZ() );
            }
            for(int i = 3; i < 6; i++){
                storeVerts.put( this.destination.getX() + this.points[i].getX() );
                storeVerts.put( this.destination.getY() + this.points[i].getY() );
                storeVerts.put( this.destination.getZ() + this.points[i].getZ() );
            }

            md.getVertexCoords().setNeedsRefresh(true);

            //ar.updateData(this);
            //sw.setWhichChild(Switch.CHILD_ALL);
            sw.setAllVisible();
        }
    }




}
