package ws.loaders.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ws.ResourceHandle;
import ws.camera.CharacterCamera;
import ws.loaders.Loader;
import ws.loaders.groovy.objects.SceneObject;

import javax.media.j3d.BranchGroup;
import java.io.IOException;

public final class GroovyLoader implements Loader{

    public GroovyLoader(String fileName) throws IOException{
        Binding binding = new Binding();
        binding.setVariable("BUILDER", new SceneBuilder());

        GroovyShell groovyShell = new GroovyShell(binding);
        groovyShell.evaluate(ResourceHandle.getString(fileName));
        SceneObject scene = (SceneObject)binding.getVariable("scene");
        scene.build();
        characterCamera = scene.getCharacterCamera();
        rootNode = scene.getRootNode();
        activeNode = scene.getActiveNode();
        this.backFlipDistance = scene.getBackFlipDistance();
        this.detectDistance = scene.getDetectDistance();
    }

    private final CharacterCamera characterCamera;
    @Override
    public final CharacterCamera getCharacterCamera() {
        return characterCamera;
    }

    private final BranchGroup activeNode;
    @Override
	public final BranchGroup getActiveNode() {
		return activeNode;
	}

	private final BranchGroup rootNode;
    @Override
	public final BranchGroup getRootNode() {
		return rootNode;
	}

    private final float backFlipDistance;

    @Override
    public final float getBackFlipDistance() {
        return backFlipDistance;
    }

    private final float detectDistance;
    @Override
    public final float getDetectDistance() {
        return detectDistance;
    }
}
