package ws.loaders.groovy.objects;

import ws.loaders.groovy.FactoryElement;
import ws.loaders.tools.joint.FrameType;

import java.util.Map;

public final class AnimationFrameObject extends FactoryElement {

    public AnimationFrameObject(Object value, Map attributes) {
        super(value, attributes);
    }

    private FrameType frameType = null;

    public final FrameType getFrameType() {
        return frameType;
    }

    public final void setFrameType(FrameType frameType) {
        this.frameType = frameType;
    }

    private Integer keyCode = null;

    public final Integer getKeyCode() {
        return keyCode;
    }

    public final void setKeyCode(Integer keyCode) {
        this.keyCode = keyCode;
    }

    private String file;

    public final String getFile() {
        return file;
    }

    public final void setFile(String file) {
        this.file = file;
    }


}
