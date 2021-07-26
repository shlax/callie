package org.callie.math;

public enum  Axis {
    X, Y, Z;

    public static Axis apply(String s) {
        if("x".equalsIgnoreCase(s)) return X;
        if("y".equalsIgnoreCase(s)) return Y;
        if("z".equalsIgnoreCase(s)) return Z;
        throw new IllegalArgumentException(s);
    }
}
