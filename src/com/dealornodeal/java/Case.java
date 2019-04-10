package com.dealornodeal.java;

public class Case {
    private int value;
    private boolean selected = false;
    private boolean myCase = false;

    public Case(int val) {
        value = val;
    }

    public int getVal() {
        return value;
    }

    public void selected() {
        selected = true;
    }

    public boolean invalid() {
        return selected;
    }

    public void myCase() {
        myCase = true;
    }

    public boolean isMyCase() {
        return myCase;
    }
}