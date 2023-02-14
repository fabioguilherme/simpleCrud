package com.portofolio.demo.aplication.order.model;

import java.time.LocalDateTime;

public class OrderDto {

    private final Long id;
    private final int quantity;
    private final String itemName;
    private final String userName;
    private final String userEmail;
    private final LocalDateTime creationDate;
    private final String uri;

    public OrderDto(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.itemName = builder.itemName;
        this.userName = builder.userName;
        this.userEmail = builder.userEmail;
        this.creationDate = builder.creationDate;
        this.uri = builder.uri;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getUri() {
        return uri;
    }


    public static final class Builder {
        private Long id;
        private int quantity;
        private String itemName;
        private String userName;
        private String userEmail;
        private LocalDateTime creationDate;
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

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder itemName(String itemName) {
            this.itemName = itemName;
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

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public OrderDto build() {
            return new OrderDto(this);
        }
    }
}
