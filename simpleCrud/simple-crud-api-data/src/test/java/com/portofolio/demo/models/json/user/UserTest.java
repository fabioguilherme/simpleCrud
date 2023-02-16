package com.portofolio.demo.models.json.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    void canBuild() {
        // Given
        Long id = 1L;
        String name = "fakeName";
        String email = "fake@email.com";
        String uri = "http:/localhostfake:8080/api/user/1";

        // When
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setUri(uri);

        // Then
        assertThat(user).isNotNull();

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getUri()).isEqualTo(uri);
    }
}