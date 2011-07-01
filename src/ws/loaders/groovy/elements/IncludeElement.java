package ws.loaders.groovy.elements;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import ws.ResourceHandle;

import java.io.IOException;
import java.util.Map;

public final class IncludeElement extends AbstractFactory {

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        Binding binding = new Binding(attributes);
        try {
            GroovyShell groovyShell = new GroovyShell(binding);
            groovyShell.evaluate(ResourceHandle.getString(value.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return binding.getVariable("object");
    }
    
}
