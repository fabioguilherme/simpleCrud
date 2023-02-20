package com.portofolio.demo.models.json.item;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateItemRequestJsonTest {

    @Test
    void shouldBeInvalidIfNameIsNull() {
        // Given
        String name = null;

        // When
        CreateItemRequestJson item = new CreateItemRequestJson();
        item.setName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);

        Set<ConstraintViolation<CreateItemRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(item);


        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateItemRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Name must not be null");
    }

    @Test
    void canBuild() {
        // Given
        String name = "fakename";

        // When
        CreateItemRequestJson item = new CreateItemRequestJson();
        item.setName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);

        Set<ConstraintViolation<CreateItemRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(item);


        assertThat(violations.size()).isEqualTo(0);
        assertThat(item.getName()).isEqualTo(name);
    }

    @Test
    void shouldBeInvalidIfNameIsEmpty() {
        // Given
        String name = "";

        // When
        CreateItemRequestJson item = new CreateItemRequestJson();
        item.setName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);

        Set<ConstraintViolation<CreateItemRequestJson>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(item);

        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateItemRequestJson>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Name of the item can not be empty");
    }
}