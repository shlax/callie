package ts.doc;

public class DocAction {

    private String name;
    private String desc;

    public DocAction(String name) {
        this.name = name;
        desc = null;
    }

    public DocAction(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    // <li><b>[Float] onTime</b></li>

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<li><b>");
        sb.append(name);
        sb.append("</b>");

        if(desc != null) sb.append(" "+desc);

        sb.append("</li>");
        return  sb.toString();
    }
}
