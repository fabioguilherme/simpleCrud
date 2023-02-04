package com.portofolio.demo.models.json;

public class Item {

    private final String name;

    private Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Item withName(String name) {
        return new Item(name);
    }
}
