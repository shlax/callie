package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import wa.Appearance;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AppearanceObject;
import ws.loaders.groovy.objects.InterpolatorObj;
import ws.loaders.groovy.objects.ModelObject;
import ws.loaders.tools.GeometryLoader;

import java.util.Map;

public final class ModelElement extends NodeElement {
    private final GeometryLoader geometryLoader; // = new GeometryLoader();

    public ModelElement(GeometryLoader geometryLoader) {
        this.geometryLoader = geometryLoader;
    }


    @Override
    public ModelObject newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        ModelObject m = new ModelObject(value, attributes, geometryLoader);
        this.setSceneObjectType(m, value, attributes);

        if(value != null){
            m.setFile(value.toString());
        }

        // Appearance
        Object a = attributes.get(SceneBuilder.appearance);
        if(a instanceof AppearanceObject ) m.setAppearance( ((AppearanceObject)a).getAppearance() );
        else if(a instanceof Appearance) m.setAppearance( (Appearance)a );
        
        // Geometry
        Object st = attributes.get(SceneBuilder.file);
        m.setFile(st.toString() );

/*        // stripifier
        st = attributes.get(SceneBuilder.stripifier);
        if(st != null)m.setStrip( st instanceof Boolean ? (Boolean)st : new Boolean(st.toString()) );

        // orient
        st = attributes.get(SceneBuilder.orient);
        if(st != null)m.setOrient( st instanceof Boolean ? (Boolean)st : new Boolean(st.toString()) ); */

        // userData 
        st = attributes.get(SceneBuilder.userData);
        if(st != null)m.setUserData(st);

        attributes.clear();
        return m;
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if(child instanceof FactoryElement) if(((FactoryElement)child).isUsed())return;

        if(parent instanceof ModelObject && child instanceof AppearanceObject){
            ModelObject g = (ModelObject)parent;
            AppearanceObject so = (AppearanceObject)child;
            g.setAppearance(so.getAppearance());
        }else if(parent instanceof ModelObject && child instanceof InterpolatorObj){
            ModelObject g = (ModelObject)parent;
            InterpolatorObj so = (InterpolatorObj)child;
            g.setInterpolator(so);
        }else System.err.println(parent+" -> "+child);
    }
}
