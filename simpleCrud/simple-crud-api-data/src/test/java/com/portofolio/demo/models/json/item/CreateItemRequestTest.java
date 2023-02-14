package com.portofolio.demo.models.json.item;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateItemRequestTest {

    @Test
    void shouldBeInvalidIfNameIsNull() {
        // Given
        String name = null;

        // When
        CreateItemRequest item = new CreateItemRequest();
        item.setName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);

        Set<ConstraintViolation<CreateItemRequest>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(item);


        assertThat(vialations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateItemRequest>> list = vialations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("must not be null");
    }

    @Test
    void canBuild() {
        // Given
        String name = "fakename";

        // When
        CreateItemRequest item = new CreateItemRequest();
        item.setName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);

        Set<ConstraintViolation<CreateItemRequest>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(item);


        assertThat(vialations.size()).isEqualTo(0);
        assertThat(item.getName()).isEqualTo(name);
    }

    @Test
    void shouldBeInvalidIfNameIsEmpty() {
        // Given
        String name = "";

        // When
        CreateItemRequest item = new CreateItemRequest();
        item.setName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);

        Set<ConstraintViolation<CreateItemRequest>> vialations = Validation.buildDefaultValidatorFactory().getValidator().validate(item);

        assertThat(vialations.size()).isEqualTo(1);
        List<ConstraintViolation<CreateItemRequest>> list = vialations.stream().toList();
        assertThat(list.get(0).getMessage()).isEqualTo("name of the item cannot be empty");
    }
}