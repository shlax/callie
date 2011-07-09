package ws.loaders;

import com.ardor3d.scenegraph.Node;
import ws.camera.CharacterCamera;

public interface Loader {
    public CharacterCamera getCharacterCamera();

    public Node getActiveNode();
    public Node getRootNode();

    public float getBackFlipDistance();
    
    public float getDetectDistance();
}
