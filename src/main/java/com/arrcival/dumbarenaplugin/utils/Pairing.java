package com.arrcival.dumbarenaplugin.utils;

public class Pairing<X, Y> {
    public final X first;
    public final Y second;
    public Pairing(X x, Y y) {
        this.first = x;
        this.second = y;
    }
}