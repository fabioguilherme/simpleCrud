package com.portofolio.demo.models.json.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CreateItemRequestJson implements Serializable {


    @NotNull(message = "Name must not be null")
    @Size(min = 1, message = "Name of the item can not be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
