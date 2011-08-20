package ws.joint;

import javax.vecmath.Matrix4f;

public interface Bhone {

    /**
     * time s intervalu <0,1>
     */    
    public boolean update(Matrix4f tmp, Matrix4f tmpRot, float time, boolean update);
    
	public void setNewValues(int index);
}
