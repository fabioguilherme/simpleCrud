package com.portofolio.demo.aplication.item.model;

public class CreateItemRequest {

    private String name;

    protected CreateItemRequest() {
    }

    private CreateItemRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CreateItemRequest withName(String name) {
        return new CreateItemRequest(name);
    }
}
