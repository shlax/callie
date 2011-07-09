package ws.loaders.groovy.elements;

import groovy.lang.Closure;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.BehaviorObj;

import java.util.Map;

public final class BehaviorEl extends NodeElement implements Doc {

    @Override
    public String docDescription() {
        return "timer";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "onTime( onTime:{\n" +
            "    println(\"after 10 sec\");\n" +
            "    return 10f;\n" +
            "});"
        };
    }

    @Override
    public String docValue() {
        return "as: |onTime|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return null;
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return null;
    }

    @Override
    public DocAction[] docActions() {
        return new DocAction[]{
            new DocAction("[Float] onTime([Float])")
        };
    }

    @Override
    public DocControl[] docControl() {
        return null;
    }

    @Override
    public final BehaviorObj newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        BehaviorObj o = new BehaviorObj(value, attributes);

        /*if(value instanceof SharedGroup){
            o.setSharedGroup((SharedGroup)value);
        }else */
        if(value instanceof Closure){
            o.setClosure((Closure)value);
        }

        if (attributes != null){
            Object tmp = attributes.get(SceneBuilder.onTime);
            if(tmp != null) o.setClosure((Closure)tmp);

            attributes.clear();
        }

        return o;
    }
}
