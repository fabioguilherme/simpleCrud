package com.portofolio.demo.aplication.user.model;

public class CreateUserRequest {

    private final String name;
    private final String email;

    private CreateUserRequest(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public static final class Builder {
        private String name;
        private String email;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public CreateUserRequest build() {
            return new CreateUserRequest(this);
        }
    }
}
