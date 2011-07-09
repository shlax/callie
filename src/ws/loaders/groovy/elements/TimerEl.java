package ws.loaders.groovy.elements;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.TimerObj;
import ws.tools.controls.Timer;

import java.util.Map;

public final class TimerEl extends AbstractFactory implements Doc{

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr(null, "time", "Float", "1f", "set increasing and decreasing time //second"),
            new DocAttr(null, "incTime", "Float", "1f", "set increasing time //second"),
            new DocAttr(null, "decTime", "Float", "0f", "set decreasing time //second"),
            new DocAttr(null, "decTime", "Float", "0f", "set decreasing time //second"),
            new DocAttr(null, "mode", "{inc,dec,incDec}", "inc", "increasing, decreasing, increasing + decreasing"),
            new DocAttr(null, "enabled", "Boolean", "true", "start animating after scene is created"),
            new DocAttr(null, "loopCount", "Integer,loop", "loop", "no of loops"),
            new DocAttr(null, "ramp", "Float", "0f", "set increasing and decreasing ramp time //second"),
            new DocAttr(null, "incRamp", "Float", "0f", "set increasing ramp time //second"),
            new DocAttr(null, "decRamp", "Float", "0f", "set decreasing ramp time //second"),
            new DocAttr(null, "atZeroOne", "Float", "0f", "set at zero and one duration //second"),
            new DocAttr(null, "atZero", "Float", "0f", "set at zero duration //second"),
            new DocAttr(null, "atOne", "Float", "0f", "set at one duration //second"),
        };
    }

    @Override
    public final TimerObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TimerObj obj = new TimerObj(value, attributes);

        if(value != null){
            obj.setTime( value instanceof Float ? (Float)value : Float.parseFloat(value.toString()) );
        }

        Object o = attributes.get(SceneBuilder.time);
        if(o != null) obj.setTime( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

        o = attributes.get(SceneBuilder.incTime);
        if(o != null) obj.setIncTime( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

        o = attributes.get(SceneBuilder.decTime);
        if(o != null) obj.setDecTime( o instanceof Float ? (Float)o : Float.parseFloat(o.toString()) );

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

    @Override
    public String docDescription() {
        return "<div>This object provides common methods for converting a time value into an alpha value (a value in the range 0 to 1).</div>\n" +
                "<div style=\"text-align: center;\"><img src=\"img/alpha.png\"/></div>\n" +
                "<div style=\"text-align: right;\">see: <a href=\"http://download.java.net/media/java3d/javadoc/1.5.2/javax/media/j3d/Alpha.html\">java doc</a></div>";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "time(time:45f);",
            "time(time:0.2f, mode:incDec, ramp:0.02f);",
            "animTime = _time(time:0.2f, mode:incDec, ramp:0.02f);\n" +
            "anim = animTime.control();\n" +
            "// ...\n" +
            "anim.pause();\n" +
            "anim.loop(3);",
        };
    }

    @Override
    public String docValue() {
        return "as: |time|";
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return null;
    }

    @Override
    public DocAction[] docActions() {
        return null;
    }

    @Override
    public DocControl[] docControl() {
        return new DocControl[]{
            new DocControl("pause()","stop animation"),
            new DocControl("pause([Float])","pause animation at the specified time"),
            new DocControl("[Boolean] paused()","return true if animation is paused"),
            new DocControl("start()","start animation"),
            new DocControl("start([Float])","start animation at the specified time"),
            new DocControl("loop([Integer])","set loop count"),
            new DocControl("loop()","loops indefinitely"),
            new DocControl("setMode({<u>inc</u>,dec,incDec})","increasing, decreasing, increasing + decreasing"),
            new DocControl("atZeroOne([Float])","set at zero and one duration //second"),
            new DocControl("atZero([Float])","set at zero duration //second"),
            new DocControl("atOne([Float])","set at one duration //second"),
            new DocControl("time([Float])","set increasing and decreasing time //second"),
            new DocControl("incTime([Float])","set increasing time //second"),
            new DocControl("decTime([Float])","set decreasing time //second"),
            new DocControl("ramp([Float])","set increasing and decreasing ramp time //second"),
            new DocControl("incRamp([Float])","set increasing ramp time //second"),
            new DocControl("decRamp([Float])","set decreasing ramp time //second"),
            new DocControl("[Float] value()","return actual value <0,1>"),
            new DocControl("[Float] value([Float])","return value <0,1> based on the specified time"),
        };
    }
}
