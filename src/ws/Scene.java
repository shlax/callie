package ws;

import com.sun.j3d.utils.behaviors.vp.ViewPlatformBehavior;
import com.sun.j3d.utils.pickfast.PickCanvas;
import com.sun.j3d.utils.universe.SimpleUniverse;
import ws.ai.Ai;
import ws.ai.Target;
import ws.camera.CharacterCamera;
import ws.camera.UserCamera;
import ws.loaders.Loader;
import ws.loaders.tools.SoundLoader;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

public class Scene extends ViewPlatformBehavior {
    private final WakeupOnElapsedFrames wk = new WakeupOnElapsedFrames(0);

    private final Canvas3D canvas;
    private final SimpleUniverse univ;
    private final BranchGroup gameRoot = new BranchGroup();
    private final String sceneName;

    public Scene(String sceneName, Canvas3D canvas, SimpleUniverse univ) throws Exception {
        this.setSchedulingBounds(new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE));

        this.sceneName = sceneName;
        this.canvas = canvas;
        this.univ = univ;

        gameRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        gameRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        gameRoot.compile();
        univ.addBranchGraph(gameRoot);
        univ.getViewingPlatform().setViewPlatformBehavior( this );
    }

    private volatile boolean isActive = false;

    @Override
    public void initialize() {
        this.wakeupOn(wk);
    }

    private boolean wasInit = false;

    @Override
    public void processStimulus(Enumeration criteria) {
        if(isActive){
            if(!wasInit){
                wasInit = true;
                Gui.mouseX = 0;
                Gui.mouseY = 0;
                characterCamera.initialize(targetTG);
            }

            characterCamera.processStimulus(targetTG);
        }
        this.wakeupOn(wk);
    }

    private PickCanvas pickCanvas;
    private BranchGroup root;
    private BranchGroup active;
    private CharacterCamera characterCamera;

    private final Point3d source = new Point3d();
            //private final Point3f destination = new Point3f();
    private final Vector3d direction = new Vector3d();
    private final PickRay ray = new PickRay();

    public void load(float dif) throws Exception{
        UserCamera.reset(dif);

        Loader s = new ws.loaders.groovy.GroovyLoader(sceneName);

        root = s.getRootNode();
        active = s.getActiveNode();
		characterCamera = s.getCharacterCamera();

        univ.getViewer().getView().setBackClipDistance( s.getBackFlipDistance() );
        Gui.detectDistance = s.getDetectDistance();

        root.setCapability(BranchGroup.ALLOW_DETACH);
        root.compile();
        gameRoot.addChild(root);

        s = null;
        System.gc();

        pickCanvas = new PickCanvas(canvas, active);

        pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
		pickCanvas.setFlags(PickInfo.NODE);
		pickCanvas.setTolerance(0.0f);

        Gui.setPicked(null);
        Gui.keys.clear();
        Gui.buttons.clear();
   //     canvas.setVisible(true);

        Ai.runAi();
        isActive = true;
    }

    private final ArrayList<Target> targets = new ArrayList<Target>();

    public void processPick(MouseEvent e){
        if(!isActive) return;

        pickCanvas.setShapeLocation(e);
        PickInfo[] pickInfoArr = pickCanvas.pickAllSorted();
        if(pickInfoArr != null){
            targets.clear();
            for(PickInfo tmp : pickInfoArr){
                Object ud = tmp.getNode().getUserData();
                if(ud != null && ud instanceof Target && ((Target)ud).isTargetActive() ){
                    targets.add( (Target)ud );
                }else break;
            }
        }
            //System.out.println(ud);
            // if(noPick.equals(ud)) continue;
        if(!targets.isEmpty()){
            Target target = null;

            for(Target tar : targets){
                UserCamera.TARGET.getUserPositionTo(source);
//                                        direction.set( tmp.getClosestIntersectionPoint() );
                tar.getTargetPoint(direction);
                direction.set(direction.x - source.x, direction.y - source.y, direction.z - source.z);

                //System.out.println(tar);
                ray.set(source, direction);
                PickInfo pickInfo[] = active.pickAllSorted(PickInfo.PICK_GEOMETRY, PickInfo.NODE, ray);
                for(PickInfo tpi : pickInfo){
                    Object n = tpi.getNode().getUserData();
                    //System.out.println(tpi.getNode()+" "+n);
                    if( UserCamera.CHARACTER.equals(n) ) continue;
                    if( tar == n ){
                        target = tar;
                        break;
                    }
                    if(!(n instanceof Target)) break;
                }
                if(target != null) break;
            }
                            //System.out.println(canPick);
            Gui.setPicked(target);


        }else Gui.setPicked(null);
    }

    public void destroy(){
        Ai.clear();
        SoundLoader.cleanUp();

        isActive = false;
        wasInit = false;
        gameRoot.removeAllChildren();
    //    canvas.setVisible(false);
        System.gc();
        //System.out.println("out");
    }

}
