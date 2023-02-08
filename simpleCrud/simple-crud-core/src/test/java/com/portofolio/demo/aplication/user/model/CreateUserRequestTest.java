package com.portofolio.demo.aplication.user.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CreateUserRequestTest {

    @Test
    public void canCreate() {
        // Given
        String name = "fakeName";
        String email = "fake@email.com";

        // When
        CreateUserRequest dto = CreateUserRequest.Builder.with().name(name).email(email).build();

        // Then

        assertThat(dto).isNotNull();

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getEmail()).isEqualTo(email);
    }
}