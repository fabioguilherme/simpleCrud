package com.portofolio.demo.models.json.item;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    void shouldBeInvalidIfNameIsNull() {
        // Given
        String name = null;

        // When
        Item item = new Item();
        item.setName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);

        Set<ConstraintViolation<Item>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(item);


        assertThat(violations.size()).isEqualTo(1);
        List<ConstraintViolation<Item>> list = violations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("must not be null");
    }

    @Test
    void canBuild() {
        // Given
        String name = "fakename";

        // When
        Item item = new Item();
        item.setName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);

        Set<ConstraintViolation<Item>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(item);


        assertThat(violations.size()).isEqualTo(0);
        assertThat(item.getName()).isEqualTo(name);
    }

    @Test
    void shouldBeInvalidIfNameIsEmpty() {
        // Given
        String name = "";

        // When
        Item item = new Item();
        item.setName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);

        Set<ConstraintViolation<Item>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(item);

        assertThat(vialations.size()).isEqualTo(1);
        List<ConstraintViolation<Item>> list = vialations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("Name of the item can not be empty");
    }
}