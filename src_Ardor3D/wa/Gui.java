package wa;

import com.ardor3d.framework.DisplaySettings;
import com.ardor3d.framework.FrameHandler;
import com.ardor3d.framework.jogl.JoglAwtCanvas;
import com.ardor3d.framework.jogl.JoglCanvas;
import com.ardor3d.framework.jogl.JoglCanvasRenderer;
import com.ardor3d.util.ContextGarbageCollector;
import com.ardor3d.util.Timer;
import ws.ai.Ai;
import ws.ai.Target;
import ws.camera.AnimationCamera;
import ws.camera.CoverCamera;
import ws.camera.UserCamera;
import ws.loaders.tools.SoundLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Gui{

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

    private static boolean isActice = true;
    private static int signX = 1;
    private static int signY = 1;
    private static float cameraSpeed = 1f/500f;

    //private static boolean restart = false;

    public static void main(String[] args) throws AWTException {
        final Robot r = new Robot();

        final MainScene mainScene = new MainScene();
        JoglCanvasRenderer canvasRenderer = new JoglCanvasRenderer(mainScene);
        DisplaySettings settings = new DisplaySettings(800, 600, 24, 0, 0, 8, 0, 0, false, false);
        JoglCanvas canvas = new JoglCanvas(canvasRenderer, settings);
        canvas.setVSyncEnabled(true);

        {
            MenuBar mb = new MenuBar();
            canvas.setMenuBar(mb);

            Menu gameMenu = new Menu("Game");
            mb.add(gameMenu);
            //for(final String arg : args){
            {
                MenuItem reset = new MenuItem("restart easy");
                gameMenu.add(reset);

                reset.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mainScene.start(27f, "data/house.groovy");
                    }
                });
            }{
                MenuItem reset = new MenuItem("restart normal");
                gameMenu.add(reset);

                reset.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mainScene.start(1f, "data/house.groovy");
                    }
                });
            }
            //}

            Menu cameraMenu = new Menu("Camera");
            mb.add(cameraMenu);
            CheckboxMenuItem miInverseX = new CheckboxMenuItem("inverse X", false);
            cameraMenu.add(miInverseX);
            CheckboxMenuItem miInverseY = new CheckboxMenuItem("inverse Y", false);
            cameraMenu.add(miInverseY);

            miInverseX.addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    signX *= -1;
                }
            });
            miInverseY.addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    signY *= -1;
                }
            });

            MenuItem cSpeed = new MenuItem("speed");
            cameraMenu.add(cSpeed);

            cSpeed.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String in = JOptionPane.showInputDialog("speed 1/?", "500");
                    if(in != null){
                        float ns = Float.parseFloat(in);
                        cameraSpeed = 1f/ns;
                    }
                }
            });

            canvas.setTitle("[Esc] release mouse");
        }
        final Canvas l = new Canvas(){

            private final float detectDistance = 25f;

            private final BufferedImage bi = new BufferedImage(800, 14, BufferedImage.TYPE_INT_RGB);
            private final Graphics2D g = bi.createGraphics();

            @Override
            public void update(Graphics gg) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, 800, 14);

                g.setColor(Color.BLACK);
                g.drawRect(2, 1, 100 ,11);
                g.drawRect(107, 1, 51 ,11);
                g.drawRect(162, 1, 11 ,11);

                g.setColor(Color.ORANGE);
                g.fillRect(3, 2, (int)(UserCamera.getLive()*66f),10);

                if(AnimationCamera.isActionPosible || CoverCamera.isActionPosible){
                    g.setColor(Color.MAGENTA);
                    g.fillRect(163, 2, 10,10);
                }

                float dst = Ai.getFirst();
                if(dst < detectDistance){
                    g.setColor( Color.BLUE );
                    g.fillRect(108, 2, (int)(50f - ((dst / detectDistance) * 50f)), 10);
                }

                gg.drawImage(bi, 0, 0, null);
            }
        };

        javax.swing.Timer tim = new  javax.swing.Timer(1000/60 ,new ActionListener ()                                       {
            public void actionPerformed (ActionEvent e) {
                l.repaint();
            }
        });

        l.setMinimumSize(new Dimension(800, 14));
        l.setPreferredSize(new Dimension(800, 14));
        l.setMaximumSize(new Dimension(800, 14));

        canvas.add(l, BorderLayout.SOUTH);

        canvas.init();

        Component comps[] = canvas.getComponents();
        JoglAwtCanvas jAwt = null;
        for(Component c : comps){
            if(c instanceof JoglAwtCanvas){
                jAwt = (JoglAwtCanvas)c;
                break;
            }
        }

        if(jAwt != null){
            final JoglAwtCanvas intCanvas = jAwt;

            final Cursor system = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
            final Cursor target = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
            final Cursor normal = java.awt.Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), "none");

            intCanvas.setCursor(normal);

            canvas.addKeyListener(new KeyAdapter(){
                @Override
                public final void keyPressed(KeyEvent e) {
                    /* if(e.getKeyCode() == KeyEvent.VK_M){
                        Point3f p = new Point3f();
                        UserCamera.getUserPosition(p);
                        System.out.println(p);
                    } */
                    if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                        isActice = false;
                        buttons.clear();
                        keys.clear();
                        intCanvas.setCursor(system);
                    }else if(isActice) Gui.keys.add(e.getKeyCode());
                }
                @Override
                public final void keyReleased(KeyEvent e) {
                    Gui.keys.remove(e.getKeyCode());
                    //if(e.getKeyCode() == KeyEvent.VK_Q) setPicked(null);
                }
            });



            canvas.addMouseMotionListener(new MouseMotionListener(){
                @Override
                public final void mouseMoved(MouseEvent e) {
                    if(isActice/* && !Gui.keys.contains(KeyEvent.VK_ALT) */){
                        Dimension s = intCanvas.getSize();

                        Gui.mouseX += ((float)(signX*(e.getPoint().x - (s.width/2))))*cameraSpeed;
                        Gui.mouseY += ((float)(signY*(e.getPoint().y - (s.height/2))))*cameraSpeed;

                        Point p = intCanvas.getLocationOnScreen();

                        r.mouseMove(p.x + ( s.width/2 ), p.y+ (s.height / 2));
                    }
                }

                @Override
                public final void mouseDragged(MouseEvent e) {
                    mouseMoved(e);
                }
            });

            canvas.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public final void mouseWheelMoved(MouseWheelEvent e) {
                    scroolPercent += ((float)e.getWheelRotation())/10f;
                }
            });
                        //BasicText.createDefaultTextLabel(
            canvas.addMouseListener(new MouseListener() {

                @Override
                public final void mouseReleased(MouseEvent e) {
                    buttons.remove(e.getButton());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        Point p = intCanvas.getLocationOnScreen();
                        Dimension s = intCanvas.getSize();
                        r.mouseMove(p.x + (s.width / 2), p.y + (s.height / 2));
                        intCanvas.setCursor(normal);
                        setPicked(null);
                    }
                }

                @Override
                public final void mousePressed(MouseEvent e) {
                    if (!isActice) {
                        isActice = true;
                        intCanvas.setCursor(normal);
                    } else {
                        buttons.add(e.getButton());
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            Target pred = getPicked();
                            if (pred == null || !pred.isTargetActive()) {
                                //TODO pick process:
                                mainScene.processPick();
                            }
                        } else if (UserCamera.hasWeapon() && e.getButton() == MouseEvent.BUTTON3) intCanvas.setCursor(target);
                    }
                }

                @Override
                public final void mouseClicked(MouseEvent e) {
                }

                @Override
                public final void mouseExited(MouseEvent e) {
                    isActice = false;
                    buttons.clear();
                    keys.clear();
                    intCanvas.setCursor(system);
                }

                @Override
                public final void mouseEntered(MouseEvent e) {}
            });
        }

        mainScene.init(canvas);

        Timer timer = new Timer();
        FrameHandler frameHandler = new FrameHandler(timer);
        frameHandler.addUpdater(mainScene);
        frameHandler.addCanvas(canvas);

        frameHandler.init();

        mainScene.start(27f, "data/house.groovy");
        tim.start();

        // Run in this same thread.
        while (!exit) {
            frameHandler.updateFrame();
            Thread.yield();
        }

        tim.stop();
        //System.out.println("a");
        Ai.clear();
        SoundLoader.cleanUp();
        //System.out.println("b");
        canvas.getCanvasRenderer().makeCurrentContext();

        // Done, do cleanup
        ContextGarbageCollector.doFinalCleanup(canvas.getCanvasRenderer().getRenderer());
        canvas.getCanvasRenderer().releaseCurrentContext();
        canvas.close();

        //cleunup
        //System.gc();
    }

}
