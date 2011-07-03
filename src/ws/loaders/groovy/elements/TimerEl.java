package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.TimerObj;
import ws.tools.Timer;

import java.util.Map;

public final class TimerEl extends AbstractFactory {

    @Override
    public final TimerObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TimerObj obj = new TimerObj(value, attributes);

        Object o = attributes.get(SceneBuilder.time);
        if(o != null) obj.setTime( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

        o = attributes.get(SceneBuilder.mode);
        if(o != null) obj.setMode(o instanceof Timer.Mode ? (Timer.Mode) o : Timer.Mode.valueOf(o.toString()));

        o = attributes.get(SceneBuilder.enabled);
        if(o != null) obj.setEnabled(o instanceof Boolean ? (Boolean) o : Boolean.parseBoolean(o.toString()));

        o = attributes.get(SceneBuilder.loopCount);
        if(o != null) obj.setLoopCount(o instanceof Integer ? (Integer) o : Integer.parseInt(o.toString()));

        o = attributes.get(SceneBuilder.atZeroOne);
        if(o != null) obj.atOneZero( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

        o = attributes.get(SceneBuilder.atZero);
        if(o != null) obj.atZero( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

        o = attributes.get(SceneBuilder.atOne);
        if(o != null) obj.atOne( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

        o = attributes.get(SceneBuilder.ramp);
        if(o != null) obj.ramp( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

        o = attributes.get(SceneBuilder.incRamp);
        if(o != null) obj.incRamp( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

        o = attributes.get(SceneBuilder.decRamp);
        if(o != null) obj.decRamp( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

        attributes.clear();
        return obj;
    }
}
