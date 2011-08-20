//TODO: OnTime
/*
package ws.tools.controls;

import groovy.lang.Closure;

import java.util.Enumeration;

public final class OnTime extends Behavior{

    private final Closure onTime;

    public OnTime(Closure onTime) {
        this.onTime = onTime;
    }

    @Override
    public void initialize() {
        this.processStimulus(null);
    }

    @Override
    public void processStimulus(Enumeration criteria) {
        Object o = onTime.call();
        if(o == null)return;
        float f = Float.parseFloat(o.toString())* 1000f;
        this.wakeupOn(new WakeupOnElapsedTime( (long )f ));
    }
}
*/