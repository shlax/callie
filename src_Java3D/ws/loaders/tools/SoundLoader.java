package ws.loaders.tools;

import ws.ResourceHandle;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;

public final class SoundLoader {
    public SoundLoader(){}

    private static HashMap<String, Clip> clips = new HashMap<String, Clip>();

    public final Clip getClip( String sf ) throws UnsupportedAudioFileException, IOException, LineUnavailableException { // String ss){
        Clip ret = clips.get(sf);
        if(ret != null) return ret;

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( ResourceHandle.getInputStream(sf) );
        Clip c = AudioSystem.getClip();
        c.open(audioInputStream);

        clips.put(sf, c);
        return c;
    }

    public static void cleanUp(){
        for(Clip c : clips.values()){
            c.stop();
            c.close();
        }
        clips.clear();
    }

}
