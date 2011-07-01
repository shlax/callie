package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.util.Map;

public final class TransformObject extends FactoryElement {

    public TransformObject(Object value, Map attributes) {
        super(value, attributes);        
    }

    private Float x = null;
    private Float y = null;
    private Float z = null;

    private Float rotX = null;
    private Float rotY = null;
    private Float rotZ = null;

    private Float scaleX = null;
    private Float scaleY = null;
    private Float scaleZ = null;

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public void setZ(Float z) {
        this.z = z;
    }

    public void setRotX(Float rotX) {
        this.rotX = rotX;
    }

    public void setRotY(Float rotY) {
        this.rotY = rotY;
    }

    public void setRotZ(Float rotZ) {
        this.rotZ = rotZ;
    }

    public void setScaleX(Float scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(Float scaleY) {
        this.scaleY = scaleY;
    }

    public final void setScaleZ(Float scaleZ) {
        this.scaleZ = scaleZ;
    }

    private Transform3D td = null;

    public final Transform3D getTransform3D(){
        if(td == null){
            td = new Transform3D();
            boolean jednotkova = true;
            if(x != null || y != null || z != null){
                td.set(new Vector3f(x == null ? 0 : x, y == null ? 0 : y, z == null ? 0 : z));
                jednotkova = false;
            }

            if(rotZ != null){
                if(jednotkova){
                    td.rotZ((rotZ*Math.PI)/180d);
                }else{
                    Transform3D tmp = new Transform3D();
                    tmp.rotZ((rotZ*Math.PI)/180d);
                    td.mul(tmp);
                }
                jednotkova = false;
            }

            if(rotY != null){
                if(jednotkova){
                    td.rotY((rotY*Math.PI)/180d);
                }else{
                    Transform3D tmp = new Transform3D();
                    tmp.rotY((rotY*Math.PI)/180d);
                    td.mul(tmp);
                }
                jednotkova = false;
            }

            if(rotX != null){
                if(jednotkova){
                    td.rotX((rotX*Math.PI)/180d);
                }else{
                    Transform3D tmp = new Transform3D();
                    tmp.rotX((rotX*Math.PI)/180d);
                    td.mul(tmp);
                }
                jednotkova = false;
            }


            if(scaleX != null || scaleY != null || scaleZ != null){
                if(jednotkova){
                    td.setScale(new Vector3d(scaleX == null ? 0 : scaleX, scaleY == null ? 0 : scaleY, scaleZ == null ? 0 : scaleZ ) );
                }else{
                    Transform3D tmp = new Transform3D();
                    tmp.setScale(new Vector3d(scaleX == null ? 0 : scaleX, scaleY == null ? 0 : scaleY, scaleZ == null ? 0 : scaleZ ) );
                    td.mul(tmp);
                }
            }
        }
        return td;
    }

    private String name = null;
    public final String getName() {
        return name;
    }
    public final void setName(String name) {
        this.name = name;
    }
}
