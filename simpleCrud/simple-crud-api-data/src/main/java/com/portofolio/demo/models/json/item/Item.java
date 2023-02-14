package com.portofolio.demo.models.json.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Item {

    private Long id;
    @NotNull
    @Size(min = 1, message = "name of the item cannot be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
