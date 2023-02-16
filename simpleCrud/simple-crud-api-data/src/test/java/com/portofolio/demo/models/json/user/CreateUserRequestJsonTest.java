package com.portofolio.demo.models.json.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserRequestJsonTest {

    @Test
    void canBuild() {
        // Given
        String name = "fakeName";
        String email = "fake@email.com";

        // When
        CreateUserRequestJson request = new CreateUserRequestJson();
        request.setName(name);
        request.setEmail(email);

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getName()).isEqualTo(name);
        assertThat(request.getEmail()).isEqualTo(email);
    }

    @Test
    void shouldInvalidIfNameIsNull() {

        // Given
        String name = null;
        String email = "fake@email.com";

        // When
        CreateUserRequestJson request = new CreateUserRequestJson();
        request.setName(name);
        request.setEmail(email);

        // Then

        Set<ConstraintViolation<CreateUserRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);


        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateUserRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Name can not be null or empty");
    }

    @Test
    void shouldInvalidIfNameIsEmpty() {

        // Given
        String name = "";
        String email = "fake@email.com";

        // When
        CreateUserRequestJson request = new CreateUserRequestJson();
        request.setName(name);
        request.setEmail(email);

        // Then

        Set<ConstraintViolation<CreateUserRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);


        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateUserRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Name can not be null or empty");
    }

    @Test
    void shouldInvalidIfEmailIsNull() {

        // Given
        String name = "fake-name";
        String email = null;

        // When
        CreateUserRequestJson request = new CreateUserRequestJson();
        request.setName(name);
        request.setEmail(email);

        // Then

        Set<ConstraintViolation<CreateUserRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);


        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateUserRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Email can not be null or empty");
    }

    @Test
    void shouldInvalidIfEmailIsEmpty() {

        // Given
        String name = "fake-name";
        String email = "";

        // When
        CreateUserRequestJson request = new CreateUserRequestJson();
        request.setName(name);
        request.setEmail(email);

        // Then

        Set<ConstraintViolation<CreateUserRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);


        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateUserRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Email can not be null or empty");
    }

    @Test
    void shouldInvalidIfEmailIsNotValid() {

        // Given
        String name = "fake-name";
        String email = "fake@@email,com";

        // When
        CreateUserRequestJson request = new CreateUserRequestJson();
        request.setName(name);
        request.setEmail(email);

        // Then
        Set<ConstraintViolation<CreateUserRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateUserRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Email must be valid");
    }
}