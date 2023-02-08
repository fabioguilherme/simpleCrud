package com.portofolio.demo.aplication.user.model;

public class UserDto {

    private final Long id;
    private final String name;
    private final String email;
    private final String uri;

    private UserDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.uri = builder.uri;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUri() {
        return uri;
    }


    public static final class Builder {
        private Long id;
        private String name;
        private String email;
        private String uri;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
