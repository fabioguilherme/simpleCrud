package com.portofolio.demo.aplication.order.model;

public class CreateOrderRequest {

    private final Long itemId;
    private final Long userId;
    private final int quantity;

    public CreateOrderRequest(Builder builder) {
        this.itemId = builder.itemId;
        this.userId = builder.userId;
        this.quantity = builder.quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public static final class Builder {
        private Long itemId;
        private Long userId;
        private int quantity;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder itemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public CreateOrderRequest build() {
            return new CreateOrderRequest(this);
        }
    }
}
