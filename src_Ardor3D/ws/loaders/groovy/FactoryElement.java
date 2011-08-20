package ws.loaders.groovy;

import java.util.Map;

public class FactoryElement {

    /*private String name = null;
    public final String getName() {
        return name;
    }
    public final void setName(String name) {
        this.name = name;
    } */

    private boolean used = false;
    public final boolean isUsed() {
        return used;
    }
    public final void use() {
        this.used = true;
    }

    public FactoryElement(Object value, Map attributes){
        /*if(value != null && value instanceof String) this.setName((String)value);
        else{
            if(attributes != null){
                Object val = attributes.get(SceneBuilder.name);
                if(val != null) this.setName(val.toString());
            }
        }*/        
    }
}
