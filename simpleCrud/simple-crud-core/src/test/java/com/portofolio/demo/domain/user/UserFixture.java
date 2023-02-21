package com.portofolio.demo.domain.user;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class UserFixture {

    public static User getUser() throws NoSuchFieldException {
        String name = "fakeName";
        String email = "fakename@email.com";

        User user = User.Builder.with().name(name).email(email).build();

        Field field = user.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, user, 1L);

        return user;
    }

    public static User getUseWithEmailAndName(String email, String name) throws NoSuchFieldException {
        User user = User.Builder.with().name(name).email(email).build();

        Field field = user.getClass().getDeclaredField("id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, user, 1L);

        return user;
    }

    public static User getNewUseWithEmailAndName(String email, String name) {
        return User.Builder.with().name(name).email(email).build();
    }
}
