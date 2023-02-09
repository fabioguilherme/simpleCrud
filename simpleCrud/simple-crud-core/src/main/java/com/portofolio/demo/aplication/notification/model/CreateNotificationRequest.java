package com.portofolio.demo.aplication.notification.model;

public class CreateNotificationRequest {

    private final Long userId;
    private final String message;

    private CreateNotificationRequest(Builder builder) {
        this.userId = builder.userId;
        this.message = builder.message;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }


    public static final class Builder {
        private Long userId;
        private String message;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public CreateNotificationRequest build() {
            return new CreateNotificationRequest(this);
        }
    }
}
