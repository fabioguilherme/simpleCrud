package com.portofolio.demo.models.json.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateUserEmailRequestJsonTest {

    @Test
    void canBuild() {
        // Given
        String email = "fake@email.com";

        // When
        UpdateUserEmailRequestJson request = new UpdateUserEmailRequestJson();
        request.setEmail(email);

        // Then
        assertThat(request).isNotNull();

        assertThat(request.getEmail()).isEqualTo(email);
    }


    @Test
    void shouldInvalidIfEmailIsNull() {

        // Given
        String email = null;

        // When
        UpdateUserEmailRequestJson request = new UpdateUserEmailRequestJson();
        request.setEmail(email);

        // Then

        Set<ConstraintViolation<UpdateUserEmailRequestJson>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);


        assertThat(vialations.size()).isEqualTo(1);
        List<ConstraintViolation<UpdateUserEmailRequestJson>> list = vialations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Email can not be null or empty");
    }

    @Test
    void shouldInvalidIfEmailIsEmpty() {

        // Given
        String email = "";

        // When
        UpdateUserEmailRequestJson request = new UpdateUserEmailRequestJson();
        request.setEmail(email);

        // Then

        Set<ConstraintViolation<UpdateUserEmailRequestJson>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);


        assertThat(vialations.size()).isEqualTo(1);
        List<ConstraintViolation<UpdateUserEmailRequestJson>> list = vialations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Email can not be null or empty");
    }

    @Test
    void shouldInvalidIfEmailIsNotValid() {

        // Given
        String name = "fake-name";
        String email = "fake@@email,com";

        // When
        UpdateUserEmailRequestJson request = new UpdateUserEmailRequestJson();
        request.setEmail(email);

        // Then

        Set<ConstraintViolation<UpdateUserEmailRequestJson>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(request);


        assertThat(vialations.size()).isEqualTo(1);
        List<ConstraintViolation<UpdateUserEmailRequestJson>> list = vialations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Email must be valid");
    }
}