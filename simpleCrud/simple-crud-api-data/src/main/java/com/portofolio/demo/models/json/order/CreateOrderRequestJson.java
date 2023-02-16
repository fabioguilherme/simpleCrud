package com.portofolio.demo.models.json.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateOrderRequestJson {

    @NotNull(message = "ItemId must not be null")
    private Long itemId;
    @NotNull(message = "UserId must not be null")
    private Long userId;
    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Minimum value for the quantity is 1.")
    private Integer quantity;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
