package com.owl.api.example.model;

public class Manufacturer {
    private String name;

    public Manufacturer() {
    }

    public Manufacturer(String name) {
    	super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}