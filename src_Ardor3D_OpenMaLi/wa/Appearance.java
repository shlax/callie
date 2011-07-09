package wa;

import com.ardor3d.renderer.state.*;
import com.ardor3d.scenegraph.MeshData;
import com.ardor3d.scenegraph.Node;

public class Appearance {

    public Node getRenderState(MeshData meshData){
        return null;
    }

    private CullState cullState = null;
    private MaterialState materialState = null;
    private TextureState textureState = null;
    private BlendState blendState = null;

}
