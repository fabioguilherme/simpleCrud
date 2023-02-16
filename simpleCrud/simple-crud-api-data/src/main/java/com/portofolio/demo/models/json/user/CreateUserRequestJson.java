package com.portofolio.demo.models.json.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequestJson {

    @NotBlank(message = "Name can not be null or empty")
    private String name;

    @NotBlank(message = "Email can not be null or empty")
    @Email(message = "Email must be valid")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
