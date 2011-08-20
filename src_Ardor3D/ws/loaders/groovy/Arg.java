package ws.loaders.groovy;

import groovy.lang.Closure;
import groovy.util.Factory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

public final class Arg implements Factory {

    private final Factory f;

    public Arg(Factory f) {
        this.f = f;
    }

    @Override
    public final boolean isLeaf() {
        return f.isLeaf();
    }

    @Override
    public final boolean isHandlesNodeChildren() {
        return f.isHandlesNodeChildren();
    }

    @Override
    public final void onFactoryRegistration(FactoryBuilderSupport factoryBuilderSupport, String s, String s1) {
        f.onFactoryRegistration(factoryBuilderSupport, s, s1);
    }

    @Override
    public final Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        Object ret = f.newInstance(factoryBuilderSupport, o, o1, map);
        if(ret instanceof FactoryElement) ((FactoryElement)ret).use();
        return ret;
    }

    @Override
    public final boolean onHandleNodeAttributes(FactoryBuilderSupport factoryBuilderSupport, Object o, Map map) {
        return f.onHandleNodeAttributes(factoryBuilderSupport, o, map);
    }

    @Override
    public final boolean onNodeChildren(FactoryBuilderSupport factoryBuilderSupport, Object o, Closure closure) {
        return f.onNodeChildren(factoryBuilderSupport, o, closure);
    }

    @Override
    public final void onNodeCompleted(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1) {
        f.onNodeCompleted(factoryBuilderSupport, o, o1);
    }

    @Override
    public final void setParent(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1) {
        f.setParent(factoryBuilderSupport, o, o1);
    }

    @Override
    public final void setChild(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1) {
        f.setChild(factoryBuilderSupport, o, o1);
    }
}
