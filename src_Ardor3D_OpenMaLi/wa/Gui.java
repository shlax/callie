package wa;

import com.ardor3d.framework.Canvas;
import com.ardor3d.framework.DisplaySettings;
import com.ardor3d.framework.FrameHandler;
import com.ardor3d.framework.jogl.JoglCanvas;
import com.ardor3d.framework.jogl.JoglCanvasRenderer;
import com.ardor3d.input.*;
import com.ardor3d.input.awt.AwtFocusWrapper;
import com.ardor3d.input.awt.AwtKeyboardWrapper;
import com.ardor3d.input.awt.AwtMouseManager;
import com.ardor3d.input.awt.AwtMouseWrapper;
import com.ardor3d.input.logical.*;
import com.ardor3d.util.ContextGarbageCollector;
import com.ardor3d.util.Timer;
import ws.ai.Target;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class Gui {

    private static class KeyPressed implements TriggerAction{
        private final int keyCode;
        private KeyPressed(int keyCode) {
            this.keyCode = keyCode;
        }

        @Override
        public void perform(Canvas canvas, TwoInputStates twoInputStates, double v) {
            keys.add(keyCode);
        }
    }

    private static class KeyReleased implements TriggerAction{
        private final int keyCode;

        private KeyReleased(int keyCode) {
            this.keyCode = keyCode;
        }

        @Override
        public void perform(Canvas canvas, TwoInputStates twoInputStates, double v) {
            keys.remove(keyCode);
        }
    }

    private static class MousePressed implements TriggerAction{
        private final int button;

        private MousePressed(int button) {
            this.button = button;
        }

        @Override
        public void perform(Canvas canvas, TwoInputStates twoInputStates, double v) {
            buttons.add(button);
        }
    }

    private static class MouseReleased implements TriggerAction{
        private final int button;

        private MouseReleased(int button) {
            this.button = button;
        }

        @Override
        public void perform(Canvas canvas, TwoInputStates twoInputStates, double v) {
            buttons.remove(button);
        }
    }

    public final static Set<Integer> keys = /* Collections.synchronizedSet(*/ new HashSet<Integer>();
	public final static Set<Integer> buttons = /* Collections.synchronizedSet( */ new HashSet<Integer>(3);

    public static float mouseX = 0;
	public static float mouseY = 0;
    public static float scroolPercent = 0;

    private static Target picked = null;
    public static Target getPicked(){ return picked; }
    public static void setPicked(Target n){ picked = n; }

    private static boolean exit = false;
    public static void exit() { exit = true; }

    public static void main(String[] args) {
        MainScene mainScene = new MainScene();
        JoglCanvasRenderer canvasRenderer = new JoglCanvasRenderer(mainScene);
        DisplaySettings settings = new DisplaySettings(800, 600, 24, 0, 0, 8, 0, 0, false, false);
        JoglCanvas canvas = new JoglCanvas(canvasRenderer, settings);
        canvas.init();

        final AwtMouseManager mouseManager = new AwtMouseManager(canvas);
        PhysicalLayer physicalLayer = new PhysicalLayer(new AwtKeyboardWrapper(canvas), new AwtMouseWrapper(canvas, mouseManager), DummyControllerWrapper.INSTANCE, new AwtFocusWrapper(canvas));
        LogicalLayer logicalLayer = new LogicalLayer();

        logicalLayer.registerTrigger(new InputTrigger( new KeyPressedCondition(Key.W), new KeyPressed(KeyEvent.VK_W) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyPressedCondition(Key.A), new KeyPressed(KeyEvent.VK_A) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyPressedCondition(Key.S), new KeyPressed(KeyEvent.VK_S) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyPressedCondition(Key.D), new KeyPressed(KeyEvent.VK_D) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyPressedCondition(Key.Q), new KeyPressed(KeyEvent.VK_Q) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyPressedCondition(Key.E), new KeyPressed(KeyEvent.VK_E) ));

        logicalLayer.registerTrigger(new InputTrigger( new KeyReleasedCondition(Key.W), new KeyReleased(KeyEvent.VK_W) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyReleasedCondition(Key.A), new KeyReleased(KeyEvent.VK_A) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyReleasedCondition(Key.S), new KeyReleased(KeyEvent.VK_S) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyReleasedCondition(Key.D), new KeyReleased(KeyEvent.VK_D) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyReleasedCondition(Key.Q), new KeyReleased(KeyEvent.VK_Q) ));
        logicalLayer.registerTrigger(new InputTrigger( new KeyReleasedCondition(Key.E), new KeyReleased(KeyEvent.VK_E) ));

        logicalLayer.registerTrigger(new InputTrigger( new MouseButtonPressedCondition(MouseButton.LEFT), new MousePressed(MouseEvent.BUTTON1) ));
        logicalLayer.registerTrigger(new InputTrigger( new MouseButtonPressedCondition(MouseButton.RIGHT), new MousePressed(MouseEvent.BUTTON3) ));

        logicalLayer.registerTrigger(new InputTrigger( new MouseButtonReleasedCondition(MouseButton.LEFT), new MouseReleased(MouseEvent.BUTTON1) ));
        logicalLayer.registerTrigger(new InputTrigger( new MouseButtonReleasedCondition(MouseButton.RIGHT), new MouseReleased(MouseEvent.BUTTON3) ));

        mouseManager.setGrabbed(GrabbedState.GRABBED);
        mouseManager.setPosition(400, 300);
        mouseManager.setGrabbed(GrabbedState.NOT_GRABBED);

        logicalLayer.registerTrigger(new InputTrigger(new MouseWheelMovedCondition(), new TriggerAction() {
            @Override
            public void perform(Canvas canvas, TwoInputStates twoInputStates, double v) {
                scroolPercent += twoInputStates.getCurrent().getMouseState().getDwheel();
            }
        }));

        logicalLayer.registerTrigger(new InputTrigger(new MouseMovedCondition(), new TriggerAction() {

            public void perform(final Canvas source, final TwoInputStates inputStates, final double tpf) {
                MouseState ms = inputStates.getCurrent().getMouseState();

                mouseX += ms.getDx();
                mouseY -= ms.getDy();

                mouseManager.setGrabbed(GrabbedState.GRABBED);
                mouseManager.setPosition(400, 300);
                mouseManager.setGrabbed(GrabbedState.NOT_GRABBED);
                //System.out.println(mouseX+" "+mouseY);
            }
        }));

        logicalLayer.registerInput(canvas, physicalLayer);

        mainScene.init(canvas, logicalLayer);

        Timer timer = new Timer();
        FrameHandler frameHandler = new FrameHandler(timer);
        frameHandler.addUpdater(mainScene);
        frameHandler.addCanvas(canvas);

        frameHandler.init();
        // Run in this same thread.
        while (!exit) {
            frameHandler.updateFrame();
            Thread.yield();
        }
        canvas.getCanvasRenderer().makeCurrentContext();

        // Done, do cleanup
        ContextGarbageCollector.doFinalCleanup(canvas.getCanvasRenderer().getRenderer());
        canvas.getCanvasRenderer().releaseCurrentContext();
        canvas.close();
    }
}
