package ws.joint;

import javax.media.j3d.Transform3D;

public interface Bhone {

    /**
     * time s intervalu <0,1>
     */    
    public void update(Transform3D tmp, Transform3D tmpRot, float time);
    
	public void setNewValues(int index);
}
