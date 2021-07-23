grammar Mod;

mod returns [ org.callie.model.Model result ] :
    { java.util.List<org.callie.model.Face> lf = new java.util.ArrayList<>(); }
    p=points '{' f=face3 { lf.addAll($f.r); } ( fi=face3 { lf.addAll($fi.r); } )* '}'
    { $result = new org.callie.model.Model( $p.r.toArray(new org.callie.model.Point3[0] ), lf.toArray( new org.callie.model.Face[0] ) ); };

face3 returns [ java.util.List<org.callie.model.Face> r ] :
    { $r = new java.util.ArrayList<>(3); }
    '[' a=face ',' b=face ',' c=face ']'
    { $r.add($a.r); $r.add($b.r); $r.add($c.r); };

face returns [ org.callie.model.Face r ] :
    i=intNum ':' n=vector3 ':' u=uvs
    { $r = new org.callie.model.Face($i.r, $n.r, $u.r.toArray( new org.callie.model.Point2[0] ) ); };

uvs returns [ java.util.List<org.callie.model.Point2> r ] :
    { $r = new java.util.ArrayList<>(); }
    '[' p=vector2 { $r.add($p.r); } (',' pi=vector2 { $r.add($pi.r); } )* ']' ;

points returns [ java.util.List<org.callie.model.Point3> r ] :
    { $r = new java.util.ArrayList<>(); }
    '[' p=vector3 { $r.add($p.r); } (',' pi=vector3 { $r.add($pi.r); } )* ']' ;

vector3  returns [ org.callie.model.Point3 r ]:
    '(' a=floatNum ',' b=floatNum ',' c=floatNum ')'
    { $r = new org.callie.model.Point3($a.r, $b.r, $c.r); };

vector2  returns [ org.callie.model.Point2 r ]:
    '(' a=floatNum ',' b=floatNum ')'
    { $r = new org.callie.model.Point2($a.r, $b.r); };

floatNum returns [ float r ]:
    s=('+'|'-')? n=DIGITS ('.' m=DIGITS)? (('e'|'E') e=('+'|'-')? p=DIGITS )?
    {
        StringBuilder sb = new StringBuilder();
        if($s.text != null) sb.append($s.text);
        sb.append($n.text);
        if($m.text != null){
            sb.append('.').append($m.text);
        }
        if($p.text != null){
            sb.append('E');
            if($e.text != null) sb.append($e.text);
            sb.append($p.text);
        }
        $r = Float.parseFloat(sb.toString());
    };

intNum returns [ int r ]:
    s=('+'|'-')? n=DIGITS
    { $r = Integer.parseInt($s.text == null ? $n.text : $s.text + $n.text ); } ;

DIGITS : ('0'..'9')+;

WS : (' '|'\t'|'\n'|'\r')+ -> skip ;
