package ws.joint;

import org.openmali.vecmath2.Matrix4f;

public interface Bhone {

    /**
     * time s intervalu <0,1>
     */    
    public void update(Matrix4f tmp, Matrix4f tmpRot, float time);
    
	public void setNewValues(int index);
}
