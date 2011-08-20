package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
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
        a = 0f;
    }

    public Color(float r, float g, float b, float a, Object value, Map attributes) {
        super(value, attributes);

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public final Color3f getColor3f(){
        return new Color3f(r, g, b);
    }

    public final Color4f getColor4f(){
        return new Color4f(a, r, b, a);
    }
}
