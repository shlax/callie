package ws;

import com.sun.j3d.utils.universe.SimpleUniverse;
import ws.ai.Ai;
import ws.ai.Target;
import ws.camera.AnimationCamera;
import ws.camera.UserCamera;

import javax.media.j3d.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public final class Gui {
    // public static final String noPick = "!pick";

    private Gui(){} 
    // public static final BoundingSphere region = new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE);

	public final static Set<Integer> keys = Collections.synchronizedSet(new HashSet<Integer>());
	public final static Set<Integer> buttons = Collections.synchronizedSet(new HashSet<Integer>(3));

	public static float mouseX = 0;
	public static float mouseY = 0;

	public static float scroolPercent = 0;

	private static Target picked = null;
    public static synchronized Target getPicked(){
        return picked;
    }

    public static synchronized void setPicked(Target n){
        picked = n;
    }

	public static int width = 320;// = canvas.getWidth()/2;
	public static int height = 240;// = canvas.getHeight()/2;

    public static int width2 = width/2;// = canvas.getWidth()/2;
	public static int height2 = height/2;// = canvas.getHeight()/2;
    
	private static int x = width /2;// = canvas.getLocationOnScreen().x+width;
	private static int y = height /2;// = canvas.getLocationOnScreen().y+height;

    private static boolean isActice = true;

    private static int signX = 1;
    private static int signY = 1;

    public static float detectDistance = 0;

    private static Scene scene = null;

	public static void main(String[] args) throws Exception{
        Properties prop = new Properties();
        InputStream is = Gui.class.getClassLoader().getResourceAsStream("gui.properties");
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        is = null;

        String sceneName = prop.getProperty("scene");
        ResourceHandle.local = Boolean.valueOf(prop.getProperty("local"));
        ResourceHandle.bin = Boolean.valueOf(prop.getProperty("bin"));

        boolean fullSc = Boolean.valueOf(prop.getProperty("fullscreen"));
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();


        if(fullSc){
            width = gd.getDisplayMode().getWidth();
            height = gd.getDisplayMode().getHeight();
        }else{
            width = Integer.parseInt(prop.getProperty("width"));
            height = Integer.parseInt(prop.getProperty("height"));
        }

        width2 = width/2;
        height2 = height/2;

        prop = null;

        /*tg.setCapability(TransformGroupObject.ALLOW_TRANSFORM_WRITE);
        tg.addChild(new Sphere(0.25f)); */
        //XmlLoader s = new XmlLoader("data/level-01.tools");
        //Loader s = new ws.loaders.groovy.GroovyLoader("data/emptyScene.groovy");

        //Loader s = new ws.loaders.groovy.GroovyLoader("data/scene.groovy");

        //Loader s = new ws.loaders.xml.XmlLoader("data/game/level-01/level-01.xml");;

        final Image endImage = new ImageIcon(ResourceHandle.getURL("data/endScreen.png")).getImage();

		final Cursor system = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		final Cursor target = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		final Cursor normal = java.awt.Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), "none");

        /*
        public final static Set<Integer> keys = Collections.synchronizedSet(new HashSet<Integer>());
	    public final static Set<Integer> buttons = Collections.synchronizedSet(new HashSet<Integer>(3));
        */

		final Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration()){
			private static final long serialVersionUID = 1L;

            private float alphaDeadScreen = 0f;

            // init foo object 
            private final GraphicsContext3D graphicsContext3D  = getGraphicsContext3D();
            private final TriangleArray foo = new TriangleArray(3, TriangleArray.COORDINATES);
            {
                graphicsContext3D.setAppearance(new Appearance());
                foo.setCoordinate(0, new float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f});
            }

            private final J3DGraphics2D g = this.getGraphics2D();
            
			@Override
			public void postRender() {
                // draw foo object
                graphicsContext3D.draw(foo);

                // show keys buttons
                /*
                g.setColor(Color.BLUE);
                StringBuilder sb = new StringBuilder();
                for(Integer k : keys){
                    if( k == KeyEvent.VK_W)sb.append("[W] ");
                    else if( k == KeyEvent.VK_E)sb.append("[E] ");
                    else if( k == KeyEvent.VK_SPACE)sb.append("[SPACE] ");
                }
                if(buttons.contains(MouseEvent.BUTTON1)) sb.append("[x|");
                else sb.append("[ |");

                if(buttons.contains(MouseEvent.BUTTON3)) sb.append("x]");
                else sb.append(" ]");
                g.setFont(g.getFont().deriveFont(Font.BOLD));
                g.drawString(sb.toString(), 175, 19);
                */
                //

                if(UserCamera.isTargetActiveStatic()){
                    g.setColor(Color.BLACK);
                    g.drawRect(9, 9, 100 ,11);
                    g.drawRect(114, 9, 51 ,11);
                    g.drawRect(169, 9, 11 ,11);

                    g.setColor(Color.ORANGE);
                    g.fillRect(10, 10, (int)(UserCamera.getLive()*66f),10);

                    if(AnimationCamera.isActionPosible){
                        g.setColor(Color.WHITE);
                        g.fillRect(170, 10, 10,10);
                    }

                    float dst = Ai.getFirst();
                    if(dst < detectDistance){
                        g.setColor( Color.BLUE );
                        g.fillRect(115, 10, (int)(50f - ((dst / detectDistance) * 50f)), 10);
                    }
                }else{
                    alphaDeadScreen += alphaDeadScreen == 0f ? 0.0001 : (alphaDeadScreen > 0.01f ?  0.01f : alphaDeadScreen); // TODO dead screen
                    if(alphaDeadScreen > 1f) alphaDeadScreen = 1f;
                    g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OUT, alphaDeadScreen) );                    
                    g.drawImage(endImage, 0, 0, null);
                }

				g.flush(false);			
			}
		};

        //System.out.println(canvas.getStereoAvailable() );

		canvas.setCursor(normal);
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));

        final SimpleUniverse univ = new SimpleUniverse(canvas);

        univ.getViewer().getView().setFieldOfView( 52.5 * Math.PI / 180.0 );
        univ.getViewer().getView().setFrontClipDistance(0.1);
        //univ.getViewer().getView().setBackClipDistance(backFlipDistance);
		univ.getViewer().getView().setMinimumFrameCycleTime(30); // = 30 Hz

        //univ.getViewer().getView().setSceneAntialiasingEnable(true);
        //univ.getViewer().getView().setDepthBufferFreezeTransparent(false);
        //univ.getViewer().getView().setTransparencySortingPolicy(View.TRANSPARENCY_SORT_GEOMETRY);
        
        univ.getViewingPlatform().setNominalViewingTransform();

        scene = new Scene(sceneName, canvas, univ);

		final Robot r = new Robot();



        // -------------------------------------------------------------------------------------

        ComponentListener canvasComponentListener = new ComponentListener(){
			@Override
			public final void componentHidden(ComponentEvent e) {}
            @Override
			public final void componentShown(ComponentEvent e) {}

			@Override
			public final void componentMoved(ComponentEvent e) {
                moveResize();
			}
			@Override
			public final void componentResized(ComponentEvent e) {
               moveResize();
			}

            private final void moveResize(){
                x = canvas.getLocationOnScreen().x+(canvas.getWidth()/2);
		        y = canvas.getLocationOnScreen().y+(canvas.getHeight()/2);
            }
		};
		canvas.addComponentListener(canvasComponentListener);

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
                    canvas.setCursor(system);
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
                    Gui.mouseX += ((float)(signX*(e.getPoint().x - width2)))/200f;
                    Gui.mouseY += ((float)(signY*(e.getPoint().y - height2)))/200f;
                    r.mouseMove(x, y);                    
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
                scroolPercent += e.getWheelRotation();
            }
        });

		canvas.addMouseListener(new MouseListener(){
            
			@Override
			public final void mouseReleased(MouseEvent e) {
				buttons.remove(e.getButton());
				if(e.getButton() == MouseEvent.BUTTON3){
					r.mouseMove(x, y);
					canvas.setCursor(normal);
                    setPicked(null);
				}
			}

			@Override
			public final void mousePressed(MouseEvent e) {
                if(!isActice){
                    isActice = true;
                    canvas.setCursor(normal);
                }else{
                    buttons.add(e.getButton());
                    if(e.getButton() == MouseEvent.BUTTON1){
                        Target pred = getPicked();
                        if(pred == null || !pred.isTargetActive()){
                            scene.processPick(e);
                        }
                    }else if(UserCamera.hasWeapon() && e.getButton() == MouseEvent.BUTTON3) canvas.setCursor(target);                    
                }
			}

			@Override
			public final void mouseClicked(MouseEvent e) {}

			@Override
			public final void mouseExited(MouseEvent e) {
				isActice = false;
                buttons.clear();
				keys.clear();
                canvas.setCursor(system);
			}

			@Override
			public final void mouseEntered(MouseEvent e) {}
		});

        //root.addChild(tg);
		//root.compile();

        // frame
        Frame f = new Frame();
        f.setResizable(false);
        f.setLayout(new BorderLayout());
        f.add(canvas, BorderLayout.CENTER);

        f.addWindowListener(new WindowAdapter(){
                @Override
                public final void windowClosing(WindowEvent e) {
                    scene.destroy();
                    e.getWindow().setVisible(false);
                    univ.cleanup();
                    e.getWindow().dispose();

                    System.exit(0);
                }
            });

        r.mouseMove(gd.getDisplayMode().getWidth()/2, gd.getDisplayMode().getHeight()/2);

        if(fullSc){
            f.setUndecorated(true);
            
            f.setLocation(0,0);
            f.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());

          //canvas.setVisible(false);
            f.setVisible(true);            
            //gd.setFullScreenWindow(f); // don't work  ;)
        }else{
            MenuBar mb = new MenuBar();
            f.setMenuBar(mb);

            Menu gameMenu = new Menu("Game");
            mb.add(gameMenu);
            //for(final String arg : args){
            {
                MenuItem reset = new MenuItem("restart easy");
                gameMenu.add(reset);

                reset.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        scene.destroy();
                        try {
                            scene.load(27f);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }{
                MenuItem reset = new MenuItem("restart normal");
                gameMenu.add(reset);

                reset.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        scene.destroy();
                        try {
                            scene.load(1f);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
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

            f.setTitle("[Esc] release mouse");
            f.addComponentListener(canvasComponentListener);

            f.pack();
            f.setLocation((gd.getDisplayMode().getWidth()/2)-(f.getWidth()/2), (gd.getDisplayMode().getHeight()/2)-(f.getHeight()/2));

         //   canvas.setVisible(false);
            f.setVisible(true);
        }

//        Ai.runAi();
        //scene = new Scene(args[0], canvas, univ);
        scene.load(27f );
	}

}
