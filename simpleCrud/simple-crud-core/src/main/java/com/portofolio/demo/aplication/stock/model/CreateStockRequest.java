package com.portofolio.demo.aplication.stock.model;

public class CreateStockRequest {

    private final Long itemId;
    private final int quantity;

    private CreateStockRequest(Builder builder) {
        this.itemId = builder.itemId;
        this.quantity = builder.quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }


    public static final class Builder {
        private Long itemId;
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

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public CreateStockRequest build() {
            return new CreateStockRequest(this);
        }
    }

}
