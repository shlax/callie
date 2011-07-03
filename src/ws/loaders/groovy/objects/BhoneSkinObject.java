package ws.loaders.groovy.objects;

import ws.loaders.tools.joint.*;

import javax.media.j3d.Appearance;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class BhoneSkinObject {
    private final BhoneLoader bhoneLoader;
    private final SkinLoader skinLoader;
    
    public BhoneSkinObject(BhoneLoader bhoneLoader, SkinLoader skinLoader) {
        this.bhoneLoader = bhoneLoader;
        this.skinLoader = skinLoader;
    }

    private Appearance appearance = null;
    public final void setAppearance(Appearance appearanceObject) {
        this.appearance = appearanceObject;
    }

    private String bhoneFile = null;
    public final void setBhoneFile(String bhoneFile) {
        this.bhoneFile = bhoneFile;
    }

    private String skinFile = null;
    public final void setSkinFile(String skinFile) {
        this.skinFile = skinFile;
    }

    private Float accStartTime = null;
    public final void setAccStartTime(Float accStartTime) {
        this.accStartTime = accStartTime;
    }

    private ArrayList<BhoneSkinRestriction> bhoneSkinRestrictions = null; // = new ArrayList<BhoneSkinRestriction>();
    public final void addBhoneSkinRestriction(BhoneSkinRestriction r){
        if(bhoneSkinRestrictions == null) bhoneSkinRestrictions = new ArrayList<BhoneSkinRestriction>();
        this.bhoneSkinRestrictions.add(r);
    }

    private HashMap<String, BhoneSkinFrameObject> bhoneSkinFrames = null; // = new HashMap<String, BhoneSkinFrameObject>();
    public final void addBhoneSkinFrame(String s, BhoneSkinFrameObject f){
        if(bhoneSkinFrames == null) bhoneSkinFrames = new HashMap<String, BhoneSkinFrameObject>();
        this.bhoneSkinFrames.put(s == null ? f.getName() : s, f);
    }

    public BhoneSkinFrameObject getBhoneSkinFrameObject(String key){
        return bhoneSkinFrames == null ? null : bhoneSkinFrames.get(key);
    }

    private Float scale = 1f;
    public final void setScale(Float scale) {
        this.scale = scale;
    }

    public final LoadedBhoneSkin getLoadedBhoneSkin(String order[]){
        try {
            //System.out.println(bhoneFile);
            LoadedBhone loadedBhone = this.bhoneLoader.loadBhone(bhoneFile, scale);
            if(bhoneSkinRestrictions != null)for(BhoneSkinRestriction tmp : bhoneSkinRestrictions){
                if(tmp.getAxis() == BhoneSkinRestriction.Axis.X){
                    loadedBhone.setXaccelerator(tmp.getName(), tmp.getAcceleratedValue() );
                }else if(tmp.getAxis() == BhoneSkinRestriction.Axis.Y){
                    loadedBhone.setYaccelerator(tmp.getName(), tmp.getAcceleratedValue() );
                }else if(tmp.getAxis() == BhoneSkinRestriction.Axis.Z){
                    loadedBhone.setZaccelerator(tmp.getName(), tmp.getAcceleratedValue() );
                }
            }
            
            LoadedSkin loadedSkin = skinLoader.loadSkin(skinFile, scale);
            LoadedBhoneSkin loadedBhoneSkin = new LoadedBhoneSkin(loadedBhone, loadedSkin, appearance);

            if(accStartTime != null) loadedBhoneSkin.setOffsetStartTime(accStartTime);

            for(String sTmp : order){
             //   System.out.println(sTmp);
                BhoneSkinFrameObject tmp = this.bhoneSkinFrames.get(sTmp);
                if(tmp == null) System.err.println(sTmp);
                loadedBhoneSkin.addLoadedBhoneFrame(tmp.getBhoneFrame(scale), tmp.getClip());
            }

            //bhoneSkinRestrictions = null;
            //bhoneSkinFrames = null;

            return loadedBhoneSkin;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    
}
