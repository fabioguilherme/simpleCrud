package com.portofolio.demo.aplication.notification.model;

import java.time.LocalDateTime;

public class NotificationDto {

    private final Long id;
    private final String message;
    private final String userName;
    private final String userEmail;
    private final String uri;
    private final LocalDateTime creationDate;

    private NotificationDto(Builder builder) {
        this.id = builder.id;
        this.message = builder.message;
        this.userName = builder.userName;
        this.userEmail = builder.userEmail;
        this.uri = builder.uri;
        this.creationDate = builder.creationDate;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUri() {
        return uri;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public static final class Builder {
        private Long id;
        private String message;
        private String userName;
        private String userEmail;
        private String uri;
        private LocalDateTime creationDate;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public NotificationDto build() {
            return new NotificationDto(this);
        }
    }
}
