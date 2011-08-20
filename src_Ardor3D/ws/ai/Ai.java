package ws.ai;

import groovy.lang.Closure;
import ws.ai.agents.AiWolker;
import ws.camera.UserCamera;
import ws.map.Y25Triangle;
import ws.map.ai.NodeMap;
import ws.tools.controls.Location;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Ai implements Runnable{
    public static final float EPSILON = 0.0001f;
    // private final float PI2 = (float)Math.PI/2f;

    private Ai(){}
    private static final Ai AI = new Ai();

    private static AtomicBoolean stillRun = new AtomicBoolean();
    private static Thread t;

    public static final void runAi(){
        for(AiWolker t : agents)t.initialize();

        stillRun.set(true);
        t = new Thread(AI);
        t.setName("AI");
        //t.setDaemon(true);
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }

    public static final void clear(){
        synchronized (agents){
            agents.clear();
        }
        synchronized (colision){
            colision.clear();
        }
        lastUserPosition = null;

        stillRun.set(false);
        synchronized (AI){
            AI.notifyAll();
        }

        if(t != null){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        onDettect = null;
    }

    public static final void processStimulus(){
        //System.out.println("b"+agents.size());
        synchronized (agents){
            for(int i = agents.size() -1; i>= 0; i--){
                AiWolker a = agents.get(i);
                if(!a.processStimulus()){
                    agents.remove(i);
                    synchronized (colision){
                        colision.remove(a);
                    }
                }
            }
        }
    }

    private final Float[] angles = new Float[]{ 0f, (float)Math.PI/2, (float)Math.PI, ((float)Math.PI*3)/2, 0f };

    @Override
    public void run() {
        ArrayList<AiWolker> localWolkers = new ArrayList<AiWolker>();
        while (stillRun.get()){
            //System.out.println(".");
            boolean fail = false;
            do{
                localWolkers.clear();
                synchronized (agents){
                    for (AiWolker a : agents) if(a.isTargetActive())localWolkers.add(a);
                }

                //System.out.println("AiObject .");
                for(AiWolker aw : localWolkers){
                    if(!aw.isAiContoled()){
                        Location l = aw.navigateTo();
                        if(l!= null){
                            Y25Triangle actT = aw.getActualTriangle();
                            Y25Triangle dsT = l._getDestination();
                            NodeMap[] movingMap = aw.getMovingMap();
                            NodeMap p = null;
                            NodeMap t = null;
                            for(NodeMap tmp : movingMap){
                                if(tmp.getY25Triangle() == actT) p = tmp;
                                if(tmp.getY25Triangle() == dsT) t = tmp;
                            }

                            if(t != null){
                                NodeMap[] path = p.getPath(t);
                                if(path != null){
                                    if(!aw.moveTo(path, angles, false)){
                                        fail = true;
                                        aw.navigateBack(l);
                                    }
                                }
                            }
                        }
                        continue;
                    }

                    //TODO: change AI logic
                    Y25Triangle lastUserPosition = Ai.lastUserPosition;

                    if(aw.changeUserPosition(lastUserPosition)){

                        Y25Triangle actT = aw.getActualTriangle();
                        NodeMap[] movingMap = aw.getMovingMap();
                        NodeMap p = null;
                        NodeMap t = null;
                        for(NodeMap tmp : movingMap){
                            if(tmp.getY25Triangle() == actT) p = tmp;
                            if(tmp.getY25Triangle() == lastUserPosition) t = tmp;
                        }

                        if(t != null){
                            NodeMap[] path = p.getPath(t);
                            //System.out.println(path.length);
                            if(path != null){
                            //    System.out.println(aw+" "+path[path.length - 1].getY25Triangle());
                                if(!aw.moveTo(path, angles, false)){
                                    fail = true;
                                    aw.resetUserPosition();
                                }
                            }
                        }
                        //else aw.moveTo(null, null, false);
                    }

                    //System.out.println( aw.processing()+ " "+ aw.secondaryProcessing() );
                    if(!aw.processing() || aw.secondaryProcessing()){
                        //System.out.println("AiObject ..");

                        NodeMap t = aw.getGuardTriangle();
                        if(t != null){
                            Y25Triangle actT = aw.getActualTriangle();
                            NodeMap[] movingMap = aw.getMovingMap();

                            NodeMap p = null;
                            for(NodeMap tmp : movingMap) if(tmp.getY25Triangle() == actT) p = tmp;
                            if(p == null) aw.moveTo(null, null, false);
                            else{
                                NodeMap[] path = p.getPath(t);
                                if(path != null) aw.moveTo(path, null, false);
                                else aw.moveTo(null, null, false);
                            }
                        }else if(!aw.secondaryProcessing()) aw.moveTo(null, null, true);

                    }
                }
                if(fail){
                    try {
                        AI.wait(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }while (fail);
            synchronized (AI){
                try {
                    AI.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (stillRun){
            stillRun.notifyAll();
        }
        //System.out.println("ai end");
    }

    private final static ArrayList<AiWolker> agents = new ArrayList<AiWolker>();
    private static volatile Y25Triangle lastUserPosition = null;

    public static final void addTargetWolker(AiWolker tv){
        synchronized(colision){
            colision.add(tv);
        }
        synchronized (agents){
            agents.add(tv);
        }
        synchronized (AI){
            AI.notifyAll();
        }
    }



    /*
    public static final void removeTargetWolker(Object tv){
        synchronized (agents){
            agents.remove(tv);
        }
        synchronized (AI){
            AI.notifyAll();
        }
        synchronized (colision){
            colision.remove(tv);
        }
    }
    */

    private static Closure onDettect = null;
    public static final void setOnDettect(Closure onDettect) {
        Ai.onDettect = onDettect;
    }

    public static final void userDetected(){
        Y25Triangle tmp = UserCamera.getUserY25Triangle();
        if(tmp != lastUserPosition){
            if(onDettect != null)onDettect.call(new Location(tmp));

            lastUserPosition = tmp;
           // System.out.println(lastUserPosition);
            synchronized (AI){
                AI.notifyAll();
            }
        }
    }

    public static final void notifiAI(){
        synchronized (AI){
            AI.notifyAll();
        }        
    }

    private final static Point3f colPoint = new Point3f();
    private final static Point3f userPoint = new Point3f();

    public static final float getFirst(){
        float min = Float.MAX_VALUE;
        UserCamera.getUserPositionTo(userPoint);
        //boolean seted = false;
        synchronized(agents){
            for(Colision tmp : agents){
                tmp.getPosition(colPoint);
                float dst = userPoint.distanceSquared(colPoint);
                if(dst < min) min = dst;
            }
        }
        //if(!seted) lastFirst.set(Float.MAX_VALUE, Float.MIN_VALUE, Float.MAX_VALUE);
        return min == Float.MAX_VALUE ? Float.MAX_VALUE : (float)Math.sqrt(min);
    }
    /*
    private static final Point3f lastFirst = new Point3f();

    public static final void getLatFirstPoint(Tuple3f p){
        synchronized (lastFirst){
            p.set(lastFirst);
        }
    }
    */
    private static final ArrayList<Colision> colision = new ArrayList<Colision>();

    public static final Colision getObjectColision(Tuple3f a, float r){
        synchronized(colision){
            for(Colision tmp : colision) if(tmp.testColision(a, r)) return tmp;
            return null;
        }
    }

}
