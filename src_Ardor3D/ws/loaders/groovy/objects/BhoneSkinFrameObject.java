package ws.loaders.groovy.objects;

import ws.loaders.tools.SoundLoader;
import ws.loaders.tools.joint.BhoneFrameLoader;
import ws.loaders.tools.joint.FrameType;
import ws.loaders.tools.joint.LoadedBhoneFrame;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public final class BhoneSkinFrameObject {
    private final BhoneFrameLoader bhoneFrameLoader;
    private final SoundLoader soundLoader;

    public BhoneSkinFrameObject(BhoneFrameLoader bhoneFrameLoader, SoundLoader soundLoader) {
        this.bhoneFrameLoader = bhoneFrameLoader;
        this.soundLoader = soundLoader;
    }

    private boolean shot = false;
    public final void setShot(boolean shot) {
        this.shot = shot;
    }
    public final boolean isShot() {
        return shot;
    }

    private String name = null;

    public final void setName(String name) {
        this.name = name;
    }
    public final String getName() {
        return name;
    }

    private String clip = null;
    public final void setClip(String clip) {
        this.clip = clip;
    }
    
    public final Clip getClip() {
        if(clip != null){
            try {
                return this.soundLoader.getClip(clip);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String bhoneFrame = null;

    public final void setBhoneFrame(String bhoneFrame) {
        this.bhoneFrame = bhoneFrame;
    }

    final String getFile(){
        return bhoneFrame;
    }

    public final LoadedBhoneFrame getBhoneFrame(float scale) {
        try {
            return this.bhoneFrameLoader.load(bhoneFrame, scale);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public final void setKeyCode(Character keyCode) {
        this.keyCode = KeyStroke.getKeyStroke(keyCode).getKeyCode();
    }
}
