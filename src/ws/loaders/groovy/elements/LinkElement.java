package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.LinkObject;
import ws.loaders.groovy.objects.SharedGroupObject;

import java.util.Map;

public final class LinkElement extends NodeElement implements Doc{

    @Override
    public String docDescription() {
        return null;
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "wing = _shared(){ model(file:\"data/butterfly/wing.mod\") }\n" +
            "\n" +
            "rotation(wingTime, transform:wingAxis, min:1f, max:10f ){\n" +
            "   link(wing); };\n" +
            "rotation(wingTime, transform:wingAxis, min:-1f, max:-10f ){\n" +
            "   link(wing); };"
        };
    }

    @Override
    public String docValue() {
        return "as: |link|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr("*", "link", "shared")
        };
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
        return null;
    }

    @Override
    public final LinkObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        LinkObject o = new LinkObject(value, attributes);

        /*if(value instanceof SharedGroup){
            o.setSharedGroup((SharedGroup)value);
        }else */
        if(value instanceof SharedGroupObject){
            o.setSharedGroup((SharedGroupObject)value);
        }

        if(attributes != null){
            Object tmp = attributes.get(SceneBuilder.link);
            if(tmp != null) o.setSharedGroup((SharedGroupObject)tmp);

            attributes.clear();
        }

        return o;
    }
    
}
