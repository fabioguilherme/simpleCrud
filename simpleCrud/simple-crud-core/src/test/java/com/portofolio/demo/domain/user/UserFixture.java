package com.portofolio.demo.domain.user;

public class UserFixture {

    public static User getUser() {
        String name = "fakeName";
        String email = "fakeName@email.com";

        return User.Builder.with().name(name).email(email).build();
    }

    public static User getUseWithEmailAndName(String email, String name) {
        return User.Builder.with().name(name).email(email).build();
    }
}
