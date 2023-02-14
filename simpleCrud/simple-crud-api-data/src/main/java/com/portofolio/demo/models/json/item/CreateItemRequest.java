package com.portofolio.demo.models.json.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateItemRequest {
    @NotNull
    @Size(min = 1, message = "Name of the item can not be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
