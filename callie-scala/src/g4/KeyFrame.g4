grammar KeyFrame;



vector returns [ org.callie.math.Vector3 r ]:
    '(' i=floatNum ',' j=floatNum ',' k=floatNum ')'
    { $r = org.callie.math.Vector3.apply($i.r, $j.r, $k.r); };

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

NAME : ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9' | '_')*;

DIGITS : ('0'..'9')+;

WS : (' '|'\t'|'\n'|'\r')+ -> skip ;
