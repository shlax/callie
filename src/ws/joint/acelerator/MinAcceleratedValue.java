package ws.joint.acelerator;

public final class MinAcceleratedValue extends AcceleratedValue{

    private final float min;

    public MinAcceleratedValue(float min, float t1) {
        super(t1);
        this.min = min;
    }

    @Override
    public final float getValue(float t) {
        float tmp = super.getValue(t);
        // System.out.println("min "+lmin+" "+lmax);
        if(tmp < min){
            if(this.isActive){
                isActive = false;

			    lastValue = min; 	// reset value
			    v0 = 0; 			// reset speed

			// 0 - ziaden pohyb
			    a = 0;
			    v = 0;
            }
            return min;
        }
        /*if(tmp < min){
            if(this.isActive){
                this.isActive = false;
                this.a = 0f;
                this.v = 0f;
            }
            return tmp;
        }else */return tmp;
    }
}
