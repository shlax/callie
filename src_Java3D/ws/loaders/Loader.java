package ws.loaders;

import ws.camera.CharacterCamera;

import javax.media.j3d.BranchGroup;

public interface Loader {
    public CharacterCamera getCharacterCamera();

    public BranchGroup getActiveNode();
    public BranchGroup getRootNode();

    public float getBackFlipDistance();
    
    public float getDetectDistance();
}
