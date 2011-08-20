package ws.loaders.groovy.objects;

import com.ardor3d.math.ColorRGBA;
import ws.loaders.groovy.FactoryElement;

import java.util.Map;

public final class Color extends FactoryElement {

    private final float r;
    private final float g;
    private final float b;

    private final float a;

    public Color(float r, float g, float b, Object value, Map attributes) {
        super(value, attributes);

        this.r = r;
        this.g = g;
        this.b = b;
        a = 1f;
    }

    public Color(float r, float g, float b, float a, Object value, Map attributes) {
        super(value, attributes);

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public final ColorRGBA getColor3f(){
        return new ColorRGBA(r, g, b, a);
    }
}
