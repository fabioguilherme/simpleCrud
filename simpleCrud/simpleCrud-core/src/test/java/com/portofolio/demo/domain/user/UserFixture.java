package com.portofolio.demo.domain.user;

public class UserFixture {

    public static User.Builder getUser() {
        String name = "fakeName";
        String email = "fakeName@email.com";

        return User.Builder.with().email(name).email(email);
    }
}
