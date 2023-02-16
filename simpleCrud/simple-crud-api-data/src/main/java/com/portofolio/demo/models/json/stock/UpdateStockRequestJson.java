package com.portofolio.demo.models.json.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateStockRequestJson {

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Minimum value for the quantity is 1.")
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
