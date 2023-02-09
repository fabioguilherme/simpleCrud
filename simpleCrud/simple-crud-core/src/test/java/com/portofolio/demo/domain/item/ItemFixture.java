package com.portofolio.demo.domain.item;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ItemFixture {
    public static Item getItem() throws NoSuchFieldException {
        Item item = Item.withName("FAKE");

        Field field = item.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, item, 1L);


        return item;
    }

    public static Item getItemWithName(String name) throws NoSuchFieldException {

        Item item = Item.withName(name);

        Field field = item.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, item, 1L);


        return item;
    }

    public static Item getNewItemWithName(String name) {
        return Item.withName(name);
    }
}
