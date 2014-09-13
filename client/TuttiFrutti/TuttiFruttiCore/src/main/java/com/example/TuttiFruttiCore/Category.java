package com.example.TuttiFruttiCore;

import java.io.Serializable;

/**
 * Created by Sebastian on 24/08/2014.
 */
public class Category implements Serializable{
    int id;
    String name;
    boolean isStared;
    boolean isReported;
    boolean isFixed;
    boolean isSelected;

    public Category(){}
    public Category(int id, String name, boolean isStared, boolean isReported, boolean isFixed ){
        this.name=name;
        this.isStared=isStared;
        this.isReported=isReported;
        this.isFixed=isFixed;
        this.id=id;
        this.isSelected=false;
    }


    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }


    public void setId(int id) { this.id=id; }

    public int getId() { return this.id; }

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

    @Override
    public boolean equals(Object o) {
        return ((Category)o).getId() == (getId());
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
