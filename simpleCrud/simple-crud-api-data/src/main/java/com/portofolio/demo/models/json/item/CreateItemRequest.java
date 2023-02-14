package com.portofolio.demo.models.json.item;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateItemRequest {
    @NotNull
    @Size(min = 1, message = "name of the item cannot be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
