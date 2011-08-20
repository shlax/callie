package ws.loaders.groovy;

import com.ardor3d.scenegraph.Node;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ws.ResourceHandle;
import ws.camera.CharacterCamera;
import ws.loaders.Loader;
import ws.loaders.groovy.objects.SceneObject;

import java.io.IOException;

//import javax.media.j3d.BranchGroup;

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
        //this.backFlipDistance = scene.getBackFlipDistance();
        //this.detectDistance = scene.getDetectDistance();
        scene.clean();
    }

    private final CharacterCamera characterCamera;
    @Override
    public final CharacterCamera getCharacterCamera() {
        return characterCamera;
    }

    private final Node activeNode;
    @Override
	public final Node getActiveNode() {
		return activeNode;
	}

	private final Node rootNode;
    @Override
	public final Node getRootNode() {
		return rootNode;
	}

    /* private final float backFlipDistance;

    @Override
    public final float getBackFlipDistance() {
        return backFlipDistance;
    }

    private final float detectDistance;
    @Override
    public final float getDetectDistance() {
        return detectDistance;
    } */
}
