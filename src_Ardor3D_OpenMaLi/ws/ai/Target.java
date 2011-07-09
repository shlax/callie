package ws.ai;

import org.openmali.vecmath2.Tuple3f;

public interface Target {

//    public void getTargetPoint(Tuple3d t);
    public void getTargetPoint(Tuple3f t);
    
    public float getTargetRadius();
        
	public void hit(float power);

    public boolean isTargetActive();
}
