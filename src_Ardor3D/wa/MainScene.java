package wa;

import com.ardor3d.framework.Scene;
import com.ardor3d.framework.Updater;
import com.ardor3d.framework.jogl.JoglCanvas;
import com.ardor3d.image.util.AWTImageLoader;
import com.ardor3d.intersection.*;
import com.ardor3d.math.ColorRGBA;
import com.ardor3d.math.Ray3;
import com.ardor3d.math.Vector3;
import com.ardor3d.renderer.Camera;
import com.ardor3d.renderer.Renderer;
import com.ardor3d.renderer.state.*;
import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.Node;
import com.ardor3d.util.ContextGarbageCollector;
import com.ardor3d.util.GameTaskQueue;
import com.ardor3d.util.GameTaskQueueManager;
import com.ardor3d.util.ReadOnlyTimer;
import com.ardor3d.util.resource.ResourceLocatorTool;
import com.ardor3d.util.resource.SimpleResourceLocator;
import ws.ResourceHandle;
import ws.ai.Ai;
import ws.ai.Target;
import ws.camera.CharacterCamera;
import ws.camera.UserCamera;
import ws.loaders.Loader;
import ws.loaders.tools.SoundLoader;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public final class MainScene implements Scene, Updater{

    private boolean activeScene = false;

    //private Loader s;

    public final void start(float diff, String file) {
        synchronized (this){
            activeScene = false;
        }
        Ai.clear();
        UserCamera.reset(diff);
        SoundLoader.cleanUp();
        deadTime = -1;

        try {
            {
                Loader s = new ws.loaders.groovy.GroovyLoader(file);

                root = s.getRootNode();
                active = s.getActiveNode();
                characterCamera = s.getCharacterCamera();

            }{
                System.runFinalization();
                System.gc();
            }

            // Create a ZBuffer to display pixels closest to the camera above farther ones.
            final ZBufferState buf = new ZBufferState();
            buf.setEnabled(true);
            buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
            root.setRenderState(buf);

            Ai.runAi();
            characterCamera.initialize(camera);

            //new com.ardor3d.extension.model.util.nvtristrip.NvTriangleStripper().visit(root);
            root.updateGeometricState(0);

          //  s.postInit();

        } catch (IOException e) {
            e.printStackTrace();
        }


        synchronized (this){
            activeScene = true;
        }
    }

    private JoglCanvas canvas;
   // private LogicalLayer logicalLayer;
    public final void init(JoglCanvas canvas/*, LogicalLayer logicalLayer*/){
        this.canvas = canvas;
        //this.logicalLayer = logicalLayer;
    }

    private Node root; // = new Node();
    private Node active; // = new Node();

    @Override
    public final void init() {

        // Add our awt based image loader.
        AWTImageLoader.registerLoader();

        // Set the location of our example resources.
        try {
            SimpleResourceLocator srl = new SimpleResourceLocator(ResourceHandle.local ? new File("data").getAbsoluteFile().toURI().toURL() :  ResourceLocatorTool.getClassPathResource(ws.ResourceHandle.class, "data/"));
            ResourceLocatorTool.addResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, srl);
        } catch (final URISyntaxException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        camera = canvas.getCanvasRenderer().getCamera();

        camera.setFrustumPerspective(50d, ((double)canvas.getWidth())/((double)canvas.getHeight()), 0.8d, Short.MAX_VALUE);
        //registerInputTriggers();
                // create scene

    }

    private CharacterCamera characterCamera;

    private Camera camera;

    @Override
    public final void update(ReadOnlyTimer timer) {
        if (canvas.isClosing()) { Gui.exit(); }

        //logicalLayer.checkTriggers(timer.getTimePerFrame());

        // Execute updateQueue item
        GameTaskQueueManager.getManager(canvas.getCanvasRenderer().getRenderContext()).getQueue(GameTaskQueue.UPDATE).execute();

        synchronized (this){
            if(activeScene){
                Ai.processStimulus();
                characterCamera.processStimulus(camera);
            }
        }

        root.updateGeometricState(timer.getTimePerFrame(), true);
    }

    private long deadTime = -1;
    private final FogState fs = new FogState();
    {
        fs.setColor(new ColorRGBA(0,0,0,1));
        fs.setStart(0.80f);
        fs.setEnd(0.81f);
        fs.setDensityFunction(FogState.DensityFunction.Linear);
    }

    @Override
    public final boolean renderUnto(Renderer renderer) {
        // Execute renderQueue item
        GameTaskQueueManager.getManager(canvas.getCanvasRenderer().getRenderContext()).getQueue(GameTaskQueue.RENDER).execute(renderer);

        // Clean up card garbage such as textures, vbos, etc.
        ContextGarbageCollector.doRuntimeCleanup(renderer);

        if (!canvas.isClosing()) {
            // Draw the root and all its children.
            synchronized (this){
                if(activeScene){
                    if(!UserCamera.isTargetActiveStatic()){
                        if(deadTime < 0) deadTime  = System.nanoTime();
                        float density = System.nanoTime() - deadTime;
                        density /= 100000000f;
                        if(density > 10)density = 10;
                        //System.out.println(density);
                        fs.setEnd(0.81f + (10-density) );
                        //if(density > 1) System.out.println('a');
                        root.setRenderState(fs);
                    }
                    renderer.draw(root);
                }
            }
            return true;
        }else return false;

    }

    private final Ray3 pickRay = new Ray3();
    private final ArrayList<Target> targets = new ArrayList<Target>();

    private final Point3f source = new Point3f();
            //private final Point3f destination = new Point3f();
    private final Vector3f direction = new Vector3f();

    public final void processPick() {
        targets.clear();

        {
            pickRay.setOrigin(camera.getLocation());
            pickRay.setDirection(camera.getDirection());

            // do pick and move the sphere
            PrimitivePickResults pickResults = new PrimitivePickResults();
           // pickResults.setCheckDistance(true);
            PickingUtil.findPick(active, pickRay, pickResults);
            for(int  i = 0; i < pickResults.getNumber(); i++){
                PickData tmp = pickResults.getPickData(i);
                Pickable p = tmp.getTarget();
                if(p instanceof Mesh){
                    Mesh s  = (Mesh)p;
                    Object ud = s.getUserData();
                    if(ud == UserCamera.CHARACTER)continue;

                    if(ud != null && ud instanceof Target && ((Target)ud).isTargetActive() ){
                        targets.add( (Target)ud );
                    }else break;
                }
            }
        }

        if(!targets.isEmpty()){
            Target target = null;

            for(Target tar : targets){
                UserCamera.TARGET.getUserShotPositionTo(source);
//                                        direction.set( tmp.getClosestIntersectionPoint() );
                tar.getTargetPoint(direction);
                direction.set(direction.x - source.x, direction.y - source.y, direction.z - source.z);

                //System.out.println(tar);
                pickRay.setOrigin( new Vector3(source.x, source.y, source.z) );
                pickRay.setDirection( new Vector3(direction.x, direction.y, direction.z)  );

                PrimitivePickResults pickResults = new PrimitivePickResults();
                pickResults.setCheckDistance(true);
                PickingUtil.findPick(active, pickRay, pickResults);
                for(int i = 0; i <pickResults.getNumber(); i++){
                    PickData tmp = pickResults.getPickData(i);
                    //System.out.println(tmp.getIntersectionRecord().getClosestDistance());
                    Pickable p = tmp.getTarget();
                    if(p instanceof Mesh){
                        Mesh s  = (Mesh)p;
                        Object ud = s.getUserData();
                        if(ud == UserCamera.CHARACTER) continue;

                        if( tar == ud ){
                            target = tar;
                            break;
                        }else if(!(ud instanceof Target)) break;
                    }
                }
            }
            //System.out.println(canPick);
            //System.out.println(target);
            Gui.setPicked(target);


        }else Gui.setPicked(null);
    }

    @Override
    public final PickResults doPick(Ray3 ray3) { return null; }
}
