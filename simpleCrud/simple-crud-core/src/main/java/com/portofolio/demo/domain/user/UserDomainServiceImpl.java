package com.portofolio.demo.domain.user;

public class UserDomainServiceImpl implements UserDomainService {
    @Override
    public User createUser(String name, String email) {
        return User.Builder.with().name(name).email(email).build();
    }

    @Override
    public void updateEmail(User user, String email) {
        user.updateEmail(email);
    }
}