package com.portofolio.demo.domain.notification;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.shared.errors.BusinessException;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    protected Notification() {
    }

    private Notification(Builder builder) {
        this.user = builder.user;
        this.message = builder.message;
        this.type = NotificationType.EMAIL; //this will be the default value
        this.creationDate = LocalDateTime.now();

        validate();
    }

    private void validate() {

        if (this.user == null) {
            throw new BusinessException("User can not be null");
        }

        if (this.message == null) {
            throw new BusinessException("Message can not be null");
        }
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    protected static final class Builder {
        private User user;
        private String message;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
