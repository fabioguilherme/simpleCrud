package com.portofolio.demo.models.json.notification;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CreateNotificationRequestJson implements Serializable {

    @NotNull
    private Long userId;
    @NotNull
    @Size(min = 1, message = "Message of the notification can not be empty")
    private String message;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
