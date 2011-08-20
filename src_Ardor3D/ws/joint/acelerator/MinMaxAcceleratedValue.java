package ws.joint.acelerator;

public class MinMaxAcceleratedValue extends AcceleratedValue{

    private final float min;
    private final float max;

    public MinMaxAcceleratedValue(float min, float max, float t1) {
        super(t1);
        this.min = min;
        this.max = max;
    }

    @Override
    public final float getValue(float t) {
        float tmp = super.getValue(t);
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
        }else if(tmp > max){
            if(this.isActive){
                isActive = false;

			    lastValue = min; 	// reset value
			    v0 = 0; 			// reset speed

			// 0 - ziaden pohyb
			    a = 0;
			    v = 0;
            }
            return max;
        }else return tmp;

    }
}
