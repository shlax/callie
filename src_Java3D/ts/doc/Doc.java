package ts.doc;

public interface Doc {

    public String docDescription();

    public String[] docExamples();

    public String docValue();

    public DocAttr[] docAtributes();
    public DocSubNode[] docSubNodes();
    public DocAction[] docActions();
    public DocControl[] docControl();

}
