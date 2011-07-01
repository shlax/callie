package ws.loaders.groovy.elements;

import groovy.lang.Closure;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.MapObject;
import ws.loaders.tools.map.MapLoader;
import ws.map.Type;

import java.util.Arrays;
import java.util.Map;

public final class MapElement extends AbstractFactory {
    private final MapLoader mapLoader; // = new MapLoader();

    public MapElement(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }

    @Override
    public final MapObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        MapObject o = new MapObject(mapLoader);

        o.setFile( value.toString() );

        Object typ = attributes.get(SceneBuilder.mapType);
        if(typ != null) o.setType( typ instanceof Type ? (Type) typ : Type.valueOf(typ.toString()) );

        typ = attributes.get(SceneBuilder.active);
        if(typ != null) o.setActive(Boolean.parseBoolean(typ.toString()));

        typ = attributes.get(SceneBuilder.onEnter);
        if(typ instanceof Closure)o.setOnEnter((Closure)typ);

        typ = attributes.get(SceneBuilder.onExit);
        if(typ instanceof Closure)o.setOnExit((Closure)typ);

        attributes.clear();
        return o;
    }
    
}
