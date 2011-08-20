package ws.camera;

import com.ardor3d.scenegraph.Node;
import wa.Gui;
import ws.ai.Ai;
import ws.camera.areas.Colision;
import ws.map.Type;
import ws.map.Y25Map;
import ws.map.Y25Triangle;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import java.awt.event.KeyEvent;
import java.util.EnumSet;

public class MovingCamera extends Camera{

    protected final Y25Map mapa;
    private final float userColideRadius;
    
    protected final float angleAceleration; // = 0.0075f
    private final float speedAceleration; // = 0.00001f;

    private final Colision[] col;
   // private final ActionArea[] areas;
    private final boolean notColide(Vector3f v){
    //    for(ActionArea a : areas)a.check(v);
        for(Colision c : col) if(c.colide(v))return false;
        return true;
    }

    protected MovingCamera(float userColideRadius, float angleAceleration, float speedAceleration, Colision[] col, Y25Map mapa,
                           Node colide, Vector3f startPosition, Y25Triangle startTriangle, float minDistance, float maxDistance, float defMaxMinDistance, float height, float maxSide) {
        super(colide, startPosition, minDistance, maxDistance, defMaxMinDistance, height, maxSide);

        // this.c = c;
        this.mapa = mapa;
        this.userColideRadius = userColideRadius;
        this.angleAceleration = angleAceleration;
        this.speedAceleration = speedAceleration;
        this.col = col;

        this.motTrans.set(this.objectPosition);
        this.angTrans.rotY(objectY+PIf); // look back
        this.motTrans.mul(this.angTrans);

        this.newPosition.set(this.objectPosition);
      //  System.out.println("D "+newPosition);
        this.mapa.setLastY25Triangle(startTriangle);
        this.mapa.getY(newPosition, EnumSet.of(Type.TERAIN));
      //  System.out.println("E "+newPosition);
        this.objectPosition.set(newPosition);
        UserCamera.setUserPosition(this.mapa.getLastY25Triangle(), this.objectPosition);
    }

    protected float v = 0f;
    protected float objectY = 0f;

    private final Vector3f newPosition = new Vector3f();

    protected final Matrix4f angTrans = new Matrix4f();
    protected final Matrix4f motTrans = new Matrix4f();

    protected float reqV = 0;
    protected float reqAngele = 0;
    protected int jump = 0;

    private final EnumSet movType = EnumSet.of(Type.TERAIN, Type.JUMP);
    private Type predTyp = null; 

    @Override
    protected boolean process(float actTime, float duration) {
        // protected float angleY = 0; -> get
        
        // protected Y25Triangle objectTriangle = null; <- set
        // protected final Vector3f objectPosition = new Vector3f(0,0,0);  <- set  

        boolean updateTrans = false;
        boolean changePosition = false;
    
        // speed
        if(v < reqV){
            v += speedAceleration*duration;
            if(v > reqV) v = reqV;
        }else if(v > reqV){
            v -= speedAceleration*duration;
            if(v < 0) v = 0;
        }

        if(v > 0f){ // position
            if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)) reqAngele = getRequestAngle(angleY); // ide kam je natocena kamera + WASD

            float d = duration*(-v);

            // count new position
            newPosition.setX( objectPosition.getX()+d*((float)Math.sin(this.objectY)) );
            newPosition.setZ( objectPosition.getZ()+d*((float)Math.cos(this.objectY)) );
            newPosition.setY( objectPosition.getY() );

            if( notColide(newPosition) && Ai.getObjectColision(newPosition, userColideRadius) == null){
                boolean move = false;

                Type t = this.mapa.fastY(newPosition, movType);
                //System.out.println(newPosition);

                if(t == Type.TERAIN){
                    move = true;

                    predTyp = t;
                }else if(t == Type.JUMP){
                    if(jump > 0) move = true;
                    else UserCamera.wrongMove();

                    predTyp = t;
                }else if(predTyp == Type.JUMP){
                    UserCamera.wrongMove();
                }

                if(move){
                    this.objectPosition.set(newPosition);
                    UserCamera.setUserPosition(this.mapa.getLastY25Triangle(), this.objectPosition);
                    
                    updateTrans = true;
                    changePosition = true;
                }else v = 0f;                

            }
        }

        if(this.objectY != reqAngele){ // angle
            // v smere
            float dx;
            if(reqAngele > objectY) dx = reqAngele - objectY;
            else dx = PIf*2f - ( objectY - reqAngele );

            // v protismere
            float dy;
            if(reqAngele > objectY) dy = PIf*2f - ( reqAngele - objectY );
            else dy = objectY - reqAngele;

            float dz = Math.min(dx, dy);
            float s = angleAceleration*duration;

            if(dz < s) this.objectY = reqAngele;
            else this.objectY = normalizeAngle(this.objectY, (dx < dy ? 1f : -1f)*s ); // += (dx < dy ? 1f : -1f)*(dz < s ? dz : s );

            updateTrans = true;
        }

        //System.out.println(motTrans);

        if(updateTrans){
            this.motTrans.set(this.objectPosition);
            this.angTrans.rotY(objectY+PIf); // look back
            this.motTrans.mul(this.angTrans);
        }
        // this.c.process(actTime, this.motTrans, fire);
        //if(Gui.keys.contains(KeyEvent.VK_M))System.out.println(objectY);

        return changePosition;
        // c.process(actTime, motTrans, fire);

    }

    // Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)
    private final float getRequestAngle(float angle){
        if(Gui.keys.contains(KeyEvent.VK_S)){
            if(Gui.keys.contains(KeyEvent.VK_A)) return this.normalizeAngle(angle, (3f*PIf)/4f);
            else if(Gui.keys.contains(KeyEvent.VK_D)) return this.normalizeAngle(angle, -(3f*PIf)/4f); 
            return this.normalizeAngle(angle, PIf);
        }else if(Gui.keys.contains(KeyEvent.VK_A)){
            if(Gui.keys.contains(KeyEvent.VK_W)) return this.normalizeAngle(angle, PIf/4f);
            return this.normalizeAngle(angle, PIf/2);
        }else if(Gui.keys.contains(KeyEvent.VK_D)){
            if(Gui.keys.contains(KeyEvent.VK_W)) return this.normalizeAngle(angle, -PIf/4f);
            return this.normalizeAngle(angle, -PIf/2);
        }else return angle;
    }

}
