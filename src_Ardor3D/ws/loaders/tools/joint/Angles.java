package ws.loaders.tools.joint;

import javax.vecmath.Tuple3f;

public final class Angles {
    private final String name;
    private final Tuple3f a;

    public Angles(String name, Tuple3f a) {
        this.name = name;
        this.a = a;
    }

    public final String getName() {
        return name;
    }

    public final Tuple3f getAngle() {
        return a;
    }
}
