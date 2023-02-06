package com.portofolio.demo.domain.item;

public class ItemFixture {
    public static Item getItem() {
        return Item.withName("FAKE");
    }

    public static Item getItemWithName(String name) {
        return Item.withName(name);
    }
}
