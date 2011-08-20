package ws.tools.controls;

import ws.ai.Ai;
import ws.ai.agents.AiWolker;

public final class AgentControl {
    private AiWolker wolker;
    public final void _setAiWolker(AiWolker w){
        this.wolker = w;
    }

    public final void control(){
        wolker.setAiContoled(false);
    }

    public final void release(){
        wolker.setAiContoled(true);
    }

    public final void detected(){
        Ai.userDetected();
    }

    public final void navigate(Location location){
        wolker.navigate(location);
    }

    public final Location location(){
        return new Location(wolker.getActualTriangle());
    }
}
