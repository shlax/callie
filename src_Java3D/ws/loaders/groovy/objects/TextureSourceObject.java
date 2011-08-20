package ws.loaders.groovy.objects;

public final class TextureSourceObject {

    private int index;
    public final void setIndex(int index) { this.index = index; }
    public final int getIndex() { return index; }

    private Integer alphaSource = null;
    public final void setAlphaSource(Integer alphaSource) { this.alphaSource = alphaSource; }
    public Integer getAlphaSource() { return alphaSource; }

    private Integer alphaFunction = null;
    public void setAlphaFunction(Integer alphaFunction) { this.alphaFunction = alphaFunction; }
    public Integer getAlphaFunction() { return alphaFunction; }

    private Integer rgbSource = null;
    public void setRgbSource(Integer rgbSource) { this.rgbSource = rgbSource; }
    public Integer getRgbSource() { return rgbSource; }

    private Integer rgbFunction = null;
    public void setRgbFunction(Integer rgbFunction) { this.rgbFunction = rgbFunction; }
    public Integer getRgbFunction() { return rgbFunction; }


}
