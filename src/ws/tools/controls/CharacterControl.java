package ws.tools.controls;

import ws.camera.UserCamera;

public final class CharacterControl {

    public final void hit(float power){
        UserCamera.TARGET.hit( (1.5f * power) / 100f );
    }

    public final void kill(){
        UserCamera.wrongMove();
    }

    public final void heal(float power){
        UserCamera.heal( (1.5f * power) / 100f );
    }

    public final Location location(){
        return new Location(UserCamera.getUserY25Triangle());
    }
}
