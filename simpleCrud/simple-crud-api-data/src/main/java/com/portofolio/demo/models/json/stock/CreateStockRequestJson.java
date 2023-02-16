package com.portofolio.demo.models.json.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateStockRequestJson {

    @NotNull(message = "ItemId must not be null")
    private Long itemId;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Minimum value for the quantity is 1.")
    private Integer quantity;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
