package ws.joint.acelerator;

public class AcceleratedValue {

    private float start = 0;

	protected float a = 0;
	protected float v0 = 0;
	protected float v = 0;

	private float at = 0;

	private final float t1; // = 0.3f;

    public AcceleratedValue(float t1){
        this.t1 = t1;
    }

	public final void setTarget(float value){
        if(lastTime < t1) v0 += a*lastTime;
		else v0 = v;

	//        koncim kde som zacinal                &   nemam ziadnu pociatocnu rychlost
		if( Math.abs(value - lastValue) < 0.000001f && v0 < 0.000001 ){
			isActive = false;

			lastValue = value; 	// reset value
			v0 = 0; 			// reset speed

			// 0 - ziaden pohyb
			a = 0;
			v = 0;
		}else{
			isActive = true;
			start = lastValue;

			float t1Pow = (t1*t1)/2;
			float v0t1 = v0*t1;

			float t2 = 1 - t1;

            a = ((value - start) - v0*t2 - v0t1)/( t1Pow + t1*t2 );
            v = v0 + a*t1;

			at = v0t1 + a*t1Pow + start;
		}
	}

	protected float lastValue = 0;
	private float lastTime = 0;

	protected boolean isActive = false;

	public float getValue(float t){
        if(!isActive) return this.lastValue;
        
		this.lastTime = t;
		if(t < t1) lastValue = start + v0*t + (a*t*t)/2;
        else lastValue = at + (t-t1)*v;

		return lastValue;
	}
}
/*
	// test
	public static void main(String[] args) {
		javax.swing.JFrame f = new javax.swing.JFrame(){
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(java.awt.Graphics g) {
				super.paint(g);
				g.setColor(java.awt.Color.BLACK);
				g.drawLine(0, 100, 1500, 100);
				g.drawLine(0, 300, 1500, 300);
				g.drawLine(0, 500, 1500, 500);

				AcceleratedValue tmp = new AcceleratedValue(0.3f);

				float duration = 200;
				float value = 200;

				int cut1 = 180;
				int cut2 = 100;

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
				//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)i, 300-(int)tmp.getValue(i/duration), 1, 1);

				//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*2), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*3), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*4), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*5), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*6), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*7), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)i, 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*2), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*3), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*4), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*5), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*6), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*7), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)i, 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*2), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*3), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*4), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*5), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*6), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}
				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+duration*7), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)i, 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(-value);
				for(long i = 0; i <= cut2; i++ ){
				//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(cut1+i), 300-(int)tmp.getValue(i/duration), 1, 1);

				//	g.fillRect((int)(cut1+i), 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+cut1+cut2), 300-(int)tmp.getValue(i/duration), 1, 1);

				//	g.fillRect((int)(i+cut1+cut2), 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(-value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+cut1+cut2+duration), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)(i+cut1+cut2), 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(value);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+cut1+cut2+duration*2), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)(i+cut1+cut2), 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(0);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+cut1+cut2+duration*3), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)(i+cut1+cut2), 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(0);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+cut1+cut2+duration*4), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)(i+cut1+cut2), 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}

				tmp.setTarget(0);
				for(long i = 0; i <= duration; i++ ){
					//	System.out.println(i+" "+tmp.getValue(i));
					g.setColor(java.awt.Color.RED);
					g.fillRect((int)(i+cut1+cut2+duration*5), 300-(int)tmp.getValue(i/duration), 1, 1);

					//	g.fillRect((int)(i+cut1+cut2), 300-(int)(tmp.getWalue(i)*multi), 1, 1);
				}


			}
		};
		f.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
		f.setSize(1500,600);
		f.setVisible(true);
	}
}

*/