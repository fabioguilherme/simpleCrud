package com.portofolio.demo.domain.item;

import com.portofolio.demo.shared.errors.BusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ItemTest {

    @Test
    void canBuild() {
        // Given
        String name = "fakename";

        // When
        Item item = Item.withName(name);

        // Then
        assertThat(item).isNotNull();

        assertThat(item.getName()).isEqualTo(name);
    }

    @Test
    void shouldThrowAnBusinessExceptionIfNameFieldIsNull() {
        // Given
        String name = null;

        // When
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> Item.withName(name)).withMessage("Field name can not be null");

    }
}