package com.portofolio.demo.aplication.user.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UserDtoTest {

    @Test
    public void canCreate() {
        // Given
        Long id = 1L;
        String name = "fakeName";
        String email = "fake@email.com";
        String uri = "http:/localhostfake:8080/api/user/1";

        // When
        UserDto dto = UserDto.Builder.with().id(id).name(name).email(email).uri(uri).build();

        // Then
        assertThat(dto).isNotNull();

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getUri()).isEqualTo(uri);
    }
}