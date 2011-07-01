package ws.loaders.groovy.objects;

import ws.loaders.tools.SoundLoader;
import ws.loaders.tools.shot.LoadedShot;

import javax.media.j3d.Appearance;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public final class ShotObject{

    private final SoundLoader soundLoader;

    public ShotObject(SoundLoader soundLoader) {
        this.soundLoader = soundLoader;
    }

    private AppearanceObject appearance = null;

    private Float cadence = null;
    private Float firePower = null;
    private Float minDistance = null;
    private Float maxDistance = null;
    private Float shotDuration = null;
    private Float shotRadius = null;

   // private Clip clip = null;

    public final void setAppearance(AppearanceObject appearance) {
        this.appearance = appearance;
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

    public final void setCadence(Float cadence) {
        this.cadence = cadence;
    }

    public final void setFirePower(Float firePower) {
        this.firePower = firePower;
    }

    public final void setMinDistance(Float minDistance) {
        this.minDistance = minDistance;
    }

    public final void setMaxDistance(Float maxDistance) {
        this.maxDistance = maxDistance;
    }

    public final void setShotDuration(Float shotDuration) {
        this.shotDuration = shotDuration;
    }

    public final void setShotRadius(Float shotRadius) {
        this.shotRadius = shotRadius;
    }

    public final LoadedShot getLoadedShot(){
        float kadencia = this.cadence == null ? 150f : this.cadence;
        float hit = this.firePower == null ? 1f : this.firePower;
        float minDistance = this.minDistance == null ? 10f : this.minDistance;
        float maxDistance = this.maxDistance == null ? 50f : this.maxDistance;
        float timeVisible = this.shotDuration == null ? 50f : this.shotDuration;
        float radius = this.shotRadius == null ? 0.001f : this.shotRadius;

        Appearance ap = appearance.getAppearance();
        LoadedShot ls = new LoadedShot(kadencia, hit, minDistance, maxDistance, timeVisible, radius, ap);

        if(clip != null) ls.setShot(getClip());
        return ls;
    }
}
