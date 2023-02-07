package com.portofolio.demo.domain.user;

import com.mysql.cj.util.StringUtils;
import com.portofolio.demo.shared.errors.BusinessException;
import jakarta.persistence.*;

@Entity
@Table(name = "simple_crud_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    public User(Builder builder) {
        this.email = builder.email;
        this.name = builder.name;

        validate();
    }

    protected User() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    private void validate() {

        if (this.name == null || this.name.isEmpty()) {
            throw new BusinessException("Name can not be null or empty");
        }

        if (this.email == null || this.email.isEmpty()) {
            throw new BusinessException("Email can not be null or empty");
        }

        if (!this.email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new BusinessException("Email is not valid");
        }
    }

    protected void updateEmail(String email) {

        if (StringUtils.isNullOrEmpty(email)) {
            throw new BusinessException("Email can not be empty or null");
        }

        this.email = email;
    }

    protected static final class Builder {
        private String email;
        private String name;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
