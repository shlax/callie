grammar KeyFrame;

@members {
  float scale = 1f;

  public void setScale(float x){
    scale = x;
  }
}

mainNode returns [org.callie.ringing.KeyFrameLoader.MainNodeKeys result]:
    { java.util.List<org.callie.ringing.KeyFrameLoader.NodeKeys> l = new java.util.ArrayList<>(); }
    '[' n=NAME ':' o=vector ':' a=vector (i=node {l.add($i.r);} )* ']'
    { $result = org.callie.ringing.KeyFrameLoader.MainNodeKeys.create($n.text, $o.r.mul(scale), $a.r, l);  };

node returns [org.callie.ringing.KeyFrameLoader.NodeKeys r]:
    { java.util.List<org.callie.ringing.KeyFrameLoader.NodeKeys> l = new java.util.ArrayList<>(); }
    '[' n=NAME ':' v=vector (i=node {l.add($i.r);} )* ']'
    { $r = org.callie.ringing.KeyFrameLoader.NodeKeys.create($n.text, $v.r, l);  };

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
