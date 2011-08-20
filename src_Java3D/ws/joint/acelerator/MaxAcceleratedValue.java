package ws.joint.acelerator;

public class MaxAcceleratedValue extends AcceleratedValue{

    private final float max;

    public MaxAcceleratedValue(float max, float t1) {
        super(t1);
        this.max = max;
    }

    @Override
    public final float getValue(float t) {
        float tmp = super.getValue(t);
        // System.out.println("max "+lmin+" "+lmax);
        if(tmp > max){
            if(this.isActive){
                isActive = false;

			    lastValue = max; 	// reset value
			    v0 = 0; 			// reset speed

			// 0 - ziaden pohyb
			    a = 0;
			    v = 0;
            }
            return max;
        }
        /* if(tmp > max){
            if(this.isActive){
                this.isActive = false;
                this.a = 0f;
                this.v = 0f;
            }
            return tmp;
        }else */return tmp;
    }
}

