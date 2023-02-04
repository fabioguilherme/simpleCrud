package com.portofolio.demo.domain.user;

public interface UserDomainService {

    User createUser(String name, String email);

    void updateEmail(User user, String email);
}
