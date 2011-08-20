package wa;

import com.ardor3d.renderer.state.*;
import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.MeshData;
import com.ardor3d.scenegraph.Node;

public final class Appearance {

    public final Mesh getRenderState(MeshData meshData){
        Mesh m = new Mesh();
        m.setMeshData(meshData);

        if(lightState != null) m.setRenderState(lightState);
        if(materialState != null) m.setRenderState(materialState);
        if(textureState != null) m.setRenderState(textureState);
        if(blendState != null) m.setRenderState(blendState);
        if(cullState != null) m.setRenderState(cullState);

        return m;
    }

    public final void getRenderState(Node m){
        if(lightState != null) m.setRenderState(lightState);
        if(materialState != null) m.setRenderState(materialState);
        if(textureState != null) m.setRenderState(textureState);
        if(blendState != null) m.setRenderState(blendState);
        if(cullState != null) m.setRenderState(cullState);
    }

    public LightState lightState = null;
    public MaterialState materialState = null;
    public TextureState textureState = null;
    public BlendState blendState = null;
    public CullState cullState = null;

}
