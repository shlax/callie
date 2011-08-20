package ts.doc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class DocGenerator {

    private final static File dstDir = new File("c:\\Users\\root\\IdeaProjects\\sceneBuilderDoc\\doc\\gen");

    public void create(HashMap<String, ?> setIn){
        ArrayList<String> names = new ArrayList<String>(setIn.keySet());
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return  o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });

        StringBuilder menu = new StringBuilder();

        LinkedList<String> missing = new LinkedList<String>();

        for(String s : names){
            menu.append("<li><a href=\""+s+".php\">"+s+"</a></li>\n");
            File out = new File(dstDir, s+".php");

            try {
                PrintStream ps = new PrintStream(out, "UTF-8");
                ps.println("<?php include(\"_header.php\"); ?>\n" +
                            "<h2>"+s+"</h2>\n");

                Object val = setIn.get(s);
                if(val instanceof Doc){
                    Doc d = (Doc)val;
                    {
                        String tmp = d.docDescription();
                        if(tmp != null) ps.println("<p>"+tmp+"</p>");
                    }{
                        String tmp = d.docValue();
                        if(tmp != null){
                            ps.println("<h4>value</h4>");
                            ps.println("<p>"+tmp+"</p>");
                        }
                    }{
                        DocAttr[] tmp = d.docAtributes();
                        if (tmp != null){
                            ps.println("<h4>atributes</h4>");
                            ps.println("<ul>");
                            for(DocAttr a : tmp) ps.println("\t"+a.toString());
                            ps.println("</ul>");
                        }
                    }{
                        DocSubNode[] tmp = d.docSubNodes();
                        if (tmp != null){
                            ps.println("<h4>nodes</h4>");
                            ps.println("<ul>");
                            for(DocSubNode a : tmp) ps.println("\t"+a.toString());
                            ps.println("</ul>");
                        }
                    }{
                        DocAction[] tmp = d.docActions();
                        if (tmp != null){
                            ps.println("<h4>actions</h4>");
                            ps.println("<ul>");
                            for(DocAction a : tmp) ps.println("\t"+a.toString());
                            ps.println("</ul>");
                        }
                    }{
                        DocControl[] tmp = d.docControl();
                        if (tmp != null){
                            ps.println("<h4>control</h4>");
                            ps.println("<ul>");
                            for(DocControl a : tmp) ps.println("\t"+a.toString());
                            ps.println("</ul>");
                        }
                    }{
                        String examples[] = d.docExamples();
                        if (examples != null){
                            ps.println("<h3>Examples</h3>");
                            for(String e : examples){
                                ps.println("<pre class=\"brush: groovy\">");
                                ps.println(e);
                                ps.println("</pre>");
                            }
                        }
                    }
                }else missing.add(s);

                ps.println("<?php include(\"_tail.php\"); ?>");
                ps.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println();
        System.out.println(menu);
        System.out.println();

        for(String s : missing) System.out.println(s);
    }

}
