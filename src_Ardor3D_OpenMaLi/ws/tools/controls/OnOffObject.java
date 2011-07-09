package ws.tools.controls;

import ws.loaders.groovy.OnOfInterface;

public final class OnOffObject {

    private OnOfInterface col;

    public OnOffObject() {}

    public OnOffObject(OnOfInterface col) {
        this.col = col;
    }

    public final void setCon(OnOfInterface col) {
        this.col = col;
    }

    public final void on(){
        col.setActive(true);
    }

    public final void off(){
        col.setActive(false);
    }
}
