package ts.doc;

public class DocAttr {

    private String required;

    private String name;
    private String type;
    private String def;

    private String desc;

    public DocAttr(String required, String name, String type, String def, String desc) {
        this.required = required;
        this.name = name;
        this.type = type;
        this.def = def;
        this.desc = desc;
    }

    public DocAttr(String required, String name, String type, String desc) {
        this.required = required;
        this.name = name;
        this.type = type;
        this.def = null;
        this.desc = desc;
    }

    public DocAttr(String required, String name, String type) {
        this.required = required;
        this.name = name;
        this.type = type;
        this.def = null;
        this.desc = null;
    }

    // <li><b>active</b> <i>[Boolean:true/false]</i> default true / if is used <i>control</i> and ( <i>onEnter</i> or <i>onExit</i> ) false</li>
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<li>");
        if(required != null) sb.append(required+" ");
        sb.append("<b>"+name+"</b> <i>[");
        // Boolean:true/false]</i> default true / if is used <i>control</i> and ( <i>onEnter</i> or <i>onExit</i> ) false</li>";
        if( type.charAt(0) == '{' ){
            sb.append(type);
            if(def != null) sb.append(":"+def);
        }else if( Character.isUpperCase(type.charAt(0))){
            sb.append(type);
            if(def != null) sb.append(":"+def);
        }else{
            String[] el = type.split("\\|");
            for(int i = 0; i < el.length; i++){
                if(i != 0)sb.append("|");
                String q = el[i];
                int ind = q.indexOf('[');
                if(ind != -1)q = q.substring(0, ind);
                sb.append("<a href=\""+q+".php\">"+el[i]+"</a>");
            }
        }
        sb.append("]</i>");
        if(desc != null)sb.append(" "+desc);
        sb.append("</li>");

        return sb.toString();
    }
}
