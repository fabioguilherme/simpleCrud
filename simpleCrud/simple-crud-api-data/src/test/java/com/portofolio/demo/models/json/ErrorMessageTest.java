package com.portofolio.demo.models.json;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorMessageTest {

    @Test
    void canBuildErrorMessage() {
        // Given
        String message = "fake error message";

        // When
        ErrorMessage error = ErrorMessage.createErrorWithMessage(message);

        // Then
        assertThat(error).isNotNull();

        assertThat(error.getMessage()).isEqualTo(message);
    }
}