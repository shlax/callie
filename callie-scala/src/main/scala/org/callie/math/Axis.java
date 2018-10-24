package org.callie.math;

public enum  Axis {
    X, Y, Z ;

    public static Axis apply(String s) {
        if("x".equals(s)) return X;
        if("y".equals(s)) return Y;
        if("z".equals(s)) return Z;
        throw new RuntimeException(s);
    }
}
