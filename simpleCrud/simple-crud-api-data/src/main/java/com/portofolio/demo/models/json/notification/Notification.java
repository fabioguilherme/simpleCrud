package com.portofolio.demo.models.json.notification;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notification implements Serializable {

    private Long id;
    private String message;
    private String userName;
    private String userEmail;
    private String uri;
    private LocalDateTime creationDate;


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


    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
