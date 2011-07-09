package ws.loaders.groovy.elements;

import groovy.lang.Closure;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.MapObject;
import ws.loaders.tools.map.MapLoader;
import ws.map.Type;

import java.util.Map;

public final class MapElement extends AbstractFactory implements Doc{
    private final MapLoader mapLoader; // = new MapLoader();

    public MapElement(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }

    @Override
    public String docDescription() {
        return "map structre";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "map(\"data/house/run.str\", mapType:terain);",
            "map(\"data/house/run.str\", mapType:jump, onEnter:{\n" +
            "    println(\"enter ...\");\n" +
            "});",
            "mapa = map(\"data/house/run.str\", mapType:jump, onExit:{\n" +
            "    mapa.off(); // player cannot enter this area again\n" +
            "}).control();",
        };
    }

    @Override
    public String docValue() {
        return "as: |file|";
    }

    @Override
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr("*", "file", "String", "path to (*.str) file"),
            new DocAttr(null, "mapType", "{terain,jump}", "terain","terain: basic terain / jump: player must jump"),
            new DocAttr(null, "active", "Boolean", "true", null),
        };
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return null;
    }

    @Override
    public DocAction[] docActions() {
        return new DocAction[]{
            new DocAction("onEnter"),
            new DocAction("onExit"),
        };
    }

    @Override
    public DocControl[] docControl() {
        return new DocControl[]{
            new DocControl("on()", "activate map"),
            new DocControl("off()", "deactivate map"),
        };
    }

    @Override
    public final MapObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        MapObject o = new MapObject(mapLoader);

        if( value != null ){
            o.setFile( value.toString() );
        }

        Object typ = attributes.get(SceneBuilder.mapType);
        if(typ != null) o.setType( typ instanceof Type ? (Type) typ : Type.valueOf(typ.toString()) );

        typ = attributes.get(SceneBuilder.active);
        if(typ != null) o.setActive(Boolean.parseBoolean(typ.toString()));

        typ = attributes.get(SceneBuilder.file);
        if(typ != null) o.setFile( typ.toString() );

        typ = attributes.get(SceneBuilder.onEnter);
        if(typ instanceof Closure)o.setOnEnter((Closure)typ);

        typ = attributes.get(SceneBuilder.onExit);
        if(typ instanceof Closure)o.setOnExit((Closure)typ);

        attributes.clear();
        return o;
    }
    
}
