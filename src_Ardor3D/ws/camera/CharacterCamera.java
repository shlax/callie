package ws.camera;

import com.ardor3d.scenegraph.Node;
import wa.Gui;
import ws.ai.Target;
import ws.camera.areas.Colision;
import ws.map.Y25Map;
import ws.map.Y25Triangle;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class CharacterCamera extends MovingCamera{

    protected final Character c;
    protected final float actionDuration;
    
    private final float runSpeed;
    private final float walkSpeed;

    private final float jumpSpeedRun;
    private final float jumpSpeed;

    public CharacterCamera(float heightUp, float heightDown, Character c, float jumpSpeed, float jumpSpeedStart, float runSpeed, float walkSpeed, float actionDuration,
                           float userColideRadius, float angleAceleration, float speedAceleration, Colision[] col, Y25Map mapa,
                           Node colide, Vector3f startPosition, Y25Triangle startTriangle, float minDistance, float maxDistance, float defMaxMinDistance, float height, float maxSide) {
        super(userColideRadius, angleAceleration, speedAceleration, col, mapa,
              colide, startPosition, startTriangle, minDistance, maxDistance, defMaxMinDistance, height, maxSide);

        this.c = c;
        this.actionDuration = actionDuration;
        this.runSpeed = runSpeed;
        this.walkSpeed = walkSpeed;
        this.jumpSpeed = jumpSpeed;
        this.jumpSpeedRun = jumpSpeedStart;

        this.heightUp = heightUp;
        this.heightDown = heightDown;
        this.targerHeight = heightUp;

    }

    protected int status = STAND;
    protected int newStatus = STAND;
    
    protected float start = 0f;
    protected float predTime = 0;

    private final Point3f targetPosition = new Point3f();
    private Target target = null;
    private boolean canFireTarget = false;

    private int weaponUpDown = 0;

    private final float heightUp;// = 1.4f;
    private final float heightDown;// = 0.8f;
    protected volatile float targerHeight;// = heightUp;

    @Override
    protected boolean process(float time, float duration) {
        int nextStatus;
        // Target t = null;

        // protected float angleY = 0; -> get

        // protected float reqAngele = 0; <- set
        // protected float reqV = 0; <- set
        // protected boolean jump = false; <- set        

        boolean timeOut = false;
        if(this.start + actionDuration < time){ // presiel cas
            this.status = newStatus; // aktualny je dalsi
            newStatus = -1;
            this.predTime = this.start + actionDuration; // kedy mal skoncit
            timeOut = true;
        }

        if(weaponUpDown != 0){
            //System.out.println(weaponUpDown);
            this.canFireTarget = false;
            
            if(weaponUpDown == 1){
                if(newStatus == -1){
                    weaponUpDown = 2;
                    c.setWeaponEnabled(true);
                }
                nextStatus = this.RIFLE_PICK;
            }else if(weaponUpDown == 2){
                if(newStatus == -1) weaponUpDown = 0;
                nextStatus = RIFLE_STAND;

            }else{
                if(newStatus == -1){
                    weaponUpDown = 0;
                    c.setWeaponEnabled(false);
                }
                nextStatus = RIFLE_PICK;
            }
        }else if(jump > 0){
            this.canFireTarget = false;

            if(this.status == JUMPL1){
                if(newStatus == -1) reqV = jump == 1 ? jumpSpeed : jumpSpeedRun ;
                nextStatus = JUMPL2;
            }else if(this.status == JUMPL2){
                nextStatus = JUMPL3;
            }else if(this.status == JUMPL3){
                if(newStatus == -1) reqV = jump == 1 ? walkSpeed : runSpeed;
                nextStatus = RUN2;
                jump = 0;

            }else if(this.status == JUMPR1){
                if(newStatus == -1) reqV = jump == 1 ? jumpSpeed : jumpSpeedRun ;
                nextStatus = JUMPR2;
            }else if(this.status == JUMPR2){
                nextStatus = JUMPR3;
            }else if(this.status == JUMPR3){
                if(newStatus == -1) reqV = jump == 1 ? walkSpeed : runSpeed;
                nextStatus = RUN4;
                jump = 0;

            }else if(newStatus == -1) nextStatus = STAND;
            else nextStatus = newStatus;
            
        }else if(this.target != null && this.target.isTargetActive() && Gui.buttons.contains(MouseEvent.BUTTON3)){
            if(newStatus == -1){
                // t = target;
                // target = Gui.getPicked();
                //if( target != null && target.isTargetActive()){
                target.getTargetPoint(targetPosition);
                reqAngele = (float)Math.atan2(this.objectPosition.getX() - targetPosition.getX(), this.objectPosition.getZ() - targetPosition.getZ());
                if(reqAngele < 0f) reqAngele += 2*PIf;
                nextStatus = RIFLE_FIRE;

                if(this.status == RIFLE_FIRE) this.canFireTarget = true;
                
                /* }else{
                    this.target = null;
                    nextStatus = RIFLE_STAND;
                } */
            }else{
                target.getTargetPoint(targetPosition);
                reqAngele = (float)Math.atan2(this.objectPosition.getX() - targetPosition.getX(), this.objectPosition.getZ() - targetPosition.getZ());
                if(reqAngele < 0) reqAngele += 2*PIf;
                
                nextStatus = RIFLE_FIRE;
            }
        }else{
            target = null;
            this.canFireTarget = false;

            if(this.status == STAND){
                if(Gui.keys.contains(KeyEvent.VK_SPACE)){
                    reqV = this.walkSpeed;
                    nextStatus = JUMPR1;
                    jump = 1;
                }else if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    reqV = runSpeed;
                    nextStatus = RUN1;
                }else if(Gui.keys.contains(KeyEvent.VK_Q)){
                    this.reqV = 0;
                    targerHeight = heightDown;
                    nextStatus = DOWN;
                }else if(UserCamera.hasWeapon() && Gui.buttons.contains(MouseEvent.BUTTON3)){
                    nextStatus = RIFLE_PICK;
                    weaponUpDown = 1;
                    //System.out.println("X");
                }else{
                    this.reqV = 0;
                    nextStatus = STAND;
                }
            }else if(this.status == DOWN){
                this.reqV = 0;
                if(Gui.keys.contains(KeyEvent.VK_Q)){
                    nextStatus = DOWN;
                }else{
                    targerHeight = heightUp;
                    nextStatus = STAND;
                }
            }else if(this.status == RUN1){
                if( Gui.keys.contains(KeyEvent.VK_SPACE) ){
                    //this.reqV = this.runSpeed;
                    nextStatus = JUMPL1;
                    jump = 2;
                }else if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    // reqAngele = getRequestAngle(angleY);
                    nextStatus = RUN2;
                }else{
                    reqV = 0;
                    nextStatus = STAND;
                }
            }else if(this.status == RUN2){
                if( Gui.keys.contains(KeyEvent.VK_SPACE) ){
                    //this.reqV = this.runSpeed;
                    nextStatus = JUMPL1;
                    jump = 2;
                }else if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    // reqAngele = getRequestAngle(angleY);
                    nextStatus = RUN3;
                }else{
                    reqV = 0;
                    nextStatus = STAND;
                }
            }else if(this.status == RUN3){
                if( Gui.keys.contains(KeyEvent.VK_SPACE) ){
                    //this.reqV = this.runSpeed;
                    nextStatus = JUMPR1;
                    jump = 2;
                }else if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    // reqAngele = getRequestAngle(angleY);
                    nextStatus = RUN4;
                }else{
                    reqV = 0;
                    nextStatus = STAND;
                }
            }else if(this.status == RUN4){
                if( Gui.keys.contains(KeyEvent.VK_SPACE) ){
                    //this.reqV = this.runSpeed;
                    nextStatus = JUMPR1;
                    jump = 2;
                }else if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    // reqAngele = getRequestAngle(angleY);
                    nextStatus = RUN1;
                }else{
                    this.reqV = 0;
                    nextStatus = STAND;
                }
            }else if(this.status == RIFLE_STAND){
                if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    reqV = walkSpeed;
                    nextStatus = RIFLE_RUN1;
                }else if(Gui.buttons.contains(MouseEvent.BUTTON1)){
                    Target tmpTarget = Gui.getPicked();
                    //System.out.println(tmpTarget);
                    if(tmpTarget != null && tmpTarget.isTargetActive()) target = tmpTarget;
                    if( target != null && target.isTargetActive()){
                        target.getTargetPoint(targetPosition);

                        

                        reqAngele = (float)Math.atan2(this.objectPosition.getX() - targetPosition.getX(), this.objectPosition.getZ() - targetPosition.getZ());
                        if(reqAngele < 0) reqAngele += 2*PIf;
                        nextStatus = RIFLE_FIRE;                        
                    }else nextStatus = RIFLE_STAND;
                }else if(!Gui.buttons.contains(MouseEvent.BUTTON3)){
                    nextStatus = RIFLE_PICK;
                    weaponUpDown = -1;
                }else{
                    this.reqV = 0;
                    nextStatus = RIFLE_STAND;
                }
            }else if(this.status == RIFLE_RUN1){
                if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    // reqAngele = getRequestAngle(angleY);
                    nextStatus = RIFLE_RUN2;
                }else{
                    reqV = 0;
                    nextStatus = RIFLE_STAND;
                }
            }else if(this.status == RIFLE_RUN2){
                if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    // reqAngele = getRequestAngle(angleY);
                    nextStatus = RIFLE_RUN3;
                }else{
                    reqV = 0;
                    nextStatus = RIFLE_STAND;
                }
            }else if(this.status == RIFLE_RUN3){
                if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    // reqAngele = getRequestAngle(angleY);
                    nextStatus = RIFLE_RUN4;
                }else{
                    reqV = 0;
                    nextStatus = RIFLE_STAND;
                }
            }else if(this.status == RIFLE_RUN4){
                if(Gui.keys.contains(KeyEvent.VK_W) || Gui.keys.contains(KeyEvent.VK_S) || Gui.keys.contains(KeyEvent.VK_A) || Gui.keys.contains(KeyEvent.VK_D)){
                    // reqAngele = getRequestAngle(angleY);
                    nextStatus = RIFLE_RUN1;
                }else{
                    reqV = 0;
                    nextStatus = RIFLE_STAND;
                }
            }else if(this.status == RIFLE_FIRE){
                nextStatus = RIFLE_STAND;
            }else nextStatus = STAND;
        }

        boolean ret = super.process(time, duration); // process v

        if(v == 0f){
            if(nextStatus == RUN1 || nextStatus == RUN2 || nextStatus == RUN3 || nextStatus == RUN4) nextStatus =  STAND;
            else if(nextStatus == RIFLE_RUN1 || nextStatus == RIFLE_RUN2 || nextStatus == RIFLE_RUN3 || nextStatus == RIFLE_RUN4) nextStatus =  RIFLE_STAND; 
        }
            
        if(timeOut || nextStatus != newStatus){
            this.start = predTime;
            newStatus = nextStatus;
            //System.out.println(nextStatus);
            c.setNewPose(nextStatus);
        }

        float relativeTime = (time - this.start) / this.actionDuration;
        // if(relativeTime < 0f) relativeTime = 0f;
        if(relativeTime > 1f) relativeTime = 1f;


        c.process(relativeTime, time,  this.motTrans, canFireTarget ? target : null);
        if(target != null && !target.isTargetActive()) target = null;
        
        predTime = time;

        //if(Gui.keys.contains(KeyEvent.VK_Z))System.out.println(this.status);

        return ret;
    }
    
	protected static final int STAND = 0; // 0

	//									 left      right
	private static final int RUN1 = 1; // 1 2 3 4 + 3 4 1 2
	private static final int RUN2 = 2;
	private static final int RUN3 = 3;
	private static final int RUN4 = 4;

	//									   left    right
	private static final int JUMPL1 = 5; // 5 6 7 + 8 9 10 -> odraz, let, dopad
	private static final int JUMPL2 = 6;
	private static final int JUMPL3 = 7;

	private static final int JUMPR1 = 8;
	private static final int JUMPR2 = 9;
	private static final int JUMPR3 = 10;
        
	protected static final int RIFLE_STAND = 11;

	private static final int RIFLE_RUN1 = 12; // 12 13 14 15 + 14 15 12 13
	private static final int RIFLE_RUN2 = 13;
	private static final int RIFLE_RUN3 = 14;
	private static final int RIFLE_RUN4 = 15;

    private static final int RIFLE_FIRE = 16;

    private static final int RIFLE_PICK = 17;

    private static final int DOWN = 18;
}
