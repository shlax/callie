grammar Joint;

@members {
  float scale = 1f;

  public void setScale(float x){
    scale = x;
  }
}

node returns [ org.callie.ringing.Node result ]:
    '[' ( ( l=linear { $result = $l.r; } ) | ( n=normal { $result = $n.r; } ) ) ']';

linMap returns [ org.callie.ringing.LinMap r ]:
    '|' f=NAME
    { $r = new org.callie.ringing.LinMap($f.text); };

linear returns [ org.callie.ringing.LinNode r ]:
    { java.util.List<org.callie.ringing.LinMap> l = new java.util.ArrayList<>(); }
    n=NAME '|' v=vector ( (l=linMap { l.add($l.r); } )* ) ':' gm=groupMap
    { $r = org.callie.ringing.LinNode.create( $n.text, $v.r,l, $gm.r ); };

normal returns [ org.callie.ringing.IntNode r ]:
   { java.util.List<org.callie.ringing.Node> l = new java.util.ArrayList<>(); }
   n=NAME ':' v=vector ':' gm=groupMap ( ( nd=node { l.add( $nd.result ); } )* )
   { $r = org.callie.ringing.IntNode.create($n.text, $v.r.mul(scale), $gm.r, l); };

groupMap returns [ java.util.Map<String, java.util.Set<Integer>> r ]:
    { $r = new java.util.HashMap<>(); }
    '{' g=group {
        $r.computeIfAbsent( $g.r.nm(), x -> new java.util.HashSet<Integer>() ).addAll( $g.r.ind() );
    } ( ',' gn=group {
        $r.computeIfAbsent( $gn.r.nm(), x -> new java.util.HashSet<Integer>() ).addAll( $gn.r.ind() );
    } )* '}' ;

group returns [ org.callie.ringing.Group r ]:
    { java.util.Set<Integer> li = new java.util.HashSet<>(); }
    n=NAME ':' '(' i=intNum { li.add($i.r); } ( ( ',' in=intNum { li.add($in.r); } )* ) ')'
    { $r = new org.callie.ringing.Group( $n.text,  li ); };

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

intNum returns [ int r ]:
    s=('+'|'-')? n=DIGITS
    { $r = Integer.parseInt($s.text == null ? $n.text : $s.text + $n.text ); } ;

NAME : ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9' | '_')*;

DIGITS : ('0'..'9')+;

WS : (' '|'\t'|'\n'|'\r')+ -> skip ;