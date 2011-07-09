package ts.doc;

public class DocSubNode {

    private String req;

    private String name;
    private String poc;
    private String desc;

    public DocSubNode(String req, String name, String poc, String desc) {
        this.req = req;
        this.name = name;
        this.poc = poc;
        this.desc = desc;
    }

    public DocSubNode(String req, String name, String poc) {
        this.req = req;
        this.name = name;
        this.poc = poc;
        this.desc = null;
    }

    // <li>* <a href="animationTransform.php"><b>animationTransform</b></a> <i>[0..N]</i> add animation at specidied position</li>
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<li>");
        if(req != null) sb.append(req+" ");

        sb.append("<a href=\""+name+".php\"><b>"+name+"</b></a>");
        if(poc != null)sb.append(" <i>"+poc+"</i>");
        if(desc != null)sb.append(" "+desc);

        sb.append("</li>");
        return sb.toString();
    }
}
