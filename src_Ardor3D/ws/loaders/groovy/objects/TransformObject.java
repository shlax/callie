package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;

import javax.vecmath.Matrix4f;
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

    private Float scale = null;

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

    public void setScale(Float scale) {
        this.scale = scale;
    }

    private Matrix4f td = null;

    public final Matrix4f getTransform3D(){
        if(td == null){
            td = new Matrix4f();
            boolean jednotkova = true;
            if(x != null || y != null || z != null){
                td.set(new Vector3f(x == null ? 0 : x, y == null ? 0 : y, z == null ? 0 : z));
                jednotkova = false;
            }

            if(rotZ != null){
                if(jednotkova){
                    td.rotZ((float)(rotZ*Math.PI)/180f);
                }else{
                    Matrix4f tmp = new Matrix4f();
                    tmp.rotZ((float)(rotZ*Math.PI)/180f);
                    td.mul(tmp);
                }
                jednotkova = false;
            }

            if(rotY != null){
                if(jednotkova){
                    td.rotY((float)(rotY*Math.PI)/180f);
                }else{
                    Matrix4f tmp = new Matrix4f();
                    tmp.rotY((float)(rotY*Math.PI)/180f);
                    td.mul(tmp);
                }
                jednotkova = false;
            }

            if(rotX != null){
                if(jednotkova){
                    td.rotX((float )(rotX*Math.PI)/180f);
                }else{
                    Matrix4f tmp = new Matrix4f();
                    tmp.rotX((float)(rotX*Math.PI)/180f);
                    td.mul(tmp);
                }
                jednotkova = false;
            }


            if(scale != null){
                if(jednotkova){
                    td.setScale(scale);
                }else{
                    Matrix4f tmp = new Matrix4f();
                    tmp.setScale(scale);
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
