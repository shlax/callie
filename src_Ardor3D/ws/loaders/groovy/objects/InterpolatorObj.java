package ws.loaders.groovy.objects;

import com.ardor3d.math.Quaternion;
import com.ardor3d.math.Vector3;
import com.ardor3d.math.type.ReadOnlyQuaternion;
import com.ardor3d.math.type.ReadOnlyVector3;
import com.ardor3d.scenegraph.controller.ComplexSpatialController;
import com.ardor3d.scenegraph.controller.SpatialController;
import com.ardor3d.scenegraph.controller.interpolation.CurveInterpolationController;
import com.ardor3d.scenegraph.controller.interpolation.LinearVector3InterpolationController;
import com.ardor3d.scenegraph.controller.interpolation.QuaternionInterpolationController;
import com.ardor3d.scenegraph.controller.interpolation.Vector3InterpolationController;
import com.ardor3d.spline.CatmullRomSpline;
import com.ardor3d.spline.Curve;
import ws.loaders.groovy.FactoryElement;

import java.util.ArrayList;
import java.util.Map;

public final class InterpolatorObj extends FactoryElement {

    public InterpolatorObj(Object value, Map<?, ?> attributes) {
        super(value, attributes);
    }

    boolean curve = true;
    public final void setCurve(boolean curve) {
        this.curve = curve;
    }

    private ArrayList<ReadOnlyVector3> points = null; // = new ArrayList<ReadOnlyVector3>();
    public final void addPoint(Vector3 v){
        if(points == null) points = new ArrayList<ReadOnlyVector3>();
        points.add(v);
    }

    private float speed = 1f;
    public final void setSpeed(float speed) {
        this.speed = speed;
    }

    private boolean active = true;
    public final void setActive(boolean active) {
        this.active = active;
    }

    private ComplexSpatialController.RepeatType repeatType = ComplexSpatialController.RepeatType.CYCLE;
    public final void setRepeatType(ComplexSpatialController.RepeatType repeatType) {
        this.repeatType = repeatType;
    }

    private ArrayList<ReadOnlyQuaternion> inQuats = null;
    public final void addRot(Quaternion v){
        if(inQuats == null) inQuats = new ArrayList<ReadOnlyQuaternion>();
        inQuats.add(v);
    }

    public final SpatialController<?>[] getControler(){
        ArrayList<SpatialController<?>> cont = new ArrayList<SpatialController<?>>();

        if(points != null){

            if(curve){
                final CurveInterpolationController sp = new CurveInterpolationController(){
					private static final long serialVersionUID = 1L;

					@Override
                    protected void clampIndex() {
                        if(getRepeatType() == RepeatType.CYCLE){
                            if( getIndex() > getMaximumIndex())setIndex( getMinimumIndex() );
                            else if( getIndex() < getMinimumIndex())setIndex( getMaximumIndex() );
                        }
                        super.clampIndex();
                    }
                };
                sp.setActive(active);

                sp.setUpdateField(Vector3InterpolationController.UpdateField.LOCAL_TRANSLATION);

                Curve curve = new Curve(points, new CatmullRomSpline());

                sp.setRepeatType(repeatType);
                sp.setCurve(curve);


                sp.setSpeed(speed);
                sp.generateArcLengths(10, true);
                sp.setConstantSpeed(true);

                cont.add(sp);

            }else{
                final LinearVector3InterpolationController sp = new LinearVector3InterpolationController(){
					private static final long serialVersionUID = 1L;

						@Override
                        protected void clampIndex() {
                            if(getRepeatType() == RepeatType.CYCLE){
                                if( getIndex() > getMaximumIndex())setIndex( getMinimumIndex() );
                                else if( getIndex() < getMinimumIndex())setIndex( getMaximumIndex() );
                            }
                            super.clampIndex();
                        }
                };
                sp.setActive(active);

                sp.setUpdateField(Vector3InterpolationController.UpdateField.LOCAL_TRANSLATION);

                sp.setControls(points);
                sp.setSpeed(speed);
                //sp.setActive(false);

                sp.setRepeatType(repeatType);

                sp.setConstantSpeed(true);

                cont.add(sp);
            }

            points = null;
        }

        if(inQuats != null){

            final QuaternionInterpolationController controller = new QuaternionInterpolationController(){
				private static final long serialVersionUID = 1L;

				@Override
                protected void clampIndex() {
                    if(getRepeatType() == RepeatType.CYCLE){
                        if( getIndex() > getMaximumIndex())setIndex( getMinimumIndex() );
                        else if( getIndex() < getMinimumIndex())setIndex( getMaximumIndex() );
                    }
                    super.clampIndex();
                }
            };
            controller.setActive(active);

            controller.setControls(inQuats);
            controller.setRepeatType(repeatType);
            controller.setSpeed(speed);
           // System.out.println(controller.getSpeed());

            cont.add(controller);

            inQuats = null;
        }




        return cont.toArray(new SpatialController[cont.size()]);
    }
}
