package ws.loaders.groovy.objects;

import com.ardor3d.light.DirectionalLight;
import com.ardor3d.light.Light;
import com.ardor3d.light.PointLight;
import com.ardor3d.light.SpotLight;
import com.ardor3d.math.ColorRGBA;
import com.ardor3d.math.Vector3;
import ws.loaders.groovy.FactoryElement;

import java.util.Map;

public final class LightObj extends FactoryElement {

    public LightObj(Object value, Map attributes) {
        super(value, attributes);
    }

    private ColorRGBA ambient = new ColorRGBA(0f, 0f, 0f, 1f);
    private ColorRGBA specular = new ColorRGBA(0f, 0f, 0f, 1f);
    private ColorRGBA diffuse = new ColorRGBA(0.6f, 0.6f, 0.6f, 1f);

    private boolean attenuate = false;

    private float constant = 1;
    private float linear = 0;
    private float quadratic = 0;

    private Vector3 loacation = null;
    private Vector3 direction = null;

    private float angle = 0f;
    private float exponent = 0f;

    public final Light getLight(){
        Light l;
        if(loacation != null && direction != null){
            SpotLight ll = new SpotLight();
            ll.setDirection(direction);
            ll.setLocation(loacation);
            ll.setAngle(this.angle);
            ll.setExponent(this.exponent);
            l = ll;
        }else if(loacation != null){
            PointLight ll = new PointLight();
            ll.setLocation(loacation);
            l = ll;
        }else if(direction != null){
            DirectionalLight ll = new DirectionalLight();
            ll.setDirection(direction);

            l = ll;
        }else{
            DirectionalLight ll = new DirectionalLight();
            ll.setDirection(new Vector3(-1f, -1f, -1f));

            l = ll;
        }

        l.setAmbient(ambient);
        l.setDiffuse(diffuse);
        l.setSpecular(specular);

        l.setConstant(constant);
        l.setLinear(linear);
        l.setQuadratic(quadratic);
        l.setAttenuate(attenuate);

        return l;
    }

    public final void setAmbient(ColorRGBA ambient) {
        this.ambient = ambient;
    }

    public final void setSpecular(ColorRGBA specular) {
        this.specular = specular;
    }

    public final void setDiffuse(ColorRGBA diffuse) {
        this.diffuse = diffuse;
    }

    public final void setAttenuate(boolean attenuate) {
        this.attenuate = attenuate;
    }

    public final void setConstant(float constant) {
        this.constant = constant;
    }

    public final void setLinear(float linear) {
        this.linear = linear;
    }

    public final void setQuadratic(float quadratic) {
        this.quadratic = quadratic;
    }

    public final void setLoacation(Vector3 loacation) {
        this.loacation = loacation;
    }

    public final void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    public final void setAngle(float angle) {
        this.angle = angle;
    }

    public final void setExponent(float exponent) {
        this.exponent = exponent;
    }

}
