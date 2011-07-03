package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;
import ws.tools.Timer;

import javax.media.j3d.Alpha;
import java.util.Map;

public final class TimerObj  extends FactoryElement {
    // private static final BoundingSphere bs = new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE);
    public TimerObj(Object value, Map attributes) {
        super(value, attributes);
        //System.out.println("cccccc");
    }

    private final Alpha alpha = new Alpha();

    private boolean enabled = true;
    public final void setEnabled(boolean enabled) {
        //System.out.println("b "+enabled);
        this.enabled = enabled;
    }

    public final void setLoopCount(int loop){
        alpha.setLoopCount(loop);
    }

    public final void setTime(float time){
        setIncTime(time);
        setDecTime(time);
    }

    public final void setIncTime(float time){
        alpha.setIncreasingAlphaDuration((long)(time*1000));
    }

    public final void setDecTime(float time){
        alpha.setDecreasingAlphaDuration((long)(time*1000));
    }

    public final void atOneZero(float t){
        atOne(t);
        atZero(t);
    }

    public final void atOne(float alphaAtOneDuration){
        alpha.setAlphaAtOneDuration((long)(alphaAtOneDuration*1000));
    }

    public final void atZero(float alphaAtZeroDuration){
        alpha.setAlphaAtZeroDuration((long)(alphaAtZeroDuration*1000));
    }

    public final void ramp(float t){
        decRamp(t);
        incRamp(t);
    }

    public final void decRamp(float decreasingAlphaRampDuration){
        alpha.setDecreasingAlphaRampDuration((long)(decreasingAlphaRampDuration*1000));
    }

    public final void incRamp(float increasingAlphaRampDuration){
        alpha.setIncreasingAlphaRampDuration((long)(increasingAlphaRampDuration*1000));
    }

    public final void setMode(Timer.Mode mod){
        if (mod == Timer.Mode.INCDEC) alpha.setMode(Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE);
        else if (mod == Timer.Mode.INC ) alpha.setMode(Alpha.INCREASING_ENABLE );
        else if (mod == Timer.Mode.DEC ) alpha.setMode(Alpha.DECREASING_ENABLE );
    }

    public final Alpha getAlpha() {
        return alpha;
    }

    private Timer t = null;
    public final Timer control(){
        if(t == null){
            if (!enabled){
                alpha.pause(alpha.getStartTime());
                //System.out.println("a");
            }
            t = new Timer(alpha);
        }
        return t;
    }
}
