package ws.loaders.groovy.elements;

import groovy.util.FactoryBuilderSupport;
import ts.doc.*;
import ws.loaders.groovy.FactoryElement;
import ws.loaders.groovy.SceneBuilder;
import ws.loaders.groovy.objects.AppearanceObject;
import ws.loaders.groovy.objects.ModelObject;
import ws.loaders.tools.GeometryLoader;

import javax.media.j3d.Appearance;
import java.util.Map;

public final class ModelElement extends NodeElement implements Doc{
    private final GeometryLoader geometryLoader; // = new GeometryLoader();

    public ModelElement(GeometryLoader geometryLoader) {
        this.geometryLoader = geometryLoader;
    }

    @Override
    public String docDescription() {
        return "geometry for scene object";
    }

    @Override
    public String[] docExamples() {
        return new String[]{
            "model(file:\"data/butterfly/body.mod\",\n" +
            "      appearance:_appearance(texture:\"data/butterfly/butterfly.png\",\n" +
            "                             mipMap:true ));",
            "model(file:\"data/house/tools.mto\", appearance:_appearance(){\n" +
            "    texture(texture:\"data/house/tools.png\",mipMap:true);\n" +
            "    texture(texture:\"data/house/toolsLight.png\",mipMap:true);\n" +
            "});"
        };
    }

    @Override
    public String docValue() {
        return "as: |file|";
    }

    @Override
    public DocSubNode[] docSubNodes() {
        return new DocSubNode[]{
            new DocSubNode(null, "appearance", "[0..1]", "material / texture")
        };
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
    public DocAttr[] docAtributes() {
        return new DocAttr[]{
            new DocAttr("*", "file", "String", "path to (*.mod)/(*.mto) for single/multiple uv geometry"),
            new DocAttr(null, "appearance", "appearance", "material/texture"),
            new DocAttr(null, "stripifier", "Boolean", "true", null),
            new DocAttr(null, "orient", "Boolean", "false", null),
        };
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

        // stripifier
        st = attributes.get(SceneBuilder.stripifier);
        if(st != null)m.setStrip( st instanceof Boolean ? (Boolean)st : new Boolean(st.toString()) );

        // orient
        st = attributes.get(SceneBuilder.orient);
        if(st != null)m.setOrient( st instanceof Boolean ? (Boolean)st : new Boolean(st.toString()) );

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
        }else System.err.println(parent+" -> "+child);
    }
}
