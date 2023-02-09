package com.portofolio.demo.domain.user;

import com.portofolio.demo.shared.errors.BusinessException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class UserTest {
    @Test
    public void canBuild() {
        // Given
        String name = "fake-name";
        String email = "fake@email.com";


        // When
        User user = User.Builder.with().name(name).email(email).build();

        // Then
        assertThat(user).isNotNull();

        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void shouldThrowBusinessExceptionWhenUsingEmptyName() {
        // Given
        String name = "";
        String email = "fake@email.com";


        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> User.Builder.with().name(name).email(email).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Name can not be null or empty");
    }

    @Test
    public void shouldThrowBusinessExceptionWhenUsingNullName() {
        // Given
        String name = null;
        String email = "fake@email.com";


        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> User.Builder.with().name(name).email(email).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Name can not be null or empty");
    }

    @Test
    public void shouldThrowBusinessExceptionWhenUsingEmptyEmail() {
        // Given
        String name = "fake-name";
        String email = "";


        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> User.Builder.with().name(name).email(email).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Email can not be null or empty");
    }

    @Test
    public void shouldThrowBusinessExceptionWhenUsingNullEmail() {
        // Given
        String name = "fake-name";
        String email = null;


        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> User.Builder.with().name(name).email(email).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Email can not be null or empty");
    }

    @Test
    public void shouldThrowBusinessExceptionWhenUsingInvalidEmail() {
        // Given
        String name = "fake-name";
        String email = "fakeasdemailcom";


        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> User.Builder.with().name(name).email(email).build();
        assertThatExceptionOfType(BusinessException.class).isThrownBy(throwingCallable).withMessage("Email is not valid");
    }

    @Test
    void canUpdateEmail() throws Exception {
        // Given
        User user = UserFixture.getUser();
        String email = "new-email";

        // When
        user.updateEmail(email);

        // Then
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    void shouldThrowAnExceptionWhenEmailIsNull() throws Exception {
        // Given
        User user = UserFixture.getUser();
        String email = null;

        // When
        // Then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> user.updateEmail(email)).withMessage("Email can not be empty or null");

    }

    @Test
    void shouldThrowAnExceptionWhenEmailIsEmpty() throws Exception {
        // Given
        User user = UserFixture.getUser();
        String email = "";

        // When
        // Then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> user.updateEmail(email)).withMessage("Email can not be empty or null");
    }
}