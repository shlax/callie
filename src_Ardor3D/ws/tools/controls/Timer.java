//TODO: timer
/* package ws.tools.controls;

public final class Timer {
    public enum Mode{INC, DEC, INCDEC }

    private final Alpha alpha; // = new Alpha();

    public Timer(Alpha alpha) {
        this.alpha = alpha;
    }

    public final void pause(){
        alpha.pause();
    }

    public final void pause(float time){
        alpha.pause( System.currentTimeMillis() + ((long)(time*1000)) );
    }

    public final boolean paused(){
        return alpha.isPaused();
    }

    public final void start(){
        if(alpha.finished())alpha.setStartTime( System.currentTimeMillis() );
        alpha.resume();
    }

    public final void start(float time){
        alpha.resume( System.currentTimeMillis() +  ((long)(time*1000)));
    }

    public final void loop(int count){
        alpha.setLoopCount(count);
    }

    public final void loop(){
        alpha.setLoopCount(-1);
    }

    public final void setMode(Mode mod){
        if (mod == Mode.INCDEC) alpha.setMode(Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE);
        else if (mod == Mode.INC ) alpha.setMode(Alpha.INCREASING_ENABLE );
        else if (mod == Mode.DEC ) alpha.setMode(Alpha.DECREASING_ENABLE );
    }

    public final void atZeroOne(float t){
        atOne(t);
        atZero(t);
    }

    public final void atOne(float alphaAtOneDuration){
        alpha.setAlphaAtOneDuration((long)(alphaAtOneDuration*1000));
    }

    public final void atZero(float alphaAtZeroDuration){
        alpha.setAlphaAtZeroDuration((long)(alphaAtZeroDuration*1000));
    }

    public final void time(float t){
        incTime(t);
        decTime(t);
    }

    public final void decTime(float decreasingAlphaDuration){
        alpha.setDecreasingAlphaDuration((long)(decreasingAlphaDuration*1000));
    }

    public final void incTime(float increasingAlphaDuration){
        alpha.setIncreasingAlphaDuration((long)(increasingAlphaDuration*1000));
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

    public final float value(){
        return alpha.value();
    }

    public float value(float time){
        return alpha.value( alpha.getStartTime() + ((long)(time*1000)));
    }
}
*/