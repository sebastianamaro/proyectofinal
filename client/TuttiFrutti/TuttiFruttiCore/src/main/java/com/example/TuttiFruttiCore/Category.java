package com.example.TuttiFruttiCore;

/**
 * Created by Sebastian on 24/08/2014.
 */
public class Category {
    String name;
    boolean isStared;
    boolean isReported;
    boolean isFixed;

    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReported(boolean isReported) {
        this.isReported = isReported;
    }

    public void setStared(boolean isStared) {
        this.isStared = isStared;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public boolean isReported() {
        return isReported;
    }

    public boolean isStared() {
        return isStared;
    }

    public String getName() {
        return name;
    }
}
