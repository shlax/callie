package wa;

import com.ardor3d.bounding.BoundingBox;
import com.ardor3d.framework.Scene;
import com.ardor3d.framework.Updater;
import com.ardor3d.framework.jogl.JoglCanvas;
import com.ardor3d.image.util.AWTImageLoader;
import com.ardor3d.input.logical.LogicalLayer;
import com.ardor3d.intersection.PickResults;
import com.ardor3d.math.MathUtils;
import com.ardor3d.math.Matrix3;
import com.ardor3d.math.Ray3;
import com.ardor3d.math.Vector3;
import com.ardor3d.renderer.Camera;
import com.ardor3d.renderer.Renderer;
import com.ardor3d.renderer.state.ZBufferState;
import com.ardor3d.scenegraph.Node;
import com.ardor3d.scenegraph.controller.SpatialController;
import com.ardor3d.scenegraph.shape.Box;
import com.ardor3d.util.*;
import com.ardor3d.util.resource.ResourceLocatorTool;
import com.ardor3d.util.resource.SimpleResourceLocator;
import ws.ai.Ai;
import ws.camera.CharacterCamera;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class MainScene implements Scene, Updater{

    private JoglCanvas canvas;
    private LogicalLayer logicalLayer;
    public void init(JoglCanvas canvas, LogicalLayer logicalLayer){
        this.canvas = canvas;
        this.logicalLayer = logicalLayer;
    }

    private final Node root = new Node();

    @Override
    public void init() {

        // create scene

        //registerInputTriggers();

        // Add our awt based image loader.
        AWTImageLoader.registerLoader();

        camera = canvas.getCanvasRenderer().getCamera();
        // Set the location of our example resources.
        try {
            final SimpleResourceLocator srl = new SimpleResourceLocator(new File("data").toURI().toURL());
            ResourceLocatorTool.addResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, srl);
        } catch (final URISyntaxException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Create a ZBuffer to display pixels closest to the camera above farther ones.
        final ZBufferState buf = new ZBufferState();
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
        root.setRenderState(buf);



        // ---- LIGHTS

        /*{// init example
            // tmp init
            final Box _box = new Box("Box", Vector3.ZERO, 1, 1, 1);
            // Make it a bit more colorful.
            _box.setRandomColors();
            // Setup a bounding box for it.
            _box.setModelBound(new BoundingBox());
            // Set its location in space.
            _box.setTranslation(new Vector3(0, 0, 0));
            // Add to root.
            root.attachChild(_box);


            _box.addController(new SpatialController<Box>() {
                private static final long serialVersionUID = 1L;
                private final Vector3 _axis = new Vector3(1, 1, 0.5f).normalizeLocal();
                private final Matrix3 _rotate = new Matrix3();
                private double _angle = 0;

                public void update(final double time, final Box caller) {
                    // update our rotation
                    _angle = _angle + (time * 25);
                    if (_angle > 180) {
                        _angle = -180;
                    }

                    _rotate.fromAngleNormalAxis(_angle * MathUtils.DEG_TO_RAD, _axis);
                    _box.setRotation(_rotate);
                }
            });
        }*/



        Ai.runAi();
        characterCamera.initialize(camera);

        root.updateGeometricState(0);
    }

    private CharacterCamera characterCamera;

    private Camera camera;

    @Override
    public void update(ReadOnlyTimer timer) {
        if (canvas.isClosing()) { Gui.exit(); }

        logicalLayer.checkTriggers(timer.getTimePerFrame());

        // Execute updateQueue item
        GameTaskQueueManager.getManager(canvas.getCanvasRenderer().getRenderContext()).getQueue(GameTaskQueue.UPDATE).execute();


        Ai.processStimulus();
        //TODO: camera
        characterCamera.processStimulus(camera);

        //MathUtils.matrixLookAt(, at, up, rotationMatrix);
        //canvas.getCanvasRenderer().getCamera().setLocation( new Vector3(3, 6, 3) );
        //canvas.getCanvasRenderer().getCamera().lookAt(new Vector3(), new Vector3(0,1,0));
        /** Call simpleUpdate in any derived classes of ExampleBase. */
        //updateExample(timer);

        root.updateGeometricState(timer.getTimePerFrame(), true);
    }

    @Override
    public boolean renderUnto(Renderer renderer) {
        // Execute renderQueue item
        GameTaskQueueManager.getManager(canvas.getCanvasRenderer().getRenderContext()).getQueue(GameTaskQueue.RENDER).execute(renderer);

        // Clean up card garbage such as textures, vbos, etc.
        ContextGarbageCollector.doRuntimeCleanup(renderer);

        if (!canvas.isClosing()) {
            // Draw the root and all its children.
            renderer.draw(root);
            return true;
        }else return false;

    }

    @Override
    public PickResults doPick(Ray3 ray3) {
        return null;
    }
}
