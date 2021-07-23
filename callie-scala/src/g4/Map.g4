grammar Map;

map returns [ org.callie.map.MapData result ]:
     {
        java.util.ArrayList<org.callie.math.Vector3> lf = new java.util.ArrayList<>();
        java.util.ArrayList<org.callie.map.Triangle> li = new java.util.ArrayList<>();
     }
    '[' f=float3 { lf.add($f.r); } (',' fi=float3 { lf.add($fi.r); } )* ']'
    '{' i=int3 { li.add($i.r); } (',' ii=int3 { li.add($ii.r); } )* '}'
    { $result = new org.callie.map.MapData(lf.toArray(new org.callie.math.Vector3[0]), li.toArray(new org.callie.map.Triangle[0])); };

int3 returns [ org.callie.map.Triangle r ]:
    '(' i=intNum ',' j=intNum ',' k=intNum ')'
    { $r = new org.callie.map.Triangle($i.r, $j.r, $k.r); };

float3 returns [ org.callie.math.Vector3 r ]:
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

intNum returns [ int r ]:
    s=('+'|'-')? n=DIGITS
    { $r = Integer.parseInt($s.text == null ? $n.text : $s.text + $n.text ); } ;

DIGITS : ('0'..'9')+;

WS : (' '|'\t'|'\n'|'\r')+ -> skip ;