package com.portofolio.demo.aplication.stock.model;

public class StockDto {

    private final Long id;
    private final int quantity;
    private final String itemName;
    private final String uri;

    private StockDto(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.itemName = builder.itemName;
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

    public String getUri() {
        return uri;
    }


    public static final class Builder {
        private Long id;
        private int quantity;
        private String itemName;
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

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public StockDto build() {
            return new StockDto(this);
        }
    }
}
