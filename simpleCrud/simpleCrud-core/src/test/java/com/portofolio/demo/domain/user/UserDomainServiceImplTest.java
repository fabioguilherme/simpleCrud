package com.portofolio.demo.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDomainServiceImplTest {

    private UserDomainServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new UserDomainServiceImpl();
    }

    @Test
    void canCreateAnewItem() {
        // Given
        String name = "fake-name";
        String email = "fake-name@emai.com";

        // When
        User newUser = this.service.createUser(name, email);

        // Then
        assertThat(newUser).isNotNull();

        assertThat(newUser.getName()).isEqualTo(name);
        assertThat(newUser.getEmail()).isEqualTo(email);
    }

}