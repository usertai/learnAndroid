package com.example.he.toolbartest;

/**
 * Javabean
 * Created by he on 2016/12/14.
 */

public class Fruit {

    private String name;
    private int nameId;

    public Fruit(String name, int nameId) {
        this.name = name;
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public int getNameId() {
        return nameId;
    }
}
